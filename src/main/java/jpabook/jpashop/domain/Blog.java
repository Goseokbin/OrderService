package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Blog {

    @Id@GeneratedValue
    private Long blogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;

    private LocalDateTime localDateTime;
    private String tittle;

    private BlogCategory blogCategory;

    private String content;


}
