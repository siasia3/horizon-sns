package com.yumyum.sns.friend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FriendViewController {

    @GetMapping("/friendRequest")
    public String getChatUser(){

        return "friend";
    }

}
