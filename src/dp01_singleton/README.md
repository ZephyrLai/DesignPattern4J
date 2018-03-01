# 单例模式
### 一、引入
####皇帝与臣子
一个类只能生成一个对象（皇帝），其他所有类对这个对象的依赖都是同一个，体现到代码上如下：<br/>

    /**
     * @Author: Zephyr
     * @Description: 定义一个私有的构造器，Emperor自己可以new一个对象，但其他类不能new当前对象，其他类只能通过静态的getInstance方法获取Emperor对象
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
    
+ 执行结果：

![pic miss](https://github.com/ZephyrLai/DesignPattern4J/raw/master/resources/images/dp01/2-1-1.jpg)

###二、定义
+ 关键词：【自行实例化】、【Cloneable接口】

####定义：
Ensure a class has only one instance,and provide a global point of access to it  
确保每个类只有一个实例，**自行实例化**并向整个系统提供这个实例

![pic miss](https://github.com/ZephyrLai/DesignPattern4J/raw/master/resources/images/dp01/2-1-0.jpg)
####代码清单7-3

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

###三、应用
优点
+ 减少内存开支（比如：需要频繁创建或者销毁一个对象）
+ 减少系统的性能开销（比如：产生一个对象需要较多的资源）
+ 避免对资源的多重占用（比如：IO流操作过程中，只存在一个inputStream实例，则可以避免对同一个文件同时进行操作）
+ 可以用于在系统中设置全局访问点，优化和共享资源访问（比如：系统全局的计数器，整个系统共用）

缺点
+ 拓展困难（没有接口，只能通过修改代码实现修改）
+ 对测试不利（单例类没有完成则无法进行测试，没有接口也不能使用mock方式创建虚拟对象）
+ 与单一职责原则相违背（一个类应该只实现一个逻辑，而不应该关心其是否单例，是否单例应该取决于业务环境）

使用场景举例
+ 要求生成唯一序列号的环境
+ 整个系统中需要一个共享数据点或者共享数据（比如：全局计数器）
+ 创建一个对象需要太多的资源
+ 需要定义大量的静态常量和静态方法（比如：工具类，也可以直接全部声明为static） 

注意事项
在高并发环境下可能引发线程安全问题，推荐使用饿汉式（解决方式：添加关键字synchronized）
    
    /**
     * @Author: Zephyr
     * @Description: 代码清单7-4 线程不安全的单例（懒汉式：用到时再创建实例，线程不安全）
     * @Date: 2018/3/1 20:15
     */
    public class LazySingleton {
        private static LazySingleton s = null;
        
        private LazySingleton() {
        }
        
        //public static final 【synchronized】 LazySingleton getInstance(){
        public static final LazySingleton getInstance(){
            if(s==null){
                s = new LazySingleton();
            }
            return s;
        }
    }
  + 单例类不要实现Clonable接口（clone()方法的调用不通过构造器，因此即使仅有私有构造器，如果实现了Clonable接口，仍然可以通过clone()方法复制对象从而产生多个实例）


###四、扩展
有上限的多例模式：有固定数量实例的类。
核心逻辑：首先通过静态代码块生成指定数量的对象存入私有列表，再通过getInstance()方法随机返回列表中的某个对象
对应代码实现如图（代码中使用了ArrayList存放实例，考虑到线程安全问题可以使用）：

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
  效果如图：
  
![pic miss](https://github.com/ZephyrLai/DesignPattern4J/raw/master/resources/images/dp01/2-1-3.jpg)

###五、最佳实践
单例模式是23个设计模式中最简单的模式，应用也非常广泛。Spring中默认每个Bean都是单例的，优点是这样Spring就可以管理这些Bean的生命周期。
如果设置了非单例模式(Prototype类型)，则Bean初始化后的管理交给J2EE容器，Spring容器不再跟踪管理这些Bean的生命周期