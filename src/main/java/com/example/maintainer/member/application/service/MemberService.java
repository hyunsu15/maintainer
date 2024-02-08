package com.example.maintainer.member.application.service;

import com.example.maintainer.member.application.port.in.MemberUseCase;
import com.example.maintainer.member.application.port.out.MemberPort;
import com.example.maintainer.member.application.port.out.TokenProvider;
import com.example.maintainer.member.domain.Member;
import com.example.maintainer.member.domain.Password;
import com.example.maintainer.member.domain.PhoneNumber;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberUseCase {

  private final MemberPort memberPort;
  private final TokenProvider tokenProvider;

  @Override
  public void signUp(PhoneNumber phoneNumber, Password password) {
    boolean isExistPhoneNumber = memberPort.isExistPhoneNumber(phoneNumber);
    phoneNumber.checkDuplicate(isExistPhoneNumber);
    memberPort.save(phoneNumber, password);
  }

  @Override
  public String signIn(PhoneNumber phoneNumber, Password password) {
    boolean isExistMember = memberPort.isExistMember(phoneNumber, password);
    Member member = new Member(phoneNumber, password);
    member.checkExist(isExistMember);
    return tokenProvider.generateToken(phoneNumber, new Date());
  }
}
