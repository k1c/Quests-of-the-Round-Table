
package com.mycompany.app.model.Players;


public class Strategy2AI extends AbstractAI {

    public Strategy2AI(String name, String shieldImage){
        super(name,shieldImage);
        this.behaviour = new Strategy2();
    }

}
