package com.mycompany.app.model;
import com.mycompany.app.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdventureCardFactory{

    public static enum Types {EXCALIBUR, LANCE, BATTLE_AX, SWORD, HORSE, DAGGER, DRAGON, GIANT, MORDRED, GREEN_KNIGHT, BLACK_KNIGHT, EVIL_KNIGHT, SAXON_KNIGHT, ROBBER_KNIGHT, SAXONS, BOAR, THIEVES, TEST_OF_VALOR, TEST_OF_TEMPTATION, TEST_OF_MORGAN_LE_FAY, TEST_OF_THE_QUESTING_BEAST, SIR_GALAHAD, SIR_LANCELOT, KING_ARTHUR, SIR_TRISTAN, KING_PELLINORE, SIR_GAWAIN, SIR_PERCIVAL, QUEEN_GUINEVERE, QUEEN_ISEULT, MERLIN, AMOUR};

    public static AdventureCard createCard(Types type){
        switch (type) {
            //WEAPONS
            case EXCALIBUR:
                return defaultWeapon(1, "/W Excalibur.jpg", "Excalibur", 30);
            case LANCE:
                return defaultWeapon(2, "/W Lance.jpg", "Lance", 20);
            case BATTLE_AX:
                return defaultWeapon(3, "/W Battle-ax.jpg", "Battle-ax", 15);
            case SWORD:
                return defaultWeapon(4, "/W Sword.jpg", "Sword", 10);
            case HORSE:
                return defaultWeapon(5, "/W Horse.jpg", "Horse", 10);
            case DAGGER:
                return defaultWeapon(6, "/W Dagger.jpg", "Dagger", 5);

            //FOES
            case DRAGON: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(33, 36, 40));
                return specifiedFoe(7, "/F Dragon.jpg", "Dragon", 50, 70, specifiers); }
            case GIANT:
                return defaultFoe(8, "/F Giant.jpg", "Giant", 40);
            case MORDRED:
                return defaultFoe(9, "/F Mordred.jpg", "Mordred", 30);
            case GREEN_KNIGHT: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(33, 34, 36));
                return specifiedFoe(10, "/F Green Knight.jpg", "Green Knight", 25, 40, specifiers); }
            case BLACK_KNIGHT: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(33, 36, 37));
                return specifiedFoe(11, "/F Black Knight.jpg", "Black Knight", 25, 35, specifiers); }
            case EVIL_KNIGHT: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(33, 36, 38));
                return specifiedFoe(12, "/F Evil Knight.jpg", "Evil Knight", 20, 30, specifiers); }
            case SAXON_KNIGHT: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(33, 36, 42));
                return specifiedFoe(13, "/F Saxon Knight.jpg", "Saxon Knight", 15, 25, specifiers); }
            case ROBBER_KNIGHT:
                return defaultFoe(14, "/F Robber Knight.jpg", "Robber Knight", 15);
            case SAXONS: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(33, 36, 42));
                return specifiedFoe(15, "/F Saxons.jpg", "Saxons", 10, 20, specifiers); }
            case BOAR: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(33, 36, 41));
                return specifiedFoe(16, "/F Boar.jpg", "Boar", 5, 15, specifiers); }
            case THIEVES:
                return defaultFoe(17, "/F Thieves.jpg", "Thieves", 5);

            //TESTS
            case TEST_OF_VALOR:
                return defaultTest(18, "/T Test of Valor.jpg", "Test of Valor", 1);
            case TEST_OF_TEMPTATION:
                return defaultTest(19, "/T Test of Temptation.jpg", "Test of Temptation", 1);
            case TEST_OF_MORGAN_LE_FAY:
                return defaultTest(20, "/T Test of Morgan Le Fey.jpg", "Test of Morgan Le Fey", 3);
            case TEST_OF_THE_QUESTING_BEAST: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(35));
                return specifiedTest(21, "/T Test of the Questing Beast.jpg", "Test of the Questing Beast", 1, 4, specifiers); }

            //ALLIES
            case SIR_GALAHAD:
                return defaultAlly(22, "/A Sir Galahad.jpg", "Sir Galahad", 15, 1, true);
            case SIR_LANCELOT: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(36));
                return specifiedAlly(23, "/A Sir Lancelot.jpg", "Sir Lancelot", 15, 1, 25, 1, false, false, specifiers); }
            case KING_ARTHUR:
                return defaultAlly(24, "/A King Arthur.jpg", "King Arthur", 10, 2, true);
            case SIR_TRISTAN: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(30));
                return specifiedAlly(25, "/A Sir Tristan.jpg", "Sir Tristan", 10, 1, 20, 1, false, false, specifiers); }
            case KING_PELLINORE: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(35));
                return specifiedAlly(26, "/A King Pellinore.jpg", "King Pellinore", 10, 1, 10, 4, false, true, specifiers); }
            case SIR_GAWAIN: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(34));
                return specifiedAlly(27, "/A Sir Gawain.jpg", "Sir Gawain", 10, 1, 20, 1, false, false, specifiers); }
            case SIR_PERCIVAL: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(33));
                return specifiedAlly(28, "/A Sir Percival.jpg", "Sir Percival", 5, 1, 20, 1, false, false, specifiers); }
            case QUEEN_GUINEVERE:
                return defaultAlly(29, "/A Queen Guinevere.jpg", "Queen Guinevere", 0, 3, true);
            case QUEEN_ISEULT: {
                List<Integer> specifiers = new ArrayList<Integer>(Arrays.asList(25));
                return specifiedAlly(30, "/A Queen Iseult.jpg", "Queen Iseult", 0, 2, 0, 4, true, true, specifiers); }
            case MERLIN:
                return defaultAlly(31, "/A Merlin.jpg", "Merlin", 0, 1, false);

            //AMOURS
            case AMOUR:
                return defaultAmour(32, "/Amour.jpg", "Amour");
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
