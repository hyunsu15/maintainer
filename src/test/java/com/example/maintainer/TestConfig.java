package com.example.maintainer;

import com.example.maintainer.member.application.port.out.BlackListPort;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {

  @Bean
  @Primary
  public BlackListPort blackListPort() {
    return new FakeBlackListPort();
  }
}
