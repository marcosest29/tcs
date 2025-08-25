package ec.com.tcs.bank_services.controller;

import ec.com.tcs.bank_services.dto.request.ClientRequest;
import ec.com.tcs.bank_services.dto.response.ClientResponse;
import ec.com.tcs.bank_services.model.ClientEntity;
import ec.com.tcs.bank_services.service.IClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getClient(@PathVariable Long id) {
        ClientEntity client = clientService.getClient(id);
        if(ObjectUtils.isEmpty(client)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientResponse> createClient(@RequestBody @Valid ClientRequest client) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(client));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Long id, @RequestBody @Valid ClientRequest client) {
        return ResponseEntity.ok(clientService.updateClient(id,client));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        Boolean delete = clientService.deleteClient(id);
        if(!ObjectUtils.isEmpty(delete) && delete){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
