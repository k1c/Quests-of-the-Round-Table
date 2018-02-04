package com.mycompany.app.model;
import com.mycompany.app.model.*;

public class StoryCardFactory{

    public static StoryCard defaultQuest(int id, String res, String name, int numShields){
        return new QuestCard(id, res, new StoryBehaviour(numShields), name);
    }

    public static StoryCard defaultTournament(int id, String res, String name, int numShields){
        return new TournamentCard(id, res, new StoryBehaviour(numShields), name);
    }

    public static StoryCard defaultEvent(int id, String res, String name, int numShields) {
        return new EventCard(id, res, new StoryBehaviour(0), name);
    }


}