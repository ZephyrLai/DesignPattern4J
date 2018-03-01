package p01_single_responsibility;

/**
 * @Author: Zephyr
 * @Description: 代码清单7-3 单例模式通用代码（饿汉式：类加载时就创建实例，线程安全）
 * @Date: 2018/3/1 19:49
 */
public class HungrySingleton {
    private static final HungrySingleton s = new HungrySingleton();

    //私有构造器，其他类无法new当前类实例
    private HungrySingleton() {
    }

    //通过此方法向外部提供当前类实例
    public static final HungrySingleton getInstance(){
        return s;
    }

    //类中的其他方法，尽量用static
    //todo 为什么尽量用static修饰？
    public static void otherMethod(){

    }
}
