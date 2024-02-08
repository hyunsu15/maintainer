package com.example.maintainer.member.domain;

import lombok.Getter;

@Getter
public class Member {

  private final PhoneNumber phoneNumber;
  private final Password password;

  public Member(PhoneNumber phoneNumber, Password password) {
    this.phoneNumber = phoneNumber;
    this.password = password;
  }

  public void checkExist(boolean isExistMember) {
    if (!isExistMember) {
      throw new IllegalMemberException();
    }
  }
}
