package com.mycompany.app.model;

public class KingsRecognitionEvent extends StoryBehaviour{

    public KingsRecognitionEvent(int numShields){
        super(numShields);

    }

    public void applyBehaviour(GameBoard b, int player){
        b.eventKingsRecognition = b.currentStory;
    }

}
