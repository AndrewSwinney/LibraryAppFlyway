package com.barclays;


import com.barclays.controller.MemberController;
import com.barclays.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MemberControllerIntegrationTests {

    @Autowired
    MemberController memberController;

    @Test
    public void testGetAllMembers() {
        List<Member> members = memberController.getAllMembers("", "");
        assertEquals(17, members.size());
    }

    @Test
    public void testGetMember1() {
        Member member = memberController.getMember(37);
        assertEquals("Andrew", member.getName());
    }

    @Test
    void testFilterMemberByEmail() {
        List<Member> members = memberController.getAllMembers("", "andrews@email.com");
        assertEquals(1, members.size());
    }

    @Test
    public void allArgsConstructor() {
        Member member = new Member("Billy", "");
        assertEquals("Billy", member.getName());
    }

}
