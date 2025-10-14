package vaudoise.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vaudoise.insurance.model.entity.Contract;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {


    @Query("select c from Contract c where c.client.id = :clientId and (c.endDate is null or c.endDate > :today)")
    List<Contract> findActiveByClient(@Param("clientId") Long clientId, @Param("today") LocalDate today);


    @Query("select c from Contract c where c.client.id = :clientId and (c.endDate is null or c.endDate > :today) and (:updatedAfter is null or c.updateDate > :updatedAfter)")
    List<Contract> findActiveByClientAndUpdatedAfter(@Param("clientId") Long clientId, @Param("today") LocalDate today, @Param("updatedAfter") LocalDate updatedAfter);


    @Query("select coalesce(sum(c.costAmount),0) from Contract c where c.client.id = :clientId and (c.endDate is null or c.endDate > :today)")
    BigDecimal sumActiveCostsByClient(@Param("clientId") Long clientId, @Param("today") LocalDate today);

}
