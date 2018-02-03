package com.mycompany.app.model;
import com.mycompany.app.model.*;

public class AdventureCardFactory{

    public static AdventureCard defaultAlly(int id, String res, String name, int BP, int bids){
        return new AllyCard(id, res, new DefaultBehaviour(BP, bids), name);
    }

    public static AdventureCard defaultFoe(int id, String res, String name, int BP){
        return new FoeCard(id, res, new DefaultBehaviour(BP, 1), name);
    }

    public static AdventureCard defaultWeapon(int id, String res, String name, int BP){
        return new WeaponCard(id, res, new DefaultBehaviour(BP, 1), name);
    }

    public static AdventureCard defaultAmour(int id, String res, String name){
        return new AmourCard(id, res, new DefaultBehaviour(10, 1), name);
    }

    public static AdventureCard defaultTest(int id, String res, String name, int bids){
        return new TestCard(id, res, new DefaultBehaviour(0, bids), name);
    }


}
