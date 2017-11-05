package org.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hope on 17/10/31.
 */
public class Test {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/startup.container.xml");

        Reader reader = (Reader) context.getBean("mimi");
        System.out.println(reader.read("wwww"));

        System.out.println(reader.read("wwww"));

        System.out.println(reader.read("wwww"));

        System.out.println(reader.read("tttt"));

    }

    private void t (){}
}
