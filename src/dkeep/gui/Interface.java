package dkeep.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.Graphics;

import dkeep.logic.*;

public class Interface {

	private JFrame frame;
	private JLabel lblNumberOfOgres;
	private JTextField textField;
	private DungeonKeep game;
	private DungeonKeep.State state = DungeonKeep.State.LVL_PLAY;
	private int ogreN;
	private int guardType = -1;
	private JButton btnDown, btnUp, btnLeft, btnRight;
	private JTextArea boardlbl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface window = new Interface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Interface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		boardlbl = new JTextArea("");
		boardlbl.setBounds(75,75,400,400);
		boardlbl.setFont(new Font("Consolas", Font.PLAIN, 12));
		
		boardlbl.setEditable(false);
		frame.getContentPane().add(boardlbl);


		lblNumberOfOgres = new JLabel("Number of Ogres");
		lblNumberOfOgres.setBounds(22, 8, 175, 14);
		frame.getContentPane().add(lblNumberOfOgres);

		textField = new JTextField();
		textField.setBounds(150, 5, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblGameState = new JLabel("Estado do jogo");
		lblGameState.setBounds(50, 520, 117, 14);
		frame.getContentPane().add(lblGameState);


		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent foc_evn) {
				textField.setText("Please select game settings");
				textField.setText("");
			}
			@Override
			public void focusLost(FocusEvent foc_evn) {
				if (!textField.getText().equals("")) {
					try {
						ogreN = Integer.parseInt(textField.getText());
						if (ogreN < 1 || ogreN > 3) {
							ogreN = 0;
							throw new NumberFormatException();
						}
					}
					catch (NumberFormatException except) {
						textField.setText("");
						lblGameState.setText("Invalid input!");
						lblGameState.setText("Must be a number between 1 and 3.");
					}

				}
				else {
					ogreN = 0;
				}
				if (ogreN == 0) {
					lblGameState.setText("Select game settings");
				}
				else {
					lblGameState.setText("");
				}	
			}
		});
		JLabel lblGuardPersonality = new JLabel("Guard Personality");
		lblGuardPersonality.setBounds(22, 33, 150, 14);
		frame.getContentPane().add(lblGuardPersonality);

		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Rookie");
		comboBox.addItem("Drunken");
		comboBox.addItem("Suspicious");
		comboBox.setToolTipText("Select number of guards");
		comboBox.setBounds(150, 30, 200, 20);
		frame.getContentPane().add(comboBox);
		comboBox.setSelectedIndex(-1);


		comboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent foc_evn) {
				lblGameState.setText("Please select game settings");
				lblGameState.setText("");
			}
			@Override
			public void focusLost(FocusEvent foc_evn) {
				guardType = comboBox.getSelectedIndex();
				if (guardType * ogreN == 0) {
					lblGameState.setText("Please select game settings");
					lblGameState.setText("");
				}
				else {
					lblGameState.setText("");
					lblGameState.setText("");
				}
			}
		});

		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ogreN != 0 && guardType != -1){
					if(guardType == 0){
						game = new DungeonKeep(ogreN, 'R');
					}
					if(guardType == 1){
						game = new DungeonKeep(ogreN, 'D');
					}
					if(guardType == 2){
						game = new DungeonKeep(ogreN, 'S');
					}
					
					btnDown.setEnabled(true);
					btnUp.setEnabled(true);
					btnLeft.setEnabled(true);
					btnRight.setEnabled(true);
					draw();
					
				}

			}

			
		});
		btnNewGame.setBounds(600, 20, 100, 23);
		frame.getContentPane().add(btnNewGame);

		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(600, 520, 100, 23);
		frame.getContentPane().add(btnExit);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(22, 363, 359, -282);
		frame.getContentPane().add(textArea);

		btnUp = new JButton("Up");

		btnUp.setBounds(620, 220, 60, 23);
		frame.getContentPane().add(btnUp);
		btnUp.setEnabled(false);

		btnLeft = new JButton("Left");
		btnLeft.setBounds(570, 260, 60, 23);
		frame.getContentPane().add(btnLeft);
		btnLeft.setEnabled(false);

		btnRight = new JButton("Right");
		btnRight.setBounds(670, 260, 60, 23);
		frame.getContentPane().add(btnRight);
		btnRight.setEnabled(false);

		btnDown = new JButton("Down");
		btnDown.setBounds(620, 300, 60, 23);
		frame.getContentPane().add(btnDown);
		btnDown.setEnabled(false);


		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				state = game.processMovementInput("a");
				stateUpdate();
				draw();
			}

		});

		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				state = game.processMovementInput("d");
				stateUpdate();
				draw();

			}

		});

		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				state = game.processMovementInput("w");
				stateUpdate();
				draw();
			}

		});

		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				state = game.processMovementInput("s");
				stateUpdate();
				draw();
			}
		});
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);

			}

		});
	}


	public void stateUpdate(){
		
		switch(state){
		case INVALID_MOVE:
			break;
		case LVL_PLAY:
			game.processAiMovement();
			state = game.updateState();
			if(state == DungeonKeep.State.LVL_COMPLETED || state == DungeonKeep.State.GAME_OVER || state == DungeonKeep.State.GAME_COMPLETED )
				stateUpdate();
			break;
		case LVL_COMPLETED:
			System.out.println("Nivel Completado!");
			state = game.nextLvl();
			break;
		case LVL_RESTART:
			game = new DungeonKeep();
			break;
		case GAME_OVER:
			System.out.println("GAME OVER!"); //janela game over?
			state = DungeonKeep.State.GAME_OVER;
			System.exit(0);//lol
			break;
		case GAME_RESTART:
			game = new DungeonKeep();
			state = DungeonKeep.State.GAME_RESTART;
			break;
		case GAME_COMPLETED:
			System.out.println("Fim do jogo!");// janela de vitoria?
			state = DungeonKeep.State.GAME_COMPLETED;
			System.exit(0);//lol
			break;
		default:
			break;	
		}



	}
	
	private void draw() {
		String board = game.displayString();
		boardlbl.setText(board);
		
	}
	
	

}
