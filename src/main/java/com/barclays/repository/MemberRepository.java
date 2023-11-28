package com.barclays.repository;

import com.barclays.model.Book;
import com.barclays.model.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {

    List<Member> findByNameContains(String filter);

    List<Member> findByEmailAddressContains(String emailAddress);

    @Query("SELECT m FROM Member m JOIN FETCH m.books")
    List<Member> findAllMembersWithBooks();

}
