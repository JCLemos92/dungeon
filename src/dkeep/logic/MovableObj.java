package dkeep.logic;

public abstract class MovableObj {
	private int x, y;
	private int state;
	int clubX, clubY;
	
	
	public MovableObj(){
		
	}
	public MovableObj(int x, int y){
		this.x = x;
		this.y = y;
		this.state = 0;
	}
	
	public abstract boolean getCaughtStatus();

	public abstract void hit(); //object gets hit by something
	
	public abstract void addPath(char[] path);
	
	public abstract char processMovement();
	
	public abstract boolean processClub();
	
	public abstract void toggleKey();
	
	public abstract boolean getKey();
	
	public abstract void processBehaviour();
	
	public abstract char getType();
	
	public int getClubX(){
		return clubX;
	}
	
	public int getClubY(){
		return clubY;
	}
	
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public void moveRight(){
		this.x++;
	}
	public void moveLeft(){
		this.x--;
	}
	public void moveUp(){
		this.y--;
	}
	public void moveDown(){
		this.y++;
	}
}


