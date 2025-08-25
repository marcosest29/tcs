package ec.com.tcs.bank_services.service;

import ec.com.tcs.bank_services.dto.request.ClientRequest;
import ec.com.tcs.bank_services.dto.response.ClientResponse;
import ec.com.tcs.bank_services.model.ClientEntity;

public interface IClientService {

    ClientResponse createClient(ClientRequest client);

    ClientEntity getClient(Long id);

    ClientResponse updateClient(Long id, ClientRequest client);

    Boolean deleteClient(Long id);

    boolean clientExists(Long clientId);
}
