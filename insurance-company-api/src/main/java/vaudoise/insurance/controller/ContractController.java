package vaudoise.insurance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vaudoise.insurance.entity.Contract;
import vaudoise.insurance.model.ContractDtos;
import vaudoise.insurance.service.ContractService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/clients/{clientId}/contracts")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) { this.contractService = contractService; }

    @PostMapping
    public ResponseEntity<Void> createContract(@PathVariable Long clientId, @RequestBody @Valid ContractDtos.CreateContractDto dto) {
        Contract c = contractService.createContract(clientId, dto);
        return ResponseEntity.created(java.net.URI.create("/api/contracts/" + c.getId())).build();
    }

    @PatchMapping("/{contractId}/cost")
    public ResponseEntity<Void> updateCost(@PathVariable Long clientId, @PathVariable Long contractId, @RequestBody @Valid ContractDtos.UpdateCostDto dto) {
        contractService.updateCost(contractId, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ContractDtos.ContractResponse>> getActiveContracts(@PathVariable Long clientId, @RequestParam(required = false) String updatedAfter) {
        Instant instant = null;
        if (updatedAfter != null) {
            try {
                instant = Instant.parse(updatedAfter);
            } catch (DateTimeParseException ex) {
                return ResponseEntity.badRequest().build();
            }
        }
        List<Contract> list = contractService.getActiveContractsForClient(clientId, instant);
        var resp = list.stream().map(c -> new ContractDtos.ContractResponse(c.getId(), c.getStartDate(), c.getEndDate(), c.getCostAmount(), null)).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/sum")
    public ResponseEntity<String> sumActiveCosts(@PathVariable Long clientId) {
        BigDecimal sum = contractService.sumActiveContractsCost(clientId);
        return ResponseEntity.ok(sum.toPlainString());
    }
}
