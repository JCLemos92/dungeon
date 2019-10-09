package dkeep.cli;


import java.util.Scanner;

import dkeep.logic.DungeonKeep;


public class Interface {

	private int currLvl = 0;
	private DungeonKeep game;
	private DungeonKeep.State gState;
	private String input;

	private static Scanner s = new Scanner(System.in); //ler inputs

	public void run(){

		game = new DungeonKeep();


		gState = DungeonKeep.State.LVL_PLAY;

		do{  //main game loop

			if(gState != DungeonKeep.State.GAME_COMPLETED && gState != DungeonKeep.State.GAME_EXIT ){
				do
				{//current level loop


					do{//movement input loop
						game.display();

						if(gState == DungeonKeep.State.INVALID_MOVE){
							System.out.print("\nMovimento invalido!");
						}
						System.out.print("\nDireccao: ");
						input = s.next();
						gState = game.processMovementInput(input);


					}while(gState == DungeonKeep.State.INVALID_MOVE);
					game.processAiMovement();
					gState = game.updateState();


					System.out.print("\n\n");

					game.display();

					System.out.print("\n\n\n");

				}while (gState == DungeonKeep.State.LVL_PLAY);
			}


			switch (gState)
			{
			case LVL_COMPLETED:
				System.out.println("Nivel Completado!");
				gState = game.nextLvl();
				break;
			case LVL_RESTART:
				game = new DungeonKeep();
				break;
			case GAME_OVER:
				System.out.println("GAME OVER!");
				System.out.println("Tentar outra vez?(s/n)");
				do
				{
					input = s.next();
				}
				while (!input.equals("s") && !input.equals("n"));

				if (input.equals("s"))
				{
					game = new DungeonKeep();
					gState = DungeonKeep.State.GAME_RESTART;
				}
				else
				{
					gState = DungeonKeep.State.GAME_EXIT;
				}
				break;
			case GAME_RESTART:
				game = new DungeonKeep();
				gState = DungeonKeep.State.GAME_RESTART;
				break;
			case GAME_COMPLETED:
				System.out.println("Fim do jogo!");
				System.out.println("Jogar de novo?(s/n)");
				do
				{
					input = s.next();
				}
				while (!input.equals("s") && !input.equals("n"));
				if (input.equals("s"))
				{
					game = new DungeonKeep();
					gState = DungeonKeep.State.GAME_RESTART;
				}
				else
				{
					gState = DungeonKeep.State.GAME_EXIT;
				}
				break;
			default:
				break;	
			}

		}while(gState!= DungeonKeep.State.GAME_EXIT);


	}

	public static void main(String[] args)
	{
		Interface interf = new Interface();
		interf.run();

		System.out.println("Ate a proxima! abraco!");
	}

}