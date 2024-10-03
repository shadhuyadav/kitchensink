package com.kitchensink.service;


import com.kitchensink.model.Member;
import com.kitchensink.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> findAllByOrderByNameAsc() {
        return memberRepository.findAllByOrderByNameAsc();
    }

    public Optional<Member> findById(String id) {
        return memberRepository.findById(id);
    }
    public Optional<Member> findByEmailId(String emailId) {
        return Optional.ofNullable(memberRepository.findByEmail(emailId));
    }



    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public void deleteById(String id) {
        memberRepository.deleteById(id);
    }
}