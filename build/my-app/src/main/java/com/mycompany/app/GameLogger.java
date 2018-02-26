package com.mycompany.app;

import com.mycompany.app.model.Player;
import org.apache.log4j.Logger;


public class GameLogger {

    private static GameLogger ourInstance = new GameLogger ();

    final Logger logger = Logger.getLogger(GameLogger.class);

    private static final String WHITESPACE8 = "        ";
    private static final String WHITESPACE3 = "   ";


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

    public void userAction(Player player, String action){
        logger.info("User Action:"+ WHITESPACE8 + player.name + " " + action);

    }

    public void gameState(String state){
        logger.info("Game State:" + WHITESPACE8 + state);
    }

    public void objectCreation(String objectType, String description){
        logger.info(objectType + " Creation:" + WHITESPACE3 + description);
    }

    public void debug(String parameter) {
        if(logger.isDebugEnabled()){
            logger.debug("Debug:"+ WHITESPACE8 + parameter);
        }
    }

    public void warn(String parameter) {
        logger.warn("Warning:"+ WHITESPACE8 + parameter);
    }

    public void error(String parameter) {
        logger.error("Error:" + WHITESPACE8 + parameter);
    }

    public void fatal(String parameter) {
        logger.fatal("Fatal:" + WHITESPACE8 + parameter);
    }
}
