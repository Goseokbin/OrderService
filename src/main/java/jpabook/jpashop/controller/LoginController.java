package jpabook.jpashop.controller;

import jpabook.jpashop.domain.LoginForm;
import jpabook.jpashop.domain.LoginInfo;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.member.LoginDto;
import jpabook.jpashop.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {
    @Resource
    private LoginInfo loginSession;
    private final MemberRepository memberRepository;
    private final LoginService loginService;

    @GetMapping("/login/new")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login/loginForm";
    }

    @PostMapping("/login/new")
    public String login(@RequestParam("username") String username,
                        @RequestParam("pass") String pass, HttpSession session) {

        LoginDto findMember = memberRepository.LoginFindByName(username);
        loginSession = loginService.login(findMember,pass);
        session.setAttribute("userId", loginSession.getMemberId());
        session.setAttribute("userName", loginSession.getName());

        if(loginSession != null) return "redirect:/";
        else return "login/loginForm";

    }


}