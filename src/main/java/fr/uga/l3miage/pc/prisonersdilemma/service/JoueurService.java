package fr.uga.l3miage.pc.prisonersdilemma.service;
import fr.uga.l3miage.pc.prisonersdilemma.factory.StrategieFactory;
import fr.uga.l3miage.pc.prisonersdilemma.models.*;
import lombok.Setter;
import org.springframework.stereotype.Service;
import fr.uga.l3miage.pc.prisonersdilemma.enums.*;

@Setter
@Service
public class JoueurService { //NOSONAR

        /*TODO //NOSONAR
    Gérer les opérations liées aux joueurs.
Créer de nouveaux joueurs (humains ou bots).
Gérer l'abandon d'un joueur humain et sa substitution par un bot.
Maintenir les informations sur les joueurs (par exemple, le score, l'historique des décisions).
     */

    //compteurJoueur pour donner un ID à user comme quoi c'est le user 1 ou 2
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
