package wenc.shspider.test;

import org.springframework.stereotype.Component;

@Component("DukePerformer")
public class DukePerformer {

	private String name;
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return this.name;
	}
	

	public void perform() //throws Throwable
	{
		setName("duke");
		System.out.println(this.name+" sing a song.");
	}

	public void anotherPerform() {
		System.out.println(this.name+" sing ANOTHER song.");  
		
	}

}
