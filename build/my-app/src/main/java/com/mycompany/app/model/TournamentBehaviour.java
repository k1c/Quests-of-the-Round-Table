package com.mycompany.app.model;

public class TournamentBehaviour extends StoryBehaviour{

	public TournamentBehaviour(int numShields){
		super(numShields);

	}

	public void applyBehaviour(GameBoard b, int player){

		//award shields to remaining participants
		for (Player p : b.participants){
			System.out.println("Num participants: " + b.numParticipants);
			System.out.println("this.numShields: " + this.numShields);
			p.rank.addRemoveShields(b.numParticipants + this.numShields );
		}

	}

}
