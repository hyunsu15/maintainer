package com.example.maintainer.member.application.service;

import com.example.maintainer.member.application.port.in.MemberUseCase;
import com.example.maintainer.member.application.port.out.MemberPort;
import com.example.maintainer.member.domain.Password;
import com.example.maintainer.member.domain.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberUseCase {

  private final MemberPort memberPort;

  @Override
  public void signUp(PhoneNumber phoneNumber, Password password) {
    boolean isExistPhoneNumber = memberPort.isExistPhoneNumber(phoneNumber);
    phoneNumber.checkDuplicate(isExistPhoneNumber);
    memberPort.save(phoneNumber, password);
  }
}
