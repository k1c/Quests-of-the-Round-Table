package com.mycompany.app;

import com.mycompany.app.model.Player;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class GameLogger  {

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

    public void userAction(Player player, String action) {
        String str = "User Action:  " + player.name + " " + action;
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, str, null);
    }

    public void gameState(String state){
        state = "Game State:  "  + state;
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, state,null);
    }

    public void objectCreation(String objectType, String description){
        String str = objectType + " Creation:  "  + description;
        logger.log(GameLogger.class.getCanonicalName(), Level.INFO, str,null);
    }

    public void debug(String parameter) {
        if(logger.isDebugEnabled()){
            logger.log(GameLogger.class.getCanonicalName(), Level.DEBUG, parameter, null);
        }
    }

    public void warn(String parameter) {
        logger.log(GameLogger.class.getCanonicalName(), Level.WARN, parameter, null);
    }

    public void error(String parameter) {
        logger.log(GameLogger.class.getCanonicalName(), Level.ERROR, parameter, null);
    }

    public void fatal(String parameter) {
        logger.log(GameLogger.class.getCanonicalName(), Level.FATAL, parameter, null);
    }
}
