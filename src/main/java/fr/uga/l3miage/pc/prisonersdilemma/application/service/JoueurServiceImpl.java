package fr.uga.l3miage.pc.prisonersdilemma.application.service;

import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.out.strategie.StrategieAdaptor;
import fr.uga.l3miage.pc.prisonersdilemma.domain.port.in.JoueurUseCases;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.*;
import fr.uga.l3miage.pc.prisonersdilemma.domain.factory.StrategieInterneFactory;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Service
public class JoueurServiceImpl implements JoueurUseCases {

    public Joueur creerUser(String nom, int compteurJoueur) {
        return new JoueurHumain(nom, compteurJoueur);
    }

    public JoueurBot transformerUser(JoueurHumain joueurHumain, TypeStrategie strategieNom,
            boolean isStrategieExterne) {
        Strategie strategie = isStrategieExterne ? new StrategieAdaptor(strategieNom)
                : StrategieInterneFactory.creeStrategie(strategieNom);
        return joueurHumain.abandonner(strategie);
    }

}
