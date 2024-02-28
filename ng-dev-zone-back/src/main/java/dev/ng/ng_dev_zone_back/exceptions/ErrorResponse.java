package dev.ng.ng_dev_zone_back.exceptions;

import org.springframework.http.HttpStatus;

public record ErrorResponse(HttpStatus status, String message) {
}
