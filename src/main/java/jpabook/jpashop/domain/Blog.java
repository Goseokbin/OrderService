package jpabook.jpashop.domain;

import jpabook.jpashop.domain.BaseTime;
import jpabook.jpashop.domain.BlogCategory;
import jpabook.jpashop.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Blog extends BaseTime {

    @Id@GeneratedValue
    private Long blogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;

//    private LocalDateTime localDateTime;
    private String title;

    private BlogCategory blogCategory;

    private String content;

    @Builder
    public Blog(Member member, String title, BlogCategory blogCategory, String content) {
        this.member = member;
        this.title=title;
        this.blogCategory=blogCategory;
        this.content=content;
    }
}

