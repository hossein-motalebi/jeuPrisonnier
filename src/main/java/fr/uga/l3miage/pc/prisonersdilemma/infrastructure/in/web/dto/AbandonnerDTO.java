package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategie;

public record AbandonnerDTO(int idPlayer, TypeStrategie strategie, boolean strategieExterne) {
}
 