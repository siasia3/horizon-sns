package com.yumyum.sns.post.entity;

import com.yumyum.sns.attachment.entity.Attachment;
import com.yumyum.sns.comment.entity.Comment;
import com.yumyum.sns.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ATTACHMENT_ID")
    private Attachment attachment;

    @OneToMany(mappedBy = "post")
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostTag> postTags = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private String thumbnailPath;

    public Post(String content,String thumbnailPath) {
        this.content = content;
        this.thumbnailPath = thumbnailPath;
    }

    public void setPostRelation(Member member, Attachment attachment){
        this.member = member;
        if(attachment.getId()!=null && attachment.getId()>0){
            this.attachment = attachment;
        }

    }
}
