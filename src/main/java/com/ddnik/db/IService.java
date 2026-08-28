package com.ddnik.db;

import com.ddnik.db.dto.*;
import com.ddnik.db.entity.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface IService {

    /**
     * Создаёт нового пользователя.
     * @param newUser данные нового пользователя.
     * @return id созданной записи. Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     */
    Optional<Long> createUser(Users newUser) throws SQLException;

    /**
     * Создаёт бронь рабочего пространства.
     * @param booking данные брони.
     * @return id созданной брони. Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<Long> insertBooking(Bookings booking) throws SQLException, SecurityException;

    /**
     * Создаёт бронь рабочего пространства.
     * @param userId код регистрирующего бронь.
     * @param workspaceId код рабочего пространства.
     * @param startTime время начала.
     * @param endTime время окончания.
     * @param participantsCount количество людей.
     * @return id созданной брони. Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<Long> createBooking(long userId, long workspaceId, Timestamp startTime, Timestamp endTime,
                                 int participantsCount) throws SQLException, SecurityException;



    /**
     * Создаёт рабочее пространств.
     * @param workspace данные рабочего пространства.
     * @return id созданного рабочего пространства. Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<Long> insertWorkspace(Workspaces workspace) throws SQLException, SecurityException;

    /**
     * Безвозвратно удаляет рабочее пространство.
     * @param id код удаляемого рабочего пространства.
     * @return признак успеха выполнения операции:<br>
     *      <code>true</code> - рабочее пространство удалено успешно<br>
     *      <code>false</code> - не получилось удалить рабочее пространство<br>
     *      Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<Boolean> deleteWorkspace(long id) throws SQLException, SecurityException;

    /**
     * Отменяет бронь.
     * @param id идентификатор брони.
     * @return признак успеха выполнения операции:<br>
     *     <code>true</code> - бронь отменена успешно<br>
     *     <code>false</code> - не получилось отменить бронь<br>
     *     Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<Boolean> setBookingCancelled(long id) throws SQLException, SecurityException;

    /**
     * Подтверждает бронь.
     * @param id идентификатор брони.
     * @return признак успеха выполнения операции:<br>
     *     <code>true</code> - бронь подтверждена успешно<br>
     *     <code>false</code> - не получилось подтвердить бронь<br>
     *     Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<Boolean> confirmBooking(long id) throws SQLException, SecurityException;

    /**
     * Переключает статус активности рабочего пространства.
     * @param id идентификатор рабочего пространства.
     * @return признак успеха выполнения операции:<br>
     *     <code>true</code> - статус рабочего пространства изменён успешно<br>
     *     <code>false</code> - не получилось сменить статус рабочего пространства<br>
     *     Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<Boolean> toggleWorkspaceActiveStatus(long id) throws SQLException, SecurityException;

    /**
     * Переключает статус активности пользователя (разблокировать, заблокировать).
     * @param id код пользователя.
     * @return признак успеха выполнения операции:<br>
     *     <code>true</code> - статус пользователя изменён успешно<br>
     *     <code>false</code> - не получилось сменить статус пользователя<br>
     *     Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<Boolean> toggleUserActiveStatus(long id) throws SQLException, SecurityException;

     /**
     * Обновляет рабочее пространство.
     * @param workspace обновлённые данные рабочего пространства. Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @return признак успеха выполнения операции:<br>
     *      *     <code>true</code> - рабочее пространство обновлено<br>
     *      *     <code>false</code> - не удалось обновить рабочее пространство
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<Boolean> updateWorkspace(Workspaces workspace) throws SQLException, SecurityException;

    /**
     * Извлекает данные пользователя по email
     * @param email строка, содержащая email пользователя
     * @return данные пользователя. Возвращает <c>Optional.empty()</c>, если пользователь не найден.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<UsersDto> getUserByEmail(String email) throws SQLException, SecurityException;

    /**
     * Извлекает список пользователей по email. В качестве email можно использовать подстроку.
     * @param email предполагаемый email пользователя.
     * @return список пользователей.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<UsersDto> getUsersByEmail(String email) throws SQLException, SecurityException;

    /**
     * Извлекает список пользователей по роли.
     * @param role роль пользователя.
     * @return список пользователей.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<UsersDto> getUsersByRole(UserRolesDto role) throws SQLException, SecurityException;

    /**
     * Извлекает список пользователей по роли.
     * @param minDate левая граница диапазона.
     * @param maxDate правая граница диапазона.
     * @return список пользователей.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<UsersDto> getUsersByCreatedAt(Date minDate, Date maxDate) throws SQLException, SecurityException;

    /**
     * Извлекает список пользователей по статусу.
     * @param is_active статус пользователя:<br>
     *                  <code>true</code> - активен<br>
     *                  <code>false</code> - заблокирован
     * @return список пользователей.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<UsersDto> getUsersByStatus(boolean is_active) throws SQLException, SecurityException;

    /**
     * Извлекает список пользователей по ФИО.
     * @param name подстрока, входящая в ФИО.
     * @return список пользователей.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<UsersDto> getUsersByName(String name) throws SQLException, SecurityException;

    /**
     * Извлекает список всех статусов пользователей.
     * @return все статусы пользователей.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<UserRolesDto> getUserRoles() throws SQLException, SecurityException;

    /**
     * Получает данные рабочего пространства по id.
     * @param id код рабочего пространства.
     * @return данные рабочего пространства. Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<WorkspaceDto> getWorkspaceById(long id) throws SQLException, SecurityException;

    /**
     * Получает рабочие пространства по вместимости.
     * @param capacity вместимость рабочего пространства.
     * @return список рабочих пространств.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<WorkspaceDto> getWorkspacesByCapacity(int capacity) throws SQLException, SecurityException;

    /**
     * Получает рабочие пространства по вместимости.
     * @param minRate минимальное значение часовой стоимости.
     * @param maxRate максимальное значение часовой стоимости.
     * @return список рабочих пространств.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<WorkspaceDto> getWorkspacesByHourlyRate(BigDecimal minRate, BigDecimal maxRate) throws SQLException, SecurityException;

    /**
     * Получает рабочие пространства по имени.
     * @param name имя рабочего пространства.
     * @return список рабочих пространств.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<WorkspaceDto> getWorkspacesByName(String name) throws SQLException, SecurityException;

    /**
     * Получает рабочие пространства статусу.
     * @param is_active статус рабочего пространства:<br>
     *              <code>true</code> - активно<br>
     *              <code>false</code> - неактивно
     * @return список рабочих пространств.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<WorkspaceDto> getWorkspacesByStatus(boolean is_active) throws SQLException, SecurityException;

    /**
     * Получает рабочие пространства типу.
     * @param id код типа рабочего пространства.
     * @return список рабочих пространств.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<WorkspaceDto> getWorkspacesByType(long id) throws SQLException, SecurityException;

    /**
     * Получает рабочие пространства, доступные к бронированию согласно указанным параметрам.
     * @param startTime время начала брони
     * @param endTime время окончания брони
     * @param workspaceTypeId код типа рабочего пространства
     * @param participantsCount количество людей
     * @return список доступных для бронирования рабочих пространств.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<WorkspaceAvailableDto> getWorkspacesAvailableForBooking(Timestamp startTime, Timestamp endTime, long workspaceTypeId, int participantsCount) throws SQLException, SecurityException;


    /**
     * Получает список всех броней.
     * @return список броней.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<BookingDto> getBookings() throws SQLException, SecurityException;

    /**
     * Получает список броней указанного пользователя.
     * @param id код пользователя.
     * @return список броней.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<BookingDto> getBookingsByUserId(long id) throws SQLException, SecurityException;

    /**
     * Получает список броней указанного рабочего пространства.
     * @param workspace рабочее пространство
     * @return список броней.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<BookingDto> getBookingsByWorkspaceId(WorkspaceDto workspace) throws SQLException, SecurityException;

    /**
     * Получает список броней с указанным статусом.
     * @param status статус брони.
     * @return список броней.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<BookingDto> getBookingsByStatus(BookingStatusesDto status) throws SQLException, SecurityException;

    /**
     * Получает список броней пользователя с фильтром по времени бронирования.
     * @param minDate левая граница диапазона.
     * @param maxDate правая граница диапазона.
     * @return список броней.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<BookingDto> getBookingsByCreatedAt(Date minDate, Date maxDate) throws SQLException, SecurityException;

    /**
     * Получает список броней пользователя с фильтром по времени.
     * @param user_id код пользователя.
     * @param start время начала брони.
     * @param end время окончания брони.
     * @return список броней.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<BookingDto> getUserBookingsByTime(long user_id, Date start, Date end) throws SQLException, SecurityException;

    /**
     * Получает справочник типов рабочего пространства.
     * @return список типов рабочего пространства.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<WorkspaceTypesDto> getWorkspaceTypes() throws SQLException, SecurityException;

    /**
     * Получает справочник статусов броней.
     * @return список статусов броней.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     * @throws SecurityException если у пользователя недостаточно прав доступа.
     */
    List<BookingStatusesDto> getBookingStatuses() throws SQLException, SecurityException;
}
