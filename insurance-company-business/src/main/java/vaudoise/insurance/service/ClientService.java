package vaudoise.insurance.service;

import org.springframework.stereotype.Service;
import vaudoise.insurance.entity.CompanyClient;
import vaudoise.insurance.entity.Contract;
import vaudoise.insurance.entity.PersonClient;
import vaudoise.insurance.entity.Client;
import vaudoise.insurance.model.ClientDtos;
import vaudoise.insurance.repository.ClientRepository;
import vaudoise.insurance.repository.ContractRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ContractRepository contractRepository;


    public ClientService(ClientRepository clientRepository, ContractRepository contractRepository) {
        this.clientRepository = clientRepository;
        this.contractRepository = contractRepository;
    }


    public Client createPerson(ClientDtos.CreatePersonDto dto) {
        PersonClient p = new PersonClient();
        p.setName(dto.name());
        p.setPhone(dto.phone());
        p.setEmail(dto.email());
        p.setBirthDate(dto.birthDate());
        return clientRepository.save(p);
    }

    public Client createCompany(ClientDtos.CreateCompanyDto dto) {
        CompanyClient c = new CompanyClient();
        c.setName(dto.name());
        c.setPhone(dto.phone());
        c.setEmail(dto.email());
        c.setCompanyIdentifier(dto.companyIdentifier());
        return clientRepository.save(c);
    }

    public void updateClient(Long id, Client updated) {
        Client existing = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Client not found"));
        existing.setName(updated.getName());
        existing.setPhone(updated.getPhone());
        existing.setEmail(updated.getEmail());
        clientRepository.save(existing);
    }


    @Transactional
    public void deleteClient(Long clientId) {
        LocalDate now = LocalDate.now();
        List<Contract> contracts = contractRepository.findActiveByClient(clientId, now);

        contracts.forEach(contract -> {
            contract.setEndDate(now);
            contractRepository.save(contract);
        });
        clientRepository.deleteById(clientId);
    }

    public Optional<Client> findById(Long id) {
        return  clientRepository.findById(id);
    }
}
