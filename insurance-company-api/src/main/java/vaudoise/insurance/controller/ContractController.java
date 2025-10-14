package vaudoise.insurance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vaudoise.insurance.model.entity.Client;
import vaudoise.insurance.model.model.ContractDtos;
import vaudoise.insurance.model.entity.Contract;
import vaudoise.insurance.service.ClientService;
import vaudoise.insurance.service.ContractService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/clients/{clientId}/contracts")
@Slf4j
public class ContractController {

    private final ContractService contractService;
    private final ClientService clientService;

    public ContractController(ContractService contractService, ClientService clientService) { this.contractService = contractService;
        this.clientService = clientService;
    }

    @Operation(summary = "Create contract for existing client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contract created successfully"),
            @ApiResponse(responseCode = "500", description = "Client not found"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping
    public ResponseEntity<Void> createContract(@PathVariable Long clientId, @RequestBody @Valid ContractDtos.CreateContractDto dto) {
        Contract c = contractService.createContract(clientId, dto);
        return ResponseEntity.created(java.net.URI.create("/api/contracts/" + c.getId())).build();
    }

    @Operation(summary = "Update contract for existing client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contract updated successfully"),
            @ApiResponse(responseCode = "500", description = "Client or Contract not found"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PatchMapping("/{contractId}/cost")
    public ResponseEntity<Void> updateCost(@PathVariable Long clientId, @PathVariable Long contractId, @RequestBody @Valid ContractDtos.UpdateCostDto dto) {
        contractService.updateCost(contractId, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "get all contracts for existing client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get all contracts for client"),
            @ApiResponse(responseCode = "500", description = "Client not found")
    })
    @GetMapping
    public ResponseEntity<List<ContractDtos.ContractResponse>> getActiveContracts(@PathVariable Long clientId, @RequestParam(required = false) String updatedAfter) {
        Instant instant = null;
        clientService.findById(clientId).orElseThrow(() -> new IllegalArgumentException("Client not found"));
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

    @Operation(summary = "sum cost all contracts for existing client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sum cost contracts for client"),
            @ApiResponse(responseCode = "500", description = "Client not found")
    })
    @GetMapping("/sum")
    public ResponseEntity<String> sumActiveCosts(@PathVariable Long clientId) {
        Client existingClient = clientService.findById(clientId).orElseThrow(() -> new IllegalArgumentException("Client not found"));
        BigDecimal sum = contractService.sumActiveContractsCost(existingClient.getId());
        return ResponseEntity.ok(sum.toPlainString());
    }
}
