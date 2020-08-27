package jpabook.jpashop.service;

import jpabook.jpashop.domain.LoginInfo;
import jpabook.jpashop.repository.member.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class LoginService {
        @Resource
        private final LoginInfo loginSession;

        public LoginInfo login(LoginDto findMember, String writePass) {
            if (findMember.getPass().equals(writePass)) {
                loginSession.setName(findMember.getName());
                loginSession.setMemberId(findMember.getMemberId());
                return loginSession;
            }
            else
                return null;
        }
}
