package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ContactRepository contactRepository;

    private static final String LAST_APPLICATION_BY_ID_ENDPOINT = "/contact/%d/lastApplication";

    @Test
    @DirtiesContext
    public void existingContact() throws Exception {
        List<Application> applications = new ArrayList<>();
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime yesterday = today.minusDays(1);
        Contact contact = contactRepository.save(new Contact());
        applications.add(new Application(contact, yesterday, "product0"));
        applications.add(new Application(contact, today, "product1"));
        for (int i = 0; i < 2; i++) {
            applicationRepository.save(applications.get(i));
        }

        mockMvc.perform(get(String.format(LAST_APPLICATION_BY_ID_ENDPOINT, contact.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.PRODUCT_NAME").value("product1"))
                .andExpect(jsonPath("$.APPLICATION_ID").value(applications.get(1).getId()))
                .andExpect(jsonPath("$.CONTACT_ID").value(contact.getId()))
                .andExpect(jsonPath("$.DT_CREATED").value(applications.get(1).getDateCreated().withNano(0).toString()));
    }

    private void checkNotFoundById(long id) throws Exception {
        mockMvc.perform(get(String.format(LAST_APPLICATION_BY_ID_ENDPOINT, id)))
                .andExpect(status().isOk())
                .andExpect(content().string("null"));

    }

    @Test
    public void nonExistingContact() throws Exception {
        checkNotFoundById(1);
    }

    @Test
    @DirtiesContext
    public void existingContactWithNoApplications() throws Exception {
        Contact contact = contactRepository.save(new Contact(new ArrayList<>()));
        checkNotFoundById(contact.getId());
    }
}