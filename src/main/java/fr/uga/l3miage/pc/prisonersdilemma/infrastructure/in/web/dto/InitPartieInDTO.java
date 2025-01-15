package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategie;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;



@Getter
@Setter

public class InitPartieInDTO {

    @NotNull(message = "Le nom d'utilisateur est obligatoire.")
    @Size(min = 3, max = 20, message = "Le nom d'utilisateur doit comporter entre 3 et 20 caractères.")
    private String nomJoueur;

    @Positive
    private int nbMaxTours;
}
