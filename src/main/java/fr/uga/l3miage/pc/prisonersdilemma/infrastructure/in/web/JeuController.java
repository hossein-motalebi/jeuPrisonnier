package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web;

import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.AbandonnerDTO;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.DecisionDTO;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.InitPartieDTO;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.OutPartieDTO;
import fr.uga.l3miage.pc.prisonersdilemma.domain.port.in.PartieUseCases;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController

@RequestMapping("/jeu")
public class JeuController { //NOSONAR


    private final PartieUseCases  partieUseCases ;

    public  JeuController(PartieUseCases partieUseCases ){
        this.partieUseCases = partieUseCases ;
    }
    @PostMapping("/demarrer")
    public OutPartieDTO demarrerPartie(@Valid @RequestBody InitPartieDTO initPartieDto ){
         return partieUseCases.demarrerPartie(initPartieDto);
    }


    @PostMapping("/jouer-tour")
    public OutPartieDTO jouerTour(@Valid @RequestBody DecisionDTO decisionDTO){
        return partieUseCases .jouerTour(decisionDTO);
    }

    @PostMapping("/abandonner-humain")
    public OutPartieDTO abandonnerPartie(@RequestBody AbandonnerDTO abandonnerDTO){
        return partieUseCases .abandonner(abandonnerDTO);
    }

    @GetMapping("/error")
    public String handleError() {
        return "The requested resource was not found.";
    }

    //no addresse found api pour tous les autres apelles entrants



}
