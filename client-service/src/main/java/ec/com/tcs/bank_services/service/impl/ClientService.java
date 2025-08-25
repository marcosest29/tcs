package ec.com.tcs.bank_services.service.impl;

import ec.com.tcs.bank_services.dto.request.ClientRequest;
import ec.com.tcs.bank_services.dto.response.ClientResponse;
import ec.com.tcs.bank_services.exception.ClientException;
import ec.com.tcs.bank_services.mapper.ClientMapper;
import ec.com.tcs.bank_services.model.ClientEntity;
import ec.com.tcs.bank_services.repository.IClientRepository;
import ec.com.tcs.bank_services.service.IClientService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Transactional
public class ClientService implements IClientService {

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public ClientResponse createClient(ClientRequest request) {
        ClientEntity clientExists = clientRepository.findByIdentification(request.identification());
        if(!ObjectUtils.isEmpty(clientExists)){
            if(clientExists.getStatus().equals(true)){
                throw new ClientException("Ya existe un cliente con la identificacion: "+request.identification());
            }else {
                BeanUtils.copyProperties(request, clientExists, "id", "clientId");
                clientExists.setStatus(true);
                return clientMapper.toResponse(clientRepository.save(clientExists));
            }
        }
        ClientEntity clientEntity = clientMapper.toEntity(request);
        return clientMapper.toResponse(clientRepository.save(clientEntity));
    }

    @Override
    public ClientEntity getClient(Long id) {
        return clientRepository.findByClientIdAndStatusTrue(id);
    }

    @Override
    public ClientResponse updateClient(Long id, ClientRequest client) {
        ClientEntity clientExists = clientRepository.findByClientId(id);
        if(ObjectUtils.isEmpty(clientExists)){
            throw new ClientException("El cliente no existe");
        }
        ClientEntity clientValidateIdentification = clientRepository.findByIdentification(client.identification());
        if(!ObjectUtils.isEmpty(clientValidateIdentification)){
            throw new ClientException("Ya existe un cliente con la identificacion: "+client.identification());
        }
        clientRepository.updateClient(id,client.name(),client.gender(),client.age(),client.identification(),client.address(),client.phone(),client.password());
        return new ClientResponse(id,client.name(),client.identification());
    }

    @Override
    public Boolean deleteClient(Long id) {
        ClientEntity clientExists = clientRepository.findByClientId(id);
        if(ObjectUtils.isEmpty(clientExists)){
            throw new ClientException("El cliente no existe");
        }
        clientRepository.deleteClient(id);
        return true;
    }

    @Override
    public boolean clientExists(Long clientId) {
        ClientEntity clientExists = clientRepository.findByClientIdAndStatusTrue(clientId);
        return !ObjectUtils.isEmpty(clientExists);
    }
}
