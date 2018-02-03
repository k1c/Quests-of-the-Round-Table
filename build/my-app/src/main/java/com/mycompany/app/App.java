package com.mycompany.app;

import com.mycompany.app.model.GameModel;
import com.mycompany.app.controller.GameController;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
	    GameModel model = new GameModel();
	    GameController controller = new GameController(model);
    }
}
