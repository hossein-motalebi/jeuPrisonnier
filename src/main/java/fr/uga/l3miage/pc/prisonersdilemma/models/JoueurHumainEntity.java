package fr.uga.l3miage.pc.prisonersdilemma.models;

import java.util.List;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
@Setter
@Entity
public class JoueurHumainEntity extends JoueurEntity {

    public JoueurHumainEntity(String nom) {
        super();
    }

    public JoueurHumainEntity() {
        super();
    }


    @Override
    public Decision prendreDecision(List<ResultatTourEntity> historique) {
        throw new UnsupportedOperationException("La méthode prendreDecision doit être implémentée côté client.");
    }

}
