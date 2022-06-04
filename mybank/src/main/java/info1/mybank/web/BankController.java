package info1.mybank.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import info1.mybank.entities.Compte;
import info1.mybank.entities.Operation;
import info1.mybank.metier.IBankMetier;

@Controller
public class BankController {
    @Autowired
    private IBankMetier bankMetier;

    @RequestMapping("/operations")
    public String index() {
        return "comptes";
    }

    @RequestMapping("/consulterCompte")
    public String consulter(Model model, String codeCompte) {
        model.addAttribute("codeCompte", codeCompte);
        try {
            Compte cp = bankMetier.consulterCompte(codeCompte);
            Page<Operation> pageOperations = bankMetier.listOperation(codeCompte, 0, 4);
            model.addAttribute("listOperations", pageOperations.getContent());
            model.addAttribute("compte", cp);
        } catch (Exception e) {
            model.addAttribute("exception", e);
        }
        return "comptes";
    }
}
