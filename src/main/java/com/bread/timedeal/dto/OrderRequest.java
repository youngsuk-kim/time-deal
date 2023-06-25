package com.bread.timedeal.dto;

public record OrderRequest (

  Long userId,
  Long productId,

  int count
) {}
