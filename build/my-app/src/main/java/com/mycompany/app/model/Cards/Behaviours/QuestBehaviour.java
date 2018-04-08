package com.mycompany.app.model.Cards.Behaviours;

import com.mycompany.app.model.Board.GameBoard;
import com.mycompany.app.model.Players.Player;

public class QuestBehaviour extends StoryBehaviour{

    public QuestBehaviour(int numShields){
        super(numShields);

    }

    public void applyBehaviour(GameBoard b, int player){
	int kings = 0;
	if(b.eventKingsRecognition != null){
		kings += b.eventKingsRecognition.getNumStages();	
	}

        //award shields to remaining participants
        for (Player p : b.participants){
            p.rank.addRemoveShields(b.currentStory.getNumStages()+kings);
        }

        //sponsor draws adventure cards
        for(int i = 0; i < (b.currentStory.getNumStages() + b.getNumQuestCards()); i++){
            b.drawFromAdventureDeck(b.sponsor);
        }


    }

}
