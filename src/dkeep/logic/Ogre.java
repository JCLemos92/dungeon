package dkeep.logic;
import java.util.Random;

public class Ogre extends MovableObj {
	char[] movements; //lista de movimentos do guarda

	private boolean stunned;
	private int stunCounter;

	public Ogre(int x, int y){
		super(x,y); //construtor de MovableObj
		super.clubX = x;
		super.clubY = y;
		stunned = false;
		stunCounter = 2;
		processClub();
	}

	public boolean getCaughtStatus(){ // nao faz nada mas tem de estar aqui pq movable object é abstrato
		return false;
	}

	public void addPath(char[] path){

	}

	public void toggleKey(){
	}

	public boolean getKey(){
		return false;
	}

	@Override
	public char processMovement() {


		if(!stunned || stunCounter == 0){
			
			stunned = false;
			Random rand = new Random();
			int randomNum = rand.nextInt(4);
			switch(randomNum){
			case 0:
				return 'a';
			case 1:
				return 'd';
			case 2:
				return 'w';
			case 3:
				return 's';
			}
		}
		else
			stunCounter--;
		
		return 0;
	}

	public boolean processClub(){

		Random rand = new Random();
		int randomNum = rand.nextInt(4);
		switch(randomNum){
		case 0:
			if(super.getX()-1 <= 1) processClub();
			else{
				super.clubX = super.getX()-1;
				super.clubY = super.getY();


			}
			break;
		case 1:
			if(super.getX()+1 >= 8) processClub();
			else{
				super.clubX = super.getX()+1;
				super.clubY = super.getY();

			}
			break;
		case 2:
			if(super.getY()-1 <= 1) processClub();
			else{
				super.clubY = super.getY()-1;
				super.clubX = super.getX();

			}
			break;
		case 3:
			if(super.getY()+1 >= 8) processClub();
			else{
				super.clubY = super.getY()+1;
				super.clubX = super.getX();

			}
			break;
		}
		return true;
	}

	@Override
	public void hit() { //ogre gets hit by players club
		System.out.print("OGRE GETS HIT!!\n");
		stunned = true;
		stunCounter = 2;
	}

	@Override
	public void processBehaviour() {
		// TODO Auto-generated method stub

	}

	@Override
	public char getType() {
		if(!stunned)
			return 'O';
		else
			return '8';
	}
}
