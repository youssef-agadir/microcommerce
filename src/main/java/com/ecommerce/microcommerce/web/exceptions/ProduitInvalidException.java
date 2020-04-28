package com.ecommerce.microcommerce.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class ProduitInvalidException extends RuntimeException {
    public ProduitInvalidException(String s) {
        super(s);
    }
}
