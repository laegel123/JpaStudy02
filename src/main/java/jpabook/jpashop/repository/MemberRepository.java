package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    /**
     * ㅁ @PersistenceContext로 주입을 받아야 하지만 Spring Data JPA의 지원에 의해 @Autowired 로 바꿔서 적용할 수 있는데 더 간편하게 @RequiredArgsConstructor 을 붙여 코드를 줄일 수 있다.
     * 나중에는 표준 라이브러리에도 @Autowired 로 주입받을 수 있게 바뀐다고 함.
     *
    a @PersistenceContext
    private EntityManager em;
    **/

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
