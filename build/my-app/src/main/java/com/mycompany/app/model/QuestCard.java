package com.mycompany.app.model;

import com.mycompany.app.model.StoryCard;

public class QuestCard extends StoryCard{

    public QuestCard(int id, String res, String name, int numStages){
        super(id,res,name,Types.QUEST,numStages,0);

    }
}
