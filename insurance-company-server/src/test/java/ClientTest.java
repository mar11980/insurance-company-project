
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import vaudoise.insurance.VaudoiseInsuranceApplication;
import vaudoise.insurance.model.entity.Client;
import vaudoise.insurance.model.model.ClientDtos;
import vaudoise.insurance.repository.ClientRepository;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = VaudoiseInsuranceApplication.class)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://localhost:5432/vaudoise"
})
public class ClientTest {
    @Autowired
    private  ClientRepository clientRepository;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void findAllClients(){
        List<Client> clients = clientRepository.findAll();
        Assertions.assertNotNull(clients);
    }

    @Test
    void shouldCreateClientSuccessfully() throws Exception {

        ClientDtos.CreatePersonDto record = new ClientDtos.CreatePersonDto("John Doe", "+1234567890", "john.doe@example.com", LocalDate.of(1990, 1, 1));

        String clientJson = objectMapper.writeValueAsString(record);

        this.mvc.perform(post("/api/clients/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.phone", is("+1234567890")));
    }

}
