package fr.uga.l3miage.pc.prisonersdilemma.mappers;

import fr.uga.l3miage.pc.prisonersdilemma.dto.OutPartieDTO;
import fr.uga.l3miage.pc.prisonersdilemma.models.*;

public class OutPartieDtoMapper {

    private OutPartieDtoMapper(){
        throw new IllegalStateException("c'est une utiliy class");
    }

    public static OutPartieDTO map(Partie partie){
        Joueur joueur1=partie.getJoueur1();
        Joueur joueur2=partie.getJoueur2();
        OutPartieDTO.OutPartieDTOBuilder builder= OutPartieDTO.builder()
                .nomJoueur1(joueur1.getNom())
                .nomJoueur2(joueur2.getNom())
                .idPlayer1(joueur1.getId())
                .idPlayer2(joueur2.getId())
                .scoreJoueur1(joueur1.getScore())
                .scoreJoueur2(joueur2.getScore())
                .nbTourLeft(partie.getNbMaxTours()-partie.getToursCopy().length)
                .historique(partie.getToursCopy());


        builder.joueur1bot(joueur1 instanceof JoueurBot);
        builder.joueur2bot(joueur2 instanceof JoueurBot);

        return builder.build();
    }
}
