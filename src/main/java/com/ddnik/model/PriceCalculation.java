package com.ddnik.model;

import com.ddnik.exceptions.PriceCalculateException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Рассчитывает стоимость бронирования.
 */
public class PriceCalculation {

    /**
     * Рассчитать стоимость бронирования.
     * @param hourlyRate часовая стоимость рабочего пространства.
     * @param multiplier множитель стоимости.
     * @param start дата и время начала бронирования.
     * @param end дата и время окончания бронирования.
     * @return стоимость бронирования.
     * @throws PriceCalculateException в случае ошибки при выполнении расчётов.
     */
    public BigDecimal calculatePrice(BigDecimal hourlyRate, BigDecimal multiplier, Timestamp start, Timestamp end) throws PriceCalculateException {
        // Проверка на то, что параметры не null
        try {
            Objects.requireNonNull(hourlyRate);
            Objects.requireNonNull(multiplier);
            Objects.requireNonNull(start);
            Objects.requireNonNull(end);
        } catch (NullPointerException e) {
            throw new PriceCalculateException("Ошибка во время расчёта стоимости.", e);
        }

        // Расчёт продолжительности в миллисекундах
        long duration = end.getTime() - start.getTime();
        if (duration <= 0) {
            throw new PriceCalculateException("Время окончания должно быть позже времени начала.");
        }

        // Расчёт количества минут
        long minutes = Math.ceilDiv(duration, 60_000L);

        return hourlyRate
                .multiply(multiplier)
                .multiply(BigDecimal.valueOf(minutes))
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
    }
}
