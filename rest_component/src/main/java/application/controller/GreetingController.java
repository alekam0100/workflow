package application.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import application.domain.Greeting;

//@RestController
@Component
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    //@RequestMapping(value="/greeting",method=RequestMethod.GET)
   // @ResponseStatus(HttpStatus.OK)
    public Greeting greeting() {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, "name"));
    }
}