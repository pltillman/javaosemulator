# Phase 1 - Part 1 #


**In Part 1, the following tasks are to be performed:**

  1. Decompose the project into 3 subtasks:
    1. The Driver/Loader/Long-term sch subtasks,
    1. The Short-term/Dispatcher/Context-Switcher subtask and
    1. the CPU and associated modules.
  1. Then define all the interfaces and data structures to be shared by all the 3 major components, and integrate them into your simulator).
  1. Download the set of tasks/programs (in the program-data-file) to be executed from the course website.
  1. Design and write the code for the short-term loader (you may take advantage of the loader-module in the given code).
  1. Add a program cache in the CPU module and compile it. And when the scheduler assigns a process to the CPU, the whole process must be loaded into the program cache of the CPU from the RAM. Run this single CPU as a single thread (a uniprocessor system).
  1. Execute the set of given processes (30) and measure the following.
    * Average waiting and completion time for each process
    * Number of I/O each process made
    * Average RAM and cache used



http://javaosemulator.googlecode.com/files/OSProjectSpec-BlockDiagram.JPG