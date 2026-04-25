package com.practice.e_commerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_histories")
@Getter
@Setter
public class OrderHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long orderId;

  private String action; // CREATED, CANCELLED, PAID...

  private String statusBefore;
  private String statusAfter;

  private String note;

  private LocalDateTime createdAt;
}
