**OS simulator written purely in Java**


_Project Specification – Phase 1 CS 3243/5243 – Operating Systems_


The semester course project is on the design and implementation of an OS simulator. The project is divided into two phases. Phase 1 has two parts. The requirements for Phase 1 are discussed below.

The simulator will run on a virtual CPU. The CPU’s architecture and instruction repertoire are posted at the course website. This specification of the basic OS simulator is also posted at the course website. The project would involve designing/implementing a number of program modules to complete Phase 1 of the OS simulator.


**Original Specification:**

The memory hierarchy comprises a set of simulated registers, a program/data simulated RAM, called memory and a simulated hard drive called disk. The contents of the disk and RAM are hex characters; and of sizes 2048 and 1024 words, respectively. (Each word is 4 bytes or 8 hex characters long.)

Ancillary programs to dispatch processes/programs from disk to RAM, to compute effective addresses, to access memory, to fetch instructions and decode instructions will also be needed. Additional support programs for conversions between hex, decimal, and binary numbers will be necessary.

The following sections describe the components of the simulator and its virtual machine environments.



**The Driver:**

The OS driver constitutes the core, or the main thread, of the simulator. Its function is to simply call the loader to load user programs, which are already assembled (given as a stream of hex character) and stored in a ‘program-file.’ The Driver calls the Scheduler to select a user program from a list of ‘ready’ jobs for dispatch. The Dispatcher places the selected job or program in an ‘execution context’ to run on the virtual CPU. The CPU executes the programs in the simulated RAM. When a program completes, or an interrupt is received, the Scheduler and the Dispatcher process the next program, or respond to interrupts accordingly. This cycle continues forever or until your simulator is shut down when no more user programs are pending.

The following program sketch should help you design and implement the Driver module for a single batched system:

> Driver {
> > loader();
> > loop
> > > scheduler();
> > > dispatcher();
> > > CPU();
> > > waitforinterrupt();

> > endloop;
> > }



**The Loader**

The loader module opens (once at the start) the program-file and does the loading of the user programs/jobs into the disk. Programs are loaded into disk according to the format of the batched user programs in the program-file. Ancillary programs would be needed to process (strip off) the special control “cards” – which start with, e.g., ‘// Job1 17 2’ before storing the (binary) hex-code. The data sections start with, e.g., ‘// Data B C C’.

The ‘// Job1 17 2’ means user program/job 1 is of size 17 (hex) or (23 in decimal) words with the priority set to 2. Each data segment of a program starts with ‘// Data B C C’, which means the number of words in the job’s input buffer is 11 (B in hex), its output buffer size is 12 (C in hex) words and the size of its temporary buffer is 12 (C in hex) words. The control cards in the program-file contain directives – attribute data about each program, must be extracted and stored in the Process Control Block (PCB) (see below).

The basic steps of the loader are:


> while (not end-of-programdata-file) do {
> > Read-File();
> > Extract program attributes into the PCB
Insert hex-code into simulated RAM

> }



**The Scheduler**

The Scheduler loads programs from the disk into RAM according to the given scheduling policy. The scheduler must note in the PCB, which physical addresses in RAM each program/job begins. This ‘start’ address must be stored in the base-register of the process. It must also use the Input/Output buffer size information (in the control cards) for allocating space in RAM. The Scheduler method either loads one program at a time (in a simple batching system) or multiple programs (in a multiprogramming system) depending on passed parameter(s).  Thus, the Scheduler works closely with the Memory manager and the Effective-Address method.



**Dispatcher**

The Dispatcher method assigns a process to the CPU. It is also responsible for context switching of jobs when necessary (more on this later!). For now, the dispatcher will extract parameter data from the PCB and accordingly set the CPU’s PC, and other registers, before the OS calls the CPU to execute it.

**Memory**

This method is the only means/unit by which RAM can be accessed. A known absolute/physical address must always be passed to this method. The Memory simply fetches an instruction or datum or writes datum into RAM.



**Effective-Address**

This method takes a logical address and returns the corresponding absolute/physical address for the calling unit (e.g., the CPU). The Effective-Address method also supports the Fetch and Decode methods – a part of the CPU, during instruction fetch-decode-execute cycle. The Effective-Address supports two kinds of address translations – direct and indirect using the index register.

The basic steps for calculating the effective addresses are:

> direct addressing: 	EA = C(base-reg)+ D;		// D is the 16-bit offset or displacement
> indirect addressing: 	EA = C(base-reg) + C(I-reg) + D;



**Fetch**
With support from the Memory module/method, this method fetches instructions or data from RAM depending on the content of the CPU’s program counter (PC). On instruction fetch, the PC value should point to the next instruction to be fetched. The Fetch method therefore calls the Effective-Address method to translate the logical address to the corresponding absolute address, using the base-register value and a ‘calculated’ offset/address displacement. The Fetch, therefore, also supports the Decode method of the CPU.



**Decode**

The Decode method is a part of the CPU. Its function is to completely decode a fetched instruction – using the different kinds of address translation schemes of the CPU architecture. (See the supplementary information in the file: Instruction Format.) On decoding, the needed parameters must be loaded into the appropriate registers or data structures pertaining to the program/job and readied for the Execute method to function properly.



**Execute**

This method is essentially a switch-loop of the CPU. One of its key functions is to increment the PC value on ‘successful’ execution of the current instruction.  Note also that if an I/O operation is done via an interrupt, the process is suspended until the DMA-Channel method completes the read/write operation.



**The CPU**

In this simulation we are going to separate compute-only instructions from I/O instructions. Thus, we envision an implementation of two concurrent threads – one to handle each type of instruction. We first discuss the read/write I/O instructions of this thread – the DMA-Channel.



**DMA-Channel**

In small systems with programmed I/O interfaces using interrupts, the CPU can be employed to service slow character-oriented devices since it can service thousands of compute-instructions between any two I/O commands. However, for block-oriented devices, e.g., disk or RAM I/O, it is desirable to delegate a separate device, e.g., the disk channel controller, to work concurrently with the CPU. In this way, the disk device-channel controller works independently on I/O requests, which frees the CPU to focus on compute-only instructions.

The CPU calls the two routines to perform Read and Write from/to RAM (for simplicity):

> Read(ch, next(p\_rec), buf[next\_io](next_io.md));
> Write(ch, next(p\_rec), buf[next\_io](next_io.md));

where ch is the particular channel or DMA controller, p\_rec is the RAM address of the physical data to be transferred, and buf is the starting address of a RAM buffer into/from which the data is to be transferred.

The heart of the DMA-channel module/thread will look like the following:

> DMA () {
> > loop
> > switch(type) {
> > > case 0: Read(ch, next(p\_rec), buf[next\_io](next_io.md));
> > > case 1: Write(ch, next(p\_rec), buf[next\_io](next_io.md));

> > }
> > next\_io := next\_io + 4;		// assuming 1 word of 4 bytes at a time
> > > end; //loop
> > > signal(ComputeOnly)			// signal the main CPU to use the channel/bus

> }

ComputeOnly
This method implements a simple instruction cycle algorithm with dynamic relocation of the program (relative to the base-register).

> loop

> w : = Fetch(memory[map(PC)]);// fetch instruction at RAM address – mapped PC
> Decode(w, oc, addrptr);	// part of decoding of the instruction in w

> PC := PC + 1;			// getting ready for next instruction

> Execute(oc) {

> case 0: 		// corresponding code using addrptr of operands

> case 1: 		// corresponding code or send interrupt

> …
> }
> end; // loop

The CPU is supported by a PCB, which may have the following (suggested) structure:

> typedef struct PCB {
> > cpuid:	             // information the assigned CPU (for multiprocessor system)


> struct state:	     // record of environment that is saved on interrupt

// including the pc, registers, permissions, buffers, caches, active

// pages/blocks

> code-size;

> struct registers:    // accumulators, index, general

> struct sched:	     // burst-time, priority, queue-type, time-slice, remain-time

> struct accounts:     // cpu-time, time-limit, time-delays, start/end times, io-times

> struct memories:     // page-table-base, pages, page-size

> // base-registers – logical/physical map, limit-reg

> struct progeny:	     // child-procid, child-code-pointers

> parent: ptr;	     // pointer to parent (if this process is spawned, else ‘null’)

> struct resources:    // file-pointers, io-devices – unitclass, unit#, open-file-tables

> status;		     // {running, ready, blocked, new}

> status\_info:	     // pointer to ‘ready-list of active processes’ or
// ‘resource-list on blocked processes’

> priority: integer;	// of the process

> }


Queues and other code

Need – queues (new, ready, block-on-io, suspend)

Need – conversion routines or use built-in calls between hex, decimal, binary types in Java.



**==III.	Multiprocessor Architecture:==**

The virtual CPU for Phase 1 is designed as with distributed system architecture in mind. The core simulation system maintains the memory and the various queues as well as the PCB. A detailed explanation of the working of the system is given below:

The loader loads all the programs into disk and the scheduler is called to load the programs into the simulated RAM as specified in the loader section above.

The multiprocessor dispatcher (m-dispatcher), which extends the single-CPU dispatcher described above, accesses the ready queue and assigns the processes to available CPU’s in order. Any process can be assigned to any available CPU. The m-dispatcher makes a note of which segment of RAM or memory space is assigned to each CPU and the CPU can only access that part of the RAM. The CPU can access instructions & data specified in only its part of the RAM. Similarly output can be written to only the assigned part of the RAM for a given process. Care must be taken to ensure that the CPU does not modify or access memory outside its assigned space. If a process tries to access memory outside its assigned range, a trap is generated and the program is aborted.

The CPU fetches each instruction from its assigned cache, decodes the instruction and executes it. On end of the program (or trap), the CPU signals the scheduler about the end of the program (or trap), and it is assigned the next process from the ready queue.

To facilitate the design and minimize the overhead/delay due to bus contention, caches are to be used in each CPU. Each cache must be of equivalent in size to the largest job size. A short-term loader module must be written to support the swapping of instructions/data between the RAM and the caches.



**Multiprocessor Memory Management:**

As mentioned above, the memory is maintained by the system. Therefore, appropriate semaphores and locks must be used to access memory. When a process needs to access memory, it must first acquire a lock and while it has the lock, no other process should be able to access even its portion memory.



**Multiprocessor program cache:**

As indicated, each CPU maintains a program cache (for instructions and data).  Thus, as the m-dispatcher assigns a process to a CPU, the whole process is loaded into the program cache of the CPU by the short-term loader. A CPU fetches each instruction from its assigned cache, decodes the instruction and executes it.  On the execution of instructions, a note is made of which addresses in the cache, and the corresponding addresses in memory that are being modified. For example, if an instruction specifies a ‘write’ to memory, the appropriate output-buffer section of the cache is rather changed. Also the address of the output-buffer section to be changed in memory is noted. At the end of the process execution, only the modified words are written back from the cache to memory.  The scheduler is signaled about the termination of each process so that the CPU can be assigned the next process from the ready queue. This cycle continues until all the programs on disk are executed or until the ready queue becomes empty.
