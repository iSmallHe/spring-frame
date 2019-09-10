package proxy;

public class HelloSub implements Hello {
    public void say() {
        System.out.println("谢谢");
    }

    public void say(String name) {
        System.out.println("谢谢"+name);
    }
}
