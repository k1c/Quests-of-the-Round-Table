package com.mycompany.app.model;

import com.mycompany.app.model.StoryCard;

public class TournamentCard extends StoryCard{
    public TournamentCard(int id, String res, String name, int numBonusShields){
        super(id,res,name,Types.TOURNAMENT,0,numCBonusShields);

    }
}
