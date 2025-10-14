package vaudoise.insurance.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

@Entity
@DiscriminatorValue("COMPANY")
@Getter
@Setter
public class CompanyClient extends Client {

    @Pattern(regexp = "^[A-Za-z]{3}-\\d{3}$", message = "companyIdentifier must match AAA-123 pattern")
    private String companyIdentifier;
}
