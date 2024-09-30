package com.practice.springbootpractice.service;

import com.practice.springbootpractice.domain.Member;
import com.practice.springbootpractice.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
  private final MemberRepository memberRepository;

  @Autowired
  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  // 회원가입
  public Long join(Member member) {
    ifPreExist(member);

    memberRepository.save(member);
    return member.getId();
  }

  private void ifPreExist(Member member) {
    memberRepository.findByName(member.getName())
                    .ifPresent(m -> {
                      throw new IllegalStateException("이미 존재하는 회원입니다.");
                    });
  }

  public List<Member> findMembers() {
    return memberRepository.findAll();
  }

  public Optional<Member> findOne(Long memberId) {
    return memberRepository.findById(memberId);
  }
}
