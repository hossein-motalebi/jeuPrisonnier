package fr.uga.l3miage.pc.prisonersdilemma.domain.port.in;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Joueur;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurBot;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurHumain;

public interface JoueurUseCases
{
    Joueur creerUser(String nom, Boolean isBot, TypeStrategie strategieNom, boolean isStrategieExterne);
    JoueurBot transformerUser(JoueurHumain joueurHumain, TypeStrategie strategieNom, boolean isStrategieExterne);

}
