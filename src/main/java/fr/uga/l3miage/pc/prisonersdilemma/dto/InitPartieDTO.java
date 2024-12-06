package fr.uga.l3miage.pc.prisonersdilemma.dto;

import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;



@Getter
@Setter

public class InitPartieDTO {

    @NotNull(message = "Le nom d'utilisateur est obligatoire.")
    @Size(min = 3, max = 20, message = "Le nom d'utilisateur doit comporter entre 3 et 20 caractères.")
    private String nomJoueur1;
    @Size(min = 3, max = 20, message = "Le nom d'utilisateur doit comporter entre 3 et 20 caractères.")
    @NotNull(message = "Le nom d'utilisateur est obligatoire.")
    private String nomJoueur2;
    @NotNull
    private boolean joueur1bot;
    @NotNull
    private boolean joueur2bot;
    private TypeStrategie strategieJoueur1;

    private boolean strategieExterneJoueur1=false;
    private boolean strategieExterneJoueur2=false;

    private TypeStrategie strategieJoueur2;
    @Positive
    private int nbMaxTours;
}
