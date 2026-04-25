package com.practice.e_commerce.controller;

import com.practice.e_commerce.request.PaymentRequest;
import com.practice.e_commerce.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping
  public ResponseEntity<Void> pay(
      @Valid @RequestBody PaymentRequest request
  ) {
    paymentService.pay(request);
    return ResponseEntity.ok().build();
  }
}
