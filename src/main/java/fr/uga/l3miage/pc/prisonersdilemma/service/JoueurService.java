package fr.uga.l3miage.pc.prisonersdilemma.service;
import fr.uga.l3miage.pc.prisonersdilemma.factory.StrategieFactory;
import fr.uga.l3miage.pc.prisonersdilemma.models.*;
import lombok.Setter;
import org.springframework.stereotype.Service;
import fr.uga.l3miage.pc.prisonersdilemma.enums.*;

@Setter
@Service
public class JoueurService {


    protected int compteurJoueur = 1;

    public Joueur creerUser(String nom,Boolean isBot, TypeStrategie strategieNom,boolean isStrategieExterne){
        if (!isBot || strategieNom==null){ //joueurHomain //NOSONAR
            JoueurHumain joueurHumain = new JoueurHumain(nom,compteurJoueur);
            compteurJoueur++;
            return joueurHumain;
        }
        else{ //joueurBot
            Strategie strategie = isStrategieExterne ? new StrategieAdaptor(strategieNom) :
                    StrategieFactory.creeStrategie(strategieNom);
            JoueurBot joueurBot = new JoueurBot(nom,strategie,compteurJoueur);
            compteurJoueur++;
            return joueurBot;
        }
    }

    public JoueurBot transformerUser(JoueurHumain joueurHumain, TypeStrategie strategieNom,boolean isStrategieExterne){
        Strategie strategie = isStrategieExterne ? new StrategieAdaptor(strategieNom) :
                StrategieFactory.creeStrategie(strategieNom);
        return joueurHumain.abandonner(strategie);
    }



}
