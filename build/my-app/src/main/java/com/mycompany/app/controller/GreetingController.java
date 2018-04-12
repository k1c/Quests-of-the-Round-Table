package com.mycompany.app.controller;


import com.mycompany.app.model.Card;
import com.mycompany.app.model.GameModel;
import com.mycompany.app.model.GameStates;
import com.mycompany.app.model.GenericPlayer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GreetingController {

    private static GameModel gameModel;
    private int player_counter = 0;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/newgame")
    public String newgame() {
        return "newgame";
    }

    @PostMapping("/newgame")
    public String newgame(@RequestParam(name="num_humans") int num_humans, @RequestParam(name="num_ai1") int num_ai1, @RequestParam(name="num_ai2") int num_ai2) {
         gameModel = new GameModel();
        String[] s = {"", "", "", ""};
        gameModel.initGame(num_humans, num_ai1, num_ai2, s);
        player_counter = 0;
        return "lobby";
    }

    @GetMapping("/lobby")
    public String lobby(){
        return "lobby";
    }

    @ResponseBody
    @PostMapping("/namesubmit")
    public void namesubmit(@RequestParam(name="name") String name, HttpServletResponse res){
        List<GenericPlayer> ps = gameModel.getHumanPlayers();
        GenericPlayer p = gameModel.getHumanPlayers().get(player_counter);
        gameModel.setPlayerName(p.id(), name);
        player_counter++;
        res.addCookie(new Cookie("id", p.id() + ""));
    }

    @ResponseBody
    @GetMapping("/waiting")
    public List<GenericPlayer> waiting() {
        List<GenericPlayer> p = gameModel.getHumanPlayers();

        if (p != null)
            return p;

        return null;
    }

    @ResponseBody
    @GetMapping("/currentplayer")
    public GenericPlayer currentPlayer(@RequestParam(name="id") int id){
        return gameModel.getPlayer(id);
    }

    @ResponseBody
    @GetMapping("/waitingplayers")
    public List<GenericPlayer> waitingplayers(@RequestParam(name="id") int id){
        return gameModel.otherPlayers(id);
    }

    @GetMapping("/game")
    public String game() {
        return "game";
    }

    @ResponseBody
    @GetMapping("/checkready")
    public boolean checkready() {
        return player_counter == gameModel.getHumanPlayers().size();
    }

    @ResponseBody
    @GetMapping("/currentturn")
    public int currentturn() {
        return gameModel.getCurrentPlayer().id();
    }
    @ResponseBody
    @GetMapping("/state")
    public GameStates state() {
        return gameModel.getState();
    }

    @ResponseBody
    @GetMapping("/storyCard")
    public Card storyCard() {
        return gameModel.getCurrentStory();
    }

    @ResponseBody
    @GetMapping("/nextState")
    public void nextState(){
        gameModel.next();
    }

    @ResponseBody
    @PostMapping("/discardCards")
    public void discardCards(@RequestBody int[] cards, @RequestParam(name="id") int id) {
        List<Integer> ids = new ArrayList<>();
        for (int i : cards) {
            ids.add(i);
        }
        List<Card> converted = gameModel.convertAdventureIds(ids);

        gameModel.discard(id, converted);
    }

    @ResponseBody
    @GetMapping("/checkUpdate")
    public int checkUpdate() {
        return gameModel.getStateCounter();
    }
}
