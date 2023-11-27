package com.barclays.controller;

import com.barclays.model.Member;
import com.barclays.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

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
    public List<Member> getAllMembers(@RequestParam(required = false) String filter,
                                      @RequestParam(required = false) String emailAddress) {
        if (filter != null && !filter.isEmpty()) {
            return memberService.findByNameContains(filter);
        } else if (emailAddress != null && !emailAddress.isEmpty()) {
            return memberService.findByEmailAddressContains(emailAddress);
        }
        return memberService.findAll();
    }

    @GetMapping("/{id}")
    public Member getMember(@PathVariable int id) {
        return memberService.findById(id);
    }
}

