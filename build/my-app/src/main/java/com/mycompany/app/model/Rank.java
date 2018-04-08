package com.mycompany.app.model;

public class Rank {

    public static enum RankType {SQUIRE, KNIGHT, CHAMPION_KNIGHT, KNIGHT_OF_THE_ROUND_TABLE};

    protected static int MAX_SHIELDS_SQUIRE = 5;
    protected static int MAX_SHIELDS_KNIGHT = 7;
    protected static int MAX_SHIELDS_CHAMPION_KNIGHT = 10;

    protected int shields;

    protected int BP;
    protected String path;
    protected RankType rank;

    public Rank(){
	    setSquire();
    }

    public Rank(Rank r){
	    this.BP = r.BP;
	    this.path = r.path;
	    this.rank = r.rank;
	    this.shields  = r.shields;
    }

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

    protected void setKnightOfTheRoundTable(){
        rank = RankType.KNIGHT_OF_THE_ROUND_TABLE;
    }

    public RankType getRank(){
        return rank;
    }

    public int getBP(){
        return BP;
    }

    public int getShields() {
	    return shields;
    }

    public String getPath(){
	return path;
    }

    public int getMaxShields(){
        if (rank == RankType.SQUIRE) {return MAX_SHIELDS_SQUIRE;}
        else if (rank == RankType.KNIGHT) {return MAX_SHIELDS_KNIGHT;}
        else if (rank == RankType.CHAMPION_KNIGHT) {return MAX_SHIELDS_CHAMPION_KNIGHT;}
        else {return 0;}
    }

    public void addRemoveShields(int numShields){
        shields += numShields;
        shields = Math.max(shields, 0);
        rankUp();
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
            setKnightOfTheRoundTable();
            System.out.println("WINRAR!");
        }
    }
}
