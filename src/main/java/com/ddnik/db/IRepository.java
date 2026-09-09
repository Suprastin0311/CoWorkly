package com.ddnik.db;

import com.ddnik.db.dto.*;
import com.ddnik.db.entity.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * Реализует хранимые функции базы данных.
 */
public interface IRepository {

    /**
     * Создать пользователя.
     * @param user данные нового пользователя.
     * @return код нового пользователя. Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     */
    Optional<Long> insertUser(Users user);

    /**
     * Создать бронирование.
     * @param userId код пользователя.
     * @param workspaceId код бронируемого рабочего пространства.
     * @param start дата и время начала брони.
     * @param end дата и время окончания брони.
     * @param participantsCount количество человек, занимающих рабочее пространство.
     * @param price стоимость бронирования.
     * @return код нового бронирования. Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     */
    Optional<Long> insertBooking(long userId, long workspaceId, Timestamp start, Timestamp end, int participantsCount, BigDecimal price);

    /**
     * Создать рабочее пространство.
     * @param workspace данные нового рабочего пространства.
     * @return код нового рабочего пространства. Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     */
    Optional<Long> insertWorkspace(Workspaces workspace);

    /**
     * Отменить бронирование.
     * @param id код бронирования.
     * @return признак успешности выполнения операции.<br>
     * <code>true</code> - бронирование отменено успешно.<br>
     * <code>false</code> - не удалось отменить бронирование.<br>
     * Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     */
    Optional<Boolean> setBookingCancelled(long id);

    /**
     * Подтвердить (оплатить) бронирование.
     * @param id код бронирования.
     * @return признак успешности выполнения операции.<br>
     * <code>true</code> - бронирование подтверждено успешно.<br>
     * <code>false</code> - не удалось подтвердить бронирование.<br>
     * Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     */
    Optional<Boolean> confirmBooking(long id);

    /**
     * Переключить статус рабочего пространства.
     * @param id код рабочего пространства.
     * @return признак успешности выполнения операции.<br>
     * <code>true</code> - статус изменён успешно.<br>
     * <code>false</code> - не удалось изменить статус.<br>
     * Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     */
    Optional<Boolean> toggleWorkspaceActiveStatus(long id);

    /**
     * Обновить рабочее пространство.
     * @param workspace обновлённые данные рабочего пространства.
     * @return признак успешности выполнения операции.<br>
     * <code>true</code> - рабочее пространство обновлено успешно.<br>
     * <code>false</code> - не удалось обновить рабочее пространство.<br>
     * Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     */
    Optional<Boolean> updateWorkspace(Workspaces workspace);

    /**
     * Удалить рабочее пространство.
     * @param id код рабочего пространства.
     * @return признак успешности выполнения операции.
     * <code>true</code> - рабочее пространство удалено успешно.<br>
     * <code>false</code> - не удалось удалить рабочее пространство.<br>
     * Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     */
    Optional<Boolean> deleteWorkspace(long id);

    /**
     * Получить данные пользователя по email для авторизации.
     * @param email email пользователя.
     * @return данные пользователя. Возвращает {@link Optional#empty()}, если пользователь не найден.
     */
    Optional<UsersDto> getUserAuth(String email);

    /**
     * Получить список пользователей по id.
     * @param id код пользователя.
     * @return список найденных пользователей.
     */
    List<UsersDto> getUsersById(long id);

    /**
     * Получить список пользователей по email.
     * @param email код пользователя.
     * @return список найденных пользователей.
     */
    List<UsersDto> getUsersByEmail(String email);

    /**
     * Получить список пользователей по коду роли.
     * @param id код роли пользователя.
     * @return список найденных пользователей.
     */
    List<UsersDto> getUsersByRole(long id);

    /**
     * Получить список пользователей по дате создания.
     * @param minDate левая граница фильтра даты создания.
     * @param maxDate правая граница фильтра даты создания.
     * @return список найденных пользователей.
     */
    List<UsersDto> getUsersByCreatedAt(Date minDate, Date maxDate);

    /**
     * Получить список пользователей по статусу.
     * @param is_active статус пользователя:<br>
     *                  <code>true</code> - актвивен.<br>
     *                  <code>false</code> - заблокирован
     * @return список найденных пользователей.
     */
    List<UsersDto> getUsersByStatus(boolean is_active);

    /**
     * Получить список пользователей по ФИО.
     * @param name подстрока в ФИО.
     * @return список найденных пользователей.
     */
    List<UsersDto> getUsersByName(String name);

    /**
     * Переключить статус пользователя. Заблокировать пользователя, если он активен, разблокировать, если он заблокирован.
     * @param id код пользователя.
     * @return признак успешности выполнения операции.
     * <code>true</code> - статус изменён успешно.<br>
     * <code>false</code> - не удалось изменить статус.<br>
     * Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     */
    Optional<Boolean> toggleUserActiveStatus(long id);

    /**
     * Получить список ролей пользователя.
     * @return список ролей пользователя.
     */
    List<UserRolesDto> getUserRoles();

    /**
     * Получить рабочее пространство по коду.
     * @param id код рабочего пространства.
     * @return рабочее пространство. Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     */
    Optional<WorkspaceDto> getWorkspaceById(long id);

    /**
     * Получить список всех рабочих пространств.
     * @return список рабочих пространств.
     */
    List<WorkspaceDto> getWorkspaces();

    /**
     * Получить список рабочих пространств с отбором по вместимости.
     * @param capacity вместимость.
     * @return список рабочих пространств.
     */
    List<WorkspaceDto> getWorkspacesByCapacity(int capacity);

    /**
     * Получить список рабочих пространств с отбором по часовой стоимости.
     * @param minRate левая граница фильтра.
     * @param maxRate правая граница фильтра.
     * @return список рабочих пространств.
     */
    List<WorkspaceDto> getWorkspacesByHourlyRate(BigDecimal minRate, BigDecimal maxRate);

    /**
     * Получить список рабочих пространств с отбором по названию.
     * @param name название.
     * @return список рабочих пространств.
     */
    List<WorkspaceDto> getWorkspacesByName(String name);

    /**
     * Получить список рабочих пространств с отбором по статусу.
     * @param is_active активно ли рабочее пространство.
     * @return список рабочих пространств.
     */
    List<WorkspaceDto> getWorkspacesByStatus(boolean is_active);

    /**
     * Получить список рабочих пространств с отбором по типу.
     * @param typeId код типа.
     * @return список рабочих пространств.
     */
    List<WorkspaceDto> getWorkspacesByType(long typeId);

    /**
     * Получить список рабочих пространств доступных для бронирования.
     * @param startTime дата и время начала бронирования.
     * @param endTime дата и время окончания бронирования.
     * @param workspaceTypeId код типа рабочего пространства.
     * @param participantsCount количество посетителей.
     * @return список рабочих пространств.
     */
    List<WorkspaceAvailableDto> getWorkspacesAvailableForBooking(Timestamp startTime, Timestamp endTime, long workspaceTypeId, int participantsCount);

    /**
     * Получить список всех бронирований.
     * @return список бронирований.
     */
    List<BookingDto> getBookings();

    /**
     * Получить список бронирований с отбором по коду.
     * @param id код бронирования.
     * @return список бронирований.
     */
    List<BookingDto> getBookingsByUserId(long id);

    /**
     * Получить список бронирований с отбором по рабочему пространству.
     * @param id код рабочего пространства.
     * @return список бронирований.
     */
    List<BookingDto> getBookingsByWorkspaceId(long id);

    /**
     * Получить список бронирований с отбором по рабочему пространству и коду пользователя.
     * @param userId код пользователя.
     * @param workspaceId код рабочего пространства.
     * @return список бронирований.
     */
    List<BookingDto> getBookingsByWorkspaceId(long userId, long workspaceId);

    /**
     * Получить список бронирований с отбором по статусу.
     * @param id код статуса бронирования.
     * @return список бронирований.
     */
    List<BookingDto> getBookingsByStatus(long id);

    /**
     * Получить список бронирований с отбором по статусу и коду пользователя.
     * @param userId код пользователя.
     * @param statusId код статуса бронирования.
     * @return список бронирований.
     */
    List<BookingDto> getBookingsByStatus(long userId, long statusId);

    /**
     * Получить список бронирований с отбором по дате бронирования.
     * @param minDate левая граница фильтра по дате.
     * @param maxDate правая граница фильтра по дате.
     * @return список бронирований.
     */
    List<BookingDto> getBookingsByCreatedAt(Date minDate, Date maxDate);

    /**
     * Получить список бронирований с отбором по дате бронирования и коду пользователя.
     * @param userId код пользователя.
     * @param minDate левая граница фильтра по дате.
     * @param maxDate правая граница фильтра по дате.
     * @return список бронирований.
     */
    List<BookingDto> getBookingsByCreatedAt(long userId, Date minDate, Date maxDate);

    /**
     * Получить список бронирований, ожидающих подтверждения.
     * @return список бронирований.
     */
    List<BookingDto> getBookingsPendingPayment();

    /**
     * Получить список бронирований пользователя, ожидающих подтверждения.
     * @param userId код пользователя.
     * @return список бронирований.
     */
    List<BookingDto> getBookingsPendingPayment(long userId);

    /**
     * Получить список типов рабочих пространств.
     * @return список типов рабочих пространств.
     */
    List<WorkspaceTypesDto> getWorkspaceTypes();

    /**
     * Получить список статусов бронирования.
     * @return список статусов бронирования.
     */
    List<BookingStatusesDto> getBookingStatuses();

    /**
     * Получить тариф рабочего пространства.
     * @param id код рабочего пространства.
     * @return тариф рабочего пространства. Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     */
    Optional<Tariffs> getTariffByWorkspaceTypeId(long id);
}
