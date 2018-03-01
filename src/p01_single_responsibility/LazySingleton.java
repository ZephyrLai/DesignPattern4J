package p01_single_responsibility;

/**
 * @Author: Zephyr
 * @Description: 代码清单7-4 线程不安全的单例（懒汉式：用到时再创建实例，线程不安全）
 * @Date: 2018/3/1 20:15
 */
public class LazySingleton {
    private static LazySingleton s = null;

    private LazySingleton() {
    }

    //public static final synchronized LazySingleton getInstance(){
    public static final LazySingleton getInstance(){
        if(s==null){
            s = new LazySingleton();
        }
        return s;
    }
}
