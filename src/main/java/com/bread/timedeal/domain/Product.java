package com.bread.timedeal.domain;

import com.bread.timedeal.dto.ProductUpdateRequest;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

  public Product(Long id, String name, Stock stock, TimeSale timeSale) {
    this.id = id;
    this.name = name;
    this.stock = stock;
    this.timeSale = timeSale;
  }

  public Product(Stock stock, TimeSale timeSale, String name) {
    this.stock = stock;
    this.timeSale = timeSale;
    this.name = name;
  }

  public Product() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @Embedded
  private Stock stock = new Stock(1);

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
  private TimeSale timeSale;

  @Version
  private Long version;

  @Column
  private Boolean isDeleted = false;

  public Product add(Stock stock) {
    this.stock.plus(stock);
    return this;
  }

  public int count() {
    return this.stock.count();
  }

  public Product decrease(Stock stock, LocalDateTime now) {
    if (this.timeSale.timeOver(now)) {
      throw new RuntimeException("할인 기간이 종료 된 상품 입니다.");
    }

    if (this.count() < 1) {
      throw new RuntimeException("재고가 부족 합니다.");
    }

    this.stock.minus(stock);

    return this;
  }

  public LocalDateTime endTime() {
    return timeSale.getSaleEndTime();
  }

  public void delete() {
    this.isDeleted = true;
  }

  public Product update(ProductUpdateRequest request) {
    if (request.name() != null) {
      this.name = request.name();
    }

    if (request.time() != null) {
      this.timeSale = new TimeSale(request.time());
    }

    if (request.stock() != null) {
      this.stock = new Stock(request.stock());
    }

    return this;
  }

  public String getName() {
    return this.name;
  }

  public Long getId() {
    return this.id;
  }

}
