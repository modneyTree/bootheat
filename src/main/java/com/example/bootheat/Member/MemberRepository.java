package com.example.bootheat.Member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//public interface MemberRepository {
//    Member save(Member member);
//    Optional<Member> findById(Long id);
//    Optional<Member> findByName(String name);
//    List<Member> findAll();
//}

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
}