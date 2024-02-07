package com.example.maintainer.member.domain;

import java.util.Set;
import lombok.Getter;

@Getter
public class PhoneNumber {

  private static final int PHONE_NUMBER_LENGTH = 11;
  private static final int PHONE_NUMBER_CONTAINS_MINUS_SIGN_LENGTH = 11 + 2;
  private final String phoneNumber;

  public PhoneNumber(String phoneNumber) {
    validate(phoneNumber);
    this.phoneNumber = phoneNumber.replaceAll("\\d", "");
  }

  private void validate(String phoneNumber) {
    if (phoneNumber == null) {
      throw new IllegalPhoneNumberException();
    }
    if (!validatePhoneNumber(phoneNumber) && !validatePhoneNumberContainsMinusSign(phoneNumber)) {
      throw new IllegalPhoneNumberException();
    }
  }

  private boolean validatePhoneNumber(String phoneNumber) {
    if (phoneNumber.length() != PHONE_NUMBER_LENGTH) {
      return false;
    }
    if (!phoneNumber.startsWith("010")) {
      return false;
    }
    return (!phoneNumber.replaceAll("\\d", "").isEmpty());
  }

  private boolean validatePhoneNumberContainsMinusSign(String phoneNumber) {
    if (phoneNumber.length() != PHONE_NUMBER_CONTAINS_MINUS_SIGN_LENGTH) {
      return false;
    }
    if (!phoneNumber.startsWith("010")) {
      return false;
    }
    Set<Integer> minusSignIndexes = Set.of(3, 8);
    for (int i = 0; i < phoneNumber.length(); i++) {
      if (minusSignIndexes.contains(i)
          || Character.isDigit(phoneNumber.charAt(i))) {
        continue;
      }
      return false;
    }
    return true;
  }
}
