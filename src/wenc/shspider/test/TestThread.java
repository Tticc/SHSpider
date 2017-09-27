package wenc.shspider.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wenc.shspider.springcontext.SpringContext;


public class TestThread extends Thread{

	TestCus tc = (TestCus)SpringContext.myGetBean("testCus");
	public void testM1(){
		try {
			Thread.sleep(2000);
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
	public void start(){
		System.out.println("a new thread");
		testM1();
		System.out.println("thread end!");
	}
}

