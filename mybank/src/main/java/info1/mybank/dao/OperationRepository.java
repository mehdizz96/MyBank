package info1.mybank.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import info1.mybank.entities.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long>{
    @Query("select o from Operation o where o.compte.codeCompte = ?1 order by o.dateOperation desc")
    public Page<Operation> listOperation (String codeCpte, Pageable pageable);
}
