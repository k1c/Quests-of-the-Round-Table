package cpm.mycompany.app.model;

import com.mycompany.app.model.*;

public class WeaponCard extends AdventureCards{

    public WeaponCard(int id, String res, AdventureBehaviour behaviour, String name){
        this.super(id, res, behaviour, name);
        this.type = Types.WEAPON;

    }


}