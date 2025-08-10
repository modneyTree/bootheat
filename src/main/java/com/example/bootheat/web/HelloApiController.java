package com.example.bootheat.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // = @Controller + @ResponseBody
@CrossOrigin(origins = "http://localhost:3000")
public class HelloApiController {
    @GetMapping("hello-api")
    public Hello hello(@RequestParam("name") String name) {
        Hello h = new Hello();
        h.setName(name);
        return h;
    }

    static class Hello {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}