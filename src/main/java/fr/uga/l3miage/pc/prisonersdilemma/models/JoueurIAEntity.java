package fr.uga.l3miage.pc.prisonersdilemma.models;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import jakarta.persistence.*;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
@Setter
@Entity
public class JoueurIAEntity extends JoueurEntity {

    private String strategieNom;

    @Transient
    private Strategie strategie;

    public JoueurIAEntity() {
        super();
    }

    public JoueurIAEntity(String nom, Strategie strategie) {
        super(nom);
        this.strategie = strategie;
        this.strategieNom = strategie.getClass().getSimpleName();
    }

    @Override
    public Decision prendreDecision(List<ResultatTourEntity> historique) {
        if (strategie == null) {
            // Initialiser la stratégie en fonction du nom
            strategie = StrategieFactory.getStrategie(strategieNom);
        }
        return strategie.prendreDecision(historique);
    }

    public Strategie getStrategie() {
        if (strategie == null) {
            strategie = StrategieFactory.getStrategie(strategieNom);
        }
        return strategie;
    }

    public void setStrategie(Strategie strategie) {
        this.strategie = strategie;
        this.strategieNom = strategie.getClass().getSimpleName();
    }

}