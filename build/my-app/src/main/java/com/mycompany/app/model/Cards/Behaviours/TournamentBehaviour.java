package com.mycompany.app.model.Cards.Behaviours;

import com.mycompany.app.model.Board.GameBoard;
import com.mycompany.app.model.Players.Player;

public class TournamentBehaviour extends StoryBehaviour{

	public TournamentBehaviour(int numShields){
		super(numShields);

	}

	public void applyBehaviour(GameBoard b, int player){

		//award shields to remaining participants
		System.out.println("WOKING HERE");
		for (Player p : b.participants){
			System.out.println("Num participants: " + b.numParticipants);
			System.out.println("this.numShields: " + this.numShields);
			p.rank.addRemoveShields(b.numParticipants + this.numShields );
		}

	}

}
