package com.barclays.controller;

import com.barclays.model.Book;
import com.barclays.model.Member;
import com.barclays.service.MemberService;
import jakarta.websocket.server.PathParam;
import org.hibernate.mapping.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        Member createdMember = memberService.createMember(member);
        return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Member> getAllMembers(@PathParam("filter") String filter,
                                      @PathParam("emailAddress") String emailAddress) {
        if (filter != null && !filter.isEmpty()) {
            return memberService.findByNameContains(filter);
        } else if (emailAddress != null && !emailAddress.isEmpty()) {
            return memberService.findByEmailAddressContains(emailAddress);
        }
        return memberService.findAll();
    }

    @GetMapping("/with-books")
    public ResponseEntity<List<Member>> getAllMembersWithBooks() {
        List<Member> membersWithBooks = memberService.findAllMembersWithBooks();
        return ResponseEntity.ok(membersWithBooks);
    }


    @GetMapping("/{id}")
    public Member getMember(@PathVariable int id) {
        return memberService.findById(id);
    }

    @PutMapping
    public Member updateMember(@RequestBody Member member){
        return memberService.save(member);
    }

    @DeleteMapping
    public void deleteByMember(@RequestBody Member member){
        memberService.delete(member);
    }
}

