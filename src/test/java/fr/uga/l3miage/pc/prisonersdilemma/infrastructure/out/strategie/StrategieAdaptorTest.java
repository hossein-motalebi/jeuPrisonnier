package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.out.strategie;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategie;
import fr.uga.strats.g8.strategie.Strategie;
import fr.uga.strats.g8.enums.TypeAction;
import fr.uga.strats.g8.strategie.ToujoursCoopererStrategie;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;





class StrategieAdaptorTest {

    private Strategie mockStrategieExtern;
    private fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie strategieAdaptor;


    @Test
    void testDeciderTourTrahir() {
        List<TypeAction> lst = List.of(TypeAction.COOPERER);
        mockStrategieExtern= mock(ToujoursCoopererStrategie.class);

        Tour[] tours = new Tour[]{
                new Tour(Decision.COOPERER, Decision.COOPERER)
        };
        when(mockStrategieExtern.getAction(lst,3)).thenReturn(TypeAction.TRAHIR);
        strategieAdaptor = new StrategieAdaptor(TypeStrategie.TOUJOURS_TRAHIR);
        Decision decision = strategieAdaptor.deciderTour(tours, 1, 2);
        assertEquals(Decision.TRAHIR, decision);

    }

    @Test
    void testDeciderTourCooperer() {
        List<TypeAction> lst = List.of(TypeAction.COOPERER);
        mockStrategieExtern= mock(ToujoursCoopererStrategie.class);

        Tour[] tours = new Tour[]{
                new Tour(Decision.COOPERER, Decision.COOPERER)
        };
        when(mockStrategieExtern.getAction(lst,3)).thenReturn(TypeAction.TRAHIR);
        strategieAdaptor = new StrategieAdaptor(TypeStrategie.TOUJOURS_COOPERER);
        Decision decision = strategieAdaptor.deciderTour(tours, 2, 1);
        assertEquals(Decision.COOPERER, decision);

    }

    @Test
    void testArrayToListConverter() {
        Tour[] tours = new Tour[]{
                new Tour(Decision.COOPERER, Decision.COOPERER),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.TRAHIR, Decision.COOPERER)
        };
        strategieAdaptor = new StrategieAdaptor(TypeStrategie.TOUJOURS_COOPERER);
        List<TypeAction> lst = ((StrategieAdaptor)strategieAdaptor).arrayToListConverter(tours, 1);
        assertEquals(List.of(TypeAction.COOPERER, TypeAction.TRAHIR, TypeAction.TRAHIR), lst);
        assertFalse(lst.isEmpty());
        assertNotEquals(List.of(TypeAction.TRAHIR, TypeAction.TRAHIR, TypeAction.COOPERER), lst);

    }

    @Test
    void testArrayToListConverterVide() {
        Tour[] tours = new Tour[]{
        };
        strategieAdaptor = new StrategieAdaptor(TypeStrategie.TOUJOURS_COOPERER);
        List<TypeAction> lst = ((StrategieAdaptor)strategieAdaptor).arrayToListConverter(tours, 1);
        assertTrue(lst.isEmpty());

    }
}