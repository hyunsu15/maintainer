package com.example.maintainer.member.adapter.in.web;

import com.example.maintainer.member.application.port.in.MemberUseCase;
import com.example.maintainer.member.domain.Password;
import com.example.maintainer.member.domain.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
class MemberController {

  private final MemberUseCase useCase;

  @PostMapping("/sign/up")
  ResponseEntity<CustomResponse> signUp(@RequestBody SignUpRequest request) {
    useCase.signUp(new PhoneNumber(request.phoneNumber()), new Password(request.password()));
    return ResponseEntity.ok()
        .body(new CustomResponse(new Meta(HttpStatus.OK.value(), "OK"), null));
  }

  @PostMapping("/sign/in")
  ResponseEntity<CustomResponse<String>> signIn(@RequestBody SignInRequest request) {
    return ResponseEntity.ok()
        .body(new CustomResponse(new Meta(HttpStatus.OK.value(), "OK"),
            useCase.signIn(new PhoneNumber(request.phoneNumber()),
                new Password(request.password()))));
  }

  @PostMapping("/sign/out")
  ResponseEntity<CustomResponse<String>> signOut(@RequestBody SignOutRequest request) {
    useCase.signOut(request.token());
    return ResponseEntity.ok()
        .body(new CustomResponse(new Meta(HttpStatus.OK.value(), "OK"), null));
  }
}
