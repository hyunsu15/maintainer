package com.example.maintainer.product.domain;

import lombok.Getter;

@Getter
public class FirstInitial {

  private static final char[] INITIALS = {
      'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ',
      'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
  };
  private String value;

  public FirstInitial(String value) {
    this.value = transferFirstInitialKorean(value);
  }

  private String transferFirstInitialKorean(String value) {
    StringBuffer firstInitialKorean = new StringBuffer();
    for (char ch : value.toCharArray()) {
      if (Character.isWhitespace(ch)) {
        continue;
      }
      int initialKoreanIndex = getFirstInitialKorean(ch);
      if (initialKoreanIndex < 0 || initialKoreanIndex >= INITIALS.length) {
        return null;
      }
    }

    for (char ch : value.toCharArray()) {
      if (Character.isWhitespace(ch)) {
        firstInitialKorean.append(ch);
        continue;
      }
      int initialKoreanIndex = getFirstInitialKorean(ch);
      firstInitialKorean.append(INITIALS[initialKoreanIndex]);
    }
    return firstInitialKorean.toString();
  }

  private int getFirstInitialKorean(char ch) {
    return (char) ((ch - 0xAC00) / 28 / 21);
  }
}
