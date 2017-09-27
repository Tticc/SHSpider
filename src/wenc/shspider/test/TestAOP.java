package wenc.shspider.test;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class TestAOP {
	public static void main(String[] args){  
        StudentBean student1  
                = (StudentBean) new CGLibProxyFactory().createStudent(new StudentBean());  
        StudentBean student2  
                = (StudentBean) new CGLibProxyFactory().createStudent(new StudentBean("wang"));  
        student1.print();  
        student2.print();  
    }  
}



class StudentBean{  
    private String name;  
    public StudentBean() {  
    }  
    public StudentBean(String name) {  
        this.name = name;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }
    public void print() {  
        System.out.println("Hello World!");  
    }  
}

class CGLibProxyFactory implements MethodInterceptor {  
    private Object object;  
    /** 
    * 创建代理类对象 
    * @param object 被代理对象 
    * @return 代理类对象 
    */  
    public  Object createStudent(Object object){  
        this.object=object;  
        //利用Enhancer来创建代理类  
        Enhancer enhancer=new Enhancer();  
        //为目标对象指定父类  
        enhancer.setSuperclass(object.getClass());  
        //设置回调函数  
        enhancer.setCallback(this);  
        //返回生成的代理类  
        return enhancer.create();  
    }  
  
    /** 
    * 业务处理逻辑代码 
    * 利用到了java的反射 
    * @param o 
    * @param method 
    * @param objects 
    * @param methodProxy 
    * @return 代理类对象 
    * @throws Throwable 
    */  
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {  
        StudentBean student=(StudentBean)object;  
        Object result=null;  
        if (student.getName()!=null){  
            methodProxy.invoke(object,objects);//利用反射来执行相应的方法  
        }else{  
            System.out.println("该方法已被拦截！");  
        }  
        return result;  
    }  
}