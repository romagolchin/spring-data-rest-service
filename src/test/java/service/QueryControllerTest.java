package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
//@ActiveProfiles("test")
public class QueryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ContactRepository contactRepository;

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

        mockMvc.perform(get("/lastApplication").param("contactId", "" + contact.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("product1"))
                .andExpect(jsonPath("$.applicationId").value(applications.get(1).getId()))
                .andExpect(jsonPath("$.dateCreated").value(applications.get(1).getDateCreated().toString()));
    }

    @Test
    public void nonExistingContact() throws Exception {
        mockMvc.perform(get("/lastApplication").param("contactId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value((Object) null));
    }

    @Test
    public void existingContactWithNoApplications() throws Exception {
        Contact contact = contactRepository.save(new Contact(new ArrayList<>()));
        mockMvc.perform(get("/lastApplication").param("contactId", "" + contact.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dateCreated").value((Object) null))
                .andExpect(jsonPath("$.productName").value((Object) null));

    }
}