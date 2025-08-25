package ec.com.tcs.bank_services.service;

import ec.com.tcs.bank_services.dto.request.ClientRequest;
import ec.com.tcs.bank_services.dto.response.ClientResponse;
import ec.com.tcs.bank_services.exception.ClientException;
import ec.com.tcs.bank_services.mapper.ClientMapper;
import ec.com.tcs.bank_services.model.ClientEntity;
import ec.com.tcs.bank_services.repository.IClientRepository;
import ec.com.tcs.bank_services.service.impl.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    @Mock
    private IClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientService clientService;

    private ClientRequest request;
    private ClientEntity clientEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        request = new ClientRequest(
                "Marcos Estrella", "M", 28,
                "1723347900", "Conocoto", "0979226839", "1234"
        );

        clientEntity = new ClientEntity();
        clientEntity.setClientId(1L);
        clientEntity.setName("Marcos Estrella");
        clientEntity.setIdentification("1723347900");
        clientEntity.setStatus(true);
    }

    @Test
    void testCreateClient_Success() {
        when(clientRepository.findByIdentification("1723347900")).thenReturn(null);
        when(clientMapper.toEntity(request)).thenReturn(clientEntity);
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
        when(clientMapper.toResponse(clientEntity))
                .thenReturn(new ClientResponse(1L, "Marcos Estrella", "1723347900"));

        ClientResponse response = clientService.createClient(request);

        assertNotNull(response);
        assertEquals("Marcos Estrella", response.name());
        assertEquals("1723347900", response.identification());
        verify(clientRepository).save(any(ClientEntity.class));
    }

    @Test
    void testCreateClient_AlreadyExists(){
        when(clientRepository.findByIdentification("1723347900")).thenReturn(clientEntity);

        ClientException exception = assertThrows(ClientException.class,
                () -> clientService.createClient(request));

        assertEquals(exception.getMessage(),"Ya existe un cliente con la identificacion: 1723347900");
        verify(clientRepository, never()).save(any());
    }

}
