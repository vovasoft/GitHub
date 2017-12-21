import org.junit.Test;

/**
 * @author vova
 * @version Create in 下午8:44 2017/12/14
 */


public class App {
    public static void main(String[] args) {
        T1 t1 = new T1();
        T1 t2= new T1();
        T1 t4 = new T1();
        T1 t3 = new T1();
        
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }


    @Test
    public void test1(){
     //   long currentTime = System.currentTimeMillis();
        new T2().fun();
     //   System.out.println("test1:"+(System.currentTimeMillis()-currentTime));
    }
    
    @Test
    public void test2(){
     //   long currentTime = System.currentTimeMillis();
        new T1().start();
        new T1().start();
        new T1().start();
        new T1().start();
        new T1().start();
        new T1().start();
        new T1().start();
        new T1().start();
        new T1().start();
        new T1().start();

     //   System.out.println("test2:"+(System.currentTimeMillis()-currentTime));
    }
}
