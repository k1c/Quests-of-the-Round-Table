import com.mycompany.app.model.*;

public class FoeCard extends AdventureCards{
	public FoeCard(int id, String res,AdventureBehavior behavior,String name){
		this.super(id,res,behavior,name);
		this.type = Types.FOE;
	}
}

