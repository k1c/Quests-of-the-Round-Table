package com.mycompany.app.model;

import java.util.*;

public class StoryDeck {

    public ArrayList<StoryCard> storyDeck;

    public void shuffle(ArrayList<StoryCard> storyDeck) {
        Collections.shuffle(storyDeck);
    }

}