package com.mycompany.app.model;

import java.util.ArrayList;
import java.util.List;

public class RiggedGameModel{
		
	public static GameBoard rig1(){
		AdventureCardFactory F = new AdventureCardFactory();
		List<HumanPlayer> humans = new ArrayList<>();
		GameBoard b = new GameBoard();

		HumanPlayer p1 = new HumanPlayer("Player 1", "Shield Blue.png");
		p1.hand.add(F.createCard(AdventureCardFactory.Types.SAXONS));
		p1.hand.add(F.createCard(AdventureCardFactory.Types.BOAR));
		p1.hand.add(F.createCard(AdventureCardFactory.Types.DAGGER));
		p1.hand.add(F.createCard(AdventureCardFactory.Types.SWORD));

		HumanPlayer p2 = new HumanPlayer("Player 2", "Shield Green.png");
		p2.hand.add(F.createCard(AdventureCardFactory.Types.DAGGER));

		HumanPlayer p3 = new HumanPlayer("Player 3", "Shield Red.png");
		p3.hand.add(F.createCard(AdventureCardFactory.Types.HORSE));
		p3.hand.add(F.createCard(AdventureCardFactory.Types.EXCALIBUR));
		p3.hand.add(F.createCard(AdventureCardFactory.Types.AMOUR));

		HumanPlayer p4 = new HumanPlayer("Player 4", "Shield Purple.png");
		p4.hand.add(F.createCard(AdventureCardFactory.Types.BATTLE_AX));
		p4.hand.add(F.createCard(AdventureCardFactory.Types.LANCE));
		p4.hand.add(F.createCard(AdventureCardFactory.Types.THIEVES));

		humans.add(p1);
		humans.add(p2);
		humans.add(p3);
		humans.add(p4);

		b.initRig(new ArrayList<AbstractAI>(),humans,
				CardLoader.loadAdventureCards(), CardLoader.rigGame1(),true,false,true);
		return b;
	}

	public static GameBoard rig2(){
		AdventureCardFactory F = new AdventureCardFactory();
		List<HumanPlayer> humans = new ArrayList<>();
		GameBoard b = new GameBoard();

		HumanPlayer p1 = new HumanPlayer("Player 1", "Shield Blue.png");
		p1.hand.add(F.createCard(AdventureCardFactory.Types.SAXONS));
		p1.hand.add(F.createCard(AdventureCardFactory.Types.BOAR));
		p1.hand.add(F.createCard(AdventureCardFactory.Types.DAGGER));
		p1.hand.add(F.createCard(AdventureCardFactory.Types.SWORD));

		HumanPlayer p2 = new HumanPlayer("Player 2", "Shield Green.png");
		p2.hand.add(F.createCard(AdventureCardFactory.Types.DAGGER));
		p2.hand.add(F.createCard(AdventureCardFactory.Types.TEST_OF_VALOR));
		p2.hand.add(F.createCard(AdventureCardFactory.Types.ROBBER_KNIGHT));
		p2.hand.add(F.createCard(AdventureCardFactory.Types.EVIL_KNIGHT));
		p2.hand.add(F.createCard(AdventureCardFactory.Types.DRAGON));

		HumanPlayer p3 = new HumanPlayer("Player 3", "Shield Red.png");
		p3.hand.add(F.createCard(AdventureCardFactory.Types.HORSE));
		p3.hand.add(F.createCard(AdventureCardFactory.Types.EXCALIBUR));
		p3.hand.add(F.createCard(AdventureCardFactory.Types.AMOUR));

		HumanPlayer p4 = new HumanPlayer("Player 4", "Shield Purple.png");
		p4.hand.add(F.createCard(AdventureCardFactory.Types.BATTLE_AX));
		p4.hand.add(F.createCard(AdventureCardFactory.Types.LANCE));
		p4.hand.add(F.createCard(AdventureCardFactory.Types.THIEVES));

		humans.add(p1);
		humans.add(p2);
		humans.add(p3);
		humans.add(p4);

		b.initRig(new ArrayList<AbstractAI>(),humans,
				CardLoader.loadAdventureCards(), CardLoader.rigGame2(),true,false,true);
		return b;
	}

	public static GameBoard rig3(){
		AdventureCardFactory F = new AdventureCardFactory();
		List<HumanPlayer> humans = new ArrayList<>();
		GameBoard b = new GameBoard();

		HumanPlayer p1 = new HumanPlayer("Player 1", "Shield Blue.png");
		p1.inPlay.add(F.createCard(AdventureCardFactory.Types.MERLIN));
		p1.inPlay.add(F.createCard(AdventureCardFactory.Types.SIR_TRISTAN));

		HumanPlayer p2 = new HumanPlayer("Player 2", "Shield Green.png");
		p2.inPlay.add(F.createCard(AdventureCardFactory.Types.QUEEN_ISEULT));

		HumanPlayer p3 = new HumanPlayer("Player 3", "Shield Red.png");

		HumanPlayer p4 = new HumanPlayer("Player 4", "Shield Purple.png");

		humans.add(p1);
		humans.add(p2);
		humans.add(p3);
		humans.add(p4);

		b.initRig(new ArrayList<AbstractAI>(),humans,
				CardLoader.loadAdventureCards(), CardLoader.rigGame3(),true,false,true);
		return b;
	}
}
