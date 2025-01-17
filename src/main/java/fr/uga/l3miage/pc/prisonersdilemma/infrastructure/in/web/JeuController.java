package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web;

import fr.uga.l3miage.pc.prisonersdilemma.domain.port.in.DecisionUseCases;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.*;
import fr.uga.l3miage.pc.prisonersdilemma.domain.port.in.PartieUseCases;
import fr.uga.l3miage.pc.prisonersdilemma.application.service.SseService;
import java.util.List;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@AllArgsConstructor
@RestController
@CrossOrigin(origins = {"http://localhost:4200" , "http://127.0.01:4200" , "http://129.88.219.103:4200"})
@RequestMapping("/jeu")
public class JeuController { // NOSONAR

    private final DecisionUseCases decisionUseCases;
    private final PartieUseCases partieUseCases;
    private final SseService sseService;

    @GetMapping("{idPartie}/stream-partie/")
    public SseEmitter streamPartie(@PathVariable int idPartie) {
        return sseService.createEmitter(idPartie);
    }

    @PostMapping("/init-partie")
    public initPartieOutDTO initPartie(@Valid @RequestBody InitPartieInDTO initPartieInDto) {
        int idPartie = partieUseCases.initPartie(initPartieInDto);
        return new initPartieOutDTO(idPartie);
    }

    @PostMapping("{idPartie}/join-partie/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void joinPartie(@PathVariable int idPartie,
            @RequestBody JoinPartieRequestDTO joinPartieRequestDTO) {
        partieUseCases.joinPartie(idPartie, joinPartieRequestDTO);
    }

    @PostMapping("{idPartie}/jouer-tour/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void jouerTour(
            @PathVariable int idPartie,
            @Valid @RequestBody DecisionInDTO decisionInDTO) {
        decisionUseCases.ajouterDecision(idPartie, decisionInDTO);
    }

    @PostMapping("{idPartie}/abandonner-humain")
    public void abandonnerPartie(
            @PathVariable int idPartie,
            @RequestBody AbandonnerDTO abandonnerDTO) {
        partieUseCases.abandonner(idPartie, abandonnerDTO);
    }

    @GetMapping("/info-parties")
    public List<PartieEnAttendDTO> infoPartiesDTO() {
        return partieUseCases.getPartiesEnAttend();

    }

    @GetMapping("/error")
    public String handleError() {
        return "The requested resource was not found.";
    }

    // no addresse found api pour tous les autres apelles entrants

}
