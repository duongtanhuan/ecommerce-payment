package com.practice.e_commerce.service.impl;

import com.practice.e_commerce.common.OrderStatus;
import com.practice.e_commerce.common.PaymentStatus;
import com.practice.e_commerce.entity.Order;
import com.practice.e_commerce.entity.Payment;
import com.practice.e_commerce.exception.BusinessException;
import com.practice.e_commerce.exception.ErrorCode;
import com.practice.e_commerce.repository.OrderRepository;
import com.practice.e_commerce.repository.PaymentRepository;
import com.practice.e_commerce.request.PaymentRequest;
import com.practice.e_commerce.service.OrderHistoryService;
import com.practice.e_commerce.service.PaymentService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

  private final OrderRepository orderRepository;
  private final PaymentRepository paymentRepository;
  private final OrderHistoryService orderHistoryService;

  @Transactional
  public void pay(PaymentRequest request) {

    Order order = orderRepository.findById(request.getOrderId())
        .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

    if (order.getStatus() != OrderStatus.PENDING) {
      throw new BusinessException(ErrorCode.INVALID_ORDER_STATE);
    }

    if (order.getPaymentStatus() == PaymentStatus.PAID) {
      throw new BusinessException(ErrorCode.ALREADY_PAID);
    }

    Payment payment = new Payment();
    payment.setOrderId(order.getId());
    payment.setAmount(order.getTotalAmount());
    payment.setMethod(request.getMethod());
    payment.setStatus(PaymentStatus.PROCESSING.name());
    payment.setCreatedAt(LocalDateTime.now());

    paymentRepository.save(payment);

    boolean success = simulatePayment();

    if (success) {
      payment.setStatus(PaymentStatus.PAID.name());
      payment.setTransactionId("TXN-" + System.currentTimeMillis());

      OrderStatus before = order.getStatus();

      order.setPaymentStatus(PaymentStatus.PAID);
      order.setStatus(OrderStatus.CONFIRMED);

      orderHistoryService.log(
          order.getId(),
          "PAID",
          before.name(),
          OrderStatus.CONFIRMED.name(),
          "Payment success"
      );

    } else {
      payment.setStatus(PaymentStatus.FAILED.name());
      order.setPaymentStatus(PaymentStatus.FAILED);
    }
  }

  private boolean simulatePayment() {
    return true;
  }
}