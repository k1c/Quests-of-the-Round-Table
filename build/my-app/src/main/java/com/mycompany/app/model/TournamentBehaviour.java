package com.mycompany.app.model;

public class TournamentBehaviour extends StoryBehaviour{

	public TournamentBehaviour(int numShields){
		super(numShields);

	}

	public void applyBehaviour(GameBoard b, int player){

		//award shields to remaining participants
		System.out.println("WOKING HERE");
		for (Player p : b.participants){
			p.rank.addRemoveShields(b.currentStory.getNumStages() + this.numShields );
		}

	}

}
