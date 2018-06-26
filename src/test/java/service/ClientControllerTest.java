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

import java.time.LocalDate;

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

    private static final String LAST_APPLICATION_BY_ID_ENDPOINT = "/client/%d/lastApplication";

    @Test
    @DirtiesContext
    public void existingContact() throws Exception {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        long contactId = 1;
        applicationRepository.save(new Application(contactId, yesterday, "product0"));
        Application lastApplication = new Application(contactId, today, "product1");
        applicationRepository.save(lastApplication);

        mockMvc.perform(get(String.format(LAST_APPLICATION_BY_ID_ENDPOINT, contactId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.PRODUCT_NAME").value("product1"))
                .andExpect(jsonPath("$.APPLICATION_ID").value(lastApplication.getId()))
                .andExpect(jsonPath("$.CONTACT_ID").value(contactId))
                .andExpect(jsonPath("$.DT_CREATED").value(lastApplication.getDateCreated().toString()));
    }

    @Test
    public void nonExistingContact() throws Exception {
        mockMvc.perform(get(String.format(LAST_APPLICATION_BY_ID_ENDPOINT, 1)))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

    }
}