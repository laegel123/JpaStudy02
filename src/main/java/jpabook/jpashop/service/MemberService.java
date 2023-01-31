package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// 단순 조회 시 readOnly = true 로 주면 dirty checking 등을 하지 않아 성능상 이점이 있음.
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    // 필드 인잭션
    /**
    ㅁ @Autowired
    private MemberRepository memberRepository;
    **/

    // 세터 인잭션
    /**
     * private MemberRepository memberRepository;
     *
     *  ㅁ @Autowired public void setMemberRepository(MemberRepository memberRepository) {
     * this.memberRepository = memberRepository;
     * }
     **/

    // 생성자 인잭션
    // compile 시점에 값 세팅이 되어있는지 확인할 수 있기 때문에 final 붙인다.
    // @RequiredArgsConstructor -> final 붙은 필드 생성자로 만들어준다.
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     * @param member
     * @return
     */
    // readOnly = true 로 주면 수정이 안되기 때문에 따로 트랜잭션 적용. 기본값 readOnly = false
    @Transactional
    public Long join(Member member) {
        validateDuplicatedMember(member); // 중복회원검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복회원 검증
     * @param member
     */
    private void validateDuplicatedMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     * @return
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 한 건 조회
     * @param id
     * @return
     */
    public Member findMember(Long id) {
        return memberRepository.findOne(id);
    }
}
