package com.ncut.face.erp.bootstrap.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class ViewController {
    @RequestMapping(value = "/demo")
    public String demo() {
        return "demo";
    }
}
