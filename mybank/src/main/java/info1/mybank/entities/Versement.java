package info1.mybank.entities;

import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@Entity
@DiscriminatorValue("V")
public class Versement extends Operation {

    public Versement(Date dateOperation, double montant, Compte cp) {
        super(dateOperation, montant, cp);
    }

    public Versement() {
    }
    
}
