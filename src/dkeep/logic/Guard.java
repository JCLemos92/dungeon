package dkeep.logic;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Guard extends MovableObj{

	static int mov;
	private char type;
	private boolean asleep;
	private boolean changeDir;

	char[] movements; //lista de movimentos do guarda

	public Guard(int x, int y, char t){
		super(x,y); //construtor de MovableObj
		mov = 0;
		asleep = false;
		changeDir = false;
		type = t;
	}

	public void processBehaviour(){


		int randomNum = ThreadLocalRandom.current().nextInt(1, 4 + 1);



		if(randomNum == 1){ //25% de hipotese de mudar de comportamento
			switch(type){
			case 'D':
			case 'd':
				if(!asleep){//adormece
					asleep = true;
					this.type = 'd';


				}
				else{ // acorda
					asleep = false;
					int changeDirNumber = ThreadLocalRandom.current().nextInt(1, 2 + 1); //50% hipotese de mudar de direccao
					if(changeDirNumber == 1){
						invertDir();
					}

					this.type = 'D';
				}
				break;

			case 'S': 
				int changeDirNumber = ThreadLocalRandom.current().nextInt(1, 2 + 1); //50% hipotese de mudar de direccao
				if(changeDirNumber == 1){
					invertDir();
				}

				break;

			default:
				break;

			}

		}


	}

	public char getType(){
		return type;
	}

	public boolean getCaughtStatus(){ // nao faz nada mas tem de estar aqui pq movable object é abstrato
		return false;
	}

	public void addPath(char[] path){
		this.movements = path;
	}

	public char processMovement(){
		if(!asleep && this.movements!=null){
			mov++;
			//System.out.println(Arrays.toString(movements));
			return this.movements[(mov-1)%this.movements.length];
		}
		else return ' ';
	}

	public void toggleKey(){
	}

	public boolean getKey(){
		return false;
	}

	public void invertDir(){
		for(int i=0; i < movements.length; i++){
			switch(movements[i]){
			case 'a':
				movements[i] = 'd';
				break;
			case 'd':
				movements[i] = 'a';
				break;
			case 'w':
				movements[i] = 's';
				break;
			case 's':
				movements[i] = 'w';
				break;
			default:
				break;
			}
		}
		for(int i = 0; i < movements.length / 2; i++)
		{
			char temp = movements[i];
			movements[i] = movements[movements.length - i - 1];
			movements[movements.length - i - 1] = temp;
		}
	}

	@Override
	public void hit() { //guard hit by club?
		// TODO Auto-generated method stub
	}

	@Override
	public boolean processClub() {
		// TODO Auto-generated method stub
		clubX = super.getX();
		clubY = super.getY();
		return true;
	}

}