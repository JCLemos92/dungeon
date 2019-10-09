package dkeep.logic;
public class Hero extends MovableObj{
	
	private boolean caught = false; //apanhado por guarda ou nao
	
	private boolean key = false;
	
	private boolean club = false; //tem o club ou n
	
	
	
	public Hero(int x, int y){
		super(x,y); //construtor de MovableObj
	}
	
	public void hit(){ //player gets caught (club or adjacent to guard)
		caught = caught == true ?  false :  true; //troca caught de falso para true ou vice-versa
	}
	
	public boolean getCaughtStatus(){
		return caught;
	}
	
	public void addPath(char[] path){
		
	}
	
	public void toggleKey(){
		key = !key;
	}
	
	public boolean getKey(){
		return key;
	}
	
	
	@Override
	public char processMovement() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean processClub() {
		
		return club;
		
	}

	@Override
	public void processBehaviour() {
		if(!club)
			club = true;
		
	}

	@Override
	public char getType() {
		
		if(key)
			return 'K';
		if(club)
			return 'A';
		else
			return 'H';
	}
	
}