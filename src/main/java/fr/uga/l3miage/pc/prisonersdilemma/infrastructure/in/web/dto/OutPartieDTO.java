package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder

public class OutPartieDTO {

    @NotNull(message = "Le nom d'utilisateur est obligatoire.")
    @Size(min = 3, max = 20, message = "Le nom d'utilisateur doit comporter entre 3 et 20 caractères.")
    private String nomJoueur1;

    @Size(min = 3, max = 20, message = "Le nom d'utilisateur doit comporter entre 3 et 20 caractères.")
    @NotNull(message = "Le nom d'utilisateur est obligatoire.")
    private String nomJoueur2;
    @NotNull
    private int idPlayer1;
    @NotNull
    private int idPlayer2;
    private boolean joueur2bot;
    private boolean joueur1bot;
    private int nbTourLeft;
    private int scoreJoueur1;
    private int scoreJoueur2;
    private Tour[] historique;



}
