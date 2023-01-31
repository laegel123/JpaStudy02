package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    // db에 data 넣는 걸 확인하기 위해서.
    // 이 방법 아니면 EntityManager 를 주입 받아서 테스트하는게 좋다.
    @Rollback(value = false)
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long saveId = memberService.join(member);

        // then
        // 같은 트랜잭션에서 영속성 컨택스트 안에서 같은 id 값으로 엔티티를 하나로 관리하므로 같다.
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test()
    public void 중복_회원_예외() throws Exception {

        assertThrows(IllegalStateException.class, () -> {

            // given
            Member member1 = new Member();
            member1.setName("kim1");

            Member member2 = new Member();
            member2.setName("kim1");

            // when
            memberService.join(member1);
            memberService.join(member2);

            /**
             try {
             memberService.join(member2);
             } catch (IllegalStateException e) {
             // 오류 발생해야 정상.
             return;
             }
             위 구문 대신 assertThrows 를 사용한다.
             **/

            // then
            fail("예외가 발생해야 한다.");
        });
        
    }

}
