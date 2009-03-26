void Dispatcher(){

	currentProcess = selectProcess();
	runProcess (currentProcess);
}

int selectProcess(){

	static int nextProcess = numberOfProcess;
	
	for(int i = 1; i < numberOfProcess; ++i){
	
		if(++nextProcess >= numberOfProcess)
			return -1;
			
		if(readyQueue[nextProcess].state == Ready){
		
			readyQueue[nextProcess].state == Running;
			return nextProcess;
		}
	}
}

void runProcess(int currentProcess){

	Savearea *savearea = &(readyQueue[currentProcess].sa);
	
	load savearea + 0, register1
	load savearea + 4, register2
	load savearea + 8, register3
	load savearea + 12, regester4 
	
}
