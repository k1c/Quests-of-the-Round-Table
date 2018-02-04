package com.mycompany.app.model;

import com.mycompany.app.model.AdventureCardFactoryTest;
import junit.framework.TestCase;


public class AdventureCardFactoryTest extends TestCase{
	public AdventureCardFactoryTest(String name){
		super(name);
	}

	public void testdefaultAllyCard() throws Exception{
		AdventureCard Temp = AdventureCardFactory.defaultAlly(1,"king.png","King Arthur",10,2);
		assertEquals(1,Temp.id);
		assertEquals("king.png",Temp.res);
		assertEquals("King Arthur",Temp.name);
		assertEquals(10,Temp.getBattlePoints());
		assertEquals(2,Temp.getBids());
		assertEquals(false,Temp.freeBid());

	}

	public void testdefaultFoeCard() throws Exception{
		AdventureCard Temp = AdventureCardFactory.defaultFoe(1,"theif.png","Thieves",5);
		assertEquals(1,Temp.id);
		assertEquals("theif.png",Temp.res);
		assertEquals("Thieves",Temp.name);
		assertEquals(5,Temp.getBattlePoints());
		assertEquals(1,Temp.getBids());
		assertEquals(false,Temp.freeBid());
	}
}

