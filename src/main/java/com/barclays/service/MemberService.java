package com.barclays.service;

import com.barclays.model.Member;
import com.barclays.model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;


public interface MemberService {

   Member createMember(Member member);

   List<Member> findAll();

   Member findById(int id);

   List<Member> findByNameContains(String filter);

   List<Member> findByEmailAddressContains(String emailAddress);

   void delete(Member member);

   public Member save(Member member);
}
