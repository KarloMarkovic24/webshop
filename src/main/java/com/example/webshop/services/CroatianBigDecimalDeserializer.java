package com.example.webshop.services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CroatianBigDecimalDeserializer extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException {
        String convertedString = jsonparser.getText().replace(',', '.');

        return new BigDecimal(convertedString).setScale(6, RoundingMode.HALF_UP);
    }
}