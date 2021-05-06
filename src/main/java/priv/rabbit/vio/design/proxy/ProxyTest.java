package priv.rabbit.vio.design.proxy;

import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        Subject proxy = (Subject) Proxy
                .newProxyInstance(
                        Subject.class.getClassLoader(),
                        new Class[]{Subject.class},
                        new ProxyInvocationHandler());
        System.out.println("====" + proxy.sayHello());
    }
}
