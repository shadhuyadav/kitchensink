package com.kitchensink.controller;

import com.kitchensink.model.Member;
import com.kitchensink.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import javax.xml.validation.Validator;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberControllerTest {

    @InjectMocks
    private MemberController memberController;

    @Mock
    private MemberService memberService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

    }

    @Test
    void getAllByOrderByNameAsc_ShouldReturnListOfMembers() {
     List<Member> memberList=createMember();
      when(memberService.findAllByOrderByNameAsc()).thenReturn(memberList);
        var result = memberController.getAllByOrderByNameAsc();
        assertEquals(2, result.size());
        assertEquals("Alpha", result.get(0).getName());
        assertEquals("Beta", result.get(1).getName());
    }

    @Test
    void getMemberById_ShouldReturnMember_WhenExists() {
        Member member=createMember().get(0);
        member.setId("1");
        when(memberService.findById("1")).thenReturn(Optional.of(member));
        ResponseEntity<Member> result = memberController.getMemberById("1");
        assertEquals(ResponseEntity.ok(member), result);
    }

    @Test
    void getMemberById_ShouldReturnNotFound_WhenDoesNotExist() {
        when(memberService.findById("1")).thenReturn(Optional.empty());
        ResponseEntity<Member> result = memberController.getMemberById("1");
        assertEquals(ResponseEntity.notFound().build(), result);
    }

   @Test
    void createMember_ShouldReturnCreatedMember() {
        Member member = createMember().get(0);
        when(memberService.save(member)).thenReturn(member);
        ResponseEntity<?> result = memberController.createMember(member);
        verify(memberService).save(member);
    }
    @Test
    void updateMember_ShouldReturnUpdatedMember_WhenExists() {
        Member existingMember = createMember().get(0);
        existingMember.setId("1");
        Member updatedMember =createMember().get(0);
        updatedMember.setName("Alpha Updated");
        updatedMember.setId("1");
        when(memberService.findById("1")).thenReturn(Optional.of(existingMember));
        when(memberService.save(any(Member.class))).thenReturn(updatedMember);
        ResponseEntity<Member> result = memberController.updateMember("1", updatedMember);
        assertEquals(ResponseEntity.ok(updatedMember), result);
        assertEquals("Alpha Updated", updatedMember.getName());
    }

    private List<Member> createMember(){
        Member member1=new Member();
        member1.setName("Alpha");
        member1.setEmail("Alpha@example.com");
        member1.setPhoneNumber("1234567890");

        Member member2=new Member();
        member2.setName("Beta");
        member2.setEmail("Beta@example.com");
        member2.setPhoneNumber("9876543210");
       return Arrays.asList(member1, member2);
    }

}
