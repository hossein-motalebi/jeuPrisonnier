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


    protected int compteurJoueur = 1;

    public Joueur creerUser(String nom, Boolean isBot, TypeStrategie strategieNom, boolean isStrategieExterne){
        if (!isBot || strategieNom==null){ //joueurHomain //NOSONAR
            JoueurHumain joueurHumain = new JoueurHumain(nom,compteurJoueur);
            compteurJoueur++;
            return joueurHumain;
        }
        else{ //joueurBot
            Strategie strategie = isStrategieExterne ? new StrategieAdaptor(strategieNom) :
                    StrategieInterneFactory.creeStrategie(strategieNom);
            JoueurBot joueurBot = new JoueurBot(nom,strategie,compteurJoueur);
            compteurJoueur++;
            return joueurBot;
        }
    }

    public JoueurBot transformerUser(JoueurHumain joueurHumain, TypeStrategie strategieNom,boolean isStrategieExterne){
        Strategie strategie = isStrategieExterne ? new StrategieAdaptor(strategieNom) :
                StrategieInterneFactory.creeStrategie(strategieNom);
        return joueurHumain.abandonner(strategie);
    }



}
