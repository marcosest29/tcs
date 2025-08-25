package ec.com.tcs.bank_services.service;

import ec.com.tcs.bank_services.dto.request.ClientRequest;
import ec.com.tcs.bank_services.dto.response.ClientResponse;
import ec.com.tcs.bank_services.repository.IClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IClientRepository clientRepository;

    @Test
    void testCreateClientIntegration() {
        ClientRequest request = new ClientRequest(
                "Marcos Estrella", "M", 28,
                "1723347900123", "Conocoto", "0979226839", "1234"
        );

        String url = "http://localhost:" + port + "/clientServices/api/v1/clients";
        ClientResponse response = restTemplate.postForEntity(url, request, ClientResponse.class).getBody();

        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo("Marcos Estrella");
        assertThat(clientRepository.findByIdentification("1723347900123")).isNotNull();
    }
}
