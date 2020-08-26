package jpabook.jpashop.api;

import jpabook.jpashop.domain.LoginInfo;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.member.LoginDto;
import jpabook.jpashop.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequiredArgsConstructor
public class LoginApiController {
    private final MemberRepository memberRepository;
    private final LoginService loginService;

    @Resource
    private LoginInfo loginSession;

    @GetMapping("/login/session")
    public String getSession(){
        return loginSession.toString();
    }



}
