package dkeep.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import dkeep.logic.DungeonKeep.State;

public class DungeonKeep {

	public enum State {INVALID_MOVE ,LVL_PLAY, LVL_RESTART, LVL_COMPLETED, GAME_OVER, GAME_RESTART, GAME_COMPLETED, GAME_EXIT}

	public State cState;

	private int lvl = 0;

	private  List<MovableObj> characters = new ArrayList<>();

	private  List<Map> maps = new ArrayList<>();

	private  Map current;



	public DungeonKeep(){

		lvl = 0;

		char[][] mapa = {{'X','X','X','X','X','X','X','X','X','X'},
				{'X','0','0','0','I','0','X','0','0','X'},
				{'X','X','X','0','X','X','X','0','0','X'},
				{'X','0','I','0','I','0','X','0','0','X'},
				{'X','X','X','0','X','X','X','0','0','X'},
				{'I','0','0','0','0','0','0','0','0','X'},
				{'I','0','0','0','0','0','0','0','0','X'},
				{'X','X','X','0','X','X','X','X','0','X'},
				{'X','0','I','0','I','0','X','K','0','X'},
				{'X','X','X','X','X','X','X','X','X','X'}};

		Hero h = new Hero(1,1);

		characters.add(h);
		characters.add(new Guard(8, 1, 'R'));

		Map mapeamento = new Map(mapa, 1);

		mapeamento.addHero(1, 1);

		char[] path = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};

		mapeamento.addGuard(8, 1, path, 'R');

		//Hero h = new Hero(1,1,0);

		maps.add(mapeamento);

		mapa = new char[][] {{'X','X','X','X','X','X','X','X','X'},
			{'I','0','0','0','0','0','0','k','X'},
			{'X','0','0','0','0','0','0','0','X'},
			{'X','0','0','0','0','0','0','0','X'},
			{'X','0','0','0','0','0','0','0','X'},
			{'X','0','0','0','0','0','0','0','X'},
			{'X','0','0','0','0','0','0','0','X'},
			{'X','0','0','0','0','0','0','0','X'},
			{'X','X','X','X','X','X','X','X','X'}};

			mapeamento = new Map(mapa, 2);

			mapeamento.addHero(1, 7);

			Random rand = new Random();
			int randomNum = rand.nextInt(4) + 1;

			//System.out.print(randomNum);
			//System.out.print("\n");

			for(int i = 0; i < randomNum; i++){

				mapeamento.addOgre(4, i+1);
			}

			maps.add(mapeamento);

			current = maps.get(lvl);


			characters = maps.get(lvl).getCharacters(); //change number to start in diff lvl
			System.out.println(maps.size());
	}	


	public DungeonKeep(int ogreN, char gType){

		lvl = 0;

		char[][] mapa = {{'X','X','X','X','X','X','X','X','X','X'},
				{'X','0','0','0','I','0','X','0','0','X'},
				{'X','X','X','0','X','X','X','0','0','X'},
				{'X','0','I','0','I','0','X','0','0','X'},
				{'X','X','X','0','X','X','X','0','0','X'},
				{'I','0','0','0','0','0','0','0','0','X'},
				{'I','0','0','0','0','0','0','0','0','X'},
				{'X','X','X','0','X','X','X','X','0','X'},
				{'X','0','I','0','I','0','X','K','0','X'},
				{'X','X','X','X','X','X','X','X','X','X'}};

		Hero h = new Hero(1,1);

		characters.add(h);
		characters.add(new Guard(8, 1, gType));

		Map mapeamento = new Map(mapa, 1);

		mapeamento.addHero(1, 1);

		char[] path = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};

		mapeamento.addGuard(8, 1, path, gType);

		//Hero h = new Hero(1,1,0);

		maps.add(mapeamento);

		mapa = new char[][] {{'X','X','X','X','X','X','X','X','X'},
			{'I','0','0','0','0','0','0','k','X'},
			{'X','0','0','0','0','0','0','0','X'},
			{'X','0','0','0','0','0','0','0','X'},
			{'X','0','0','0','0','0','0','0','X'},
			{'X','0','0','0','0','0','0','0','X'},
			{'X','0','0','0','0','0','0','0','X'},
			{'X','0','0','0','0','0','0','0','X'},
			{'X','X','X','X','X','X','X','X','X'}};

			mapeamento = new Map(mapa, 2);

			mapeamento.addHero(1, 7);

			

			for(int i = 0; i < ogreN; i++){

				mapeamento.addOgre(4, i+1);
			}

			maps.add(mapeamento);

			current = maps.get(lvl);


			characters = maps.get(lvl).getCharacters(); //change number to start in diff lvl
			System.out.println(maps.size());

	}

	public DungeonKeep(Hero h, Guard g, Ogre o, char[][] m){
		Map mapeamento = new Map(m, 0);

		mapeamento.addHero(h.getX(), h.getY());


		if(g != null)
			mapeamento.addGuard(g.getX(), g.getY(), null, 'G');

		if(o != null)
			mapeamento.addOgre(o.getX(), o.getY());

		maps.add(mapeamento);

		current = maps.get(0);


		characters = maps.get(0).getCharacters();
		cState = State.LVL_PLAY;

	}

	public State processMovementInput(String movement){

		boolean validMov = checkMovement(movement);

		if(!validMov){
			cState = State.INVALID_MOVE; 
			return State.INVALID_MOVE;
		}
		cState = State.LVL_PLAY;
		return cState;


	}

	public void processAiMovement() {

		for(int i = 1; i < characters.size(); i++){
			boolean moveSuccess = false;
			while(!moveSuccess){
				char c = characters.get(i).processMovement();
				int x = characters.get(i).getX();
				int y = characters.get(i).getY();
				switch(c){
				case 'a':
					if(current.getMap()[y][x-1] == '0' || current.getMap()[y][x-1] == 'k'){
						characters.get(i).moveLeft();
						cState = State.LVL_PLAY;
						moveSuccess = true;
					}
					break;
				case 'd':
					if(current.getMap()[y][x+1] == '0' || current.getMap()[y][x+1] == 'k'){
						characters.get(i).moveRight();
						cState = State.LVL_PLAY;
						moveSuccess = true;
					}
					break;
				case 'w':
					if(current.getMap()[y-1][x] == '0' || current.getMap()[y-1][x] == 'k'){
						characters.get(i).moveUp();
						cState = State.LVL_PLAY;
						moveSuccess = true;
					}
					break;
				case 's':if(current.getMap()[y+1][x] == '0' || current.getMap()[y+1][x] == 'k'){
					characters.get(i).moveDown();
					cState = State.LVL_PLAY;
					moveSuccess = true;
				}
				break;
				case ' ':
					cState = State.LVL_PLAY;
					moveSuccess = true;
					break;
				default:
					cState = State.LVL_PLAY;
					moveSuccess = true;
					break;
				}
			}
			characters.get(i).processClub();
			characters.get(i).processBehaviour();
		}



	}

	public State updateState() {



		for(int i = 1; i < characters.size(); i++){


			//check if hero pos = guard/ogre pos
			if(characters.get(i).getX() == characters.get(0).getX() && characters.get(i).getY() == characters.get(0).getY()){
				characters.get(0).hit();
				//System.out.println("You lose (adjacent)!\n");
				cState = State.GAME_OVER; 
				//return State.GAME_OVER;
			}

			//Adjacente a guarda ou ogre



			if(characters.get(i).getX() == characters.get(0).getX() && 
					(characters.get(i).getY() == characters.get(0).getY() + 1 ||
					characters.get(i).getY() == characters.get(0).getY() - 1 )){
				if(!characters.get(0).processClub()){ //no club = lose game
					characters.get(0).hit();
					//	System.out.println("You lose (adjacent)!\n");

					cState = State.GAME_OVER; 
					//return State.GAME_OVER;
				}

				else{

					characters.get(i).hit(); //ogre/guard gets hit by players club

				}
			}

			else if(characters.get(i).getY() == characters.get(0).getY() && 
					(characters.get(i).getX() == characters.get(0).getX() + 1 ||
					characters.get(i).getX() == characters.get(0).getX() - 1 )){
				if(!characters.get(0).processClub()){ //no club = lose game
					characters.get(0).hit();
					//System.out.println("You lose (adjacent)!\n");

					cState = State.GAME_OVER; 
					//return State.GAME_OVER;
				}
				else{

					characters.get(i).hit(); //ogre/guard gets hit by players club
					cState = State.LVL_PLAY;
				}
			}


			//check if player hit by club

			if(characters.get(i).getClubX() == characters.get(0).getX() && characters.get(i).getClubY() == characters.get(0).getY() ){
				characters.get(0).hit();
				//	System.out.println("You lose (hit by club)!\n");
				cState = State.GAME_OVER; 
				//return State.GAME_OVER;
			}
		}

		if(current.getMap()[characters.get(0).getY()][characters.get(0).getX()] == 'S'){
			if(maps.indexOf(current)+1 < maps.size()){
				//clearChar();
				characters.clear();
				characters = maps.get(current.getLvl()).getCharacters();
			}
			

			if(lvl == maps.size() - 1)
				cState = State.GAME_COMPLETED;
			else
				cState = State.LVL_COMPLETED; 
			//return State.LVL_COMPLETED;
		}


		if(current.getMap()[characters.get(0).getY()][characters.get(0).getX()] == 'K' ){
			maps.get(maps.indexOf(current)).openDoors();
			characters.get(0).toggleKey();
			cState = State.LVL_PLAY;
		}

		if(current.getMap()[characters.get(0).getY()][characters.get(0).getX()] == 'k' ){
			//maps.get(maps.indexOf(current)).openDoors();
			characters.get(0).toggleKey();
			cState = State.LVL_PLAY;
		}



		return cState;


	}

	public boolean checkMovement(String mov){

		boolean result = false;
		int x = characters.get(0).getX(); //pos do heroi no map 
		int y = characters.get(0).getY();
		char[][] map = current.getMap(); //mapa 1


		switch(mov){
		case "w": 
			if(map[y-1][x] == '0' || map[y-1][x] == 'K'|| map[y-1][x] == 'k'){
				result = true;
				//if(map[y-1][x] == 'k') characters.get(0).toggleKey();
				characters.get(0).moveUp();
			}
			break;

		case "a":
			if(map[y][x-1] == '0' || map[y][x-1] == 'S' || map[y][x-1] == 'K' || map[y][x-1] == 'k'){

				result = true;
				//if(map[y][x-1] == 'k') characters.get(0).toggleKey();
				characters.get(0).moveLeft();
			}
			else if(map[y][x-1] == 'I' && characters.get(0).getKey()){
				current.openDoors();
				//characters.get(0).toggleKey();
				result = true;
			}
			break;

		case "s":
			if(map[y+1][x] == '0'|| map[y+1][x] == 'K'|| map[y+1][x] == 'k'){
				result = true;
				//if(map[y+1][x] == 'k') characters.get(0).toggleKey();
				characters.get(0).moveDown();
			}
			break;

		case "d":
			if(map[y][x+1] == '0'|| map[y][x+1] == 'K'|| map[y][x+1] == 'k'){
				result = true;
				//if(map[y][x+1] == 'k') characters.get(0).toggleKey();
				characters.get(0).moveRight();
			}
			break;

		default: 
			break;
		}
		//System.out.println("\n\nX: " + characters.get(0).getX() + " \nY: " + characters.get(0).getY() + "\n\n");//debug

		return result;

	}

	public State nextLvl(){
		lvl++;
		if(lvl >= maps.size() ){
			return State.GAME_COMPLETED;
		}

		current = maps.get(lvl);
		characters = maps.get(lvl).getCharacters(); 
		if(lvl == 1)
			characters.get(0).processBehaviour();
		return State.LVL_PLAY;
	}

	public void display(){

		current.Draw(characters);

	}
	
	public String displayString(){
		String s = current.DrawString(characters);
		System.out.println(s);
		return s;

	}



	public  int getHeroX() {

		return characters.get(0).getX();
	}

	public  int getHeroY() {

		return characters.get(0).getY();
	}


	public State getState() {

		return cState;
	}


	public MovableObj getHero() {

		return characters.get(0);
	}








}
