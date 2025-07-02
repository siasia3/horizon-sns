package com.yumyum.sns.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatViewController {

    @GetMapping("/chat/{nickname}")
    public String getChatUser(@PathVariable String nickname,
                              @RequestParam Long chatroomId,
                              Model model){
        model.addAttribute("chatroomId",chatroomId);

        return "message";
    }


}
