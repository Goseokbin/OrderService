package jpabook.jpashop.service;

import jpabook.jpashop.domain.LoginInfo;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.member.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {

        private final MemberRepository memberRepository;

        public LoginDto login(String name, String writePass) {
            LoginDto findMember = memberRepository.LoginFindByName(name);
            if (findMember.getPass().equals(writePass)) {
                return findMember;
            }
            else
                return null;
        }
}
