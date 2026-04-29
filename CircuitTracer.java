import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail
 * @author Bryson Leatham
 */
public class CircuitTracer {

	/** Launch the program. 
	 * 
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 */
	public static void main(String[] args) {
		new CircuitTracer(args); //create this with args
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private void printUsage() {
		//TODO: print out clear usage instructions when there are problems with
		// any command line args
		System.out.println("Usage: java CircuitTracer <-s|-q> <-c|-g> <input file>");
		System.out.println("  -s: use stack for search");
		System.out.println("  -q: use queue for search");
		System.out.println("  -c: console output");
		System.out.println("  -g: GUI output (unimplemented) ");
		System.out.println("  <input file>: path to the circuit board input file");
	}
	
	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments. Search for shortest paths and report results.
	 * 
	 * @param args command line arguments passed through from main()
	 */
	public CircuitTracer(String[] args) {
		//TODO: parse and validate command line args - first validation provided
		if (args.length != 3) {
			printUsage();
			return; //exit the constructor immediately
		}

		// just to test and make sure its actually reading the args correctly TODO delete
		// for (String a : args) {
		// 	System.out.println(a);
		// }

		if (!args[0].equals("-s") && !args[0].equals("-q")) {
			printUsage();
			return; //exit the constructor immediately
		}

		if (!args[1].equals("-c") && !args[1].equals("-g")) {
			printUsage();
			return; //exit the constructor immediately
		}

		if (args[2].isEmpty()) {
			printUsage();
			return; //exit the constructor immediately
		}
		
		//TODO: initialize the Storage to use either a stack or queue
		Storage<TraceState> storage = null;
	
		if (args[0].equals("-s")) {
			storage = new Storage<TraceState>(Storage.DataStructure.stack);
		} else if (args[0].equals("-q")) {
			storage = new Storage<TraceState>(Storage.DataStructure.queue);
		}

		//TODO: read in the CircuitBoard from the given file
		CircuitBoard board;

		try {
			board = new CircuitBoard(args[2]);
		} catch (InvalidFileFormatException e) {
			System.out.println("InvalidFileFormatException: " + e.toString());
			return; //exit the constructor immediately
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException: " + e.toString());
			return; //exit the constructor immediately
		} 

		//TODO: run the search for best paths

		Storage<TraceState> stateStore = storage;
		ArrayList<TraceState> bestPaths = new ArrayList<TraceState>();
		
		int moveCount = 0;

		// seed the store with initial TraceState for each open neighbor of the starting component
		int[] dirRow = {-1, 1, 0, 0};
		int[] dirCol = {0, 0, -1, 1};
		for (int i = 0; i < 4; i++) {
			int newRow = board.getStartingPoint().x + dirRow[i];
			int newCol = board.getStartingPoint().y + dirCol[i];
			if (board.isOpen(newRow, newCol)) {
				stateStore.store(new TraceState(board, newRow, newCol));
				moveCount++;
			}
		}

		while (!stateStore.isEmpty()) {
			TraceState currState = stateStore.retrieve();
			
			if (currState.isSolution()) {
				if (bestPaths.isEmpty() || currState.pathLength() < bestPaths.get(0).pathLength()) {
					bestPaths.clear();
					bestPaths.add(currState);
				} else if (currState.pathLength() == bestPaths.get(0).pathLength()) {
					bestPaths.add(currState);
				}
			} else {
					int currRow = currState.getRow();
					int currCol = currState.getCol();

					for (int i = 0; i < 4; i++) {
						int newRow = currRow + dirRow[i];
						int newCol = currCol + dirCol[i];

						if (currState.isOpen(newRow, newCol)) {
							stateStore.store(new TraceState(currState, newRow, newCol));
							moveCount++;
						}
					}	
				}
			}
		


		//TODO: output results to console or GUI, according to specified choice

		if (args[1].equals("-c")) {
			// Console output
			for (TraceState path : bestPaths) {
				System.out.println(path.toString());
			}
			// System.out.println(moveCount);

		} else if (args[1].equals("-g")) {
			// GUI output (unimplemented)
			System.out.println("Please use <-c> command-line arg. GUI output is unimplemented.");
		}

		
	}

} // class CircuitTracer
