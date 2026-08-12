package com.ddnik.db;

import com.ddnik.PasswordHasher;
import com.ddnik.db.dto.BookingDto;
import com.ddnik.db.dto.UsersDto;
import com.ddnik.db.dto.WorkspaceAvailableDto;
import com.ddnik.db.dto.WorkspaceDto;
import com.ddnik.db.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class Service implements IService {

    private static final Logger logger = LoggerFactory.getLogger(Service.class);
    private IRepository repo;

    public Service() {
        repo = new Repository();
    }

    public Optional<Long> createUser(Users newUser) throws SQLException {
        String hashedPassword = PasswordHasher.hashPassword(newUser.getPassword());

        // Подготовленная запись с данными пользователя к сохранению в БД
        Users preparedUsersEntity = new Users(
                newUser.getId(),
                newUser.getEmail(),
                hashedPassword,
                newUser.getFullName(),
                newUser.getRole(),
                newUser.isBlocked(),
                newUser.getCreatedAt()
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
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email равен null или пустой.");
        }

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

    public ArrayList<WorkspaceDto> getWorkspaceByCapacity(int capacity) throws SQLException, SecurityException {
        try {
            ArrayList<WorkspaceDto> workspaces = repo.getWorkspacesByCapacity(capacity);
            logger.debug("Получено {} рабочих пространств с вместимостью {}", workspaces.size(), capacity);
            return workspaces;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    @Override
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

    @Override
    public Optional<Long> insertWorkspace(Workspaces workspace) throws SQLException, SecurityException {
        try {
            Optional<Long> workspaceId = repo.insertWorkspace(workspace);
            if (workspaceId.isPresent()) {
                logger.debug("Создано новое рабочее пространство с id {}", workspaceId);
                return workspaceId;
            }
            else {
                logger.debug("Не удалось создать новое рабочее пространство с id {}");
                return Optional.empty();
            }
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    @Override
    public Optional<Boolean> setBookingCancelled(long id) throws SQLException, SecurityException {
        try {
            Optional<Boolean> result = repo.setBookingCancelled(id);
            if (result.isPresent()) {
                logger.debug("Отменена бронь по id {}", id);
                return result;
            }
            else {
                logger.debug("Не удалось отменить бронь по id {}", id);
                return Optional.empty();
            }
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    @Override
    public Optional<Boolean> confirmBooking(long id) throws SQLException, SecurityException {
        try {
            Optional<Boolean> result = repo.confirmBooking(id);
            if (result.isPresent()) {
                logger.debug("Подтверждена бронь по id {}", id);
                return result;
            }
            else {
                logger.debug("Не удалось подтвердить бронь по id {}", id);
                return Optional.empty();
            }
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    @Override
    public Optional<Boolean> toggleWorkspaceActiveStatus(long id) throws SQLException, SecurityException {
        try {
            Optional<Boolean> result = repo.toggleWorkspaceActiveStatus(id);
            if (result.isPresent()) {
                logger.debug("Изменён статус активности рабочего пространства по id {}", id);
                return result;
            }
            else {
                logger.debug("Не удалось изменить статус активности рабочего пространства по id {}", id);
                return Optional.empty();
            }
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    @Override
    public Optional<Boolean> updateWorkspace(Workspaces workspace) throws SQLException, SecurityException {
        try {
            Optional<Boolean> result = repo.updateWorkspace(workspace);
            if (result.isPresent()) {
                logger.debug("Обновлено рабочее пространство по id {}", workspace.getId());
                return result;
            }
            else {
                logger.debug("Не удалось обновить рабочее пространство по id {}", workspace.getId());
                return Optional.empty();
            }
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    @Override
    public ArrayList<WorkspaceDto> getWorkspacesByCapacity(int capacity) throws SQLException, SecurityException {
        try {
            ArrayList<WorkspaceDto> workspaces = repo.getWorkspacesByCapacity(capacity);
            logger.debug("Получено {} рабочих пространств с вместимостью {} человек", workspaces.size(), capacity);
            return workspaces;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    @Override
    public ArrayList<WorkspaceDto> getWorkspacesByHourlyRate(BigDecimal minRate, BigDecimal maxRate) throws SQLException, SecurityException {
        try {
            ArrayList<WorkspaceDto> workspaces = repo.getWorkspacesByHourlyRate(minRate, maxRate);
            logger.debug("Получено {} рабочих пространств с почасовой стоимостью в пределах от {} до {}", workspaces.size(), minRate, maxRate);
            return workspaces;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    @Override
    public ArrayList<WorkspaceDto> getWorkspacesByName(String name) throws SQLException, SecurityException {
        try {
            ArrayList<WorkspaceDto> workspaces = repo.getWorkspacesByName(name);
            logger.debug("Получено {} рабочих пространств с почасовой стоимостью по названию {}", workspaces.size(), name);
            return workspaces;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    @Override
    public ArrayList<WorkspaceDto> getWorkspacesByStatus(boolean is_active) throws SQLException, SecurityException {
        try {
            ArrayList<WorkspaceDto> workspaces = repo.getWorkspacesByStatus(is_active);
            logger.debug("Получено {} {} рабочих пространств", workspaces.size(), is_active ? "активных" : "деактивированных");
            return workspaces;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    @Override
    public ArrayList<WorkspaceAvailableDto> getWorkspacesAvailableForBooking(Date startTime, Date endTime, long workspaceTypeId, int participantsCount) throws SQLException, SecurityException {
        try {
            ArrayList<WorkspaceAvailableDto> workspaces = repo.getWorkspacesAvailableForBooking(startTime, endTime, workspaceTypeId, participantsCount);
            logger.debug("Получено {} доступных для бронирования рабочих пространств с параметрами: дата начала - {}, дата окончания - {}, код типа - {}, количество человек - {}", workspaces.size(), startTime, endTime, workspaceTypeId, participantsCount);
            return workspaces;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    @Override
    public ArrayList<BookingDto> getBookingsByUserId(Users user) throws SQLException, SecurityException {
        try {
            ArrayList<BookingDto> bookings = repo.getBookingsByUserId(user.getId());
            logger.debug("Получено {} броней пользователя с id {}", bookings.size(), user.getId());
            return bookings;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    @Override
    public ArrayList<BookingDto> getBookingsByStatus(BookingStatuses status) throws SQLException, SecurityException {
        try {
            ArrayList<BookingDto> bookings = repo.getBookingsByStatus(status);
            logger.debug("Получено {} броней со статусом {}", bookings.size(), status.getName());
            return bookings;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    @Override
    public ArrayList<BookingDto> getUserBookingsByTime(long user_id, Date startTime, Date endTime) throws SQLException, SecurityException {
        try {
            ArrayList<BookingDto> bookings = repo.getUserBookingsByTime(user_id, startTime, endTime);
            logger.debug("Получено {} броней с датами бронирования в промежутке от {} до {}", bookings.size(), startTime, endTime);
            return bookings;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    @Override
    public ArrayList<WorkspaceTypes> getWorkspaceTypes() throws SQLException, SecurityException {
        try {
            ArrayList<WorkspaceTypes> workspaceTypes = repo.getWorkspaceTypes();
            logger.debug("Получено {} типов рабочих пространств.", workspaceTypes.size());
            return workspaceTypes;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }

    @Override
    public ArrayList<BookingStatuses> getBookingStatuses() throws SQLException, SecurityException {
        try {
            ArrayList<BookingStatuses> bookingStatuses = repo.getBookingStatuses();
            logger.debug("Получено {} статусов бронирования.", bookingStatuses.size());
            return bookingStatuses;
        } catch (SQLException | SecurityException e) {
            throw e;
        }
    }
}
