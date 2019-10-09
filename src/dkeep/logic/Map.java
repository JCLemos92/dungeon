package dkeep.logic;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Map extends JPanel {
	private char[][] mapeamento;
	private int level;
	private List<MovableObj> characters = new ArrayList<>();
	private boolean doorStatus = false;
	public Map(char[][] aux, int nivel){
		this.mapeamento = aux;
		this.level = nivel;
	}
	public void addHero(int x, int y){
		characters.add(new Hero(x, y));
	}
	public void addGuard(int x, int y, char[] path, char type){
		Guard aux = new Guard(x, y, type);
		aux.addPath(path);
		characters.add(aux);
	}
	public void addOgre(int x, int y){
		characters.add(new Ogre(x, y));
	}
	public int getLvl(){
		return this.level;
	}
	public char[][] getMap(){
		return this.mapeamento;
	}

	public List<MovableObj> getCharacters(){
		return characters;
	}

	public void openDoors(){
		for(int i=0; i < mapeamento.length; i++){
			for(int j=0; j < mapeamento[i].length; j++){
				if(mapeamento[i][j] == 'I'){
					mapeamento[i][j] = 'S';
					doorStatus = true;
				}
				
			}
		}
	}

	public void Draw(List<MovableObj> g){ 
		for(int i = 0; i < this.mapeamento.length; i++){
			for(int j = 0; j < this.mapeamento[i].length; j++){
				boolean occupied = false;
				boolean ogreOverlap = false;
				for(int k = 0; k < g.size(); k++){

					if(g.get(k) instanceof Ogre && !ogreOverlap){
						if(g.get(k).getX() == j && g.get(k).getY() == i ){
							if(this.mapeamento[i][j] == 'k'){
								System.out.print('$');
								ogreOverlap = true;
								occupied = true;
								break;
							}
							else {
								
								System.out.print(g.get(k).getType());
								ogreOverlap = true;
								occupied = true;
								break;
							}
							//occupied = true;
						}
						if(g.get(k).getClubX() == j && g.get(k).getClubY() == i && !ogreOverlap){
							if(this.mapeamento[i][j] == 'k'){
								System.out.print('$');
							}
							else{
								System.out.print('*');

							}
							occupied = true;
						}
					}


					//O ogre teve que vir para fora para conseguir desenhar o club
					else if(g.get(k).getX() == j && g.get(k).getY() == i){


						System.out.print(g.get(k).getType());


						occupied = true;


					}
				}
				if(occupied != true){
					switch(this.mapeamento[i][j]){
					case '0':
						System.out.print(' ');
						break;
					case 'k':
						if(g.get(0).getKey()) System.out.print(' ');
						else System.out.print(this.mapeamento[i][j]);
						break;
					default:
						System.out.print(this.mapeamento[i][j]);
						break;
					}
				}
				System.out.print(' ');

			}
			System.out.print('\n');

		}
	}
	
	
	public String DrawString(List<MovableObj> g) {
		String s = "";
		
		for(int i = 0; i < this.mapeamento.length; i++){
			for(int j = 0; j < this.mapeamento[i].length; j++){
				boolean occupied = false;
				boolean ogreOverlap = false;
				for(int k = 0; k < g.size(); k++){

					if(g.get(k) instanceof Ogre && !ogreOverlap){
						if(g.get(k).getX() == j && g.get(k).getY() == i ){
							if(this.mapeamento[i][j] == 'k'){
								s = s + '$';
								ogreOverlap = true;
								occupied = true;
								break;
							}
							else {
								
								s = s + g.get(k).getType();
								ogreOverlap = true;
								occupied = true;
								break;
							}
							//occupied = true;
						}
						if(g.get(k).getClubX() == j && g.get(k).getClubY() == i && !ogreOverlap){
							if(this.mapeamento[i][j] == 'k'){
								s = s + '$';
							}
							else{
								s = s + '*';

							}
							occupied = true;
						}
					}


					//O ogre teve que vir para fora para conseguir desenhar o club
					else if(g.get(k).getX() == j && g.get(k).getY() == i){


						s = s + g.get(k).getType();


						occupied = true;


					}
				}
				if(occupied != true){
					switch(this.mapeamento[i][j]){
					case '0':
						s = s + ' ';
						break;
					case 'k':
						if(g.get(0).getKey()) s = s + ' ';
						else s = s + this.mapeamento[i][j];
						break;
					default:
						s = s + this.mapeamento[i][j];
						break;
					}
				}
				s = s + ' ';

			}
			s = s + '\n';

		}
		return s;
	}
}
