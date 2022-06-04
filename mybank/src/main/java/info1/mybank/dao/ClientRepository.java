package info1.mybank.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import info1.mybank.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    
}
