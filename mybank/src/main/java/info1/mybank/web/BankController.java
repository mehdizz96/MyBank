package info1.mybank.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String consulter(Model model, String codeCompte, 
    @RequestParam(name="page",defaultValue="0") int page,
    @RequestParam(name="size",defaultValue="5") int size) {
        model.addAttribute("codeCompte", codeCompte);
        try {
            Compte cp = bankMetier.consulterCompte(codeCompte);
            Page<Operation> pageOperations = bankMetier.listOperation(codeCompte, page, size);
            model.addAttribute("listOperations", pageOperations.getContent());
            int [] pages = new int[pageOperations.getTotalPages()];
            model.addAttribute("pages", pages);
            model.addAttribute("compte", cp);
        } catch (Exception e) {
            model.addAttribute("exception", e);
        }
        return "comptes";
    }

    @RequestMapping(value = "/saveOperation", method = RequestMethod.POST)
    public String saveOperation(Model model, String typeOperation, String codeCompte, double montant, String codeCompte2){
        try {
            if(typeOperation.equals("VERS")){
                bankMetier.verser(codeCompte, montant);
            }
            else if(typeOperation.equals("RET")){
                bankMetier.retirer(codeCompte, montant);
            }
            else if(typeOperation.equals("VIR")){
                bankMetier.virement(codeCompte, codeCompte2, montant);
            }
        } catch (Exception e) {
            model.addAttribute("error", e);
            return "redirect:/consulterCompte?codeCompte="+codeCompte+"&error="+e.getMessage();
        }
        return "redirect:/consulterCompte?codeCompte="+codeCompte;
    }
}
