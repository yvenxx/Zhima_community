package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;


}
