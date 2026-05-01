****************
* Circuit Tracer
* CS221
* Apr 10, 2026
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

    This Circuit Tracer uses a public class <CircuitTracer(String[] args)> which is then called in the <main>. First, CircuitBoard parses the given input file and does prior InvalidFileFormatException checking. Then, CircuitTracer checks args and uses those to initialize Storage for a Queue or Stack. It then checks surrounding postions from the starting position, moves to open positions based on the search type, and creates a TraceState on the position.  
    
    
    Circuit Tracer can use two different brute-force search orders, Stack and Queue. Stack is a Last-In-First-Out based search, and uses depth-first exploration and follows until the it either finds a solution or gets stuck, then backtracks to the last open position. Queue, however, is a First-In-First-Out, breadth-first based search. It searches all states once, then loops back to the first open position.


    This Double-Linked List is very similar to a single-linked list
    at its core, but with the added functionality of traversing backwards.
    Being able to traverse the list from both ends allows certain methods,
    like removeLast() or addToFront(), to be constant-time. It also allows
    methods like get() or any index-based method to cut the Order coefficient
    in half by starting at the closer end to the node.

    With those index-based methods, special cases, usually at the head or tail,
    are taken care of first. Then, if the index is less than half of the list’s size,
    the current node is set to head and counts up from there; otherwise, the current
    node is set to tail and counts down to the index. Once the current node reaches
    that index, the specific method’s functions will run.

    IUDoubleLinkedList also has a  class that implements the ListIterator
    interface. It is also a fail-fast iterator, so if the list changes, it will throw a
    ConcurrentModificationException, so it doesn’t give false information.
    Its constructors allow it to start at the beginning of the list,
    or in front of any specific index as long as it is within 0 to the list size.
    This means that a call to next() will point to head, or the Node at the input index.
    ListIterator allows the user to traverse the list from both directions, add, remove,
    set, and get positional information about the list as well.

    Node.java functions are used in IUDoubleLinkedList to move nodes, select surrounding
    nodes, and even set a specific node to reference a different Generic T Object.



TESTING:

Using ListTester.java, it is very easy to test the functionality of the
Double-Linked List. Running whenever the code is changed gives feedback on
how those changes fixed or ruined functionality. Scenarios were added to test
all types of generic cases, as well as many edge cases. These edge cases are
typically for IndexOutOfBounds and NoSuchElement Exceptions, accounting for
erroneous input.

No known bugs persist, but as-is, there is no way to check the total amount
of tests implemented compared to the amount run. When creating or altering
this program, the total tests run can vary wildly.



DISCUSSION:

When creating this program, I frequently had problems getting to the
correct node, like when using index-based functions, moving from
the tail. After I was able to confirm that some of those loops worked
correctly using the ListTester, I got more comfortable looping through the list.

When adding new List Iterator scenarios in ListTester, I also struggled
because my Literator add function basically wasn’t written at that point. I
must have started it, got distracted, and never went back until the end.
I also had a problem because I made the error of adding Element_A twice to
the scenario’s list, and I tried altering multiple methods across all of
IUDoubleLinkedList, rather than noticing I made that mistake.

Overall, this project was very challenging in its specifics. Understanding
the general idea of what needed to happen wasn’t extraordinarily difficult,
but finding the exact steps to get to that point could be.


 
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

    n is the number of available spots to check
