package fr.uga.l3miage.pc.prisonersdilemma.domain.port.in;

import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.DecisionInDTO;

public interface DecisionUseCases {

    void ajouterDecision(int idPartie, DecisionInDTO decisionInDTO);

}
