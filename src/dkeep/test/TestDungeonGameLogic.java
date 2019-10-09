package dkeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import dkeep.logic.DungeonKeep;
import dkeep.logic.DungeonKeep.State;
import dkeep.logic.Map;
import dkeep.logic.Hero;
import dkeep.logic.Guard;
import dkeep.logic.Ogre;


public class TestDungeonGameLogic {
	char[][] map = {{'X','X','X','X','X'},
					{'X','0','0','0','X'},
					{'I','0','0','0','X'},
					{'I','K','0','0','X'},
					{'X','X','X','X','X'}};
	
	char[][] keep = {{'X','X','X','X','X'},
			{'X','0','0','0','X'},
			{'I','0','0','0','X'},
			{'I','k','0','0','X'},
			{'X','X','X','X','X'}};
	
	
	
	@Test
	public void testMoveHeroIntoFreeCell(){
		
		Hero h = new Hero(1,1);
		DungeonKeep game = new DungeonKeep(h, null, null, map);
		assertEquals(1, game.getHeroX());
		assertEquals(1, game.getHeroY());
		game.processMovementInput("s");
		assertEquals(1, game.getHeroX());
		assertEquals(2, game.getHeroY());	
	}
	
	@Test
	public void testMoveHeroIntoToWall(){
		Hero h = new Hero(1,1);
		DungeonKeep game = new DungeonKeep(h, null, null, map);
		assertEquals(1, game.getHeroX());
		assertEquals(1, game.getHeroY());
		game.processMovementInput("a");
		assertEquals(1, game.getHeroX());
		assertEquals(1, game.getHeroY());
	}
	
	@Test
	public void testHeroIsAdjacentToGuard(){
		Hero h = new Hero(1,1);
		Guard g = new Guard(3,1,'G');
		DungeonKeep game = new DungeonKeep(h, g, null, map);
		
		assertEquals(DungeonKeep.State.LVL_PLAY, game.getState());
		game.processMovementInput("d");
		game.updateState();
		assertEquals(DungeonKeep.State.GAME_OVER, game.getState());
		
	}
	
	@Test
	public void testMoveHeroToClosedDoor(){
		Hero h = new Hero(1,1);
		DungeonKeep game = new DungeonKeep(h, null, null, map);
		assertEquals(1, game.getHeroX());
		assertEquals(1, game.getHeroY());
		game.processMovementInput("s");
		DungeonKeep.State state = game.processMovementInput("a");
		assertEquals(1, game.getHeroX());
		assertEquals(2, game.getHeroY());
		assertEquals(DungeonKeep.State.INVALID_MOVE, state);
	}
	
	@Test
	public void testMoveHeroIntoKey(){
		Hero h = new Hero(1,1);
		DungeonKeep game = new DungeonKeep(h, null, null, map);
		assertEquals(1, game.getHeroX());
		assertEquals(1, game.getHeroY());
		game.processMovementInput("s");
		DungeonKeep.State state = game.processMovementInput("s");
		game.updateState();
		assertEquals(true, game.getHero().getKey());  
	}
	
	@Test
	public void testHeroCompletesLvl(){
		Hero h = new Hero(1,1);
		DungeonKeep game = new DungeonKeep(h, null, null, map);
		assertEquals(1, game.getHeroX());
		assertEquals(1, game.getHeroY());
		game.processMovementInput("s");
		game.processMovementInput("s");
		game.updateState();
		assertEquals(true, game.getHero().getKey());
		game.processMovementInput("a");
		game.updateState();
		assertEquals(DungeonKeep.State.GAME_COMPLETED, game.getState()); //game completed porque so tem 1 nivel neste teste
																		//senao seria LVL_COMPLETED
	}
	
	@Test
	public void testHeroAdjacentToOgre(){
		
		Hero h = new Hero(1,1);
		Ogre o = new Ogre(3,1);
		DungeonKeep game = new DungeonKeep(h, null, o, keep); 
		DungeonKeep.State state = game.processMovementInput("d");
		assertEquals(DungeonKeep.State.LVL_PLAY, state);
		state = game.processMovementInput("d");
		state = game.updateState();
		assertEquals(DungeonKeep.State.GAME_OVER, state);
		
		
	}
	
	@Test
	public void testHeroGetsKeepKey(){
		
		Hero h = new Hero(1,1);
		Ogre o = new Ogre(3,1);
		DungeonKeep game = new DungeonKeep(h, null, o, keep); 
		DungeonKeep.State state = game.processMovementInput("s");
		assertEquals(false, game.getHero().getKey()); 
		assertEquals(DungeonKeep.State.LVL_PLAY, state);
		state = game.processMovementInput("s");
		state = game.updateState();
		assertEquals(true, game.getHero().getKey());  
		
	}
	
	@Test
	public void testHeroTriesToExitWithoutKey(){
		
		Hero h = new Hero(1,1);
		Ogre o = new Ogre(3,1);
		DungeonKeep game = new DungeonKeep(h, null, o, keep); 
		DungeonKeep.State state = game.processMovementInput("s");
		assertEquals(DungeonKeep.State.LVL_PLAY, state);
		state = game.processMovementInput("a");
		state = game.updateState();
		assertEquals(false, game.getHero().getKey()); 
		assertEquals(DungeonKeep.State.INVALID_MOVE, state);  
		
	}
	
	@Test
	public void testHeroOpensExitDoor(){
		Hero h = new Hero(1,1);
		Ogre o = new Ogre(3,1);
		DungeonKeep game = new DungeonKeep(h, null, o, keep); 
		DungeonKeep.State state = game.processMovementInput("s");
		assertEquals(false, game.getHero().getKey()); 
		assertEquals(DungeonKeep.State.LVL_PLAY, state);
		state = game.processMovementInput("a");
		state = game.updateState();
		assertEquals(DungeonKeep.State.INVALID_MOVE, state); //no key 
		state = game.processMovementInput("s");
		state = game.updateState();
		assertEquals(true, game.getHero().getKey());  
		state = game.processMovementInput("a");
		state = game.updateState();
		assertEquals(DungeonKeep.State.LVL_PLAY, state);
		
	}
	
	@Test
	public void testHeroWinsTheGame(){
		
		Hero h = new Hero(1,1);
		Ogre o = new Ogre(3,1);
		DungeonKeep game = new DungeonKeep(h, null, o, keep); 
		DungeonKeep.State state = game.processMovementInput("s");
		assertEquals(false, game.getHero().getKey()); 
		assertEquals(DungeonKeep.State.LVL_PLAY, state);
		state = game.processMovementInput("s");
		state = game.updateState();
		assertEquals(true, game.getHero().getKey());  
		state = game.processMovementInput("a");
		state = game.updateState();
		assertEquals(DungeonKeep.State.LVL_PLAY, state);
		state = game.processMovementInput("a");
		state = game.updateState();
		assertEquals(DungeonKeep.State.GAME_COMPLETED, state);
		
	}
	
}
