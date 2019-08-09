package cyz.ink.portfolio.pojo;

import java.util.Random;

/**
 * @ Author      : Zink
 * @ Date        : Created in 19:44 2019/8/9
 * @ Description :
 * @ Version     : 1.0
 **/
public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(new Random().nextInt(10));
        }
    }
}
