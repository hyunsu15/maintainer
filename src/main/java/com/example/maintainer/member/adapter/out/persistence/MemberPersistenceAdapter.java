package com.example.maintainer.member.adapter.out.persistence;

import com.example.maintainer.member.application.port.out.MemberPort;
import com.example.maintainer.member.domain.Password;
import com.example.maintainer.member.domain.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberPort {

  private final MemberJpaRepository memberJpaRepository;

  @Override
  public boolean isExistPhoneNumber(PhoneNumber phoneNumber) {
    return memberJpaRepository.existsByPhoneNumber(phoneNumber.getPhoneNumber());
  }

  @Override
  public void save(PhoneNumber phoneNumber, Password password) {
    memberJpaRepository.save(
        new MemberJpaEntity(phoneNumber.getPhoneNumber(), password.getPassword()));
  }
}
