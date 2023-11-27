package com.barclays.service;


import com.barclays.model.Member;
import com.barclays.model.Movie;
import com.barclays.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Member createMember(Member member)
    {
        return memberRepository.save(member);
    }


    @Override
    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        memberRepository.findAll().forEach(members::add);
        return members;
    }

    @Override
    public Member findById(int id) {
        Optional<Member> member = memberRepository.findById((long) id);
        return member.orElseGet(() -> new Member("Default Member: Nothing found"));
    }

    @Override
    public List<Member> findByNameContains(String filter) {
        return memberRepository.findByNameContains(filter);
    }

    @Override
    public List<Member> findByEmailAddressContains(String emailAddress) {
        return memberRepository.findByEmailAddressContains(emailAddress);
    }

    public void delete(Member member) {
        memberRepository.delete(member);
    }

    @Override
    public Member save(Member m){
        return memberRepository.save(m);
    }
}
