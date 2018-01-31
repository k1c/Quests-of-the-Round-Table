package com.mycompany.app;

import com.mycompany.app.GameModel;

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
