package com.example.maintainer;

import com.example.maintainer.member.application.port.out.BlackListPort;
import java.util.HashMap;
import java.util.Map;


class FakeBlackListPort implements BlackListPort {

  Map<String, String> map = new HashMap<>();

  @Override
  public void save(String token) {
    map.put(token, token);
  }

  @Override
  public boolean isExist(String token) {
    return map.get(token) != null;
  }

  public void cleanup() {
    map.clear();
  }
}