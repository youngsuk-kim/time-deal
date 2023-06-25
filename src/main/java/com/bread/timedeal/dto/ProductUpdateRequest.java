package com.bread.timedeal.dto;

import static com.bread.timedeal.Constants.DATE_TIME_FORMAT;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public record ProductUpdateRequest(
    Integer stock,
    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
    LocalDateTime time,
    String name
) {

}
