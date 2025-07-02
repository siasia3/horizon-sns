package com.yumyum.sns.friend.controller;

import com.yumyum.sns.friend.dto.*;
import com.yumyum.sns.friend.service.FriendManagementService;
import com.yumyum.sns.friend.service.FriendRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friend-request")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;
    private final FriendManagementService friendManagementService;

    @GetMapping(value = "/state")
    public ResponseEntity<?> checkFriendRequest(@Valid @ModelAttribute FriendRequestReqDto friendRequestReqDto){

        Optional<FriendRequestResDto> friendRequestRes = friendRequestService.getFriendRequestIdByMemberIds(friendRequestReqDto.getMyId(),friendRequestReqDto.getUserId());
        ResponseEntity<FriendRequestResDto> result = friendRequestRes.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
        return result;
    }

    @PostMapping
    public ResponseEntity<FriendRequestResDto> sendFriendRequest(@Valid @RequestBody FriendRequestPostDto friendRequestPostDto){
        FriendRequestResDto friendRequest = friendRequestService.createFriendRequest(friendRequestPostDto.getSenderId(), friendRequestPostDto.getReceiverId());
        return ResponseEntity.ok(friendRequest);
    }

    @DeleteMapping(value = "/{friendRequestId}")
    public ResponseEntity<Map<String,String>> refuseFriendRequest(@PathVariable Long friendRequestId){
        friendRequestService.deleteFriendReq(friendRequestId);
        return  ResponseEntity.ok(Map.of("message","친구요청이 삭제되었습니다."));
    }

    @GetMapping(value = "/received")
    public ResponseEntity<List<ReceivedFriendRequestDto>> receivedFriendRequests(@RequestParam Long receiverId){
        List<ReceivedFriendRequestDto> receivedFriendReqs = friendRequestService.getReceivedFriendReqs(receiverId);
        return ResponseEntity.ok(receivedFriendReqs);
    }

    @GetMapping(value = "/sent")
    public ResponseEntity<List<SentFriendRequestDto>> sentFriendRequests(@RequestParam Long senderId){
        List<SentFriendRequestDto> sentFriendReqs = friendRequestService.getSentFriendReqs(senderId);
        return ResponseEntity.ok(sentFriendReqs);
    }

    @PatchMapping(value = "/{friendRequestId}/accept")
    public ResponseEntity<Map<String,String>> accept(@PathVariable Long friendRequestId){
        friendManagementService.acceptFriend(friendRequestId);
        return ResponseEntity.ok(Map.of("message","친구요청이 수락되었습니다."));
    }







}
