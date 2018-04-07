package com.mycompany.app.controller;

import com.mycompany.app.model.GenericPlayer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.mycompany.app.model.GameModel;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class GreetingController {

    private static GameModel gameModel = new GameModel();
    private int i = 0;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/newgame")
    public String newgame() {
        return "newgame";
    }

    @GetMapping("/lobby")
    public String lobby(HttpServletResponse response) {
        response.addCookie(new Cookie("id", "" + i++));
        return "lobby";
    }
    @ResponseBody
    @PostMapping("/creategame")
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
