package com.conichi.codingchallenge.apimodel;

import lombok.Value;

import java.io.Serializable;

@Value
public final class HateoasLink implements Serializable {
    String rel;
    String href;
}
