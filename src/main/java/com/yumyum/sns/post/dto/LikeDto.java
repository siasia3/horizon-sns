package com.yumyum.sns.post.dto;

import com.yumyum.sns.validated.group.DeleteGroup;
import com.yumyum.sns.validated.group.InsertGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.sql.Insert;

@Getter
@Setter
@NoArgsConstructor
public class LikeDto {

    @NotNull(groups = InsertGroup.class)
    private Long postId;
    @NotNull(groups = DeleteGroup.class)
    private Long likeId;
    private Long memberId;
}
