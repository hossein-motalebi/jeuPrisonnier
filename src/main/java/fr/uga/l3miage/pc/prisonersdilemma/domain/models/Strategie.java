package fr.uga.l3miage.pc.prisonersdilemma.domain.models;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;


public interface Strategie {
    Decision deciderTour(Tour[] tours,int idJoueur ,int idAdversaire);
}
