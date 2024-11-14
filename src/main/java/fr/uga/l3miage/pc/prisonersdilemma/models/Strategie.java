package fr.uga.l3miage.pc.prisonersdilemma.models;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;


public interface Strategie {
    Decision deciderTour(Tour[] tours,int idJoueur ,int idAdversaire);
}
