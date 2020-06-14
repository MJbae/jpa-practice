package jpabook2.jpashop.service;

import jpabook2.jpashop.domain.Member;
import jpabook2.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//단순 조회의 경우 readOnly 조건을 달면 성능이 최적화됨
@Transactional(readOnly = true)
//final이 달려있는 필드만 가지고 생성자를 만들어 줌
@RequiredArgsConstructor
public class MemberService {

    //변경할 필요가 없기 때문에 final 선언을 한다.
    private final MemberRepository memberRepository;


    /* setMemberRepository로 injection을 하면 어플리케이션 동작 중 누군가 이 메소드를
        건드릴 경우 문제가 생길 수 있음. 따라서 요즘은 생성자를 통해 injectiond을 하는 추세임.
    @Autowired //Repository를 injection
    public void setMemberRepository(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    아래 생성자 injection은 @AllArgsConstructor을 클래스 위에 달아줌으로써 생략할 수 있다.
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    */

    //회원 가입
    //단순 조회가 아닌 insert 내용이므로 추가적으로 transactional을 추가한다.
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member); // 중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
