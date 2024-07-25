package com.zerobase.springbootjpapractice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FirstController {

    @RequestMapping(value = "/first-url", method = RequestMethod.GET)
    public void first() {

    }

    @ResponseBody // view file아닌 string 리턴가능하도록
    @RequestMapping("/helloworld")
    public String helloworld() {
        return "Hello World";
    }


    public String helloString(){
        return "Hello Spring";
    }
}
