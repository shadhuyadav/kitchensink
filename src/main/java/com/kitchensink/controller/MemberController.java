package com.kitchensink.controller;

import com.kitchensink.model.Member;
import com.kitchensink.service.MemberService;
import jakarta.validation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberService memberService;

    public MemberController(MemberService memberService) {

        this.memberService = memberService;
    }

    @GetMapping
    public List<Member> getAllByOrderByNameAsc() {
        return memberService.findAllByOrderByNameAsc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable String id) {
        Optional<Member> member = memberService.findById(id);
        return member.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<Member> getMemberByEmail(@PathVariable String email) {
        Optional<Member> member = memberService.findByEmailId(email);
        return member.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<?> createMember(@Valid @RequestBody Member member) {
        return ResponseEntity.ok(memberService.save(member));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable String id, @Valid @RequestBody Member memberDetails) {
        Optional<Member> memberOptional = memberService.findById(id);
        if (!memberOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Member member = memberOptional.get();
        member.setName(memberDetails.getName());
        return ResponseEntity.ok(memberService.save(member));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable String id) {
        memberService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}