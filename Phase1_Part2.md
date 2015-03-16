# Phase 1 - Part 2 #

**In Part 2, the following tasks are to be performed:**

  1. Construct four (4) clones of the CPU and run them concurrently as threads. The threads share certain resources (memory and ready queue) which are maintained by the system. Each clone has its own/private cache. Any available thread or CPU can be used to run any processes in the queue.
  1. Modify the scheduler and the dispatcher by extending them respectively into m-scheduler and m-dispatcher (for the multiprocessor system). You may use a symmetric or asymmetric approach – either way, you need to rethink your design of the scheduling and dispatching logic.
  1. Execute the set of given processes (30) and measure the following: [Note: Each scheduled process must be run from start to finish on the designated CPU – no blocking or interruption in Phase 1]
    * Average waiting and completion time for each process
    * Number of I/O each process made
    * Average RAM and cache used

Measurements to be taken are the same as for Part 1. Compare the measurements taken in Parts 1 & 2 and discuss the effect of cloning the CPU on the performance/timings of the processes.

Turn in a 5+page of summary report, along with data tables and graphs (if any), the code (on a disk) and the core dump of the memory. Again, the report should describe your observations and findings.