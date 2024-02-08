package com.example.maintainer.member.application.port.in;

import com.example.maintainer.member.domain.Password;
import com.example.maintainer.member.domain.PhoneNumber;

public interface MemberUseCase {

  void signUp(PhoneNumber phoneNumber, Password password);
}
