# Chat Transcript #


06:30:50 PM Patrick Bobbie(Ins): let us go on with the dicussion. i am going to start by opening it with your questions,
06:31:16 PM Patrick Bobbie(Ins): focusing on the phase 1, based on your initial discussions (within the groups).
06:31:48 PM Andrew Panos: Will there be any differences from last semester?
06:32:45 PM Patrick Bobbie(Ins): not the specs, but your ideas and designs will be different - each group, each year, has a different design/programming-skills/language-choices.
06:33:47 PM Aleksey Avila Vila: So does that mean that there is no specific language we have to do the project in?
06:34:01 PM Patrick Bobbie(Ins): understand that there are three major modules that make up phase 1: 1) the driver/loader/long-term scheduler part, 2) the short-term schedule/dispatcher/context-switcher part and 3) the cpu part.
06:34:43 PM Patrick Bobbie(Ins): alek, my recommendation has always been java, but you can (and others have in the past) use c or c++ or c# (these days).
06:35:14 PM David Hood: why do you recommend java over the others?
06:35:36 PM Patrick Bobbie(Ins): the problem with c is that when it gets to the threading/child-process creation part, java could be easier unless you are very good at c.
06:35:55 PM Patrick Bobbie(Ins): i am not sure of c++, but that works well, too.
06:36:46 PM Patrick Bobbie(Ins): java has a natural environment for extending threading to help with the 'multiprocessor' part (part 2 of phase 1) when you get there; and also for phase 2 of the project.
06:37:13 PM Patrick Bobbie(Ins): take note of the 3 major modules i mentioned above.
06:38:07 PM Patrick Bobbie(Ins): do you have any questions about those modules? your design task will mostly like be decomposed along those 3 dimensions and apportioned to members in the group - or can we worked on collaboratively.
06:38:56 PM Victor Arokoyu: is there anything that we have to worry about as far as deliverables besides the actual pseudo OS implementation?
06:39:37 PM Thiago Zanetti: has entered the room.
06:40:15 PM Patrick Bobbie(Ins): what do you mean, victor? the implementation is a working simulator of all the 3 major modules that simulate a computer system that will run/execute the programs (in hex) that i have given you (see the 'program files').
06:40:24 PM Patrick Bobbie(Ins): thiago, welcome!
06:40:38 PM Andrew Panos: do we need to turn in a report this time?
06:41:01 PM Victor Arokoyu: oops i meant to say is there anything else we have to turn in besides the code?
06:42:21 PM Patrick Bobbie(Ins): i am going to ask each group to e-submit a design report - detailing your design ideas, including block diagrams (UML?), hand-drawn, visio-drawn, plus pseudo-code, if any, or logic-flow, plus major data structures that will be used - for each of the 3 modules.
06:43:01 PM Patrick Bobbie(Ins): andy, that will be the initial report, which will be due next monday (2/9). each group will submit as an attachment to an email.
06:44:15 PM Patrick Tillman: The only problem I've encountered is working out how I'm going to access all the data structures within the various components of the system. Obviously I can transfer them around as parameters, but I was looking for a cleaner way of doing it. Like the eventlisteners Java has for swing components. Can you give us any hints/tips/suggestions?
06:44:15 PM Patrick Bobbie(Ins): victor, yes, eventually, when i have seen the demos (to be arranged for face-to-face), the code on CD/DVD, plus written-bound report - including output, etc, will be submitted at the DUE DATES in the syllabus for each phase.
06:45:31 PM Patrick Bobbie(Ins): the data structures, e.g., the PCB, Disk, Memory, Ready Queue, can be 'classed' objects but you must be careful where to place (i.e., in which module(s)), to make them accessible - from Java viewpoint.
06:46:23 PM Patrick Bobbie(Ins): i believe the listeners will work, since it allows 'central control' of shared objects.
06:47:37 PM Patrick Tillman: ah.. ok great. I'll look into that a little more. Everything I've read so far implies they're only useful for gui components like buttons, lists, etc.
06:47:50 PM Aleksey Avila Vila: Do any of the three modules need special attention? As in, does one of them have higher priority than the others?
06:47:52 PM Patrick Bobbie(Ins): each group MUST meet - face-to-face or skype (e-meet) and pass files around - which is nice and easy to do in skype, on your design.
06:49:24 PM Thiago Zanetti: a good suggestion for that is using google groups.. if anyone would care to know..
06:49:32 PM Patrick Bobbie(Ins): apart from the 'driver' the rest could be independent. take a look at the block diagram i gave you (plus the video-over option). it contains a lot of the design solutions, but they need to be discussed thoroughly by each group. once questions are raised afterwards, then a chat-mode like this i can be of further help.
06:49:51 PM Thiago Zanetti: Prof Bobbie, as far as the block diagrams/UML are concern, are you looking for a final version of the project planning or is this a live document that we need to submit a draft of it, and then continue working on it throughout the semester?
06:49:55 PM Patrick Bobbie(Ins): thiago is right. past students have used that, too.
06:51:01 PM Patrick Bobbie(Ins): thiago, i am not clear on the 'final vesion of the project planning' and the 'live document ...'
06:52:09 PM Patrick Bobbie(Ins): do you mean the block diagram i gave or the one i am suggesting doing it in UML or some other environment? i meant your own design that will lead to final implementation of your simulator.
06:54:19 PM Patrick Bobbie(Ins): you will continue to finalize your designs, implement the simulator to meet the requirements of phase 1, and same to phase 2. but for next week, i need the draft of your design ideas which you will continue working on toward implementation. phase 1 is due (full demonstrable implementation) is due as stated in the syllabus.
06:56:09 PM Thiago Zanetti: So for the draft of our design, do you want a diagram of the design? And how much in depth does it need to be?
06:56:29 PM Patrick Bobbie(Ins): yes
06:56:56 PM Patrick Bobbie(Ins): as much detailed as you can, which also get you closer to implementation.
06:57:58 PM Thiago Zanetti: Okay, and right now where do you think our teams should be at? CPU, Scheduler? Just trying to get a sense of where we are and where we need to be based of what we learned
07:00:21 PM Patrick Bobbie(Ins): at time each group should have met at least once to put the concepts in chps 3 and 5 (which we just covered) - mapped into the designs of the 3 modules i referred to above. all these built around your 'data structures' knowledge from your earlier courses - esp how to build the ReadyQ, PCB, Disk, and Memory.
07:01:12 PM Patrick Bobbie(Ins): Your comp arch background should help you, by now, to have started sketching out a pseudocode for the CPU and, now, for the Long- and Short-term schedules.
07:01:40 PM Thiago Zanetti: All right. Thank you for the answer.
07:03:07 PM Patrick Bobbie(Ins): if a group has not met yet, these ideas should allow you to regroup and start working onyour designs so that you can turn in the drafts on 2/9. i will give you feedback on those to guide you to the next level.
07:05:40 PM Patrick Bobbie(Ins): do the rest have questions about phase 1 - besides thiago?
07:06:13 PM David Hood: people already beat me to them... :)
07:06:18 PM Jason Brand: not yet
07:06:28 PM David Hood: I'm not a very fast typer... :)
07:06:49 PM Aleksey Avila Vila: Yea, I got beaten to the punch a couple times
07:06:53 PM Victor Arokoyu: I'm scared
07:07:22 PM Patrick Bobbie(Ins): it is ok. keep typing shorter questions and points and i will elaborate on them as much a s i can.
07:07:39 PM Patrick Bobbie(Ins): victor, there is nothing to be scared of.
07:08:08 PM Patrick Bobbie(Ins): alek, not webct/chat just gets your input across as soon as you Enter your message.
07:08:12 PM David Hood: tell that to the students of past os classes who like to tell horror stories...
07:08:40 PM Patrick Bobbie(Ins): david, don' t listen to the past guys. there worked through and succeeded, and so can you.
07:09:22 PM Jason Brand: the specifications document is just somewhat confusing at this point
07:10:17 PM Patrick Bobbie(Ins): the spec doc is a guide. i left several details to let you bring out your creativity. is there any part that is confusing - specifically, that i can elaborate on for you?
07:10:56 PM David Hood: You wouldn't happen to have a working model you could upload as an .exe for us to sort-of get an idea of what this is supposed to do, would you?
07:10:58 PM Jason Brand: well, focusing on the CPU portion, I'm assuming that the dma controller module and each of the CPUs will operate in their own thread
07:11:06 PM Patrick Bobbie(Ins): try to read it a few times, side-by-side with the video over the diagram, which basically gives a scenario of how the entire simulator components fit together.
07:11:37 PM Jason Brand: are we going to be using preemptive multitasking at all?
07:12:01 PM Jason Brand: this cpu seems a bit primitive for that, or are we expected to add our own timer and interrupts?
07:12:16 PM Andrew Panos: I noticed your office hours are mon/wed around noon. I am not on campus at that time, any chance you will be available tues/thurs nights for questions if we schedule it?
07:13:32 PM Patrick Bobbie(Ins): ok, regarding the DMA and the CPU: 1) the DMA is a part - component of the CPU. when a instruction is fetched, decoded (by the 'fetch' and the 'decoder' respectively), and if it is an I/O (read or write), we want the DMA method to be called; if it any other operator, the the 'Compute-Only' method is called by the CPU.
07:14:31 PM Patrick Bobbie(Ins): jason, no. there will be no preemptive multitasking. you are referring to the second part of phase 1 - dealing with the 'multiprocessor' part. you will need multithreading, but each thread is going to be controlled by your 'scheduler - shortterm'.
07:15:30 PM Patrick Bobbie(Ins): yes, andy, my office hours are mw noon, but i am almost always in my office, even after tthat until my classes start at 3p.
07:16:37 PM Aleksey Avila Vila: Is there any reference material you would suggest for extra info that could help us?
07:16:43 PM Patrick Bobbie(Ins): so, jason, you see, the DMA can be assigned also to a separate thread and the 'Compute-Only' submodule to a separate thread - if you think of just those parts as multithreaded.
07:17:15 PM Jason Brand: I'm just thinking that there's usually only one memory space
07:17:42 PM Victor Arokoyu: Can we expect sample code files this semester for the project?
07:17:50 PM Jason Brand: so, for example, if we have four CPUs, there still only one DMA controller
07:18:47 PM Patrick Bobbie(Ins): Alek, the project was made up by me several years ago - about 23, when i started teaching. it wasn't extracted or excerpted from somewhere. it is a well scaled down version of a similar project i took when i was in school - like you guys. what i have done over these years is to develop the specs and the various files posted at my website.
07:19:44 PM Jason Brand: if we need to do i/o, we call on the DMA thread/controller and block until that is complete, right?
07:20:09 PM Patrick Bobbie(Ins): victor, although the website points to sample codes, i once gave such code - about 5 years ago, and switched it off because students spent time deciphering them and did not gain much - did not get the chance to think from scratch. so, no group has received such samples in the past.
07:20:25 PM Jason Brand: I'm sure that part of my own confusion comes from the fact that our OS isn't actually running on the CPU that the processes are running on
07:21:33 PM Patrick Bobbie(Ins): jason, yes, when you have done part 1 of phase 1 - single CPU (which will include the dma-thread and the compute-only thread), then in part 2 of phase 1, you simply clone the CPU into 4 objects - bingo - you then have 4 CPU threads running in parallel as multiprocessors.
07:22:27 PM Patrick Bobbie(Ins): jason, your threads are running your simulator's components - all written as java, and therefore running as compiled program on your host PC (processor) in windows.
07:23:49 PM Patrick Bobbie(Ins): so, jason, if you have the 4 CPU objects, each will encapsulate its own DMA and COMPUTE-ONLY threads; where, again, each CPU object in itself will/could run as a different thread to achieve the multiprocessor concept.
07:27:08 PM Patrick Bobbie(Ins): all of you, now that jason opened up the CPU discussion, let me add that, the CPU, as a separate class with its own set of methods, such as 'fetch', 'decode', execute', etc. will implenent the set of blocks i have given you in the block diagram - the top part, when the OS-driver calls it - following link #10 (in the diagram). after executing a complete job, the CPU returns control to the OS-driver. this is the case without any preemption. then the next job will be readied by the short-term scheduler/dispatcher when the Driver tells it; then the CPU will be called again, ...
07:29:43 PM Patrick Bobbie(Ins): are you guys there?
07:29:45 PM David Hood: so a big while loop...
07:30:29 PM Victor Arokoyu: Yes. Am just listening to this informative conversation
07:30:34 PM Aleksey Avila Vila: Yes, I am here, I am writing all of this down
07:30:45 PM Thiago Zanetti: same here.. just trying to sink in the info..
07:30:56 PM Andrew Panos: I am here
07:31:21 PM Jason Brand: likewise
07:31:34 PM Patrick Bobbie(Ins): yes, yes, a big loop the CPU. the CPU { pc=0; instreg = fetch(pc); decode(instreg); pc++} thrown into a big while (not 'halt' opcode).
07:32:33 PM Patrick Bobbie(Ins): the decode(instreg) takes the instruction register - strings the 8-hex chars in the reg into 32 bits and starts decoding according to the 'Instruction Format' file i gave you.
07:34:25 PM Patrick Bobbie(Ins): correction! after the decode(instreg); you have 'execute(opcode)', before the pc++; where the 'execute' is passed the determined opcode (from the decoder), and then 'execute' calls the function (add, subtract, read, write - whatever it is). these little functions or methods will be written in java/c/c++ or whatever, as a part of the CPU class.
07:35:33 PM Thiago Zanetti: quick question, since we mentioned instruction format:
07:35:41 PM Thiago Zanetti: 2 bits 6 bits 4 bits 4 bits 4 bits 12 bits 00 OPCODE S-reg S-reg D-reg 000
07:36:09 PM Patrick Bobbie(Ins): the 'execute' therefore, will be big 'switch' statement that looks like: .... switch(opcode) {case1: read(parameter); break; case 2: write(parameter); break; .... default; } for all the 26 or so opcodes i have given you - see the 'Opcodes' file.
07:36:09 PM Thiago Zanetti: for the arithmetic instruction, which S-reg is the source register, the first or the second?
07:37:36 PM Patrick Bobbie(Ins): thiago, for the R-arithmetic types, both s1 and s2 (the first two) are sources - that is, they hold the 2 operands needed to do the add/subtract/divide... and the third, s3, will hold the result - most like, the s3 will be the accumulator ([R0](https://code.google.com/p/javaosemulator/source/detail?r=0)).
07:37:48 PM David Hood: the first S-reg is the first operand and the second S-reg is the second operand... the opcode tells the operator
07:38:08 PM Thiago Zanetti: so it's kind of like MEPIS architecture?
07:38:14 PM Patrick Bobbie(Ins): david, yes, and s3, the third is the destination reg.
07:39:30 PM Patrick Bobbie(Ins): all, just read the Instruction format and the 'Intruction/Opcodes' files very carefully. they are critical to understanding 80% of the CPU module.
07:40:37 PM Victor Arokoyu: ok
07:42:06 PM Patrick Bobbie(Ins): i think we have dissected the CPU today. very good. that is the hardest part. keep in mind, the fetch simply fetches instruction (1 by 1) or data - an 8-hex char info or 1 word at a time. the fetch can't access Memory (yours) directly, so it has to call the Memory module/method - all parts of your simulator - including the schedulers, loaders, etc., can't go to your Memory directly - they all have to call your Memory module. that also include the Store and Load, Read, Write and any other method or function that accesses your RAM.
07:43:39 PM Patrick Bobbie(Ins): put all these together - meet, review this chat, and map the discussion into design diagrams or pseudo code or whatever and that should help you. i went over these same things today at my in-class section.
07:44:30 PM David Hood: seems fairly straight forward to me...
07:44:33 PM Aleksey Avila Vila: It doesn't sound TOO bad, heh
07:44:48 PM Victor Arokoyu: Interesting......very interesting
07:44:53 PM Patrick Bobbie(Ins): That will be 1 of the 3 major modules i mentioned at the start. the other 2 are the loader/longterm scheduler portion and the the short-term-schduler-dispatcher-context-switcher part. think of those 2 too.
07:45:13 PM Patrick Bobbie(Ins): no, i won't give you something you can't accomplish. that defeats the purpose.
07:45:46 PM Patrick Bobbie(Ins): so work on these, and i look forward to receiving your designs.
07:46:01 PM Thiago Zanetti: all right.. thank you for your time and the answers..
07:46:03 PM Jason Brand: I'm sure we'll bombard you with email
07:46:05 PM Patrick Bobbie(Ins): will somebody copy paste this chat and broadcast to all the on-line students?
07:46:20 PM David Hood: I can
07:46:33 PM Patrick Bobbie(Ins): send me your emails, and i will be happy to stay with you until you succeed.
07:46:43 PM Victor Arokoyu: This session really helped a lot. Thanks