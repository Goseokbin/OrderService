package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)                 //기본적으로 데이터 변경에는 transactional이 있어야함.
@RequiredArgsConstructor

public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Member findOne(Long memberId){
       return memberRepository.findOne(memberId);
    }

    public Member findbyName(String memberName){
        List<Member> findMember = memberRepository.findByName(memberName);
        return findMember.get(0);
    }

    @Transactional
    public void update(Long id, String name) {
        Member one = memberRepository.findOne(id);
        one.setName(name);

    }
}
