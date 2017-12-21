/**
 * @author vova
 * @version Create in 下午8:43 2017/12/14
 */


public class T2  {
    static int count=0;
    static long t =System.currentTimeMillis();
    
    
    public void fun2(){
   //     System.out.println(this.getClass()+"*****fun2");
        count++;
//        System.out.println(count);
        if (count==1000000){
            System.out.println("Done"+(System.currentTimeMillis()-t));
        }
    }

    public void fun(){
        for (int i=0;i<1000000;i++){
      //      System.out.println(this.getClass()+"====="+i);
            fun2();
        }
    }
  
}
