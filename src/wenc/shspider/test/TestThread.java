package wenc.shspider.test;


import wenc.shspider.springcontext.SpringContext;


public class TestThread extends Thread{

	TestCus tc = (TestCus)SpringContext.myGetBean("testCus");
	public void testM1(){
		try {
			Thread.sleep(10*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(tc == null){
			System.out.println("tc is null");
		}else if(tc != null){
			tc.TPP();
		}
		
	}
	
	@Override
	public void run(){
		System.out.println("a new thread");
		testM1();
		System.out.println("thread end!");
	}
}

