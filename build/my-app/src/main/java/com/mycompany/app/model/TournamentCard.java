package com.mycompany.app.model;

import com.mycompany.app.model.StoryCard;

public class TournamentCard extends StoryCard{
    public TournamentCard(int id, String res, StoryBehaviour behaviour, String name){
        super(id,res,behaviour,name,Types.TOURNAMENT);

    }
}
