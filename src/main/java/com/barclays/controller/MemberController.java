package com.barclays.controller;

import com.barclays.model.Member;
import com.barclays.service.MemberService;
import jakarta.websocket.server.PathParam;
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

    static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    // POST endpoint to create members
    @PostMapping
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        Member createdMember = memberService.createMember(member);
        return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
    }


    // GET endpoint to retrieve all members, members can be filtered by name and emailAddress
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


    // GET endpoint to retrieve all members with a book
    @GetMapping("/with-books")
    public ResponseEntity<List<Member>> getAllMembersWithBooks() {
        List<Member> membersWithBooks = memberService.findAllMembersWithBooks();
        return ResponseEntity.ok(membersWithBooks);
    }


    // GET endpoint to retrieve all members with a movie
    @GetMapping("/with-movies")
    public ResponseEntity<List<Member>> getAllMembersWithMovies() {
        List<Member> membersWithMovies = memberService.findAllMembersWithMovies();
        return ResponseEntity.ok(membersWithMovies);
    }


    // GET endpoint to get members by their ID
    @GetMapping("/{id}")
    public Member getMember(@PathVariable int id) {
        return memberService.findById(id);
    }


    // PUT endpoint to assign a book to a member
    @PutMapping("/{memberId}/books/{bookId}")
    public ResponseEntity assignBookToMember(@PathVariable Long memberId, @PathVariable Long bookId) {
        try {
            memberService.assignBookToMember(memberId, bookId);
            return ResponseEntity.ok("Book assigned to member successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // PUT endpoint to assign a movie to a member
    @PutMapping("/{memberId}/movies/{movieId}")
    public ResponseEntity assignMovieToMember(@PathVariable Long memberId, @PathVariable Long movieId) {
        try {
            memberService.assignMovieToMember(memberId, movieId);
            return ResponseEntity.ok("Movie assigned to member successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    // PUT endpoint to update existing members
    @PutMapping
    public Member updateMember(@RequestBody Member member){
        return memberService.save(member);
    }


    // DELETE endpoint to delete members
    @DeleteMapping
    public void deleteByMember(@RequestBody Member member){
        memberService.delete(member);
    }
}

