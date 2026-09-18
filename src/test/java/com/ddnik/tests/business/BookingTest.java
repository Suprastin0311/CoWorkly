package com.ddnik.tests.business;

import com.ddnik.exceptions.PriceCalculateException;
import com.ddnik.model.PriceCalculation;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingTest {

    private final PriceCalculation calc = new PriceCalculation();
    private final Logger logger = LoggerFactory.getLogger(BookingTest.class);

    //region Стандартные тесты
    @Test
    public void calculatePrice_ShouldReturnsExpectedPrice_OneHourWithoutMultiplier() {
        try {
            BigDecimal hourlyRate = new BigDecimal("100.00");
            BigDecimal multiplier = new BigDecimal("1.00");
            Timestamp start = Timestamp.valueOf("2026-09-01 10:00:00");
            Timestamp end = Timestamp.valueOf("2026-09-01 11:00:00");

            assertEquals(new BigDecimal("100.00"), calc.calculatePrice(hourlyRate, multiplier, start, end));
        } catch (PriceCalculateException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void calculatePrice_ShouldReturnsExpectedPrice_TwoHoursWithoutMultiplier() {
        try {
            BigDecimal hourlyRate = new BigDecimal("100.00");
            BigDecimal multiplier = new BigDecimal("1.00");
            Timestamp start = Timestamp.valueOf("2026-09-01 10:00:00");
            Timestamp end = Timestamp.valueOf("2026-09-01 12:00:00");

            assertEquals(new BigDecimal("200.00"), calc.calculatePrice(hourlyRate, multiplier, start, end));
        } catch (PriceCalculateException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void calculatePrice_ShouldReturnsExpectedPrice_OneHourWithMultiplier() {
        try {
            BigDecimal hourlyRate = new BigDecimal("100.00");
            BigDecimal multiplier = new BigDecimal("1.25");
            Timestamp start = Timestamp.valueOf("2026-09-01 10:00:00");
            Timestamp end = Timestamp.valueOf("2026-09-01 11:00:00");

            assertEquals(new BigDecimal("125.00"), calc.calculatePrice(hourlyRate, multiplier, start, end));
        } catch (PriceCalculateException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void calculatePrice_ShouldReturnsExpectedPrice_TwoHoursWithMultiplier() {
        try {
            BigDecimal hourlyRate = new BigDecimal("100.00");
            BigDecimal multiplier = new BigDecimal("1.25");
            Timestamp start = Timestamp.valueOf("2026-09-01 10:00:00");
            Timestamp end = Timestamp.valueOf("2026-09-01 12:00:00");

            assertEquals(new BigDecimal("250.00"), calc.calculatePrice(hourlyRate, multiplier, start, end));
        } catch (PriceCalculateException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void calculatePrice_ShouldReturnsExpectedPrice_OneHourTwentyThreeMinutesWithoutMultiplier() {
        try {
            BigDecimal hourlyRate = new BigDecimal("100.00");
            BigDecimal multiplier = new BigDecimal("1.00");
            Timestamp start = Timestamp.valueOf("2026-09-01 10:00:00");
            Timestamp end = Timestamp.valueOf("2026-09-01 11:23:00");

            assertEquals(new BigDecimal("138.33"), calc.calculatePrice(hourlyRate, multiplier, start, end));
        } catch (PriceCalculateException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void calculatePrice_ShouldReturnsExpectedPrice_OneHourTwentyThreeMinutesWithMultiplier() {
        try {
            BigDecimal hourlyRate = new BigDecimal("100.00");
            BigDecimal multiplier = new BigDecimal("1.25");
            Timestamp start = Timestamp.valueOf("2026-09-01 10:00:00");
            Timestamp end = Timestamp.valueOf("2026-09-01 11:23:00");

            assertEquals(new BigDecimal("172.92"), calc.calculatePrice(hourlyRate, multiplier, start, end));
        } catch (PriceCalculateException e) {
            logger.error(e.getMessage(), e);
        }
    }
    //endregion

    //region Тесты на пограничные значения
    @Test
    public void calculatePrice_ShouldReturnZero_HourlyRateIsZero() {
        try {
            BigDecimal hourlyRate = new BigDecimal("0.00");
            BigDecimal multiplier = new BigDecimal("1.00");
            Timestamp start = Timestamp.valueOf("2026-09-01 10:00:00");
            Timestamp end = Timestamp.valueOf("2026-09-01 11:00:00");

            assertEquals(new BigDecimal("0.00"), calc.calculatePrice(hourlyRate, multiplier, start, end));
        } catch (PriceCalculateException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void calculatePrice_ShouldReturnZero_MultiplierIsZero() {
        try {
            BigDecimal hourlyRate = new BigDecimal("100.00");
            BigDecimal multiplier = new BigDecimal("0.00");
            Timestamp start = Timestamp.valueOf("2026-09-01 10:00:00");
            Timestamp end = Timestamp.valueOf("2026-09-01 11:00:00");

            assertEquals(new BigDecimal("0.00"), calc.calculatePrice(hourlyRate, multiplier, start, end));
        } catch (PriceCalculateException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void calculatePrice_ShouldReturnZero_TimeIsZero() {
        try {
            BigDecimal hourlyRate = new BigDecimal("100.00");
            BigDecimal multiplier = new BigDecimal("1.00");
            Timestamp start = Timestamp.valueOf("2026-09-01 11:00:00");
            Timestamp end = Timestamp.valueOf("2026-09-01 11:00:00");

            assertEquals(new BigDecimal("0.00"), calc.calculatePrice(hourlyRate, multiplier, start, end));
        } catch (PriceCalculateException e) {
            logger.error(e.getMessage(), e);
        }
    }
    //endregion
}
