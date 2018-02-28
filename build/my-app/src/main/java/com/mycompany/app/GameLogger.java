package com.mycompany.app;

import com.mycompany.app.model.Player;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class GameLogger  {

public static final String ANSI_RESET = "\u001B[0m";
public static final String ANSI_BLACK = "\u001B[30m";
public static final String ANSI_RED = "\u001B[31m";
public static final String ANSI_GREEN = "\u001B[32m";
public static final String ANSI_YELLOW = "\u001B[33m";
public static final String ANSI_BLUE = "\u001B[34m";
public static final String ANSI_PURPLE = "\u001B[35m";
public static final String ANSI_CYAN = "\u001B[36m";
public static final String ANSI_WHITE = "\u001B[37m";


    private static GameLogger ourInstance = new GameLogger ();

    final Logger logger = Logger.getLogger(GameLogger.class);

    public static GameLogger  getInstanceUsingDoubleLocking() {
        if (ourInstance == null) {
            synchronized (GameLogger.class) {
                if (ourInstance == null) {
                    ourInstance = new GameLogger();
                }
            }
        }
        return ourInstance;
    }

    private GameLogger() { }

    public void playerAction(Player player, String action) {
        String str = "User Action:  " + player.name + " " + action;
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, str, null);
    }

    public void playerCard(Player player, Object cardType, String deckType) {
        String str = "Card Drawn:  " + player.name + " draws " + cardType + " from the " + deckType;
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, str,null);

    }

    public void gameState(String state){
        state = String.format("%sGame State: %s%s",ANSI_RED,ANSI_RESET,state); 
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, state,null);
    }

    public void gameStateAction(String state,String action,Object o){
        state = String.format("%s(%s)%sAction: %s: %s%s",ANSI_RED,state,ANSI_GREEN,action,ANSI_RESET,o.toString());
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, state,null);
    }


    public void objectCreation(String objectType, String description){
        String str = objectType + " Creation:  "  + description;
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, str,null);
    }

    public void count(String objBeingCounted, int num){
        String str = "Number of " + objBeingCounted + ":  " +num;
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, str,null);
    }

    public void cardDrawn(Object cardType){
        String str = "Card Drawn:  "+cardType;
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, str,null);
    }

    public void debug(String parameter) {
        if(logger.isDebugEnabled()){
            logger.log(GameLogger.class.getCanonicalName(), Level.DEBUG, parameter, null);
        }
    }

    public void warning(String parameter) {
        logger.log(GameLogger.class.getCanonicalName(), Level.WARN, parameter, null);
    }

    public void error(String parameter) {
        logger.log(GameLogger.class.getCanonicalName(), Level.ERROR, parameter, null);
    }

    public void fatal(String parameter) {
        logger.log(GameLogger.class.getCanonicalName(), Level.FATAL, parameter, null);
    }
}
