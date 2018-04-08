package com.mycompany.app;

import com.mycompany.app.model.Players.GenericPlayer;
import com.mycompany.app.model.Players.Player;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class GameLogger  {

	private static int id = 0;

public static final String ANSI_RESET = "";
public static final String ANSI_BLACK = "";
public static final String ANSI_RED = "";
public static final String ANSI_GREEN = "";
public static final String ANSI_YELLOW = "\u001B[33m";
public static final String ANSI_BLUE = "\u001B[34m";
public static final String ANSI_PURPLE = "";
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

    private String id(){
	synchronized(this){
		this.id++;
		return String.format(" log#%d ",this.id);
	}
    }
    public void playerAction(Player player, String action) {
        String str = String.format(this.id() +"%sUser Action:  %s :%s %s",ANSI_PURPLE,player.toString(),ANSI_RESET,action);
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, str, null);
    }

    public void playerAction(GenericPlayer player, String action) {
        String str = this.id() +"User Action:  " + player.name + " " + action;
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, str, null);
    }

    public void playerCard(Player player, Object cardType, String deckType) {
        String str = this.id() +"Card Drawn:  " + player.name + " draws " + cardType + " from the " + deckType;
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, str,null);

    }

    public void playerCard(GenericPlayer player, Object cardType, String deckType) {
        String str =  this.id() + "Card Drawn:  " + player.name + " draws " + cardType + " from the " + deckType;
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, str,null);

    }

    public void cardPlayed(Player player, Object cardType, String location) {
        String str = this.id() + "Card Played:  " + player.name + " plays " + cardType + " " + location;
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, str,null);
    }

    public void cardPlayed(GenericPlayer player, Object cardType, String location) {
        String str = this.id() + "Card Played:  " + player.name + " plays " + cardType + " " + location;
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, str,null);
    }

    public void gameState(String state){
        state = String.format(this.id() + "%sGame State: %s%s",ANSI_RED,ANSI_RESET,state); 
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, state,null);
    }

    public void gameStateAction(Object state,String action,Object o){
        state = String.format(this.id() + "%s(%s)%sAction: %s: %s%s",ANSI_RED,state.toString(),ANSI_GREEN,action,ANSI_RESET,o.toString());
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, state,null);
    }

    /*
	log.action("","","");
*/
    public void action(Object description,String action,Object o){
        String str = String.format(this.id() + "[%s]:%s    %s",description,action,o.toString());
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, str,null);
    }

    public void objectCreation(String objectType, String description){
        String str = this.id() + objectType + " Creation:  "  + description;
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, str,null);
    }

    public void count(String objBeingCounted, int num){
        String str = this.id() + "Number of " + objBeingCounted + ":  " +num;
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, str,null);
    }

    public void cardDrawn(Object cardType){
        String str = this.id() + "Card Drawn:  "+cardType;
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
