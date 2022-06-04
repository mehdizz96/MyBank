package info1.mybank.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import info1.mybank.entities.Compte;

public interface CompteRepository extends JpaRepository<Compte, String> {
    @Query("select c from Compte c where c.codeCompte = ?1")
    Compte findOne(String codeCpte);
    
}
