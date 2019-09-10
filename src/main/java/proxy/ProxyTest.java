package proxy;


public class ProxyTest
{
    public static void main(String[] args){
        HelloSub hs = new HelloSub();
        HelloProxy<Hello> hp = new HelloProxy(hs);
        Hello proxy = hp.getProxy();
        proxy.say();
        proxy.say("haha");
    }
}
