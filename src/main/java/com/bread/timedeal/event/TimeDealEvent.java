package com.bread.timedeal.event;

public record TimeDealEvent(Long userId, Long productId, int count) {

}
