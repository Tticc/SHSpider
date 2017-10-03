package wenc.shspider.test;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import wenc.shspider.component.CustomizatedLog4j;

@Component("testCus")
public class TestCus {
	static Logger logger = Logger.getLogger(TestCus.class);
	
	@Autowired @Qualifier("customizatedLog4j")
	private CustomizatedLog4j customizatedLog4j;
	
	public void TPP(){
		System.out.println("class TestCus");
		customizatedLog4j.pp();
		System.out.println("class TestCus end!");
	}
}
