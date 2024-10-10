package fr.uga.l3miage.pc.prisonersdilemma.models;

import java.util.List;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JoueurHumain extends Joueur {

    public JoueurHumain(String nom) {
        super();
    }

    public JoueurHumain() {
        super();
    }


    @Override
    public Decision prendreDecision(List<ResultatTour> historique) {
        throw new UnsupportedOperationException("La méthode prendreDecision doit être implémentée côté client.");
    }

}
