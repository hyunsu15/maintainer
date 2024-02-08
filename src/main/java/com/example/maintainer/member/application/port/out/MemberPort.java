package com.example.maintainer.member.application.port.out;

import com.example.maintainer.member.domain.Password;
import com.example.maintainer.member.domain.PhoneNumber;

public interface MemberPort {

  boolean isExistPhoneNumber(PhoneNumber phoneNumber);

  void save(PhoneNumber phoneNumber, Password password);

  boolean isExistMember(PhoneNumber phoneNumber, Password password);
}
