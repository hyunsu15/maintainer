package com.example.maintainer.member.application.port.out;

public interface BlackListPort {

  void save(String token);

  boolean isExist(String token);
}
