package com.bread.timedeal.dto;

import java.time.LocalDateTime;

public record ProductResponse(
    Long id,

    String name,

    int stock,

    LocalDateTime timeSale
) {

}
