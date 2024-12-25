package fr.uga.l3miage.pc.prisonersdilemma.controller;

import fr.uga.l3miage.pc.prisonersdilemma.dto.*;
import fr.uga.l3miage.pc.prisonersdilemma.service.PartieService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController

@RequestMapping("/jeu")
public class JeuController { //NOSONAR


    private final PartieService partieService;

    public  JeuController(PartieService partieService){
        this.partieService=partieService;
    }
    @PostMapping("/demarrer")
    public OutPartieDTO demarrerPartie(@Valid @RequestBody InitPartieDTO initPartieDto ){
         return partieService.demarrerPartie(initPartieDto);
    }


    @PostMapping("/jouer-tour")
    public OutPartieDTO jouerTour(@Valid @RequestBody DecisionDTO decisionDTO){
        return partieService.jouerTour(decisionDTO);
    }

    @PostMapping("/abandonner-humain")
    public OutPartieDTO abandonnerPartie(@RequestBody AbandonnerDTO abandonnerDTO){
        return partieService.abandonner(abandonnerDTO);
    }

    @GetMapping("/error")
    public String handleError() {
        return "The requested resource was not found.";
    }

    //no addresse found api pour tous les autres apelles entrants



}
