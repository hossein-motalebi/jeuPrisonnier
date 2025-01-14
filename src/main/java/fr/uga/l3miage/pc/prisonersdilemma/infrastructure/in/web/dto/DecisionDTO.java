package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;

public record DecisionDTO(Decision decisionJoueur1, Decision decisionJoueur2) {
}
