package wenc.shspider.test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Audience {
	
	
	
	@Pointcut(value="execution(* *.perform(..))")
	public void aspectMethod(){}
	
	@Before("aspectMethod()")
	public void takeSeat(){
		System.out.println("audience take seat.");	
	}
	@Before("aspectMethod()")
    public void turnOffPhone()  
    {  
        System.out.println("The audiences turn off the phone.");  
    }  
	@AfterReturning("aspectMethod()")
    public void applaud()  
    {  
        System.out.println("CLAP CLAP CLAP...");  
    }  
	@Around("aspectMethod()")
	public void doAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("===========����around���Ʒ�����=========== ");

        System.out.println("���÷���֮ǰִ�У�");
        pjp.proceed();
        System.out.println("���÷�������֮��ִ�У�");
    }
	@AfterThrowing("aspectMethod()")
    public void unHappy()  
    {  
        System.out.println("The audiences are unhappy.");  
    } 
}
