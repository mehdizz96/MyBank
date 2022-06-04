package info1.mybank;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import info1.mybank.dao.ClientRepository;
import info1.mybank.dao.CompteRepository;
import info1.mybank.dao.OperationRepository;
import info1.mybank.entities.Client;
import info1.mybank.entities.Compte;
import info1.mybank.entities.CompteCourant;
import info1.mybank.entities.CompteEpargne;
import info1.mybank.entities.Retrait;
import info1.mybank.entities.Versement;
import info1.mybank.metier.IBankMetier;

@SpringBootApplication
public class MybankApplication implements CommandLineRunner{

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	private IBankMetier bankMetier;
	public static void main(String[] args) {
		SpringApplication.run(MybankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Client c1=clientRepository.save(new Client ("Mehdi","mehdi@Gmail.com"));
		Client c2=clientRepository.save(new Client ("Anasse","anasse@Gmail.com"));
		Compte cp1=compteRepository.save(new CompteCourant("c1",new Date(), 90000, c1, 6000)) ;
		Compte cp2=compteRepository.save(new CompteEpargne("c2",new Date(),6000,c2,5.5)) ;
		operationRepository.save(new Versement (new Date(), 9000,cp1)) ;
		operationRepository.save (new Versement (new Date (),6000,cp1)) ;
		operationRepository.save (new Versement (new Date() ,2300,cp1)) ;
		operationRepository.save (new Retrait (new Date (), 9000, cp1)) ;
		operationRepository.save(new Versement (new Date(), 2300,cp2)) ;
		operationRepository.save(new Versement (new Date(),400,cp2)) ;
		operationRepository.save(new Versement (new Date(),2300,cp2)) ;
		operationRepository.save (new Retrait (new Date (), 3000, cp2)) ;

		bankMetier.verser("c1", 77777);
	}

}
