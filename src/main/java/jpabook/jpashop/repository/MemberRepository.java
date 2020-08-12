package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository                                 //Spring bean으로 등록 Component 스캔의 대상이 됨.
@RequiredArgsConstructor
public class MemberRepository {
                                            //  @PersistenceContext JPA에서 제공되는 기 애노테이 자동으로 엔티티 매니저 주입.
    private final EntityManager em ;        //@RequiredArgsConstructor로 대체

    public void save(Member member){
        em.persist(member);               //transaction이 commit되는 시점에 DB에 저장.
    }
    public Member findOne(Long id){
        return em.find(Member.class,id);    //단건 조회.
    }
    public List<Member> findAll(){
        return (List<Member>) em.createQuery("select m from Member m")//JPQL은 from의 대상이 테이블이 아니라 엔티티가 된다.
                .getResultList();
    }
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member  m where m.name = :name", Member.class)
                .setParameter("name", name).getResultList();
    }

}
