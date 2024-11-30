package fr.uga.l3miage.pc.prisonersdilemma.DTO;

import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    private TypeStrategie strategieJoueur2;
    private TypeStrategie strategieJoueur1;
    private int nbTourLeft;
    private int scoreJoueur1;
    private int scoreJoueur2;
    private Tour[] historique;



}
