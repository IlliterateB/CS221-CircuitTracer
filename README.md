****************
* Circuit Tracer
* CS221
* May 1, 2026
* Bryson Leatham
**************** 

CircuitTracer uses a CircuitBoard to find the shortest path from a start point and an end point. It can either be used with a stack or queue search, but both are brute-force. After solving, it can either be output into the console or onto a GUI.


INCLUDED FILES:

* <CircuitTracer.java> - source file - The main driver, searches a CircuitBoard for the shortest path
* <CircuitBoard.java> - source file - Parses a given file to create a CircuitBoard
* <CircuitTracerGUI.java> - source file - Creates a GUI window output for a solved Circuit
* <CircuitTracerTester.java> - source file - tests all functionality in Circuit Tracer, Board, GUI, etc.
* <InvalidFileFormatException.java> - source file - details an Invalid File Format
* <OccupiedPositionException.java> - source file - details an Occupied Position Exception
* <Storage.java> - source file - allows for easy switching between queue and stack searching
* <TraceState.java> - source file - represents paths on CircuitBoard
* <README.md> - this file


COMPILING AND RUNNING:

    To compile this project, use:
    $ javac CircuitTracer.java

    To run the compiled project, use:
    $ java CircuitTracer <-s|-q> <-c|-g> <input file>

    For the args, <-s> dignifies a stack search, <-q> for a queue search. <-c> is used for console output, <-g> for a GUI output, and <input file> is the selected file to parse and search on. 

    For a console output, the solved boards will be written out with a space in between and a 'T' signifies the Trace chosen. For a GUI output, the original board is displayed, and on the right there will be buttons to select which solution to see. When a solution is selected, the Trace path will have a gray background and a red text.


PROGRAM DESIGN AND IMPORTANT CONCEPTS:

    This Circuit Tracer uses a public class <CircuitTracer(String[] args)> which is then called in the <main>. First, CircuitBoard parses the given input file and does prior InvalidFileFormatException checking. Then, CircuitTracer checks args and uses those to initialize Storage for a Queue or Stack. It then checks surrounding postions from the starting position, moves to open positions based on the search type, and creates a TraceState on the position. The character '1' is used for the start position, '2' is the ending position, 'X' is a wall that blocks a path, a 'O' (capital o, not 0), counts as an open position that a 'T', or Trace can replace it. An example input file looks like this:

        3 3
        1 O X
        X O X
        2 O O

    The first 3 designates the number of rows on the board, and the second 3 is for the columns. If it is formatted incorrectly, an InvalidFileFormatException is thrown, and if the program for some 
    reason tries to trace a path on a non-open position, an OccupiedPositionException is thrown.
    
    Circuit Tracer can use two different brute-force search orders, Stack and Queue. Stack is a Last-In-First-Out based search, and uses depth-first exploration and follows until the it either finds a solution or gets stuck, then backtracks to the last open position. Queue, however, is a First-In-First-Out, breadth-first based search. It searches all states once, then loops back to the first open position.

    Circuit Tracer's main logic is it first checks if the positions surrounding the starting '1' are open. For each open position, a new TraceState is created at that position and added in an ArrayList of TraceStates called stateStore. Next, while stateStore isn't empty, it constantly checks whether the current path is a solution, or it keeps iterating and checking around each current position to see where to move.


TESTING:

    Using CircuitTracerTester.java, it is very easy to test many different outcomes and inputs for Circuit Tracer.
    Running after each code change allows for quick feedback whether it fixed functionality or made it worse.
    Scenarios were given to test from many input files, both formatted correctly, and incorrectly. Scenarios
    labeled at the top with "CircuitBoard Constructor Tests" test whether the inputted file is formatted correctly,
    such as if the number of rows and columns are correct, whether there is more than a single start or end/ if there
    is any at all. They may also test to see if the character at each position is actually an allowable char.

    The next two sets of tester scenarios deal with the CircuitTracer, and see if the program's output matches
    the tester's for both valid and invalid inputs. Incorrect command line argument tests are also in the 
    "CircuitTracer Invalid Command Line Tests" section, where it tests if a helpful usage message is output.
    The CircuitTracerTester also checks both Stack and Queue search, as well as both console and GUI output.

    No known bugs exist, and many conditional statements were created in CircuitBoard to test for different
    types of incorrectly formatted files.


DISCUSSION:

    When working on this project, it took me a while to fully understand and start the project.
    Many of my classes had projects this week, meaning I had to push this back and build up stress.
    Once I finally got through my mind-block, I was able to understand the TraceState and Storage 
    methods and was able to write from the pseudocode fairly quickly.

    My hardest bug I had to deal with was in the while(!stateStore.isEmpty()) loop, as I 
    accidentally connected the isSolution if statements to the rest of the searching function.
    This means it would stop after 1 or 2 moves and I couldn't tell why for over 45 minutes 
    if I had to guess. The only way I noticed was by adding a moveCount because I wanted
    to find the Order as well. That's when I saw it always stopped after the one or two moves.

    Overall, this problem wasn't too difficult comparatively, as we were given pseudocode, a
    finished tester, and many finished classes. This I am very grateful for due to the time crunch.
    I also liked this project as it got me more used to using pseudocode, and the analysis questions
    made me think deeper into the logic and movements.


EXTRA CREDIT:

    CircuitTracerGUI.java opens a window that you can see the original board, all the possible solutions for that file, and has an about tab which creates a popup window.






### Analysis ###

How does the choice of Storage configuration (stack vs queue) affect the sequence in which paths are explored in the search algorithm? (This requires more than a "stacks are LIFOs and queues are FIFOs" answer.)

    In a stack search, possibilities are built off the last created configuration. So from 2 possibilities, stack will add to the second configuration. It will then keep building off the most recent one until it reaches the end, or gets stuck and can't move, which it will then build off the most recent available configuration. In a queue search, it instead builds off each available configuration 1 at a time. If there are two initial possibilities, it will go to the first configuration and make all one-move possibilities from it, then move to the second. 

Is the total number of search states (possible paths) affected by the choice of stack or queue?
Is using one of the storage structures likely to find a solution in fewer steps than the other? Always?

    The total number of possible states doesn't change, and both choices go through every possible path. The queue is likely to find a solution the fastest since it is guaranteed to be the first shortest path. However, it is not guaranteed to be faster than a stack because the stack search can happen to go in the shortest path without doing unnecessary steps prior. 

Does using either of the storage structures guarantee that the first solution found will be a shortest path?

    The queue guarantees that the first solution will be a shortest path, but a stop is not implemented in this project as it is a brute-force search.

How is memory use (the maximum number of states in Storage at one time) affected by the choice of underlying structure?

    The queue keeps every one step move from a prior state at once, while the stack only keeps the current stack in memory at a time. This means the the queue takes up more memory, but if there were checks on the queue to stop once it found the first path, it could lead to less total memory than the brute force stack search.

What is the Big-Oh runtime order for the search algorithm?
What does the order reflect? (Maximum size of Storage? Number of board positions? Number of paths explored? Maximum path length? Something else?)

    The Big-Oh runtime is exponential. And it is based on the number of paths explored. In a test I did of a 3x6 board, having 18 spots to check resulted in 795 total actions, while blocking a spot in the corner so it can't be checked resulted in a total of 390 actions. 


What is 'n', the single primary input factor that increases the difficulty of the task?

    n is the number of available spots to check.
