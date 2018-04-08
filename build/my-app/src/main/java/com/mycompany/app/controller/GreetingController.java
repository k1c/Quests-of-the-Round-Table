package com.mycompany.app.controller;


import com.mycompany.app.model.Players.GenericPlayer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.mycompany.app.model.states_api.GameState.GameModel;

@Controller
public class GreetingController {

    private static GameModel gameModel = new GameModel();


    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @ResponseBody
    @PostMapping("/setup")
    public void setup(){
        String[] s = {"Akhil", "Carolyne", "Meow"};
        gameModel.initGame(3, 0, 0, s);
    }

    @ResponseBody
    @GetMapping("/currentplayer")
    public GenericPlayer currentPlayer(){
        return gameModel.getCurrentPlayer();
    }
}
