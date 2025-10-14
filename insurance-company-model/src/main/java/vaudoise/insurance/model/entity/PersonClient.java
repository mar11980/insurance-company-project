package vaudoise.insurance.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("PERSON")
@Getter
@Setter
public class PersonClient extends Client {

    @PastOrPresent(message = "birthDate cannot be in the future")
    @Column(updatable = false)
    private LocalDate birthDate;

}