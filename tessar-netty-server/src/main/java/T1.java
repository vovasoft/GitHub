/**
 * @author vova
 * @version Create in 下午8:43 2017/12/14
 */


public class T1 extends Thread {

    static int count=0;
    static long t = System.currentTimeMillis();
    
    
    @Override
    public synchronized void start() {
        super.start();
    }
    
    public void fun2(){
  //      System.out.println(this.getId()+"*****fun2");
        count++;
        if (count==1000000)
            System.out.println("Done"+(System.currentTimeMillis()-t));
    }

    public void fun(){
        for (int i=0;i<100000;i++){
    //        System.out.println(this.getId()+"====="+i);
            fun2();
        }
    }
    @Override
    public void run() {
        fun();
    }
}
