package jpabook2.jpashop.repository;

import jpabook2.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //EntityManager에 데이터 injection해줌, Service와 달리 Repository는 아래의 annotation으로 injection함
    //하지만 최근 spring에서는 autowired로 repository도 injection 지원할 예정
    @PersistenceContext
    private final EntityManager em;

    //insert 쿼리를 실행
   public void save(Member member){
        em.persist(member);
    }

    //특정 id의 멤버를 찾아서 반환함
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }
    //다수의 것을 찾아서 반환함
    //jpql을 사용함. sql 문법과 차이가 있음
    //sql은 table을 대상으로 쿼리를 수행 반면 jpql은 entity를 대상으로 쿼리를 수행
    //from의 대상이 entity임
    public List<Member> findAll(){
       return em.createQuery("select m from Member m", Member.class)
               .getResultList();
    }

    //이름을 기준으로 find 실시함
    public List<Member> findByName(String name){
       return em.createQuery("select m from Member m where m.name = :name", Member.class)
               .setParameter("name", name).getResultList();
    }



}
