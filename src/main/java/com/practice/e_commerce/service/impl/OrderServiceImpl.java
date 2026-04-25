package com.practice.e_commerce.service.impl;

import com.practice.e_commerce.common.OrderStatus;
import com.practice.e_commerce.common.PaymentStatus;
import com.practice.e_commerce.entity.Order;
import com.practice.e_commerce.entity.OrderItem;
import com.practice.e_commerce.entity.Product;
import com.practice.e_commerce.exception.BusinessException;
import com.practice.e_commerce.exception.ErrorCode;
import com.practice.e_commerce.mapper.OrderMapper;
import com.practice.e_commerce.repository.OrderRepository;
import com.practice.e_commerce.repository.ProductRepository;
import com.practice.e_commerce.request.CreateOrderRequest;
import com.practice.e_commerce.response.OrderResponse;
import com.practice.e_commerce.common.response.PageResponse;
import com.practice.e_commerce.service.OrderHistoryService;
import com.practice.e_commerce.service.OrderService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;
  private final OrderHistoryService orderHistoryService;
  private final OrderMapper orderMapper;

  @Transactional
  public OrderResponse createOrder(CreateOrderRequest request, Long userId) {
    Order order = new Order();
    order.setUserId(userId);
    order.setCode(generateOrderCode());
    order.setStatus(OrderStatus.PENDING);
    order.setPaymentStatus(PaymentStatus.UNPAID);

    List<OrderItem> items = new ArrayList<>();

    BigDecimal subTotal = BigDecimal.ZERO;

    for (CreateOrderRequest.Item reqItem : request.getItems()) {

      Product product = productRepository.findByIdAndDeletedFalse(reqItem.getProductId())
          .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

      int updatedStock = productRepository.decreaseStock(product.getId(), reqItem.getQuantity());

      if (updatedStock == 0) {
        throw new BusinessException(ErrorCode.OUT_OF_STOCK);
      }

      BigDecimal unitPrice = BigDecimal.valueOf(product.getPrice());
      BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(reqItem.getQuantity()));

      OrderItem item = new OrderItem();
      item.setProductId(product.getId());
      item.setProductName(product.getName());
      item.setUnitPrice(unitPrice);
      item.setQuantity(reqItem.getQuantity());
      item.setTotalPrice(totalPrice);
      item.setOrder(order);

      items.add(item);
      subTotal = subTotal.add(totalPrice);
    }

    order.setItems(items);

    order.setSubTotal(subTotal);
    order.setDiscountAmount(BigDecimal.ZERO);
    order.setShippingFee(calculateShippingFee(subTotal));
    order.setTotalAmount(
        subTotal
            .subtract(order.getDiscountAmount())
            .add(order.getShippingFee())
    );

    orderRepository.save(order);

    orderHistoryService.log(
        order.getId(),
        "CREATED",
        null,
        OrderStatus.PENDING.name(),
        "Create order"
    );

    return orderMapper.toResponse(order);
  }

  @Override
  @Transactional
  public void updateStatus(Long orderId, OrderStatus newStatus) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

    OrderStatus current = order.getStatus();

    if (!isValidTransition(current, newStatus)) {
      throw new BusinessException(ErrorCode.INVALID_STATUS_TRANSITION);
    }

    OrderStatus before = order.getStatus();

    order.setStatus(newStatus);

    orderHistoryService.log(
        order.getId(),
        "UPDATE_STATUS",
        before.name(),
        newStatus.name(),
        "Manual update"
    );
  }

  @Transactional
  public void cancelOrder(Long orderId) {

    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

    if (order.getStatus() != OrderStatus.PENDING) {
      throw new BusinessException(ErrorCode.CANNOT_CANCEL);
    }

    for (OrderItem item : order.getItems()) {
      productRepository.increaseStock(
          item.getProductId(),
          item.getQuantity()
      );
    }

    OrderStatus before = order.getStatus();
    order.setStatus(OrderStatus.CANCELLED);

    orderHistoryService.log(
        order.getId(),
        "CANCELLED",
        before.name(),
        OrderStatus.CANCELLED.name(),
        "User cancel"
    );

  }

  @Override
  public PageResponse<OrderResponse> getOrders(
      OrderStatus status,
      LocalDateTime fromDate,
      LocalDateTime toDate,
      int page,
      int size
  ) {

    Pageable pageable = PageRequest.of(
        page,
        size,
        Sort.by(Sort.Direction.DESC, "createdAt")
    );

    Page<Order> orderPage = orderRepository.findAllWithFilter(
        status,
        fromDate,
        toDate,
        pageable
    );

    return PageResponse.from(orderPage.map(orderMapper::toResponse));
  }

  private boolean isValidTransition(OrderStatus current, OrderStatus next) {

    return switch (current) {
      case PENDING -> next == OrderStatus.CONFIRMED ||
          next == OrderStatus.CANCELLED;

      case CONFIRMED -> next == OrderStatus.SHIPPING;

      case SHIPPING -> next == OrderStatus.COMPLETED;

      default -> false;
    };
  }

  private BigDecimal calculateShippingFee(BigDecimal subTotal) {
    return subTotal.compareTo(BigDecimal.valueOf(500000)) > 0
        ? BigDecimal.ZERO
        : BigDecimal.valueOf(20000);
  }

  private String generateOrderCode() {
    return "ORD-" + System.currentTimeMillis();
  }
}
