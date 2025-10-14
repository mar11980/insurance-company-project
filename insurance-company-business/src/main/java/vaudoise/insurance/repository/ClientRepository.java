package vaudoise.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vaudoise.insurance.model.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
