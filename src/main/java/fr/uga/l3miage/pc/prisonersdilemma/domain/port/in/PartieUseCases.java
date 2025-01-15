package fr.uga.l3miage.pc.prisonersdilemma.domain.port.in;

import java.util.List;

import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.*;

public interface PartieUseCases {

    void joinPartie(int idPartie, JoinPartieRequestDTO joinPartieRequestDTO);

    int initPartie(InitPartieInDTO initPartieInDTO);

    OutPartieDTO jouerTour(DecisionDTO decisionDTO, int idPartie);

    void abandonner(int idPartie, AbandonnerDTO abandonnerDTO);

    List<PartieEnAttendDTO> getPartiesEnAttend();
}
