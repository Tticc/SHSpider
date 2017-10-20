package wenc.shspider.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	@Test
	public void testRegex(){
		/*Pattern p=Pattern.compile("\\w+");
		System.out.println(p.pattern());*/
		/*Pattern p=Pattern.compile("4"); 
		String[] str=p.split("我的QQ是:456456我的电话是:0532214我的邮箱是:aaa@aaa.com");
		for(int i = 0; i < str.length; i++){
			System.out.println(str[i]);
		}*/
		Pattern p=Pattern.compile("(charset *= *)('|\")?([\\d\\w-]*)('|\")?(.*?)>"); 
		//Pattern p=Pattern.compile("charset=('|\")?(.*)('|\")?(.*)>"); 
		Matcher m=p.matcher("<meta http-equiv=\"Content-Type\" content =\"text/html; charset = Shift_JIS\" />fdsjaifoijeojfioesa>"); 
		System.out.println(m.find());//返回false,因为bb不能被\d+匹配,导致整个字符串匹配未成功. 
		System.out.println(m.group());
		System.out.println(m.groupCount());
		System.out.println("target: "+m.group(3));
		for(int i = 1; i <= m.groupCount(); i++){
			System.out.println(m.group(i));
		}
		//System.out.println(m.matches());//返回true,因为\d+匹配到了整个字符串
	}
	@Test
	public void testRegex2(){
		Pattern p=Pattern.compile("<a(.*?)(href *= *)('|\")(.*?)('|\")(.*?)>");
		Matcher m=p.matcher("<a class=\"DevCenterFullNameNonMegaBlade\" href=\"https://msdn.microsoft.com/zh-cn\">Developer Network</a>"+
                "<a class=\"DevCenterFullName\" href=\"https://msdn.microsoft.com/zh-cn\">Developer Network</a>"+
                "<a class=\"DevCenterShortName\" href=\"https://msdn.microsoft.com/zh-cn\">Developer</a>");
		System.out.println(m.groupCount());
		while(m.find()){
			System.out.println(m.group(4));
			/*for(int i = 1; i <= m.groupCount(); i++){
				System.out.println(m.group(i));
			}*/
		}
	}
	
	@Test
	public void testSplit(){
		String content = "adji<afjisdo>jidos<ajfdiso>fooee<apeioeo>ieej<ajeio";
		String[] contents = content.split("<a"); 
		System.out.println(contents.length);
		for(int i = 0; i < contents.length; i++){
			int length = contents[i].indexOf(">");
			if(length <= 0){
				continue;
			}
			contents[i] = contents[i].substring(0,length);
			System.out.println(contents[i]);
		}
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


