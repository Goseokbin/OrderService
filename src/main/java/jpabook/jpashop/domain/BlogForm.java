package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter@Setter
public class BlogForm {
    private Long blogId;

    private Long memberId;
    private String memberName;
    private LocalDateTime localDateTime;
    private BlogCategory blogCategory;

    @NotEmpty(message = "제목은 필수 입니다")
    private String title;
    private String content;



}
