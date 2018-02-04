package com.mycompany.app.model;

import com.mycompany.app.model.DefaultBehaviourTest;
import junit.framework.TestCase;


public class DefaultBehaviourTest extends TestCase{
    public DefaultBehaviourTest(String name){
        super(name);
    }

    public void testDefaultBehaviourT() throws Exception{
        AdventureBehaviour Temp = new DefaultBehaviour(10, 2, true);
        assertEquals(10, Temp.getBP());
        assertEquals(2, Temp.getBids());
        assertEquals(true, Temp.isFreeBid());

    }

    public void testDefaultBehaviourF() throws Exception{
        AdventureBehaviour Temp = new DefaultBehaviour(30, 0);
        assertEquals(30, Temp.getBP());
        assertEquals(0, Temp.getBids());
        assertEquals(false, Temp.isFreeBid());

    }


}

