package com.ddnik.db;

import com.ddnik.db.dto.*;
import com.ddnik.db.entity.*;
import com.ddnik.exceptions.UserRoleSecurityException;
import com.ddnik.model.Filters;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Реализует бизнес логику между интерфейсом и репозиторием.
 */
public interface IService {

    /**
     * Создаёт нового пользователя.
     * @param newUser данные нового пользователя.
     * @return id созданной записи. Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     */
    Optional<Long> createUser(Users newUser);

    /**
     * Создаёт бронь рабочего пространства.
     * @param userId код пользователя.
     * @param workspace данные рабочего пространства.
     * @param filters данные бронирования.
     * @return id созданной брони. Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<Long> createBooking(long userId, WorkspaceDto workspace, Filters filters) throws UserRoleSecurityException;

    /**
     * Создаёт рабочее пространств.
     * @param workspace данные рабочего пространства.
     * @return id созданного рабочего пространства. Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<Long> insertWorkspace(Workspaces workspace) throws UserRoleSecurityException;

    /**
     * Безвозвратно удаляет рабочее пространство.
     * @param id код удаляемого рабочего пространства.
     * @return признак успеха выполнения операции:<br>
     *      <code>true</code> - рабочее пространство удалено успешно<br>
     *      <code>false</code> - не получилось удалить рабочее пространство<br>
     *      Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<Boolean> deleteWorkspace(long id) throws UserRoleSecurityException;

    /**
     * Отменяет бронь.
     * @param id идентификатор брони.
     * @return признак успеха выполнения операции:<br>
     *     <code>true</code> - бронь отменена успешно<br>
     *     <code>false</code> - не получилось отменить бронь<br>
     *     Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<Boolean> setBookingCancelled(long id) throws UserRoleSecurityException;

    /**
     * Подтверждает бронь.
     * @param id идентификатор брони.
     * @return признак успеха выполнения операции:<br>
     *     <code>true</code> - бронь подтверждена успешно<br>
     *     <code>false</code> - не получилось подтвердить бронь<br>
     *     Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<Boolean> confirmBooking(long id) throws UserRoleSecurityException;

    /**
     * Переключает статус активности рабочего пространства.
     * @param id идентификатор рабочего пространства.
     * @return признак успеха выполнения операции:<br>
     *     <code>true</code> - статус рабочего пространства изменён успешно<br>
     *     <code>false</code> - не получилось сменить статус рабочего пространства<br>
     *     Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<Boolean> toggleWorkspaceActiveStatus(long id) throws UserRoleSecurityException;

    /**
     * Переключает статус активности пользователя (разблокировать, заблокировать).
     * @param id код пользователя.
     * @return признак успеха выполнения операции:<br>
     *     <code>true</code> - статус пользователя изменён успешно<br>
     *     <code>false</code> - не получилось сменить статус пользователя<br>
     *     Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<Boolean> toggleUserActiveStatus(long id) throws UserRoleSecurityException;

     /**
     * Обновляет рабочее пространство.
     * @param workspace обновлённые данные рабочего пространства. Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @return признак успеха выполнения операции:<br>
     *      *     <code>true</code> - рабочее пространство обновлено<br>
     *      *     <code>false</code> - не удалось обновить рабочее пространство
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<Boolean> updateWorkspace(Workspaces workspace) throws UserRoleSecurityException;

    /**
     * Извлекает данные пользователя по email
     * @param email строка, содержащая email пользователя
     * @return данные пользователя. Возвращает <c>Optional.empty()</c>, если пользователь не найден.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<UsersDto> getUserByEmail(String email) throws UserRoleSecurityException;

    /**
     * Извлекает список пользователей по email. В качестве email можно использовать подстроку.
     * @param email предполагаемый email пользователя.
     * @return список пользователей.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<UsersDto> getUsersByEmail(String email) throws UserRoleSecurityException;

    /**
     * Извлекает список пользователей по роли.
     * @param role роль пользователя.
     * @return список пользователей.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<UsersDto> getUsersByRole(UserRolesDto role) throws UserRoleSecurityException;

    /**
     * Извлекает список пользователей по роли.
     * @param minDate левая граница диапазона.
     * @param maxDate правая граница диапазона.
     * @return список пользователей.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<UsersDto> getUsersByCreatedAt(Date minDate, Date maxDate) throws UserRoleSecurityException;

    /**
     * Извлекает список пользователей по статусу.
     * @param is_active статус пользователя:<br>
     *                  <code>true</code> - активен<br>
     *                  <code>false</code> - заблокирован
     * @return список пользователей.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<UsersDto> getUsersByStatus(boolean is_active) throws UserRoleSecurityException;

    /**
     * Извлекает список пользователей по ФИО.
     * @param name подстрока, входящая в ФИО.
     * @return список пользователей.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<UsersDto> getUsersByName(String name) throws UserRoleSecurityException;

    /**
     * Извлекает список всех статусов пользователей.
     * @return все статусы пользователей.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<UserRolesDto> getUserRoles() throws UserRoleSecurityException;

    /**
     * Получает данные рабочего пространства по id.
     * @param id код рабочего пространства.
     * @return данные рабочего пространства. Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    Optional<WorkspaceDto> getWorkspaceById(long id) throws UserRoleSecurityException;

    /**
     * Получает рабочие пространства.
     * @return список рабочих пространств.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<WorkspaceDto> getWorkspaces() throws UserRoleSecurityException;

    /**
     * Получает рабочие пространства по вместимости.
     * @param capacity вместимость рабочего пространства.
     * @return список рабочих пространств.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<WorkspaceDto> getWorkspacesByCapacity(int capacity) throws UserRoleSecurityException;

    /**
     * Получает рабочие пространства по вместимости.
     * @param minRate минимальное значение часовой стоимости.
     * @param maxRate максимальное значение часовой стоимости.
     * @return список рабочих пространств.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<WorkspaceDto> getWorkspacesByHourlyRate(BigDecimal minRate, BigDecimal maxRate) throws UserRoleSecurityException;

    /**
     * Получает рабочие пространства по имени.
     * @param name имя рабочего пространства.
     * @return список рабочих пространств.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<WorkspaceDto> getWorkspacesByName(String name) throws UserRoleSecurityException;

    /**
     * Получает рабочие пространства статусу.
     * @param is_active статус рабочего пространства:<br>
     *              <code>true</code> - активно<br>
     *              <code>false</code> - неактивно
     * @return список рабочих пространств.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<WorkspaceDto> getWorkspacesByStatus(boolean is_active) throws UserRoleSecurityException;

    /**
     * Получает рабочие пространства типу.
     * @param id код типа рабочего пространства.
     * @return список рабочих пространств.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<WorkspaceDto> getWorkspacesByType(long id) throws UserRoleSecurityException;

    /**
     * Получает рабочие пространства, доступные к бронированию согласно указанным параметрам.
     * @param startTime время начала брони
     * @param endTime время окончания брони
     * @param workspaceTypeId код типа рабочего пространства
     * @param participantsCount количество людей
     * @return список доступных для бронирования рабочих пространств.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<WorkspaceAvailableDto> getWorkspacesAvailableForBooking(Timestamp startTime, Timestamp endTime, long workspaceTypeId, int participantsCount) throws UserRoleSecurityException;


    /**
     * Получает список всех броней.
     * @return список броней.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<BookingDto> getBookings() throws UserRoleSecurityException;

    /**
     * Получает список броней указанного пользователя.
     * @param id код пользователя.
     * @return список броней.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<BookingDto> getBookingsByUserId(long id) throws UserRoleSecurityException;

    /**
     * Получает список броней указанного рабочего пространства.
     * @param workspace рабочее пространство
     * @return список броней.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<BookingDto> getBookingsByWorkspaceId(WorkspaceDto workspace) throws UserRoleSecurityException;

    /**
     * Получает список броней указанного рабочего пространства.
     * @param workspace рабочее пространство
     * @return список броней.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<BookingDto> getBookingsByWorkspaceId(long userId, WorkspaceDto workspace) throws UserRoleSecurityException;

    /**
     * Получает список броней с указанным статусом.
     * @param status статус брони.
     * @return список броней.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<BookingDto> getBookingsByStatus(BookingStatusesDto status) throws UserRoleSecurityException;

    /**
     * Получает список броней с фильтром по статусу и коду пользователя.
     * @param userId код пользователя.
     * @param status статус.
     * @return список бронирований.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<BookingDto> getBookingsByStatus(long userId, BookingStatusesDto status) throws UserRoleSecurityException;

    /**
     * Получает список броней пользователя с фильтром по времени бронирования.
     * @param minDate левая граница диапазона.
     * @param maxDate правая граница диапазона.
     * @return список броней.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<BookingDto> getBookingsByCreatedAt(Date minDate, Date maxDate) throws UserRoleSecurityException;

    /**
     * Получает список броней пользователя с фильтром по времени бронирования и коду пользователя.
     * @param userId код пользователя.
     * @param minDate левая граница диапазона.
     * @param maxDate правая граница диапазона.
     * @return список броней.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<BookingDto> getBookingsByCreatedAt(long userId, Date minDate, Date maxDate) throws UserRoleSecurityException;


    /**
     * Получает список бронирований, ожидающий подтверждения.
     * @return список бронирований.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<BookingDto> getBookingsPendingPayment() throws UserRoleSecurityException;

    /**
     * Получает список бронирований, ожидающий подтверждения.
     * @param userId код пользователя.
     * @return список бронирований.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<BookingDto> getBookingsPendingPayment(long userId) throws UserRoleSecurityException;

    /**
     * Получает справочник типов рабочего пространства.
     * @return список типов рабочего пространства.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<WorkspaceTypesDto> getWorkspaceTypes() throws UserRoleSecurityException;

    /**
     * Получает справочник статусов броней.
     * @return список статусов броней.
     * @throws UserRoleSecurityException если у пользователя недостаточно прав доступа.
     */
    List<BookingStatusesDto> getBookingStatuses() throws UserRoleSecurityException;
}
