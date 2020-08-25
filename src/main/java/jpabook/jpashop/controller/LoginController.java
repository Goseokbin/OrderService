package jpabook.jpashop.controller;

import jpabook.jpashop.domain.LoginForm;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.member.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private  final MemberRepository memberRepository;

    @GetMapping("/login/new")
    public String loginForm(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "login/loginForm";
    }

    @PostMapping("/login/new")
    public String login(@RequestParam("username") String username,
                        @RequestParam("pass") String pass){
        LoginDto findByName = memberRepository.LoginFindByName(username);
        if(findByName.getPass()==pass){
            return "home";
        }
        else{
            return "redirect:/login/new";
        }

    }
}
