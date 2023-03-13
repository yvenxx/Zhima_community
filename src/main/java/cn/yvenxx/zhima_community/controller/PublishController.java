package cn.yvenxx.zhima_community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublishController {
    @GetMapping("/publish")
    public String publishPage(){

        return "publish";
    }


}

