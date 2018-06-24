package service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class QueryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ContactRepository contactRepository;

    @After
    public void tearDown() throws Exception {
        contactRepository.deleteAll();
        applicationRepository.deleteAll();
    }

    private static final String LAST_APPLICATION_BY_ID_ENDPOINT = "/lastApplicationByContactId";

    @Test
    public void existingContact() throws Exception {
        List<Application> applications = new ArrayList<>();
        Date yesterday = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1));
        Date today = new Date(System.currentTimeMillis());
        applications.add(new Application(yesterday, "product0"));
        applications.add(new Application(today, "product1"));
        for (int i = 0; i < 2; i++) {
            applicationRepository.save(applications.get(i));
        }
        Contact contact = contactRepository.save(new Contact(applications));

        mockMvc.perform(get(LAST_APPLICATION_BY_ID_ENDPOINT).param("contactId", "" + contact.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.PRODUCT_NAME").value("product1"))
                .andExpect(jsonPath("$.APPLICATION_ID").value(applications.get(1).getId()))
                .andExpect(jsonPath("$.CONTACT_ID").value(contact.getId()))
                .andExpect(jsonPath("$.DT_CREATED").value(applications.get(1).getDateCreated().toString()));
    }

    private void checkNotFoundById(long id) throws Exception {
        mockMvc.perform(get(LAST_APPLICATION_BY_ID_ENDPOINT).param("contactId", "" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.PRODUCT_NAME").value((Object) null))
                .andExpect(jsonPath("$.APPLICATION_ID").value(0))
                .andExpect(jsonPath("$.CONTACT_ID").value(id))
                .andExpect(jsonPath("$.DT_CREATED").value((Object) null));

    }

    @Test
    public void nonExistingContact() throws Exception {
        checkNotFoundById(1);
    }

    @Test
    public void existingContactWithNoApplications() throws Exception {
        Contact contact = contactRepository.save(new Contact(new ArrayList<>()));
        checkNotFoundById(contact.getId());
    }
}