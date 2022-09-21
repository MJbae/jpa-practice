package jpabook2.jpashop.service;

import jpabook2.jpashop.domain.Member;
import jpabook2.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
/*
본 예제에서는 테스트 수행 시 Service 기능을 포괄적으로 점검하지만
Entity 메소드 중심으로 특히 비지니스 로직 등을 체크하여 보다 정밀한 테스트를 수행해야 함
 */


/*
test 아래 폴더에 application.yml이 있으면
main 아래 application.properties 보다 우선순위를 가지고 참조함.
*/


//아래 두 가지 annotation이 붙어야 Spring과 완전히 통합된 테스트를 수행할 수 있다.
@RunWith(SpringRunner.class)
@SpringBootTest
// test이기 때문에 test data가 실제 db에 올라가면 문제가 생길 수 있다.
// 따라서 test의 경우 transactional을 걸어두면 테스트 완료 후 test data가 roll back되기 때문에
// 실제 DB에 test data가 삽입되지 않는다.
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    // @Transactional 적용하면 rollback되기 때문에 실제 DB에 injection이 안된다.
    // 테스트를 실행한 결과가 실제 DB에 들어가는 지 확인하기 위해 @Rollback(false)를 명시하여 테스트한다
    @Rollback(false)
    public void 회원가입() throws Exception{
        //given, 특정 환경 아래에서
        Member member = new Member();
        member.setName("kim");

        //when, 특정 조건을 걸고 실행하면
        Long savedId = memberService.join(member);


        //then, 특정 결과가 나온다, 이를 검증한다
        //아래 메소드에서 true가 나오는 이유는 @Transactional로 인해 한 트랜잭션에
        //한 개의 id만 가지고 데이터 처리가 되기 때문
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");


        //when
        memberService.join(member1);
        //MemberService.validateDuplicateMember() 메소드에 따라
        //동일 이름이 확인되고 exception이 발생한다.
        memberService.join(member2);

        //then
    }

}