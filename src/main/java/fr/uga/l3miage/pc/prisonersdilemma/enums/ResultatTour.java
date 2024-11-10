package fr.uga.l3miage.pc.prisonersdilemma.enums;

public enum ResultatTour {
    TENTATION(5), //T
    RECOMPENSE(3), //C
    PUNITION(1), //P
    DUPERIE(0); //D

    ResultatTour(int points) {
        this.points = points;
    }

    public final int points;

    public int getPoints() {
        return points;
    }
}
