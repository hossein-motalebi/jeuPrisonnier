package fr.uga.l3miage.pc.prisonersdilemma.service;

import fr.uga.l3miage.pc.prisonersdilemma.dto.InitPartieDTO;
import fr.uga.l3miage.pc.prisonersdilemma.dto.OutPartieDTO;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.mappers.OutPartieDtoMapper;
import fr.uga.l3miage.pc.prisonersdilemma.models.Joueur;
import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurBot;
import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurHumain;
import fr.uga.l3miage.pc.prisonersdilemma.models.Partie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PartieServiceTest {

    private JoueurService joueurService;
    private PartieService partieService;

    @BeforeEach
    public void setUp() {
        joueurService = Mockito.mock(JoueurService.class);
        partieService = new PartieService(joueurService);
    }

    @Test
    void testDemarrerPartie() {
        InitPartieDTO initPartieDTO = new InitPartieDTO();
        initPartieDTO.setNomJoueur1("Player1");
        initPartieDTO.setJoueur1bot(false);
        initPartieDTO.setNomJoueur2("Player2");
        initPartieDTO.setJoueur2bot(true);
        initPartieDTO.setStrategieJoueur2(TypeStrategie.PAVLOV);
        initPartieDTO.setNbMaxTours(10);

        Joueur joueur1 = new JoueurHumain("Player1", 1);
        Joueur joueur2 = new JoueurBot("Player2", TypeStrategie.PAVLOV, 2);

        when(joueurService.creerUser("Player1", false, null)).thenReturn(joueur1);
        when(joueurService.creerUser("Player2", true, TypeStrategie.PAVLOV)).thenReturn(joueur2);


        OutPartieDTO actualOutPartieDTO = partieService.demarrerPartie(initPartieDTO);

        OutPartieDTO expectedOutPartieDTO = OutPartieDtoMapper.map(new Partie(10, joueur1, joueur2));


        assertEquals(expectedOutPartieDTO, actualOutPartieDTO);
        verify(joueurService, times(1)).creerUser("Player1", false, null);
        verify(joueurService, times(1)).creerUser("Player2", true, TypeStrategie.PAVLOV);
    }

}