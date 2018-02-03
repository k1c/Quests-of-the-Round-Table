package com.mycompany.app.model;

import com.mycompany.app.model.*;

public class AllyCard extends AdventureCards{
	public AllyCard(int id, String res,AdventureBehavior behavior,String name){
		this.super(id,res,behavior,name);
		this.type = Types.ALLY;
	}
}

