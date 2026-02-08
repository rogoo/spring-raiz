package br.rosa.simple.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/delay")
public class DelayController {

    @GetMapping("/1")
    public String delay1() {
        sleepSeconds(1);
        return "Slept 1 second";
    }

    @GetMapping("/5")
    public String delay5() {
        sleepSeconds(5);
        return "Slept 5 seconds";
    }

    @GetMapping("/8")
    public String delay8() {
        sleepSeconds(8);
        return "Slept 8 seconds";
    }

    private void sleepSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while sleeping", e);
        }
    }
}
