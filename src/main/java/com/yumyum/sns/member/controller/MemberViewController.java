package com.yumyum.sns.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberViewController {

    @GetMapping("/member/profile/edit")
    public String editProfile(){

        return "editProfile";
    }
}
