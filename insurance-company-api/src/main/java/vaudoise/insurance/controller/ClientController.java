package vaudoise.insurance.controller;


import io.swagger.v3.oas.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vaudoise.insurance.exception.ClientNotFoundException;
import vaudoise.insurance.model.entity.Client;
import vaudoise.insurance.model.entity.CompanyClient;
import vaudoise.insurance.model.entity.PersonClient;
import vaudoise.insurance.model.model.ClientDtos;
import vaudoise.insurance.service.ClientService;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) { this.clientService = clientService; }


    @PostMapping("/person")
    public ResponseEntity<Void> createPerson(@RequestBody @Valid ClientDtos.CreatePersonDto dto) {
        Client c = clientService.createPerson(dto);
        return ResponseEntity.created(URI.create("/api/clients/" + c.getId())).build();
    }


    @PostMapping("/company")
    public ResponseEntity<Void> createCompany(@RequestBody @Valid ClientDtos.CreateCompanyDto dto) {
        Client c = clientService.createCompany(dto);
        return ResponseEntity.created(URI.create("/api/clients/" + c.getId())).build();
    }

    @Operation(summary = "find an existing client")
    @GetMapping("/{id}")
    public ResponseEntity<ClientDtos.ClientResponse> getClient(@PathVariable Long id) {
        Client c = clientService.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        if (c instanceof PersonClient p) {
            return ResponseEntity.ok(new ClientDtos.ClientResponse(c.getId(), "PERSON", c.getName(), c.getPhone(), c.getEmail(), p.getBirthDate(), null));
        } else if (c instanceof CompanyClient comp) {
            return ResponseEntity.ok(new ClientDtos.ClientResponse(c.getId(), "COMPANY", c.getName(), c.getPhone(), c.getEmail(), null, comp.getCompanyIdentifier()));
        } else {
            return ResponseEntity.ok(new ClientDtos.ClientResponse(c.getId(), "UNKNOWN", c.getName(), c.getPhone(), c.getEmail(), null, null));
        }
    }

    @Operation(summary = "Update an existing client")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateClient(@PathVariable Long id, @RequestBody @Valid ClientDtos.ClientResponse dto) {
        // We accept the response DTO shape for updates but will ignore birthDate/companyIdentifier updates
        Client updated;
        if ("PERSON".equalsIgnoreCase(dto.type())) {
            PersonClient p = new PersonClient();
            p.setName(dto.name());
            p.setPhone(dto.phone());
            p.setEmail(dto.email());
             // do not set birthDate
            updated = p;
        } else {
            CompanyClient c = new CompanyClient();
            c.setName(dto.name());
            c.setPhone(dto.phone());
            c.setEmail(dto.email());
            updated = c;
        }
        clientService.updateClient(id, updated);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete an existing client")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        Client existing = clientService.findById(id).orElseThrow(() -> new IllegalArgumentException("Client not found"));
        clientService.deleteClient(existing.getId());
        return ResponseEntity.noContent().build();
    }
}
