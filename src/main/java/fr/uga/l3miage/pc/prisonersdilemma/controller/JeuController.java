package fr.uga.l3miage.pc.prisonersdilemma.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController

@RequestMapping("/jeu")
public class JeuController {

    @RequestMapping("/start")
    public String startGame() {
        return "Game started";
        //todo if the game is not started, start it
    }

}
