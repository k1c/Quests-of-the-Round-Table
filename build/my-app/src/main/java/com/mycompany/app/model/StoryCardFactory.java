package com.mycompany.app.model;
import com.mycompany.app.model.*;

public class StoryCardFactory{

    public static StoryCard defaultQuest(int id, String res, String name, int numStages){
        return new QuestCard(id, res, name, numStages, 0);
    }

    public static StoryCard defaultTournament(int id, String res, String name, int numBonusShields){
        return new TournamentCard(id, res, name, 0, numBonusShields);
    }

    public static StoryCard defaultEvent(int id, String res, String name) {
        return new EventCard(id, res, name, 0, 0);
    }


}