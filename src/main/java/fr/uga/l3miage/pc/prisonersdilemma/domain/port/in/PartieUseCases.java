package fr.uga.l3miage.pc.prisonersdilemma.domain.port.in;

import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.DecisionDTO;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.InitPartieDTO;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.OutPartieDTO;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.AbandonnerDTO;


public interface PartieUseCases {

    OutPartieDTO demarrerPartie(InitPartieDTO initPartieDTO);
    OutPartieDTO jouerTour(DecisionDTO decisionDTO);
    OutPartieDTO abandonner(AbandonnerDTO abandonnerDTO);

}
