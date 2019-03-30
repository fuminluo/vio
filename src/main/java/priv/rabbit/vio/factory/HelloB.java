package priv.rabbit.vio.factory;

import java.math.BigInteger;

/**
 * @Author administered
 * @Description
 * @Date 2019/3/15 22:49
 **/
public class HelloB {
    public HelloB(){
        System.out.println("HelloB");
    }

    {
        System.out.println("I am HelloB");
    }

    static {
        System.out.println("I static HelloB");
    }

    public static void main(String[] args) {
        new HelloB();

        // create 3 BigInteger objects
        BigInteger bi1, bi2, bi3;

        // assign values to bi1, bi2
        bi1 = new BigInteger("123");
        bi2 = new BigInteger("50");

        // perform add operation on bi1 using bi2
        bi3 = bi1.add(bi2);

        String str = "Result of addition is " +bi3;;

        // print bi3 value
        System.out.println( str );


    }
}
