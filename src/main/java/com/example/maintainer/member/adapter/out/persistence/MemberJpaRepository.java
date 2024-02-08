package com.example.maintainer.member.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {

  boolean existsByPhoneNumber(String phoneNumber);

  boolean existsByPhoneNumberAndPassword(String phoneNumber, String password);
}
