package com.ddnik.db;

import com.ddnik.PasswordHasher;
import com.ddnik.db.dto.*;
import com.ddnik.db.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Service implements IService {

    private static final Logger logger = LoggerFactory.getLogger(Service.class);
    private IRepository repo;

    public Service() {
        repo = new Repository();
    }

    public Optional<Long> createUser(Users newUser) throws SQLException {
        String hashedPassword = PasswordHasher.hashPassword(newUser.password());

        // Подготовленная запись с данными пользователя к сохранению в БД
        Users preparedUsersEntity = new Users(
                newUser.id(),
                newUser.email(),
                hashedPassword,
                newUser.fullName(),
                newUser.role(),
                newUser.isBlocked(),
                newUser.createdAt()
        );

        try {
            Optional<Long> newRecordId = repo.insertUser(preparedUsersEntity);
            if (newRecordId.isPresent()) {
                logger.debug("Создан новый пользователь с id {}", newRecordId.get());
                return newRecordId;
            }
            else {
                logger.debug("Новый пользователь не создан");
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public Optional<UsersDto> getUserByEmail(String email) throws SQLException {
        try {
            Optional<UsersDto> user = repo.getUserByEmail(email);
            if (user.isPresent()) {
                logger.debug("Получены данные пользователя по email: {}", email);
                return user;
            }
            else {
                logger.debug("Пользователь по email {} не найден", email);
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<UsersDto> getUsersByEmail(String email) throws SQLException, SecurityException {
        List<UsersDto> users = repo.getUsersByEmail(email);
        logger.debug("Получено {} пользователей с входящей в email подстрокой {}.", users.size(), email);
        return users;
    }

    public List<UsersDto> getUsersByRole(UserRolesDto role) throws SQLException, SecurityException {
        List<UsersDto> users = repo.getUsersByRole(role.id());
        logger.debug("Получено {} пользователей с ролью {}.", users.size(), role.name());
        return users;
    }

    public List<UsersDto> getUsersByCreatedAt(Date minDate, Date maxDate) throws SQLException, SecurityException {
        List<UsersDto> users = repo.getUsersByCreatedAt(minDate, maxDate);
        logger.debug("Получено {} пользователей, зарегистрированных во временном промежутке с {} по {}.",
                users.size(), minDate, maxDate);
        return users;
    }

    public List<UsersDto> getUsersByStatus(boolean is_active) throws SQLException, SecurityException {
        List<UsersDto> users = repo.getUsersByStatus(is_active);
        logger.debug("Получено {} {} пользователей.",
                users.size(), is_active ? "активированных" : "заблокированных");
        return users;
    }

    public List<UsersDto> getUsersByName(String name) throws SQLException, SecurityException {
        List<UsersDto> users = repo.getUsersByName(name);
        logger.debug("Получено {} пользователей с входящей в ФИО подстрокой {}.", users.size(), name);
        return users;
    }

    public List<UserRolesDto> getUserRoles() throws SQLException, SecurityException {
        List<UserRolesDto> userRoles = repo.getUserRoles();
        logger.debug("Получено {} статусов пользователей", userRoles.size());
        return userRoles;
    }

    public Optional<WorkspaceDto> getWorkspaceById(long id) throws SQLException, SecurityException {
        try {
            Optional<WorkspaceDto> workspace = repo.getWorkspaceById(id);
            if (workspace.isPresent()) {
                logger.debug("Получено рабочее пространство по id {}", id);
                return workspace;
            }
            else {
                logger.debug("Не найдено рабочее пространство с id {}", id);
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public Optional<Long> insertBooking(Bookings booking) throws SQLException, SecurityException {
        try {
            Optional<Long> bookingId = repo.insertBooking(booking);
            if (bookingId.isPresent()) {
                logger.debug("Создана новая бронь с id {}", bookingId);
                return bookingId;
            }
            else {
                logger.debug("Не удалось создать новую бронь с id {}", bookingId);
                return Optional.empty();
            }
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    public Optional<Long> insertWorkspace(Workspaces workspace) throws SQLException, SecurityException {
        try {
            Optional<Long> workspaceId = repo.insertWorkspace(workspace);
            if (workspaceId.isPresent()) {
                logger.debug("Создано новое рабочее пространство с id {}", workspaceId);
                return workspaceId;
            }
            else {
                logger.debug("Не удалось создать новое рабочее пространство с id {}", workspaceId);
                return Optional.empty();
            }
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    public Optional<Boolean> deleteWorkspace(long id) throws SQLException, SecurityException {
        try {
            Optional<Boolean> result = repo.deleteWorkspace(id);
            if (result.isPresent()) {
                if (result.get()) logger.debug("Удалено рабочее пространство с id {}", id);
                else logger.debug("Не удалось удалить рабочее пространство с id {}", id);
                return result;
            }
            else {
                logger.debug("База данных не вернула ответ при удалении рабочего пространства с id {}", id);
                return Optional.empty();
            }
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    public Optional<Boolean> setBookingCancelled(long id) throws SQLException, SecurityException {
        try {
            Optional<Boolean> result = repo.setBookingCancelled(id);
            if (result.isPresent()) {
                if (result.get()) logger.debug("Отменена бронь по id {}", id);
                else logger.debug("Не удалось отменить бронь по id {}", id);
                return result;
            }
            else {
                logger.debug("База данных не вернула ответ при отмене брони по id {}", id);
                return Optional.empty();
            }
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }
    
    public Optional<Boolean> confirmBooking(long id) throws SQLException, SecurityException {
        try {
            Optional<Boolean> result = repo.confirmBooking(id);
            if (result.isPresent()) {
                if (result.get()) logger.debug("Подтверждена бронь по id {}", id);
                else logger.debug("Не удалось подтвердить бронь по id {}", id);
                return result;
            }
            else {
                logger.debug("База данных не вернула ответ при подтверждении брони по id {}", id);
                return Optional.empty();
            }
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }
    
    public Optional<Boolean> toggleWorkspaceActiveStatus(long id) throws SQLException, SecurityException {
        try {
            Optional<Boolean> result = repo.toggleWorkspaceActiveStatus(id);
            if (result.isPresent()) {
                if (result.get()) logger.debug("Изменён статус активности рабочего пространства по id {}", id);
                else logger.debug("Не удалось изменить статус активности рабочего пространства по id {}", id);
                return result;
            }
            else {
                logger.debug("База данных не вернула ответ при изменении статуса активности рабочего пространства по id {}", id);
                return Optional.empty();
            }
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    public Optional<Boolean> toggleUserActiveStatus(long id) throws SQLException, SecurityException {
        Optional<Boolean> result = repo.toggleUserActiveStatus(id);
        if (result.isPresent()) {
            if (result.get()) logger.debug("Изменён статус пользователя по id {}", id);
            else logger.debug("Не удалось изменить статус пользователя по id {}", id);
            return result;
        }
        else {
            logger.debug("База данных не вернула ответ при изменении статуса пользователя по id {}", id);
            return Optional.empty();
        }
    }

    public Optional<Boolean> updateWorkspace(Workspaces workspace) throws SQLException, SecurityException {
        try {
            Optional<Boolean> result = repo.updateWorkspace(workspace);
            if (result.isPresent()) {
                if (result.get()) logger.debug("Обновлено рабочее пространство по id {}", workspace.id());
                else logger.debug("Не удалось обновить рабочее пространство по id {}", workspace.id());
                return result;
            }
            else {
                logger.debug("База данных не вернула ответ при обновлении рабочего пространства по id {}", workspace.id());
                return Optional.empty();
            }
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }
    
    public ArrayList<WorkspaceDto> getWorkspacesByCapacity(int capacity) throws SQLException, SecurityException {
        try {
            ArrayList<WorkspaceDto> workspaces = repo.getWorkspacesByCapacity(capacity);
            logger.debug("Получено {} рабочих пространств с вместимостью {} человек", workspaces.size(), capacity);
            return workspaces;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }
    
    public ArrayList<WorkspaceDto> getWorkspacesByHourlyRate(BigDecimal minRate, BigDecimal maxRate) throws SQLException, SecurityException {
        try {
            ArrayList<WorkspaceDto> workspaces = repo.getWorkspacesByHourlyRate(minRate, maxRate);
            logger.debug("Получено {} рабочих пространств с почасовой стоимостью в пределах от {} до {}", workspaces.size(), minRate, maxRate);
            return workspaces;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }
    
    public ArrayList<WorkspaceDto> getWorkspacesByName(String name) throws SQLException, SecurityException {
        try {
            ArrayList<WorkspaceDto> workspaces = repo.getWorkspacesByName(name);
            logger.debug("Получено {} рабочих пространств с почасовой стоимостью по названию {}", workspaces.size(), name);
            return workspaces;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }
    
    public ArrayList<WorkspaceDto> getWorkspacesByStatus(boolean is_active) throws SQLException, SecurityException {
        try {
            ArrayList<WorkspaceDto> workspaces = repo.getWorkspacesByStatus(is_active);
            logger.debug("Получено {} {} рабочих пространств", workspaces.size(), is_active ? "активных" : "деактивированных");
            return workspaces;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }
    
    public ArrayList<WorkspaceDto> getWorkspacesByType(long id) throws SQLException, SecurityException {
        try {
            ArrayList<WorkspaceDto> workspaces = repo.getWorkspacesByType(id);
            logger.debug("Получено {} рабочих пространств с типом {}", workspaces.size(), id);
            return workspaces;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }
    
    public ArrayList<WorkspaceAvailableDto> getWorkspacesAvailableForBooking(Date startTime, Date endTime, long workspaceTypeId, int participantsCount) throws SQLException, SecurityException {
        try {
            ArrayList<WorkspaceAvailableDto> workspaces = repo.getWorkspacesAvailableForBooking(startTime, endTime, workspaceTypeId, participantsCount);
            logger.debug("Получено {} доступных для бронирования рабочих пространств с параметрами: дата начала - {}, дата окончания - {}, код типа - {}, количество человек - {}", workspaces.size(), startTime, endTime, workspaceTypeId, participantsCount);
            return workspaces;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    public ArrayList<BookingDto> getBookings() throws SQLException, SecurityException {
        try {
            ArrayList<BookingDto> bookings = repo.getBookings();
            logger.debug("Получено {} броней", bookings.size());
            return bookings;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    public ArrayList<BookingDto> getBookingsByUserId(UsersDto user) throws SQLException, SecurityException {
        try {
            ArrayList<BookingDto> bookings = repo.getBookingsByUserId(user.id());
            logger.debug("Получено {} броней пользователя по id {}", bookings.size(), user.id());
            return bookings;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    public ArrayList<BookingDto> getBookingsByWorkspaceId(WorkspaceDto workspace) throws SQLException, SecurityException {
        try {
            ArrayList<BookingDto> bookings = repo.getBookingsByWorkspaceId(workspace.id());
            logger.debug("Получено {} броней рабочего пространства по id {}", bookings.size(), workspace.id());
            return bookings;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }
    
    public ArrayList<BookingDto> getBookingsByStatus(BookingStatusesDto status) throws SQLException, SecurityException {
        try {
            ArrayList<BookingDto> bookings = repo.getBookingsByStatus(status.id());
            logger.debug("Получено {} броней со статусом {}", bookings.size(), status.name());
            return bookings;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    public List<BookingDto> getBookingsByCreatedAt(Date minDate, Date maxDate) throws SQLException, SecurityException {
        List<BookingDto> bookings = repo.getBookingsByCreatedAt(minDate, maxDate);
        logger.debug("Получено {} броней с датой создания в промежутке c {} по {}", bookings.size(), minDate, maxDate);
        return bookings;
    }

    public ArrayList<BookingDto> getUserBookingsByTime(long user_id, Date startTime, Date endTime) throws SQLException, SecurityException {
        try {
            ArrayList<BookingDto> bookings = repo.getUserBookingsByCreatedAt(user_id, startTime, endTime);
            logger.debug("Получено {} броней с датами бронирования в промежутке от {} до {}", bookings.size(), startTime, endTime);
            return bookings;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }
    
    public ArrayList<WorkspaceTypesDto> getWorkspaceTypes() throws SQLException, SecurityException {
        try {
            ArrayList<WorkspaceTypesDto> workspaceTypes = repo.getWorkspaceTypes();
            logger.debug("Получено {} типов рабочих пространств.", workspaceTypes.size());
            return workspaceTypes;
        } catch (SQLException e) {
            throw new SQLException("Произошла ошибка на уровне базы данных.", e);
        }
    }
    
    public List<BookingStatusesDto> getBookingStatuses() throws SQLException, SecurityException {
        try {
            List<BookingStatusesDto> bookingStatuses = repo.getBookingStatuses();
            logger.debug("Получено {} статусов бронирования.", bookingStatuses.size());
            return bookingStatuses;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }
}
