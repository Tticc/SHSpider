package wenc.shspider.test;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class TestThread extends Thread{
	private Lock lock = new ReentrantLock();
	private Condition cond = lock.newCondition();
	private List<TestAwait> talist = new ArrayList<TestAwait>();
	public static void main(String[] args) throws InterruptedException{
		TestThread tt = new TestThread();
		tt.startThreads(30);
		sleep(20000);
		while(true){
			tt.testAwait();
			System.out.println("\n\nwaitAll,done!!!!!!!");
			sleep(20000);
			tt.signalAll();
			System.out.println("all notified,done!!\n\n");
			sleep(20000);
		}
	}

	public void startThreads(int num){		
		for(int i = 0; i < num; i++){
			talist.add(new TestAwait(lock,cond));
		}
		for(int i = 0; i < num; i++){
			talist.get(i).start();
		}
	}
	public void testAwait() throws InterruptedException{
		for(int i = 0; i < talist.size(); i++){
			TestAwait ta = talist.get(i);
			ta.interruptMe();
			//ta.join();
			//talist.remove(0);
		}
	}
	public void signalAll(){
		for(int i = 0; i < talist.size(); i++){
			talist.get(i).runMe();
		}
		
		if(lock.tryLock()){
			try{
				cond.signalAll();
			}finally{
				lock.unlock();
			}
		}
		
	}
	
}

class TestAwait extends Thread{
	private Lock lock;
	private Condition cond;
	private boolean interruptMe = false;
	public TestAwait(Lock l,Condition c){
		this.lock = l;
		this.cond = c;
	}
	
	@Override
	public void run(){
		while(true){
			
				if(interruptMe){
					
						if(lock.tryLock()){
							try{
								cond.await();
							}catch(InterruptedException ex){
								ex.printStackTrace();
							}finally{
								lock.unlock();
							}
						}
						try {
							sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}else{
					System.out.println(getName() + ":else path");
					try {
						sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
		}
	}
	public void interruptMe(){
		this.interruptMe = true;
	}
	public void runMe(){
		this.interruptMe = false;
	}
}

