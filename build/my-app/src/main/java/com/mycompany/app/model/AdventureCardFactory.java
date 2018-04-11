package com.mycompany.app.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdventureCardFactory extends CardFactory{

    public static AdventureCard createCard(Types type){
        switch (type) {
            //WEAPONS
            case EXCALIBUR:
                return defaultWeapon(Types.EXCALIBUR.ordinal(), "/W Excalibur.jpg", "Excalibur", 30);
            case LANCE:
                return defaultWeapon(Types.LANCE.ordinal(), "/W Lance.jpg", "Lance", 20);
            case BATTLE_AX:
                return defaultWeapon(Types.BATTLE_AX.ordinal(), "/W Battle-ax.jpg", "Battle-ax", 15);
            case SWORD:
                return defaultWeapon(Types.SWORD.ordinal(), "/W Sword.jpg", "Sword", 10);
            case HORSE:
                return defaultWeapon(Types.HORSE.ordinal(), "/W Horse.jpg", "Horse", 10);
            case DAGGER:
                return defaultWeapon(Types.DAGGER.ordinal(), "/W Dagger.jpg", "Dagger", 5);

            //FOES
            case DRAGON: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(Types.SEARCH_FOR_THE_HOLY_GRAIL.ordinal(), Types.DEFEND_THE_QUEENS_HONOR.ordinal(), Types.SLAY_THE_DRAGON.ordinal()));
                return specifiedFoe(Types.DRAGON.ordinal(), "/F Dragon.jpg", "Dragon", 50, 70, specifiers); }
            case GIANT:
                return defaultFoe(Types.GIANT.ordinal(), "/F Giant.jpg", "Giant", 40);
            case MORDRED:
                return defaultFoe(Types.MORDRED.ordinal(), "/F Mordred.jpg", "Mordred", 30);
            case GREEN_KNIGHT: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(Types.SEARCH_FOR_THE_HOLY_GRAIL.ordinal(), Types.TEST_OF_THE_GREEN_KNIGHT.ordinal(), Types.DEFEND_THE_QUEENS_HONOR.ordinal()));
                return specifiedFoe(Types.GREEN_KNIGHT.ordinal(), "/F Green Knight.jpg", "Green Knight", 25, 40, specifiers); }
            case BLACK_KNIGHT: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(Types.SEARCH_FOR_THE_HOLY_GRAIL.ordinal(), Types.DEFEND_THE_QUEENS_HONOR.ordinal(), Types.RESCUE_THE_FAIR_MAIDEN.ordinal()));
                return specifiedFoe(Types.BLACK_KNIGHT.ordinal(), "/F Black Knight.jpg", "Black Knight", 25, 35, specifiers); }
            case EVIL_KNIGHT: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(Types.SEARCH_FOR_THE_HOLY_GRAIL.ordinal(), Types.DEFEND_THE_QUEENS_HONOR.ordinal(), Types.JOURNEY_THROUGH_THE_ENCHANTED_FOREST.ordinal()));
                return specifiedFoe(Types.EVIL_KNIGHT.ordinal(), "/F Evil Knight.jpg", "Evil Knight", 20, 30, specifiers); }
            case SAXON_KNIGHT: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(Types.SEARCH_FOR_THE_HOLY_GRAIL.ordinal(), Types.DEFEND_THE_QUEENS_HONOR.ordinal(), Types.REPEL_THE_SAXON_RAIDERS.ordinal()));
                return specifiedFoe(Types.SAXON_KNIGHT.ordinal(), "/F Saxon Knight.jpg", "Saxon Knight", 15, 25, specifiers); }
            case ROBBER_KNIGHT:
                return defaultFoe(Types.ROBBER_KNIGHT.ordinal(), "/F Robber Knight.jpg", "Robber Knight", 15);
            case SAXONS: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(Types.SEARCH_FOR_THE_HOLY_GRAIL.ordinal(), Types.DEFEND_THE_QUEENS_HONOR.ordinal(), Types.REPEL_THE_SAXON_RAIDERS.ordinal()));
                return specifiedFoe(Types.SAXONS.ordinal(), "/F Saxons.jpg", "Saxons", 10, 20, specifiers); }
            case BOAR: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(Types.SEARCH_FOR_THE_HOLY_GRAIL.ordinal(), Types.DEFEND_THE_QUEENS_HONOR.ordinal(), Types.BOAR_HUNT.ordinal()));
                return specifiedFoe(Types.BOAR.ordinal(), "/F Boar.jpg", "Boar", 5, 15, specifiers); }
            case THIEVES:
                return defaultFoe(Types.THIEVES.ordinal(), "/F Thieves.jpg", "Thieves", 5);

            //TESTS
            case TEST_OF_VALOR:
                return defaultTest(Types.TEST_OF_VALOR.ordinal(), "/T Test of Valor.jpg", "Test of Valor", 1);
            case TEST_OF_TEMPTATION:
                return defaultTest(Types.TEST_OF_TEMPTATION.ordinal(), "/T Test of Temptation.jpg", "Test of Temptation", 1);
            case TEST_OF_MORGAN_LE_FAY:
                return defaultTest(Types.TEST_OF_MORGAN_LE_FAY.ordinal(), "/T Test of Morgan Le Fey.jpg", "Test of Morgan Le Fey", 3);
            case TEST_OF_THE_QUESTING_BEAST: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(Types.SEARCH_FOR_THE_QUESTING_BEAST.ordinal()));
                return specifiedTest(Types.TEST_OF_THE_QUESTING_BEAST.ordinal(), "/T Test of the Questing Beast.jpg", "Test of the Questing Beast", 1, 4, specifiers); }

            //ALLIES
            case SIR_GALAHAD:
                return defaultAlly(Types.SIR_GALAHAD.ordinal(), "/A Sir Galahad.jpg", "Sir Galahad", 15, 1, true);
            case SIR_LANCELOT: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(Types.DEFEND_THE_QUEENS_HONOR.ordinal()));
                return specifiedAlly(Types.SIR_LANCELOT.ordinal(), "/A Sir Lancelot.jpg", "Sir Lancelot", 15, 1, 25, 1, false, false, specifiers); }
            case KING_ARTHUR:
                return defaultAlly(Types.KING_ARTHUR.ordinal(), "/A King Arthur.jpg", "King Arthur", 10, 2, true);
            case SIR_TRISTAN: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(Types.QUEEN_ISEULT.ordinal()));
                return specifiedAlly(Types.SIR_TRISTAN.ordinal(), "/A Sir Tristan.jpg", "Sir Tristan", 10, 1, 20, 1, false, false, specifiers); }
            case KING_PELLINORE: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(Types.SEARCH_FOR_THE_QUESTING_BEAST.ordinal()));
                return specifiedAlly(Types.KING_PELLINORE.ordinal(), "/A King Pellinore.jpg", "King Pellinore", 10, 1, 10, 4, false, true, specifiers); }
            case SIR_GAWAIN: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(Types.TEST_OF_THE_GREEN_KNIGHT.ordinal()));
                return specifiedAlly(Types.SIR_GAWAIN.ordinal(), "/A Sir Gawain.jpg", "Sir Gawain", 10, 1, 20, 1, false, false, specifiers); }
            case SIR_PERCIVAL: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(Types.SEARCH_FOR_THE_HOLY_GRAIL.ordinal()));
                return specifiedAlly(Types.SIR_PERCIVAL.ordinal(), "/A Sir Percival.jpg", "Sir Percival", 5, 1, 20, 1, false, false, specifiers); }
            case QUEEN_GUINEVERE:
                return defaultAlly(Types.QUEEN_GUINEVERE.ordinal(), "/A Queen Guinevere.jpg", "Queen Guinevere", 0, 3, true);
            case QUEEN_ISEULT: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(Types.SIR_TRISTAN.ordinal()));
                return specifiedAlly(Types.QUEEN_ISEULT.ordinal(), "/A Queen Iseult.jpg", "Queen Iseult", 0, 2, 0, 4, true, true, specifiers); }
            case MERLIN:
                return defaultAlly(Types.MERLIN.ordinal(), "/A Merlin.jpg", "Merlin", 0, 1, false);

            //AMOURS
            case AMOUR:
                return defaultAmour(Types.AMOUR.ordinal(), "/Amour.jpg", "Amour");
        }
        return null;

    }


    public List<AdventureCard> createCardList(Types type, int frequency){
        List<AdventureCard> temp = new ArrayList<AdventureCard>();
        for (int i = 0; i < frequency; i++){
            temp.add(createCard(type));
        }

        return temp;
    }

    public Card getCard(int id){
	if(id >=0 && id < types.length){
		return createCard(types[id]);
	}
	return null;
    }



    //ALLIES
    public static AdventureCard defaultAlly(int id, String res, String name, int BP, int bids, boolean freeBids){
        return new AllyCard(id, res, new DefaultBehaviour(BP, bids, freeBids), name);
    }

    public static AdventureCard specifiedAlly(int id, String res, String name, int defaultBP, int defaultBids, int specifiedBP, int specifiedBids, boolean defaultFreeBid, boolean specifiedFreeBid, List<Integer> specifiers){
        return new AllyCard(id, res, new SpecifiedBehaviour(defaultBP, defaultBids, specifiedBP, specifiedBids, defaultFreeBid, specifiedFreeBid, specifiers), name);
    }

    //FOES

    public static AdventureCard defaultFoe(int id, String res, String name,int BP){
        return new FoeCard(id, res, new DefaultBehaviour(BP, 1), name);
    }

    public static AdventureCard specifiedFoe(int id, String res, String name, int defaultBP, int specifiedBP, List<Integer> specifiers){
        return new FoeCard(id, res, new SpecifiedBehaviour(defaultBP, 1, specifiedBP, 1, false, false, specifiers), name);
    }

    //WEAPONS

    public static AdventureCard defaultWeapon(int id, String res, String name, int BP){
        return new WeaponCard(id, res, new DefaultBehaviour(BP, 1), name);
    }

    //AMOURS

    public static AdventureCard defaultAmour(int id, String res, String name){
        return new AmourCard(id, res, new DefaultBehaviour(10, 1, true), name);
    }

    //TESTS

    public static AdventureCard defaultTest(int id, String res, String name, int bids){
        return new TestCard(id, res, new TestBehaviour(bids), name);
    }

    public static AdventureCard specifiedTest(int id, String res, String name, int defaultBids, int specifiedBids, List<Integer> specifiers){
        return new TestCard(id, res, new TestBehaviour(defaultBids, specifiedBids, specifiers), name);
    }

}
