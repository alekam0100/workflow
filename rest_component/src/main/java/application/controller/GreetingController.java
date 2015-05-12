package application.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import application.dataaccess.TestRepository;
import application.domain.Greeting;
import application.domain.Test;

@Component
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    private TestRepository test;
    
    public Greeting greeting() {
    	Test testObject = new Test();
    	testObject.setText(Long.toString(counter.incrementAndGet()));
    	testObject.setPkIdTest((int)counter.get());
    	test.save(testObject);
        return new Greeting(counter.get(),
                            String.format(template, "name"));
    }
}