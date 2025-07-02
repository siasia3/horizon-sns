package com.yumyum.sns.friend.controller;

import com.yumyum.sns.friend.dto.FriendReqDto;
import com.yumyum.sns.friend.dto.FriendResDto;
import com.yumyum.sns.friend.service.FriendManagementService;
import com.yumyum.sns.friend.service.FriendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FriendController {

    private final FriendService friendService;
    private final FriendManagementService friendManagementService;

    @GetMapping("/friends")
    public ResponseEntity<List<FriendResDto>> getFriends(@RequestParam Long memberId){
        List<FriendResDto> friendList = friendService.getFriendsByMemberId(memberId);
        return ResponseEntity.ok(friendList);
    }

    @DeleteMapping("/friend/{friendId}")
    public ResponseEntity<Map<String,String>> removeFriendByFriendId(@PathVariable Long friendId,
                                               @Valid @ModelAttribute FriendReqDto friendReqDto){
        friendManagementService.unFriendByFriendId(friendId,friendReqDto.getMyMemberId(),friendReqDto.getFriendMemberId());
        return ResponseEntity.ok(Map.of("message","정상적으로 친구삭제되었습니다."));
    }


    @DeleteMapping("/friend")
    public ResponseEntity<Map<String,String>> removeFriendByMemberId(@Valid @ModelAttribute FriendReqDto friendReqDto){
        friendManagementService.unFriendByMemberId(friendReqDto.getMyMemberId(),friendReqDto.getFriendMemberId());
        return ResponseEntity.ok(Map.of("message","정상적으로 친구삭제되었습니다."));
    }

}
