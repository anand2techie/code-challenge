package com.conichi.codingchallenge.controller;

import com.conichi.codingchallenge.apimodel.ErrorDTO;
import com.conichi.codingchallenge.apimodel.ErrorResponse;
import com.conichi.codingchallenge.apimodel.SimpleResponse;
import com.conichi.codingchallenge.entity.ExchangeRate;
import com.conichi.codingchallenge.model.CurrencyConversionRequestDTO;
import com.conichi.codingchallenge.model.CurrencyConversionResponseDTO;
import com.conichi.codingchallenge.repository.ExchangeRateRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class CurrencyControllerIT {

    private final String CURRENCY_CONVERSION_ENDPOINT = "/api/currency/convert";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Test
    public void givenCurrenciesWithAmount_whenconvert_thenStatus200()
        throws Exception {

        BigDecimal amount = new BigDecimal(100);
        CurrencyConversionRequestDTO currencyConversionRequestDTO =
            new CurrencyConversionRequestDTO(amount, "EUR", "INR");

        //perform post request for currency conversion
        MvcResult result = mockMvc.perform(
            post(CURRENCY_CONVERSION_ENDPOINT)
                .content(objectMapper.writeValueAsString(currencyConversionRequestDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

        CurrencyConversionResponseDTO currencyConversionResponseDTO = getDTOFromResponse(result);

        //get converted amount from db (expected) to be compared with actual from API response
        ExchangeRate exchangeRate = exchangeRateRepository.findByFromCurrencyAndToCurrency("EUR", "INR");
        BigDecimal convertedAmountFromDb = exchangeRate.getRate().multiply(amount);

        Assert.assertEquals(convertedAmountFromDb, currencyConversionResponseDTO.getAmount());
    }

    @Test
    public void givenInvalidCurrenciesWithAmount_whenconvert_thenStatus400()
        throws Exception {

        BigDecimal amount = new BigDecimal(100);
        CurrencyConversionRequestDTO currencyConversionRequestDTO =
            new CurrencyConversionRequestDTO(amount, "TAD", "INR");

        //perform post request for currency conversion
        MvcResult result = mockMvc.perform(
            post(CURRENCY_CONVERSION_ENDPOINT)
                .content(objectMapper.writeValueAsString(currencyConversionRequestDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

        ErrorResponse errorResponse = getDTOFromErrorResponse(result);
        System.out.println(errorResponse);
        List<ErrorDTO> errorDTOList = (List<ErrorDTO>) errorResponse.getErrors();

        Assert.assertEquals("Currency conversion not supported from: TAD to: INR", errorDTOList.get(0).getMessage());
    }

    @Test
    public void givenCurrenciesWithInvalidAmount_whenconvert_thenStatus422()
        throws Exception {

        BigDecimal amount = null;
        CurrencyConversionRequestDTO currencyConversionRequestDTO =
            new CurrencyConversionRequestDTO(amount, "EUR", "INR");

        //perform post request for currency conversion
        MvcResult result = mockMvc.perform(
            post(CURRENCY_CONVERSION_ENDPOINT)
                .content(objectMapper.writeValueAsString(currencyConversionRequestDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

        ErrorResponse errorResponse = getDTOFromErrorResponse(result);
        List<ErrorDTO> errorDTOList = (List<ErrorDTO>) errorResponse.getErrors();

        Assert.assertEquals("must not be null", errorDTOList.get(0).getMessage());
    }

    @Test
    public void givenEmptyCurrencies_whenconvert_thenStatus422()
        throws Exception {

        BigDecimal amount = new BigDecimal(100);
        CurrencyConversionRequestDTO currencyConversionRequestDTO =
            new CurrencyConversionRequestDTO(amount, "", "INR");

        //perform post request for currency conversion
        MvcResult result = mockMvc.perform(
            post(CURRENCY_CONVERSION_ENDPOINT)
                .content(objectMapper.writeValueAsString(currencyConversionRequestDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

        ErrorResponse errorResponse = getDTOFromErrorResponse(result);
        List<ErrorDTO> errorDTOList = (List<ErrorDTO>) errorResponse.getErrors();

        Assert.assertEquals("must not be empty", errorDTOList.get(0).getMessage());
    }

    private CurrencyConversionResponseDTO getDTOFromResponse(MvcResult result)
        throws IOException {
        SimpleResponse<CurrencyConversionResponseDTO> simpleResponse = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<SimpleResponse<CurrencyConversionResponseDTO>>() {
            });

        return simpleResponse.getData().get(0);
    }

    private ErrorResponse getDTOFromErrorResponse(MvcResult result)
        throws IOException {
        ErrorResponse errorResponse = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<ErrorResponse>() {
            });
        return errorResponse;
    }

}
