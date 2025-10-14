package vaudoise.insurance.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
public class ClientDtos {

    public record CreatePersonDto(
            @NotBlank String name,
            @Pattern(regexp = "^\\+?\\d{7,15}$") String phone,
            @Email String email,
            @PastOrPresent LocalDate birthDate
    ) {}


    public record CreateCompanyDto(
            @NotBlank String name,
            @Pattern(regexp = "^\\+?\\d{7,15}$") String phone,
            @Email String email,
            @Pattern(regexp = "^[A-Za-z]{3}-\\d{3}$") String companyIdentifier
    ) {}


    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ClientResponse(
            Long id,
            String type,
            String name,
            String phone,
            String email,
            LocalDate birthDate,
            String companyIdentifier
    ) {}
}
