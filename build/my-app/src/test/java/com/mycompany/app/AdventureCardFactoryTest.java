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
		assertEquals(true,Temp.freeBid());

	}

	public void testdefaultFoeCard() throws Exception{
		AdventureCard Temp = AdventureCardFactory.defaultFoe(2,"theif.png","Thieves",5);
		assertEquals(2,Temp.id);
		assertEquals("theif.png",Temp.res);
		assertEquals("Thieves",Temp.name);
		assertEquals(5,Temp.getBattlePoints());
		assertEquals(1,Temp.getBids());
		assertEquals(false,Temp.freeBid());
	}

	public void testdefaultWeaponCard() throws Exception{
		AdventureCard Temp = AdventureCardFactory.defaultWeapon(3,"sword.png","Sword",10);
		assertEquals(3,Temp.id);
		assertEquals("sword.png",Temp.res);
		assertEquals("Sword",Temp.name);
		assertEquals(10,Temp.getBattlePoints());
		assertEquals(1,Temp.getBids());
		assertEquals(false,Temp.freeBid());
	}

	public void testdefaultAmourCard() throws Exception{
		AdventureCard Temp = AdventureCardFactory.defaultAmour(4,"amour.png","Amour");
		assertEquals(4,Temp.id);
		assertEquals("amour.png",Temp.res);
		assertEquals("Amour",Temp.name);
		assertEquals(10,Temp.getBattlePoints());
		assertEquals(1,Temp.getBids());
		assertEquals(true,Temp.freeBid());
	}

	public void testdefaultTestCard() throws Exception{
		AdventureCard Temp = AdventureCardFactory.defaultTest(5,"testofvalor.png","Test of Valor",1);
		assertEquals(5,Temp.id);
		assertEquals("testofvalor.png",Temp.res);
		assertEquals("Test of Valor",Temp.name);
		assertEquals(0,Temp.getBattlePoints());
		assertEquals(1,Temp.getBids());
		assertEquals(false,Temp.freeBid());
	}

}

