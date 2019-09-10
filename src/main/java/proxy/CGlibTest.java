package proxy;

public class CGlibTest {

    public static void main(String[] args){

        CGlibProxy cp = new CGlibProxy();
        Hello proxy = cp.getProxy(HelloSub.class);
        System.out.println(proxy.getClass().getName());
        proxy.say();
    }
}
