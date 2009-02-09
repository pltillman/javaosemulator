public class shortTermScheduler{

	public static void main(String[] args){
	
		new shortTermScheduler();
	}
	
//First come first serve
private void FCFS(){


	//Sort readyQueue in arrive_time order
	for(int index; index < readyQueue.size(); index++){
		if(readyQueue.get(index).getArrive_time() > readyQueue.get(index+1).getArrive_time){
		
			Queue temp = new Queue();
			readyQueue.get(index+1).Change(readyQueue.get(index));
			readyQueue.get(index).Change(temp);	
		}
	}
	
	//Set start_time and finish_time
	for(int j; j < readyQueue.size(); j++){
	
		if(j==0){
		
			readyQueue.get(j).setFinish_time(readyQueue.get(j).getServes_time());
		}
		else{
		
			readyQueue.get(j).setStart_time(readyQueue.get(j-1).getFinish_time());
			readyQueue.get(j).setFinish_time(readyQueue.get(j).getServes_time() + readyQueue.get(j-1).getFinish_time());
		}
	}
}

//create queue object
class Queue{

	private double arrive_time;
	private double serves_time;
	private double start_time;
	private double finish_time;
	
	public Queue(Queue q){
	
		this.arrive_time = q.getArrive_time();
		this.serves_time = q.getServes_time();
		this.start_time = 0;
		this.finish_time = 0;
	}
	
	public void Change(Queue q){
	
		this.arrive_time = q.getArrive_time();
		this.serves_time = q.getServes_time();
		this.start_time = 0;
		this.finish_time = 0;	
	}
	
	public double getArrive_time(){
	
		return arrive_time;
	}
	
	public double getServes_time(){
	
		return serves_time;
	}
	
	public double getStart_time() {
	
		return start_time;
	}
	
	public void setStart_time(double start_time) {
	
		this.start_time = start_time;
	}
	
	public double getFinish_time() {
	
		return finish_time;
	}
	
	public void setFinish_time(double finish_time) {
	
		this.finish_time = finish_time;
	}
}
)