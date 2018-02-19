package com.mycompany.app.model;
import com.mycompany.app.model.*;

public class Rank {

    public static enum RankType {SQUIRE, KNIGHT, CHAMPION_KNIGHT};

    protected static int MAX_SHIELDS_SQUIRE = 5;
    protected static int MAX_SHIELDS_KNIGHT = 7;
    protected static int MAX_SHIELDS_CHAMPION_KNIGHT = 10;

    protected int shields;

    protected int BP;
    protected String path;
    protected RankType rank;

    protected void setSquire(){
        rank = RankType.SQUIRE;
        BP = 5;
        path = "/R Squire.jpg";
    }

    protected void setKnight(){
        rank = RankType.KNIGHT;
        BP = 10;
        path = "/R Knight.jpg";
    }

    protected void setChampionKnight(){
        rank = RankType.CHAMPION_KNIGHT;
        BP = 20;
        path = "/R Champion Knight.jpg";
    }

    protected RankType getRank(){
        return rank;
    }

    protected int getBP(){
        return BP;
    }

    public int addRemoveShields(int numShields){
        shields += numShields;
        shields = Math.max(shields, 0);
        rankUp();
        return shields;
    }

    protected void rankUp(){
        if (rank == RankType.SQUIRE && shields >= MAX_SHIELDS_SQUIRE) {
            shields -= MAX_SHIELDS_SQUIRE;
            setKnight();
        }
        if (rank == RankType.KNIGHT && shields >= MAX_SHIELDS_KNIGHT) {
            shields -= MAX_SHIELDS_KNIGHT;
            setChampionKnight();
        }
        if (rank == RankType.CHAMPION_KNIGHT && shields >= MAX_SHIELDS_CHAMPION_KNIGHT){
            //WINNER WINNER CHICKEN DINNER
            //setKnightOfTheRoundTable();
        }
    }
}