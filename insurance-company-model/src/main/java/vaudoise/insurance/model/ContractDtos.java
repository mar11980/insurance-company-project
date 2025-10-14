package vaudoise.insurance.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public class ContractDtos {

    public record CreateContractDto(
            LocalDate startDate,
            LocalDate endDate,
            @NotNull @Positive BigDecimal costAmount
    ) {}


    public record UpdateCostDto(@NotNull @Positive BigDecimal costAmount) {}


    public record ContractResponse(
            Long id,
            LocalDate startDate,
            LocalDate endDate,
            BigDecimal costAmount,
            Instant updateDate //
    ) {}
}
