package com.example.maintainer.member.adapter.in.web;

import com.example.maintainer.member.application.port.in.MemberUseCase;
import com.example.maintainer.member.domain.Password;
import com.example.maintainer.member.domain.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
class MemberController {

  private final MemberUseCase useCase;

  @PostMapping("/sign/up")
  ResponseEntity<CustomResponse> signUp(SignUpRequest request) {
    useCase.signUp(new PhoneNumber(request.phoneNumber()), new Password(request.password()));
    return ResponseEntity.ok()
        .body(new CustomResponse(new Meta(HttpStatus.OK.value(), "OK"), null));
  }

  @PostMapping("/sign/in")
  ResponseEntity<CustomResponse<String>> signIn(SignInRequest request) {
    return ResponseEntity.ok()
        .body(new CustomResponse(new Meta(HttpStatus.OK.value(), "OK"),
            useCase.signIn(new PhoneNumber(request.phoneNumber()),
                new Password(request.password()))));
  }
}
