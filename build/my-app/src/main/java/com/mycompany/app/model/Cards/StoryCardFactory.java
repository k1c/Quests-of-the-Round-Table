package com.mycompany.app.model;

import java.util.ArrayList;
import java.util.List;

public class StoryCardFactory extends CardFactory{


    Types type;

    public static StoryCard createCard(Types type){
        switch (type){
            case SEARCH_FOR_THE_HOLY_GRAIL:
                return defaultQuest(33, "/Q Search for the Holy Grail.jpg", "Search for the Holy Grail", 5);
            case TEST_OF_THE_GREEN_KNIGHT:
                return defaultQuest(34, "/Q Test of the Green Knight.jpg", "Test of the Green Knight", 4);
            case SEARCH_FOR_THE_QUESTING_BEAST:
                return defaultQuest(35, "/Q Search for the Questing Beast.jpg", "Search for the Questing Beast", 4);
            case DEFEND_THE_QUEENS_HONOR:
                return defaultQuest(36, "/Q Defend the Queen's Honor.jpg", "Defend the Queen's Honor", 4);
            case RESCUE_THE_FAIR_MAIDEN:
                return defaultQuest(37, "/Q Rescue the Fair Maiden.jpg", "Rescue the Fair Maiden", 3);
            case JOURNEY_THROUGH_THE_ENCHANTED_FOREST:
                return defaultQuest(38, "/Q Journey through the Enchanted Forest.jpg", "Journey through the Enchanted Forest", 3);
            case VANQUISH_KING_ARTHURS_ENEMIES:
                return defaultQuest(39, "/Q Vanquish King Arthur's Enemies.jpg", "Vanquish King Arthur's Enemies", 3);
            case SLAY_THE_DRAGON:
                return defaultQuest(40, "/Q Slay the Dragon.jpg", "Slay the Dragon", 3);
            case BOAR_HUNT:
                return defaultQuest(41, "/Q Boar Hunt.jpg", "Boar Hunt", 2);
            case REPEL_THE_SAXON_RAIDERS:
                return defaultQuest(42, "/Q Repel the Saxon Raiders.jpg", "Repel the Saxon Raiders", 2);
            case TOURNAMENT_AT_CAMELOT:
                return defaultTournament(43, "/QT Camelot.jpg", "Tournament at Camelot", 3);
            case TOURNAMENT_AT_ORKNEY:
                return defaultTournament(44, "/QT Orkney.jpg", "Tournament at Orkney", 2);
            case TOURNAMENT_AT_TINTAGEL:
                return defaultTournament(45, "/QT Tintagel.jpg", "Tournament at Tintagel", 1);
            case TOURNAMENT_AT_YORK:
                return defaultTournament(46, "/QT York.jpg", "Tournament at York", 0);
            case KINGS_RECOGNITION:
                return defaultEvent(47, "/E King's Recognition.jpg", "King's Recognition", new KingsRecognitionEvent(2));
            case QUEENS_FAVOR:
                return defaultEvent(48, "/E Queen's Favor.jpg", "Queen's Favor", new QueensFavorEvent(0));
            case COURT_CALLED_TO_CAMELOT:
                return defaultEvent(49, "/E Court Called Camelot.jpg", "Court Called to Camelot", new CourtCalledToCamelotEvent(0));
            case POX:
                return defaultEvent(50, "/E Pox.jpg", "Pox", new PoxEvent(-1));
            case PLAGUE:
                return defaultEvent(51, "/E Plague.jpg", "Plague", new PlagueEvent(-2));
            case CHIVALROUS_DEED:
                return defaultEvent(52, "/E Chivalrous Deed.jpg", "Chivalrous Deed", new ChivalrousDeedEvent(3));
            case PROSPERITY_THROUGHOUT_THE_REALM:
                return defaultEvent(53, "/E Prosperity Throughout the Realm.jpg", "Prosperity Throughout the Realm", new ProsperityEvent(0));
            case KINGS_CALL_TO_ARMS:
                return defaultEvent(54, "/E King's Call to Arms.jpg", "King's Call to Arms", new KingsCallToArms(0));
        }
        return null;
    }

    public List<StoryCard> createCardList(Types type, int frequency){
        List<StoryCard> temp = new ArrayList<StoryCard>();
        for (int i = 0; i < frequency; i++){
            temp.add(createCard(type));
        }

        return temp;
    }


    public static StoryCard defaultQuest(int id, String res, String name, int numShields){
        return new QuestCard(id, res, new QuestBehaviour(numShields), name);
    }

    public static StoryCard defaultTournament(int id, String res, String name, int numShields){
        return new TournamentCard(id, res, new TournamentBehaviour(numShields), name);
    }

    public static StoryCard defaultEvent(int id, String res, String name, StoryBehaviour behaviour) {
        return new EventCard(id, res, behaviour, name);
    }


}
