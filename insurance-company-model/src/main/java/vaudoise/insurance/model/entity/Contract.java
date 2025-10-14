package vaudoise.insurance.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "CONTRACT")
@Getter
@Setter
@NoArgsConstructor
public class Contract implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Client client;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    private OffsetDateTime updateDate;

    @NotNull
    @Positive(message = "Cost amount must be greater than 0")
    private BigDecimal costAmount;

    @PrePersist
    public void prePersist() {
        if (startDate == null) startDate = LocalDate.now();
        updateDate = OffsetDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updateDate = OffsetDateTime.now();
    }

}
