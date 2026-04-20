package com.practice.e_commerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false, unique = true, length = 50)
  private String code;

  @NotBlank
  @Column(nullable = false, length = 255)
  private String name;

  @NotNull
  @Positive
  @Column(nullable = false)
  private Double price;

  @NotNull
  @Column(nullable = false)
  private Integer stock;

  @Column(columnDefinition = "TEXT")
  private String description;

  private Boolean deleted = false;
}

