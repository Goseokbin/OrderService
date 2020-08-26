package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter@Setter
public class BlogForm {
    private Long blogId;
    private Long memberId;
    private String memberName;
    private LocalDateTime localDateTime;
    private BlogCategory blogCategory;
    private String title;
    private String content;



}
