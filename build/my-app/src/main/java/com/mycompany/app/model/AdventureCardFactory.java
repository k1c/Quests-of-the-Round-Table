package com.mycompany.app.model;
import com.mycompany.app.model.*;

import java.util.ArrayList;
import java.util.List;

public class AdventureCardFactory{

    public static enum Types {EXCALIBUR, LANCE, BATTLE_AX, SWORD, HORSE, DAGGER, DRAGON, GIANT, MORDRED, GREEN_KNIGHT, BLACK_KNIGHT, EVIL_KNIGHT, SAXON_KNIGHT, ROBBER_KNIGHT, SAXONS, BOAR, THIEVES, TEST_OF_VALOR, TEST_OF_TEMPTATION, TEST_OF_MORGAN_LE_FAY, TEST_OF_THE_QUESTING_BEAST, SIR_GALAHAD, SIR_LANCELOT, KING_ARTHUR, SIR_TRISTAN, KING_PELLINORE, SIR_GAWAIN, SIR_PERCIVAL, QUEEN_GUINEVERE, QUEEN_ISEULT, MERLIN, AMOUR};

    public static AdventureCard createCard(Types type){
        switch (type) {
            //WEAPONS
            case EXCALIBUR:
                return defaultWeapon(1, "W Excalibur.jpg", "Excalibur", 30);
            case LANCE:
                return defaultWeapon(2, "W Lance.jpg", "Lance", 20);
            case BATTLE_AX:
                return defaultWeapon(3, "W Battle-ax.jpg", "Battle-ax", 15);
            case SWORD:
                return defaultWeapon(4, "W Sword.jpg", "Sword", 10);
            case HORSE:
                return defaultWeapon(5, "W Horse.jpg", "Horse", 10);
            case DAGGER:
                return defaultWeapon(6, "W Dagger.jpg", "Dagger", 5);
            //FOES
            case DRAGON:
                return specifiedFoe(7, "F Dragon.jpg", "Dragon", 50, 70);
            case GIANT:
                return defaultFoe(8, "F Giant.jpg", "Giant", 40);
            //case MORDRED:
            case GREEN_KNIGHT:
                return specifiedFoe(10, "F Green Knight.jpg", "Green Knight", 25, 40);
            case BLACK_KNIGHT:
                return specifiedFoe(11, "F Black Knight.jpg", "Black Knight", 25, 35);
            case EVIL_KNIGHT:
                return specifiedFoe(12, "F Evil Knight.jpg", "Evil Knight", 20, 30);
            case SAXON_KNIGHT:
                return specifiedFoe(13, "F Saxon Knight.jpg", "Saxon Knight", 15, 25);
            case ROBBER_KNIGHT:
                return defaultFoe(14, "F Robber Knight.jpg", "Robber Knight", 15);
            case SAXONS:
                return specifiedFoe(15, "F Saxons.jpg", "Saxons", 10, 20);
            case BOAR:
                return specifiedFoe(16, "F Boar.jpg", "Boar", 5, 15);
            case THIEVES:
                return defaultFoe(17, "F Thieves.jpg", "Thieves", 5);
            //TESTS
            //case TEST_OF_VALOR:
            //case TEST_OF_TEMPTATION:
            //case TEST_OF_MORGAN_LE_FAY:
            //case TEST_OF_THE_QUESTING_BEAST:
            //ALLIES
            case SIR_GALAHAD:
                return defaultAlly(22, "A Sir Galahad.jpg", "Sir Galahad", 15, 1, false);
            case SIR_LANCELOT:
                return specifiedAlly(23, "A Sir Lancelot.jpg", "Sir Lancelot", 15, 1, 25, 1, false);
            case KING_ARTHUR:
                return defaultAlly(24, "A King Arthur.jpg", "King Arthur", 10, 2, true);
            case SIR_TRISTAN:
                return specifiedAlly(25, "A Sir Tristan.jpg", "Sir Tristan", 10, 1, 20, 1, false);
            case KING_PELLINORE:
                return specifiedAlly(26, "A King Pellinore.jpg", "King Pellinore", 10, 0, 10, 4, true);
            case SIR_GAWAIN:
                return specifiedAlly(27, "A Sir Gawain.jpg", "Sir Gawain", 10, 1, 20, 1, false);
            case SIR_PERCIVAL:
                return specifiedAlly(28, "A Sir Percival.jpg", "Sir Percival", 5, 1, 20, 1, false);
            case QUEEN_GUINEVERE:
                return defaultAlly(29, "A Queen Guinevere.jpg", "Queen Guinevere", 0, 3, true);
            case QUEEN_ISEULT:
                return specifiedAlly(30, "A Queen Iseult.jpg", "Queen Iseult", 0, 2, 0, 4, true);
            //case MERLIN:

            case AMOUR:
                return defaultAmour(31, "Amour.jpg", "Amour");
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



    //ALLIES
    public static AdventureCard defaultAlly(int id, String res, String name, int BP, int bids, boolean freeBids){
        return new AllyCard(id, res, new DefaultBehaviour(BP, bids, freeBids), name);
    }

    public static AdventureCard specifiedAlly(int id, String res, String name, int defaultBP, int defaultBids, int specifiedBP, int specifiedBids, boolean freeBid){
        return new AllyCard(id, res, new SpecifiedBehaviour(defaultBP, defaultBids, specifiedBP, specifiedBids, freeBid), name);
    }

    //FOES

    public static AdventureCard defaultFoe(int id, String res, String name,int BP){
        return new FoeCard(id, res, new DefaultBehaviour(BP, 1), name);
    }

    public static AdventureCard specifiedFoe(int id, String res, String name, int defaultBP, int specifiedBP){
        return new FoeCard(id, res, new SpecifiedBehaviour(defaultBP, 1, specifiedBP, 1), name);
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
        return new TestCard(id, res, new DefaultBehaviour(0, bids), name);
    }

    public static AdventureCard specifiedTest(int id, String res, String name, int defaultBP, int defaultBids, int specifiedBP, int specifiedBids, boolean freeBid){
        return new TestCard(id, res, new SpecifiedBehaviour(0, defaultBids, 0, specifiedBids, freeBid), name);
    }

}

//Arrays.asList(" ", ...)