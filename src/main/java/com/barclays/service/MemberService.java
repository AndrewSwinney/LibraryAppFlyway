package com.barclays.service;

import com.barclays.model.Book;
import com.barclays.model.Member;
import org.springframework.stereotype.Service;

import java.util.List;


public interface MemberService {

   Member createMember(Member member);

   List<Member> findAll();

   Member findById(int id);

   List<Member> findByNameContains(String filter);

   List<Member> findByEmailAddressContains(String emailAddress);

   Member save(Member m);

   void delete(Member member);
}
