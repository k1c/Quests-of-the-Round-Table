package com.mycompany.app.model.Cards;


import com.mycompany.app.model.Cards.Behaviours.StoryBehaviour;

public class QuestCard extends StoryCard{

    public QuestCard(int id, String res, StoryBehaviour behaviour, String name){
        super(id,res,behaviour,name,Types.QUEST);
    }
}
