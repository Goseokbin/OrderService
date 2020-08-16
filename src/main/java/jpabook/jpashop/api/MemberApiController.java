package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.persistence.Embedded;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     *
     * v1처럼 엔티티에서 직접 가져오면 엔티티를 전부 가져오기 때문 원치않은 정보도 가져온다(회원정보만 원하는데 주문정보도 가져온다)
     * JsonIgnore으로 부분해결할 수 있지만 다른 API를 개발할 때 문제가 생긴다.
     * 또한 엔티티 presentation을 위한 로직이 생긴다. 분리가 안
     * */
    @GetMapping("/api/v1/members")
    public List<Member> memberV1(){
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                                .map(m -> new MemberDto(m.getName()))
                                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
    @Data
    static class MemberDto{
        private String name;

        public MemberDto(String name) {
            this.name = name;
        }
    }


    /**
     * v1 방식 :member 엔티티를 직접 받는다.
     * 엔티티에 프레젠테이션 계층을 위한 로직이 추가된다.(Not Empty 등)
     * 엔티티가 변경되면 API스펙도 변한다.
     * 결론 -> DTO를 생성해 파라미터로 받는다.
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     *
     * v2: 요청 값으로 RequestBody에 엔티티 대신 별도의 DTO를 생성한다.
     * 엔티티가 변경되도 API 스펙이 변하지 않는다.
     */

    @PostMapping("api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());
        member.setAddress(request.getAddress());
        Long join = memberService.join(member);
        return new CreateMemberResponse(join);
    }

    @PutMapping("api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }


    @Data
    static class CreateMemberRequest {
        private String name;
        @Embedded
        private Address address;
    }
    @Data
    class CreateMemberResponse {
        private Long id;
        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
    @Data
    @AllArgsConstructor
    private class UpdateMemberResponse {
        private Long id;
        private String name;
    }
    @Data
    static class UpdateMemberRequest{
        private String name;

    }
}
