package demo.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.common.controller.CommonController;
import demo.test.pojo.constant.TestUrl;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonController {



}
