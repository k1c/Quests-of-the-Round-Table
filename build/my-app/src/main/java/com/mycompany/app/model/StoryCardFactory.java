package com.mycompany.app.model;
import com.mycompany.app.model.*;

import java.util.ArrayList;
import java.util.List;

public class StoryCardFactory{

    public static enum Types {SEARCH_FOR_THE_HOLY_GRAIL, TEST_OF_THE_GREEN_KNIGHT, SEARCH_FOR_THE_QUESTING_BEAST, DEFEND_THE_QUEENS_HONOR,
                                RESCUE_THE_FAIR_MAIDEN, JOURNEY_THROUGH_THE_ENCHANTED_FOREST, VANQUISH_KING_ARTHURS_ENEMIES,
                                SLAY_THE_DRAGON, BOAR_HUNT, REPEL_THE_SAXON_RAIDERS,
                                TOURNAMNENT_AT_CAMELOT, TOURNAMENT_AT_ORKNEY, TOURNAMENT_AT_TINTAGEL, TOURNAMENT_AT_YORK,
                                KINGS_RECOGNITION, QUEENS_FAVOR, COURT_CALLED_TO_CAMELOT, POX, PLAGUE, CHIVALROUS_DEED,
                                PROSPERITY_THROUGHOUT_THE_REALM, KINGS_CALL_TO_ARMS};

    Types type;

    public static StoryCard createCard(Types type){
        switch (type){
            case SEARCH_FOR_THE_HOLY_GRAIL:
                return defaultQuest(32, "Q Search for the Holy Grail.jpg", "Search for the Holy Grail", 5);
        }
        return null;
    }


    public static StoryCard defaultQuest(int id, String res, String name, int numShields){
        return new QuestCard(id, res, new StoryBehaviour(numShields), name);
    }

    public static StoryCard defaultTournament(int id, String res, String name, int numShields){
        return new TournamentCard(id, res, new StoryBehaviour(numShields), name);
    }

    public static StoryCard defaultEvent(int id, String res, String name, int numShields) {
        return new EventCard(id, res, new StoryBehaviour(numShields), name);
    }


}
