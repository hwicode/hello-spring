package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        assertThatThrownBy(() -> memberService.join(member2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 존재하는 회원입니다.");

//        try {
//            memberService.join(member2);
//
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이1미 존재하는 회원입니다.");
//        }
        //then
    }

    @Test
    void findMembers() {
        //given
        Member member1 = new Member();
        member1.setName("hwi");

        Member member2 = new Member();
        member2.setName("spring1");

        Member member3 = new Member();
        member3.setName("spring2");

        //when
        memberService.join(member1);
        memberService.join(member2);
        memberService.join(member3);

        //then
        List<Member> members = memberService.findMembers();
        assertThat(members).contains(member1, member2, member3);
    }

    @Test
    void findOne() {
        //given
        Member member1 = new Member();
        member1.setName("hwi");

        Member member2 = new Member();
        member2.setName("spring1");

        Member member3 = new Member();
        member3.setName("spring2");

        //when
        Long member1Id = memberService.join(member1);
        memberService.join(member2);
        memberService.join(member3);

        //then
        assertThat(member1).isEqualTo(memberService.findOne(member1Id).get());

    }
}