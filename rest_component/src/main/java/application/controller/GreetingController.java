package application.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import application.dataaccess.TestRepository;
import application.domain.Greeting;
import application.domain.Test;

//@RestController
@Component
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    private TestRepository test;
    
    //@RequestMapping(value="/greeting",method=RequestMethod.GET)
    //@ResponseStatus(HttpStatus.OK)
    public Greeting greeting() {
    	Test testObject = new Test();
    	testObject.setText(Long.toString(counter.incrementAndGet()));
    	test.save(testObject);
        return new Greeting(counter.get(),
                            String.format(template, "name"));
    }
}