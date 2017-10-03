package wenc.shspider.test;

import org.junit.*;

import wenc.shspider.util.MyEnum;

public class CommonTest {
	@BeforeClass//在所有方法开始之前 static
	public static void beforeClass(){}	
	@AfterClass//在所有方法结束之后 static
	public static void afterClass(){}	
	@Before//在每一个方法开始之前 
	public void notSetUp(){//setUp
	}	
	@After//在每一个方法结束之后
	public void after(){}//tearDown

	@Test
	public void testAdd() {
		//fail("Not yet implemented");
		
		//连个参数的含义，（期望值，实际值）
		
	}
	@Test(expected = ArithmeticException.class)
	//@Ignore
	public void testDevide(){
		
		//以double为参数的重载方法里，第三个double参数的意思是：误差值，两个double参数的重载方法已被废弃。
		//Assert.assertEquals(1.0,dou,0.1);
	}
	@Test(timeout=500)
	public void testTimeOut() {
		
	}
	@Test
	public void testGetTitle(){
		String html = "fjis<masteji charset=\"utf-8\">oejit<title>i am the title</title>fhsuaiofji";
		System.out.println(getCharset(html));
	}
	public static String getTitle(String content){
		int start = content.indexOf("<title");
		if(start < 0){
			return "notitle";
		}
		content = content.substring(start+3, content.length());
		
		start = content.indexOf(">");
		content = content.substring(start+1, content.length());
		start = content.indexOf("<");
		content = content.substring(0,start);
		return content;
	}

	public static String getCharset(String contents){
		int start = contents.indexOf("charset");
		if(start == -1){
			return MyEnum.DEFAULTCHARSET.toString();
		}
		contents = contents.substring(start,start+20);
		start = contents.indexOf("=");
		contents = contents.substring(start+1);
		int end = contents.indexOf(">");
		contents = contents.substring(0,end);
		return contents;
	}
}


