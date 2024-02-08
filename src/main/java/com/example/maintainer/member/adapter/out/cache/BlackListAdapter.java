package com.example.maintainer.member.adapter.out.cache;

import com.example.maintainer.member.application.port.out.BlackListPort;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlackListAdapter implements BlackListPort {

  private final RedisTemplate<String, Object> redisTemplate;

  @Override
  public void save(String token) {
    redisTemplate.opsForValue().set(token, token);
    redisTemplate.expire(token, Duration.ofDays(1));
  }

  @Override
  public boolean isExist(String token) {
    return redisTemplate.opsForValue().get(token) != null;
  }
}
