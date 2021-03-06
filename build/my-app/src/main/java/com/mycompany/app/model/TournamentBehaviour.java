package com.mycompany.app.model;

public class TournamentBehaviour extends StoryBehaviour{

	public TournamentBehaviour(int numShields){
		super(numShields);

	}

	public void applyBehaviour(GameBoard b, int player){

		//award shields to remaining participants
		for (Player p : b.participants){
			p.rank.addRemoveShields(b.numParticipants + this.numShields );
		}

	}

}
