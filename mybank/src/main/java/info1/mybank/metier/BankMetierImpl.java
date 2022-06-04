package info1.mybank.metier;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info1.mybank.dao.CompteRepository;
import info1.mybank.dao.OperationRepository;
import info1.mybank.entities.Compte;
import info1.mybank.entities.CompteCourant;
import info1.mybank.entities.Operation;
import info1.mybank.entities.Retrait;
import info1.mybank.entities.Versement;
@Service
@Transactional
public class BankMetierImpl implements IBankMetier {
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private OperationRepository operationRepository;
    @Override
    public Compte consulterCompte(String codeCpte) {
        Compte cp = compteRepository.findOne(codeCpte);
        if (cp == null) throw new RuntimeException("Ce Compte n'existe pas !");
        return cp;
    }

    @Override
    public void verser(String codeCpte, double montant) {
        Compte cp = consulterCompte(codeCpte);
        Versement v = new Versement(new Date (), montant, cp);
        operationRepository.save(v);
        cp.setSolde(cp.getSolde()+montant);
        compteRepository.save(cp);
    }

    @Override
    public void retirer(String codeCpte, double montant) {
        Compte cp = consulterCompte(codeCpte);
        double facilitesCaisse= 0;
        if (cp instanceof CompteCourant){
            facilitesCaisse = ((CompteCourant)cp).getDecouvert();
        }
        if (facilitesCaisse + cp.getSolde() < montant){
            throw new RuntimeException("Votre solde est insuffisant !");
        }
        Retrait r = new Retrait(new Date (), montant, cp);
        operationRepository.save(r);
        cp.setSolde(cp.getSolde()-montant);
        compteRepository.save(cp);
        
    }

    @Override
    public void virement(String codeCpte1, String codeCpte2, double montant) {
        if (codeCpte1.equals(codeCpte2)){
            throw new RuntimeException("Vous pouvez pas effectuer un virement vers le meme compte");
        }
        retirer (codeCpte1, montant);
        verser (codeCpte2, montant);
    }

    @Override
    public Page<Operation> listOperation(String codeCpte, int page, int size) {
        return operationRepository.listOperation(codeCpte,PageRequest.of(page, size));
    }
    
}
