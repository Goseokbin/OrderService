package jpabook.jpashop.repository.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String name;
    private String pass;

    public LoginDto(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }
}
