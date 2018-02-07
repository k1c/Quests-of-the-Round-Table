package com.mycompany.app.model;

import com.mycompany.app.model.StoryCard;

public class QuestCard extends StoryCard{

    public QuestCard(int id, String res, StoryBehaviour behaviour, String name){
        super(id,res,behaviour,name,Types.QUEST);

    }
}
