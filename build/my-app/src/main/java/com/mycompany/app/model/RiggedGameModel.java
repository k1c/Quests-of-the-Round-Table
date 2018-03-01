package com.mycompany.app.model;

import java.util.ArrayList;
import java.util.List;

public class RiggedGameModel{
		
	public static GameBoard rig1(){
		AdventureCardFactory F = new AdventureCardFactory();

		GameBoard b = new GameBoard();

		HumanPlayer p1 = new HumanPlayer("Player 1", "Shield Blue.png");
		p1.hand.add(F.createCard(AdventureCardFactory.Types.SAXONS));
		p1.hand.add(F.createCard(AdventureCardFactory.Types.BOAR));
		p1.hand.add(F.createCard(AdventureCardFactory.Types.DAGGER));
		p1.hand.add(F.createCard(AdventureCardFactory.Types.SWORD));

		HumanPlayer p2 = new HumanPlayer("Player 1", "Shield Green.png");
		p2.hand.add(F.createCard(AdventureCardFactory.Types.DAGGER));

		HumanPlayer p3 = new HumanPlayer("Player 1", "Shield Red.png");
		p3.hand.add(F.createCard(AdventureCardFactory.Types.HORSE));
		p3.hand.add(F.createCard(AdventureCardFactory.Types.EXCALIBUR));
		p3.hand.add(F.createCard(AdventureCardFactory.Types.AMOUR));

		HumanPlayer p4 = new HumanPlayer("Player 1", "Shield Purple.png");
		p4.hand.add(F.createCard(AdventureCardFactory.Types.BATTLE_AX));
		p4.hand.add(F.createCard(AdventureCardFactory.Types.LANCE));
		p4.hand.add(F.createCard(AdventureCardFactory.Types.THIEVES));


		b.initRig(new ArrayList<AbstractAI>(),new ArrayList<HumanPlayer>(),
				CardLoader.loadAdventureCards(), CardLoader.rigGame1(),false,false,true);
		return b;
	}
}
