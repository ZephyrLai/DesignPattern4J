package p01_single_responsibility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: Zephyr
 * @Description: 代码清单7-6 臣子参拜皇帝的过程（有上限的多例模式）
 * @Date: 2018/3/1 20:29
 */
public class EmperorExpend {
    //对象属性
    private String name;

    //设置实例数量
    private static Integer maxNum=3;

    //私有构造器
    private EmperorExpend(String name) {
        this.name = name;
    }

    //对象（皇帝）列表
    private static List<EmperorExpend> emperorList = new ArrayList<EmperorExpend>();

    //静态代码块，用于产生固定数量的对象（皇帝）
    static{
        for (Integer i = 0; i < maxNum; i++) {
            emperorList.add(new EmperorExpend("皇帝0"+(i+1)));
        }
    }

    //随机参见一个皇帝
    public static EmperorExpend getInstance(){
        Random r = new Random();
        return emperorList.get(r.nextInt(maxNum));
    }

    //皇帝发话
    public String say() {
        return name;
    }
}

class MinisterExpend{
    public static void main(String[] args) {
        //有10个大臣
        int ministerNum=10;
        for (int i = 0; i < ministerNum; i++) {
            EmperorExpend e = EmperorExpend.getInstance();
            System.out.println("第"+(i+1)+"个大臣参拜的是："+e.say());
        }
    }
}