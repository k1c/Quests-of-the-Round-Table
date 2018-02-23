/*package com.mycompany.app.model;

import com.mycompany.app.model.AdventureCardFactoryTest;
import junit.framework.TestCase;
import java.util.*;


public class EventTest extends TestCase{
    public EventTest(String name){
        super(name);
    }

    public void testQueensFavor() throws Exception{
        GameBoard board = new GameBoard();
        board.initGame(4, new ArrayList<AdventureCard>(), new ArrayList<StoryCard>());

        board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.QUEENS_FAVOR);

        board.players = new ArrayList<Player>();

        for (int i = 0; i < 4; i++)
            board.players.add(new Player());


        board.applyStoryCardLogic(board.questSponsor);

        //assertEquals()




    }*/