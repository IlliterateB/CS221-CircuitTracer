import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.ArrayList;


/**
 * Main panel for Circuit Tracer GUI output 
 * Top-level organizer and coordinator between sub-panels/controls. 
 * 
 * @author Bryson Leatham
 */
public class CircuitTracerGUI extends JPanel {
	// Top-level Swing components
	private JFrame frame;              // main application window
	private JPanel boardPanel;         // panel that holds the grid of board cells
	private JLabel[][] posLabel;       // 2D array of labels corresponding to board positions

	// Sidebar components and model data
	private JList<String> solutionList; // list showing available solution paths
	private CircuitBoard board;         // original board used to initialize the GUI
	private ArrayList<TraceState> solutions; // list of solution TraceState objects
	
	public CircuitTracerGUI(CircuitBoard board, ArrayList<TraceState> solutions) {
		this.board = board;
		this.solutions = solutions;

		// Build the main application window
		frame = new JFrame("Circuit Tracer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLayout(new BorderLayout());

		int rows = board.numRows();
		int cols = board.numCols();

		// Create the board panel using a GridLayout so labels line up like the board
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(rows, cols));
		posLabel = new JLabel[rows][cols];

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				// Initialize each cell label and set its appearance based on board contents
				posLabel[r][c] = new JLabel();
				boardPanel.add(posLabel[r][c]);

				posLabel[r][c].setOpaque(true);
				posLabel[r][c].setHorizontalAlignment(JLabel.CENTER);
				posLabel[r][c].setFont(new Font("Arial", Font.BOLD, 20));
				posLabel[r][c].setPreferredSize(new Dimension(100, 50));
				posLabel[r][c].setBorder(BorderFactory.createLineBorder(Color.BLACK));

				// Render initial character for this board position (T, X, O, 1, 2)
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
		}

		frame.setVisible(true);

		// Build the model that populates the solutions list in the sidebar
		DefaultListModel<String> listModel = new DefaultListModel<>();
		for (int i = 0; i < solutions.size(); i++) {
			listModel.addElement("Solution " + (i + 1) + " (length: " + solutions.get(i).pathLength() + ")");
		}
		
		
		solutionList = new JList<>(listModel);
		solutionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		solutionList.addListSelectionListener(new ListSelectListener());

		JScrollPane scrollPane = new JScrollPane(solutionList);
		scrollPane.setPreferredSize(new Dimension(200, 600));
		frame.add(scrollPane, BorderLayout.EAST);
	
		// Simple Help tab in bar that shows an About dialog
		JMenuBar menuBar = new JMenuBar();
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(frame,
			"Circuit Tracer GUI\nAuthor: Bryson Leatham\nThis GUI allows you to view different solutions for connecting components on a circuit board.",
			"About", JOptionPane.INFORMATION_MESSAGE));
		helpMenu.add(aboutItem);
		menuBar.add(helpMenu);
		frame.setJMenuBar(menuBar);

		// Fit everything on the window
		frame.pack();

	}

	private void solutionBoard(CircuitBoard board, TraceState solution) {
		// Reset the display to the base board characters, clearing any previous highlights
		for (int r = 0; r < board.numRows(); r++) {
			for (int c = 0; c < board.numCols(); c++) {
				posLabel[r][c].setText("" + board.charAt(r, c));
				posLabel[r][c].setBackground(Color.WHITE);

				// Restore the correct color and text for each board character
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
		}

		// Highlight the solution path on the board by marking T's and changing background
		ArrayList<Point> path = solution.getPath();
		for (Point p : path) {
			posLabel[p.x][p.y].setText("T");
			posLabel[p.x][p.y].setBackground(Color.GRAY);
			posLabel[p.x][p.y].setForeground(Color.RED);
		}
	}

	private class ListSelectListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				int selectedIndex = solutionList.getSelectedIndex();
				if (selectedIndex != -1) {
					TraceState selectedSolution = solutions.get(selectedIndex);
					solutionBoard(board, selectedSolution);
				}
			}
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
	


