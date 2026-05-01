import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Main panel for Circuit Tracer GUI output 
 * Top-level organizer and coordinator between sub-panels/controls. 
 * 
 * @author Bryson Leatham
 */
public class CircuitTracerGUI extends JPanel {
	
	private JFrame frame;
	private JPanel boardPanel;
	private JLabel[][] posLabel;
	
	public CircuitTracerGUI(CircuitBoard board, ArrayList<TraceState> solutions) {
		frame = new JFrame("Circuit Tracer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLayout(new BorderLayout());

		int rows = board.numRows();
		int cols = board.numCols();

		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(rows, cols));
		posLabel = new JLabel[rows][cols];

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				posLabel[r][c] = new JLabel();
				boardPanel.add(posLabel[r][c]);

				posLabel[r][c].setOpaque(true);
				posLabel[r][c].setHorizontalAlignment(JLabel.CENTER);
				posLabel[r][c].setFont(new Font("Arial", Font.BOLD, 20));
				posLabel[r][c].setPreferredSize(new Dimension(50, 50));
				posLabel[r][c].setBorder(BorderFactory.createLineBorder(Color.BLACK));

				if (board.charAt(r, c) == 'T') {
					posLabel[r][c].setText("T");
					posLabel[r][c].setForeground(Color.RED);
				} else if (board.charAt(r, c) == 'X') {
					posLabel[r][c].setForeground(Color.BLACK);
					posLabel[r][c].setText("X");
				} else if (board.charAt(r, c) == 'O') {
					posLabel[r][c].setForeground(Color.BLACK);
					posLabel[r][c].setText("O");
				} else if (board.charAt(r, c) == '1') {
					posLabel[r][c].setForeground(Color.GREEN);
					posLabel[r][c].setText("1");
				} else if (board.charAt(r, c) == '2') {
					posLabel[r][c].setForeground(Color.BLUE);
					posLabel[r][c].setText("2");
				}
		}

		frame.add(boardPanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	}
}






	// /**
	//  * Initialize top-level panel with a grid layout for the circuit
	//  * and a sidebar to see different solutions
	//  */
	// public CircuitTracerGUI() {

	// 	// JButtons for the different output options
		
	// 	// show the selected solution output on the main panel


	// 	JButton resetButton = new JButton("Reset");
	// 	resetButton.setFont (new Font("Arial", Font.PLAIN, 24));
	// 	resetButton.addActionListener(new ResetButtonListener());
	// 	JPanel controlPanel = new JPanel();
	// 	controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
	// 	controlPanel.add(Box.createVerticalGlue()); //for vertical centering
	// 	controlPanel.add(resetButton);
	// 	controlPanel.add(Box.createVerticalGlue()); //for vertical centering
		
		

	// 	JPanel boardPanel = new JPanel();		// Subpanel for the grid of LitePegButtons			
	// 	boardPanel.setLayout(new GridLayout(BOARD_DIMENSION, BOARD_DIMENSION));		// Set grid layout for the buttons
		
		
		
	// 	// populate the colorBoard 2D array and add the buttons to window and add actiion listener to each button 
	// 	for (int i = 0; i < BOARD_DIMENSION; i++)
	// 	{
	// 		for (int j = 0; j < BOARD_DIMENSION; j++)
	// 		{
	// 			colorBoard[i][j] = new LitePegButton();
	// 			colorBoard[i][j].setPreferredSize(new Dimension(25, 25));		// set default size for button
	// 			boardPanel.add(colorBoard[i][j]);
	// 			colorBoard[i][j].addActionListener(new LitePegButtonListener());
	// 		}
	// 	}

	// 	//Set layout of this panel
	// 	//Add subpanels to this panel
	// 	add(boardPanel, BorderLayout.CENTER);		// formats the board panel and the reset button panel
	// 	add(controlPanel, BorderLayout.EAST);
	// }
	
	// /**
	//  * Private listener class to respond to reset button clicks by
	//  * reseting all pegs back to black.
	//  */
	// private class ResetButtonListener implements ActionListener {
	// 	@Override
	// 	public void actionPerformed(ActionEvent e) {
	// 		// Call the reset() method of all LitePegButtons in a nested loop.
	// 		// if (e.getSource() instanceof LitePegButton) {
    //         //     ((LitePegButton) e.getSource()).reset();
    //         // }

	// 		// iterates through colorBoard 2D array and resets each button to black
	// 		for (int i = 0; i < BOARD_DIMENSION; i++)
	// 		{
	// 			for (int j = 0; j < BOARD_DIMENSION; j++)
	// 			{
	// 				colorBoard[i][j].reset();
	// 			}
	// 		}
	// 	}
	// }

	// /**
	//  * Private listener class to advance the color of the clicked
	//  * LitePegButton.
	//  */
	// private class LitePegButtonListener implements ActionListener 
	// {
	// 	@Override
	// 	public void actionPerformed(ActionEvent e) {
	// 		// Cast the event source to a LitePegButton and call its nextColor() method
			
	// 		// had to search up how to get method from other file without calling an object with it. this works though.
	// 		if (e.getSource() instanceof LitePegButton) {
    //             ((LitePegButton) e.getSource()).nextColor();
    //         }
	// 	}
	// }
	


