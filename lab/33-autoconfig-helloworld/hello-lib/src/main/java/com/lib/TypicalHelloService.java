package com.lib;

// TODO-12: Study "hello-lib" project
// - It is just a library that contains this "TypicalHelloService"
//   class and "HelloService" interface.
// - Consider this is a 3rd-party library, which you cannot change
public class TypicalHelloService implements HelloService {

    @Override
    public void greet() {
        System.out.println("Hello, Typical!");
    }

}
