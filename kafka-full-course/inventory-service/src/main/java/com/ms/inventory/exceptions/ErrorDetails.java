package com.ms.inventory.exceptions;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timestamp, String message, String errorCode, int statusCode) {
}
