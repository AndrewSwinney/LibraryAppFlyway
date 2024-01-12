package com.barclays.service;


import com.barclays.model.Book;
import com.barclays.model.Member;
import com.barclays.model.Movie;
import com.barclays.repository.BookRepository;
import com.barclays.repository.MemberRepository;
import com.barclays.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {


    private MemberRepository memberRepository;


    private BookRepository bookRepository;


    private MovieRepository movieRepository;


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
        return member.orElseGet(() -> new Member("Default Member: Nothing found", ""));
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

    @Override
    public List<Member> findAllMembersWithBooks() {
        return memberRepository.findAllMembersWithBooks();
    }
    @Override
    public List<Member> findAllMembersWithMovies() {
        return memberRepository.findAllMembersWithMovies();
    }

    @Override
    public void assignBookToMember(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setMember(member);
        bookRepository.save(book);
    }

    @Override
    public void assignMovieToMember(Long memberId, Long movieId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        movie.setMember(member);
        movieRepository.save(movie);
    }

}
