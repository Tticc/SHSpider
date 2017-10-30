package wenc.shspider.util;

import java.util.ArrayList;
import java.util.List;

public class TooLargeException extends Exception{
	public static void main(String[] args){
		System.out.println("the TooLargeException main method");
		List<String> li = new ArrayList<String>();
		li.add("first");
		li.add("scenod");
		li.add("third");
		li.add("four");
		li.add("five");
		for(int i = 0; i < li.size(); i++){
			System.out.println(li.get(i));
		}
	}
	@Override
	public void printStackTrace(){
		System.out.println("Error: file too large, not a html file");
		super.printStackTrace();
	}
}
