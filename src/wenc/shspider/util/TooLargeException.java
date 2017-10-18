package wenc.shspider.util;

public class TooLargeException extends Exception{
	@Override
	public void printStackTrace(){
		System.out.println("Error: file too large, not a html file");
		super.printStackTrace();
	}
}
