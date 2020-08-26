package jpabook.jpashop.repository.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private Long memberId;
    private String name;
    private String pass;

    public LoginDto(Long memberId, String name, String pass) {
        this.memberId = memberId;
        this.name = name;
        this.pass = pass;
    }
}
