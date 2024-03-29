package com.conichi.codingchallenge.apimodel;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

/**
 * Base for JSON API responses with optional warnings.
 *
 * @see <a href="http://jsonapi.org">json:apimodel</a>
 */
@Data
@FieldDefaults(level = PRIVATE)
public abstract class Response implements Serializable {

    final List<HateoasLink> links;
}
