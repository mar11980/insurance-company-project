package vaudoise.insurance.model.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
public class ClientDtos {

    public record CreatePersonDto(
            @NotBlank String name,
            @Pattern(
                    regexp = "^\\+\\d{7,15}$",
                    message = "Phone number must start with '+' and contain 7 to 15 digits"
            ) String phone,
            @Email @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email must be in format name@entreprise.domain") String email,
            @PastOrPresent LocalDate birthDate
    ) {}


    public record CreateCompanyDto(
            @NotBlank String name,
            @Pattern(
                    regexp = "^\\+\\d{7,15}$",
                    message = "Phone number must start with '+' and contain 7 to 15 digits"
            ) String phone,
            @Email @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                    message = "Email must be in format name@entreprise.domain") String email,
            @Pattern(regexp = "^[A-Za-z]{3}-\\d{3}$",message = "company " +
                    "identifier must be in format aaa-123") String companyIdentifier
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
