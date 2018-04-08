package com.mycompany.app.model.Cards;


import com.mycompany.app.model.Cards.Behaviours.StoryBehaviour;

public class TournamentCard extends StoryCard{
    public TournamentCard(int id, String res, StoryBehaviour behaviour, String name){
        super(id,res,behaviour,name,Types.TOURNAMENT);

    }
}
