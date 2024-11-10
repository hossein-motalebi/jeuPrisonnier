package fr.uga.l3miage.pc.prisonersdilemma.models;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

import java.util.List;

public interface Strategie {
    public Decision deciderTour(Tour[] tours);
}
