package jpabook2.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    //Model은 스프링부트에서 제공하는 UI로, controller에서 view로 아래 변수를 넘긴다
    public String hello(Model model){
        model.addAttribute("data", "hello!!!");
        //return hello가 의미하는 바는, resources/temples/hello.html을 가리킨다.
        // 아래 hello에는 .html이 자동으로 붙게된다.
        return "hello";

    }

}
