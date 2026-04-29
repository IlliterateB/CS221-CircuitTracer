### README ###







### Analysis ###

How does the choice of Storage configuration (stack vs queue) affect the sequence in which paths are explored in the search algorithm? (This requires more than a "stacks are LIFOs and queues are FIFOs" answer.)

    In a stack search, possibilities are built off the last created configuration. So from 2 possibilities, stack will add to the second configuration. It will then keep building off the most recent one until it reaches the end, or gets stuck and can't move, which it will then build off the most recent available configuration. In a queue search, it instead builds off each available configuration 1 at a time. If there are two initial possibilities, it will go to the first configuration and make all one-move possibilities from it, then move to the second. 

Is the total number of search states (possible paths) affected by the choice of stack or queue?
Is using one of the storage structures likely to find a solution in fewer steps than the other? Always?

    The total number of possible states doesn't change, and both choices go through every possible path. The queue is likely to find a solution the fastest since it is guaranteed to be the first shortest path. However, it is not guaranteed to be faster than a stack because the stack search can happen to go in the shortest path without doing unnecessary steps prior. 

Does using either of the storage structures guarantee that the first solution found will be a shortest path?

    The queue guarantees that the first solution will be a shortest path.

How is memory use (the maximum number of states in Storage at one time) affected by the choice of underlying structure?

    The queue keeps every one step move from a prior state at once, while the stack only keeps the current stack in memory at a time.

What is the Big-Oh runtime order for the search algorithm?
What does the order reflect? (Maximum size of Storage? Number of board positions? Number of paths explored? Maximum path length? Something else?)


What is 'n', the single primary input factor that increases the difficulty of the task?

    n is the size of the circuit that was given.
