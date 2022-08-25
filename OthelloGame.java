import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class OthelloGame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JButton[] button = new JButton[67];
	int[][] othelloMatrix = new int[8][8];
	Font font = new Font("Comic Sans MS", Font.BOLD, 18);
	int count;
	Icon player_1_icon, player_2_icon, guide_icon;
	boolean Player_1_turn;
	final boolean GAME_INCOMPLETE = false;
	final boolean GAME_COMPLETE = true;
	final int PLAYER_1 = 1;
	final int PLAYER_2 = 2;
	final int DRAW = 0;
	int player_1_count;
	int player_2_count;
	int empty_count;
	String player_1_name;
	String player_2_name;
	JTextArea player_1_display;
	JTextArea player_2_display;
	int[] xDir = {0, 1, 1, 1, 0, -1, -1, -1};
	int[] yDir = {1, 1, 0, -1, -1, -1, 0, 1};

	OthelloGame() {
		super("Othello");

		for(int i=0; i<8; i++) {
			int x = 10;
			int y = 70+50*i;
			for(int j=0; j<8; j++) {
				button[count] = new JButton();
				button[count].setBounds(x+50*j, y, 50, 50);
				button[count].setBackground(Color.decode("#51FF24"));
				add(button[count]);
				button[count++].addActionListener(this);
			}
		}

		player_1_icon = new ImageIcon("C:\\Users\\nil04\\OneDrive\\Desktop\\javaEclipse\\OthelloGame\\src\\OthelloGame\\black.png");
		player_2_icon = new ImageIcon("C:\\Users\\nil04\\OneDrive\\Desktop\\javaEclipse\\OthelloGame\\src\\OthelloGame\\white.png");
		guide_icon = new ImageIcon("C:\\Users\\nil04\\OneDrive\\Desktop\\javaEclipse\\OthelloGame\\src\\OthelloGame\\guide.png");

		button[27].setIcon(player_1_icon);
		othelloMatrix[3][3] = PLAYER_1;
		button[28].setIcon(player_2_icon);
		othelloMatrix[3][4] = PLAYER_2;
		button[35].setIcon(player_2_icon);
		othelloMatrix[4][3] = PLAYER_2;
		button[36].setIcon(player_1_icon);
		othelloMatrix[4][4] = PLAYER_1;


		player_1_count = 2;
		player_2_count = 2;
		empty_count = 60;

		button[64] = new JButton("RESET");
		button[64].setBounds(160, 480, 100, 50);
		button[64].setBackground(Color.white);
		button[64].setFont(font);
		add(button[64]);
		button[64].addActionListener(this);

		Player_1_turn = true;

		button[65] = new JButton();
		button[65].setIcon(player_1_icon);
		button[65].setBounds(10, 10, 50, 50);
		add(button[65]);

		player_1_display = new JTextArea(2,15);
		player_1_display.setBounds(60, 10, 147, 50);
		player_1_display.setBackground(Color.decode("#51FF24"));
		player_1_display.setEditable(false);
		player_1_display.setFont(font);
		add(player_1_display);

		button[66] = new JButton();
		button[66].setIcon(player_2_icon);
		button[66].setBounds(207, 10, 50, 50);
		add(button[66]);

		player_2_display = new JTextArea();
		player_2_display.setBounds(257, 10, 155, 50);
		player_2_display.setBackground(Color.decode("#51FF24"));
		player_2_display.setEditable(false);
		player_2_display.setFont(font);
		add(player_2_display);

		player_1_display.setText("  "+"->  Count: \n   Player_1 : "+player_1_count);
		player_2_display.setText("  "+"    Count: \n   Player_2 : "+player_2_count);

		setDesign();
		setSize(435, 580);
		setLayout(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		player_1_name = JOptionPane.showInputDialog(OthelloGame.this, "Player 1's Name");
		player_2_name = JOptionPane.showInputDialog(OthelloGame.this, "Player 2's Name");

		player_1_display.setText("  "+"->  Count: \n   "+player_1_name+" : "+player_1_count);
		player_2_display.setText("  "+"    Count: \n   "+player_2_name+" : "+player_2_count);

		count = 0;
		guide();
	}

	private void guide() {

		boolean cell_possible = false;
		boolean move_possible = false;
		int player_Symbol;
		if(Player_1_turn) {
			player_Symbol = PLAYER_1;
		}
		else {
			player_Symbol = PLAYER_2;
		}
		for(int x=0; x<8; x++) {
			for(int y=0; y<8; y++) {
				cell_possible = false;
				if(othelloMatrix[x][y]==0) {
					for(int k=0; k<8; k++) {
						int xStep = xDir[k];
						int yStep = yDir[k];
						int i = x+xStep;
						int j = y+yStep;
						if(i<0 || i>=8 || j<0 || j>=8 || othelloMatrix[i][j] == player_Symbol || othelloMatrix[i][j] == 0) {
							continue;
						}
						i = i+xStep;
						j = j+yStep;
						while (i>=0 && i<8 && j>=0 && j<8) {
							if(othelloMatrix[i][j] == 0) {
								break;
							}
							else if (othelloMatrix[i][j] != player_Symbol) {
								i = i+xStep;
								j = j+yStep;
							}
							else {
								cell_possible = true;
								move_possible = true;
								button[x*8+y].setIcon(guide_icon);
								break;
							}
						}
						if(cell_possible) {
							break;
						}
					}
				}
			}
		}
		if(!move_possible) {			
			if(Player_1_turn) {
				JOptionPane.showMessageDialog(OthelloGame.this, "*** Opsss No Possible Move *** !!! "+player_2_name+"'s Move !!!");
			}
			else {
				JOptionPane.showMessageDialog(OthelloGame.this, "*** Opsss No Possible Move *** !!! "+player_1_name+"'s Move !!!");
			}
			Player_1_turn = ! Player_1_turn;
			guide();
		}
	}

	public static void main(String[] args) {
		new OthelloGame();
	}

	private void setDesign() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button[64]) {
			resetGame();
		}
		else {
			for(int i=0; i<64; i++) {
				if(e.getSource() == button[i]) {
					clear_Guide();
					if(button[i].getIcon() == null) {
						int x = i/8;
						int y = i%8;
						boolean result = GAME_INCOMPLETE;
						result = move(x, y, Player_1_turn, i);
						if(result == GAME_COMPLETE) {
							if(Player_1_turn) {
								player_1_display.setText("  "+"->  Count: \n   "+player_1_name+" : "+player_1_count);
								player_2_display.setText("  "+"    Count: \n   "+player_2_name+" : "+player_2_count);
							}
							else {
								player_1_display.setText("  "+"    Count: \n   "+player_1_name+" : "+player_1_count);
								player_2_display.setText("  "+"->  Count: \n   "+player_2_name+" : "+player_2_count);
							}
							int winner = checkWinner();
							if(winner == PLAYER_1) {
								JOptionPane.showMessageDialog(OthelloGame.this, "!!! "+player_1_name+" Wins !!!");
								resetGame();
								break;
							}
							else if(winner == PLAYER_2) {
								JOptionPane.showMessageDialog(OthelloGame.this, "!!! "+player_2_name+" Wins !!!");
								resetGame();
								break;
							}
							else {
								JOptionPane.showMessageDialog(OthelloGame.this, "!!! It's a Draw !!!");
								resetGame();
								break;
							}
						}
					}
				}
				guide();
			}
		}
		if(Player_1_turn) {
			player_1_display.setText("  "+"->  Count: \n   "+player_1_name+" : "+player_1_count);
			player_2_display.setText("  "+"    Count: \n   "+player_2_name+" : "+player_2_count);
		}
		else {
			player_1_display.setText("  "+"    Count: \n   "+player_1_name+" : "+player_1_count);
			player_2_display.setText("  "+"->  Count: \n   "+player_2_name+" : "+player_2_count);
		}
	}

	private void clear_Guide() {
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(button[i*8+j].getIcon() == guide_icon) {
					button[i*8+j].setIcon(null);
				}
			}
		}
	}

	private int checkWinner() {
		if(player_1_count > player_2_count) {
			return PLAYER_1;
		}
		else if(player_2_count > player_1_count) {
			return PLAYER_2;
		}
		else {
			return DRAW;
		}
	}

	private boolean move(int x, int y, boolean player_1_turn, int button_number) {
		boolean isPossible = false;
		int player_Symbol;
		if(player_1_turn) {
			player_Symbol = PLAYER_1;
		}
		else {
			player_Symbol = PLAYER_2;
		}

		for(int k=0; k<8; k++) {
			int xStep = xDir[k];
			int yStep = yDir[k];
			int i = x+xStep;
			int j = y+yStep;
			if(i<0 || i>=8 || j<0 || j>=8 || othelloMatrix[i][j] == player_Symbol || othelloMatrix[i][j] == 0) {
				continue;
			}
			i = i+xStep;
			j = j+yStep;
			while (i>=0 && i<8 && j>=0 && j<8) {
				if(othelloMatrix[i][j] == 0) {
					break;
				}
				else if (othelloMatrix[i][j] != player_Symbol) {
					i = i+xStep;
					j = j+yStep;
				}
				else {
					isPossible = true;
					makeChanges(player_Symbol, x+xStep, y+yStep, xStep, yStep, i, j);
					break;
				}
			}
		}
		if(isPossible) {
			othelloMatrix[x][y] = player_Symbol;
			if(player_1_turn) {
				button[button_number].setIcon(player_1_icon);
				player_1_count++;
			}
			else {
				button[button_number].setIcon(player_2_icon);
				player_2_count++;
			}
			empty_count--;
			Player_1_turn = ! Player_1_turn;
		}

		if(empty_count == 0) {
			return GAME_COMPLETE;
		}
		else {
			return GAME_INCOMPLETE;
		}
	}

	private void makeChanges(int player_Symbol, int x, int y, int xStep, int yStep, int i, int j) {
		if(!(x == i && y == j)) {
			othelloMatrix[x][y] = player_Symbol;
			int button_number = (x*8)+y;
			if(player_Symbol == PLAYER_1) {
				button[button_number].setIcon(player_1_icon);
				player_1_count++;
				player_2_count--;
			}
			else {
				button[button_number].setIcon(player_2_icon);
				player_2_count++;
				player_1_count--;
			}
			makeChanges(player_Symbol, x+xStep, y+yStep, xStep, yStep, i, j);
		}
	}

	private void resetGame() {
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				othelloMatrix[i][j] = 0;
				int button_number = (i*8)+j;
				button[button_number].setIcon(null);
			}
		}

		button[27].setIcon(player_1_icon);
		othelloMatrix[3][3] = PLAYER_1;
		button[28].setIcon(player_2_icon);
		othelloMatrix[3][4] = PLAYER_2;
		button[35].setIcon(player_2_icon);
		othelloMatrix[4][3] = PLAYER_2;
		button[36].setIcon(player_1_icon);
		othelloMatrix[4][4] = PLAYER_1;

		player_1_count = 2;
		player_2_count = 2;
		empty_count = 60;

		Player_1_turn = true;

		if(Player_1_turn) {
			player_1_display.setText("  "+"->  Count: \n   "+player_1_name+" : "+player_1_count);
			player_2_display.setText("  "+"    Count: \n   "+player_2_name+" : "+player_2_count);
		}
		else {
			player_1_display.setText("  "+"    Count: \n   "+player_1_name+" : "+player_1_count);
			player_2_display.setText("  "+"->  Count: \n   "+player_2_name+" : "+player_2_count);
		}
		
		guide();
	}

}
