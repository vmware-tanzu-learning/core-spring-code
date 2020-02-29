package com.lib;

// TODO-12: Study "hello-lib" project
//          It is just a library that contains this "TypicalHelloService"
//          class.
public class TypicalHelloService implements HelloService {

    @Override
    public void greet() {
        System.out.println("Hello, Typical!");
    }

}
