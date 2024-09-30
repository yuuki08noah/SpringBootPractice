package com.practice.springbootpractice.service;

import com.practice.springbootpractice.domain.Member;
import com.practice.springbootpractice.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Assertions;

@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {

  @Autowired MemberService memberService;
  @Autowired
  MemberRepository memberRepository;

  @Test
  void join() {
    Member member = new Member();
    member.setName("springgkkk");

    Long saveId = memberService.join(member);

    Member findMember = memberService.findOne(saveId).get();
    assertThat(member.getName()).isEqualTo(findMember.getName());
  }

  @Test
  void pre_existing_exception() {
    // given
    Member member1 = new Member();
    member1.setName("spring");

    Member member2 = new Member();
    member2.setName("spring");

    // when
    memberService.join(member1);
    IllegalStateException e = Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
  }
}
