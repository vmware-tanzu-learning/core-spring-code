package com.lib;

public class TypicalHelloService implements HelloService {

    @Override
    public void greet() {
        System.out.println("Hello, Typical!");
    }

}
