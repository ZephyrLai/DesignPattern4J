package p01_single_responsibility;

/**
 * @Author: Zephyr
 * @Description: 定义一个私有的构造器，Emperor自己可以new一个对象，
 *               但其他类不能new当前对象，其他类只能通过静态的getInstance方法获取Emperor对象
 * @Date: 2018/3/1 19:09
 */
public class Emperor {
    private static final Emperor emperor= new Emperor();

    //私有构造器
    private Emperor() { }

    //静态实例获取
    public static Emperor getInstance(){
        return emperor;
    }

    public static String say(){
        return "无事退朝";
    }
}

/**
 * 每天臣子都会面对同一个皇帝
 */
class Minister{
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            Emperor e = Emperor.getInstance();
            System.out.println("上朝第"+(i+1)+"天,皇帝说"+e.say());
        }
    }
}


