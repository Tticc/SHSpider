package wenc.shspider.util;

public class ErrorResponseException extends Exception{
	private int responseCode;
	public ErrorResponseException(int responseCode){
		this.responseCode = responseCode;
	}
	@Override
	public void printStackTrace(){
		System.out.println("Error:get failure response code");
		super.printStackTrace();
	}
	public int getResponseCode(){
		return this.responseCode;
	}
}
