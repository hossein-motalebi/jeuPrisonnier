package fr.uga.l3miage.pc.prisonersdilemma.dto;

import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;

public record AbandonnerDTO(int idPlayer , TypeStrategie strategie , boolean strategieExterne) {
}
