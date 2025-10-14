package vaudoise.insurance.service;

import org.springframework.stereotype.Service;
import vaudoise.insurance.model.entity.Client;
import vaudoise.insurance.model.model.ContractDtos;
import vaudoise.insurance.model.entity.Contract;
import vaudoise.insurance.repository.ClientRepository;
import vaudoise.insurance.repository.ContractRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class ContractService {


    private final ContractRepository contractRepository;
    private final ClientRepository clientRepository;


    public ContractService(ContractRepository contractRepository, ClientRepository clientRepository) {
        this.contractRepository = contractRepository;
        this.clientRepository = clientRepository;
    }


    @Transactional
    public Contract createContract(Long clientId, ContractDtos.CreateContractDto dto) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new IllegalArgumentException("Client not found"));
        Contract c = new Contract();
        c.setClient(client);
        c.setStartDate(dto.startDate() == null ? LocalDate.now() : dto.startDate());
        c.setEndDate(dto.endDate());
        c.setCostAmount(dto.costAmount());
        c.setUpdateDate(OffsetDateTime.now(ZoneOffset.UTC));
        return contractRepository.save(c);
    }


    @Transactional
    public void updateCost(Long contractId, ContractDtos.UpdateCostDto dto) {
        Contract c = contractRepository.findById(contractId).orElseThrow(() -> new IllegalArgumentException("Contract not found"));
        c.setCostAmount(dto.costAmount());
        c.setUpdateDate(OffsetDateTime.now(ZoneOffset.UTC));
        contractRepository.save(c);
    }


    public List<Contract> getActiveContractsForClient(Long clientId, Instant updatedAfter) {
        LocalDate today = LocalDate.now();
        if (updatedAfter == null) {
            return contractRepository.findActiveByClient(clientId, today);
        } else {
            return contractRepository.findActiveByClientAndUpdatedAfter(clientId, today, LocalDate.from(updatedAfter));
        }
    }


    public BigDecimal sumActiveContractsCost(Long clientId) {
        LocalDate today = LocalDate.now();
        return contractRepository.sumActiveCostsByClient(clientId, today);
    }
}