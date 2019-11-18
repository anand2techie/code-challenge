package com.conichi.codingchallenge.controller;

import com.conichi.codingchallenge.apimodel.SimpleResponse;
import com.conichi.codingchallenge.model.CurrencyConversionRequestDTO;
import com.conichi.codingchallenge.model.CurrencyConversionResponseDTO;
import com.conichi.codingchallenge.service.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    @Autowired
    private CurrencyConversionService currencyConversionService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/convert")
    public SimpleResponse<CurrencyConversionResponseDTO> convert(
        @RequestBody @Valid CurrencyConversionRequestDTO currencyConversionRequestDTO) {
        return new SimpleResponse(new CurrencyConversionResponseDTO(currencyConversionService
            .convert(currencyConversionRequestDTO.getAmount(), currencyConversionRequestDTO.getFrom(),
                currencyConversionRequestDTO.getTo())));
    }
}