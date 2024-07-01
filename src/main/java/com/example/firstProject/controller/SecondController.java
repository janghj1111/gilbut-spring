package com.example.firstProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecondController {

    @GetMapping("/random-quote")
    public String randomQuote(Model model){ //연습문제(p.93)

        String[] quotes = {"개발자 망신은 Copy&Paste가 시킨다 " + "-개발자속담-"
                ,"완벽함은 아무것도 더할 것이 없을 때가 아닌, 아무것도 제거할 것이 남지 않았을 때 달성된다." + "- Antoine de Saint-Exupéry(프랑스의 소설가이자 공군 장교) -"
                , "월요일에 작성한 코드를 디버깅하느라 그 주의 나머지를 허비하느니 월요일에 침대 안에 머무는게 나을 때도 있다."  + "- Christopher Thompson-"
                , "애초에 디버깅은 코드를 작성하는 것 보다 배나 힘들다. 그러니, 코드를 최대한 빈틈없이 작성하는 사람은, 당연히, 디버그할 정도로 똑똑하지 않은 것이다." + "Brian Kernighan"
        };
        int randInt = (int) (Math.random() * quotes.length);
        System.out.println(randInt);
        model.addAttribute("randomQuote", quotes[randInt]);
        return "quote";
    }
}
