package com.ddnik.db;

import com.ddnik.db.dto.BookingDto;
import com.ddnik.db.dto.UsersDto;
import com.ddnik.db.dto.WorkspaceDto;
import com.ddnik.db.entity.BookingStatuses;
import com.ddnik.db.entity.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class Repository implements IRepository {

    private static final Logger logger = LoggerFactory.getLogger(Repository.class);

    //region Users
    public Optional<Long> insertUser(Users user) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            CallableStatement cs = conn.prepareCall("{call insert_user(?, ?, ?, ?, ?, ?)}")) {

            // Вводим параметры
            cs.setString(1, user.getEmail());
            cs.setString(2, user.getPassword());
            cs.setString(3, user.getFullName());
            cs.setLong(4, user.getRole());
            cs.setBoolean(5, user.isBlocked());
            cs.setTimestamp(6, new Timestamp(user.getCreatedAt().getTime()));

            if(cs.execute()) { // выполняем функцию
                ResultSet rs = cs.getResultSet(); // получаем результат
                Optional<Long> newRecordID = Optional.of(rs.getLong(1));
                logger.debug("Создана новая запись в таблице users, id - {}", newRecordID);
                return newRecordID; // возвращаем id новой записи
            }
            else {
                logger.debug("Из БД извлечено 0 записей");
                return Optional.empty(); // если execute() не выполнился
            }
        } catch (SQLTimeoutException e) {
            throw new SQLTimeoutException("Время выполнения превысило установленный лимит и запрос был прерван.", e);
        }
    }

    public Optional<UsersDto> getUserByEmail(String email) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_user_by_email(?)")) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // выбираем первую запись
                    UsersDto user = new UsersDto(
                            rs.getLong("id"),
                            rs.getString("email"),
                            rs.getString("password_hash"),
                            rs.getString("full_name"),
                            rs.getString("role"),
                            rs.getBoolean("is_blocked"),
                            new Date(rs.getTimestamp("created_at").getTime())
                    );
                    logger.debug("Из БД извлечена 1 запись из таблицы users по email {}", user.email());
                    return Optional.of(user);
                }
                else {
                    logger.debug("Из БД извлечено 0 записей");
                    return Optional.empty(); // если запрос не вернул результат
                }
            } catch (SQLTimeoutException e) {
                throw new SQLTimeoutException("Время выполнения превысило установленный лимит и запрос был прерван.", e);
            }
        }
    }
    //endregion

    //region Workspaces

    public Optional<WorkspaceDto> getWorkspacesById(int id) throws SQLException {
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_workspaces_by_id(?)");) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Optional<WorkspaceDto> result = Optional.of(new WorkspaceDto(
                            rs.getString("type"),
                            rs.getString("name"),
                            rs.getInt("capacity"),
                            rs.getBigDecimal("hourly_rate"),
                            rs.getString("status")));
                    logger.debug("Из БД извлечена 1 запись из таблицы workspaces по id {}", id);
                    return result;
                }
                else {
                    logger.debug("Из БД извлечено 0 записей");
                    return Optional.empty();
                }
            }
        } catch (SQLTimeoutException e) {
            throw new SQLTimeoutException("Время выполнения превысило установленный лимит и запрос был прерван.", e);
        }
    }

    public ArrayList<WorkspaceDto> getWorkspacesByCapacity(int capacity) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_workspaces_by_capacity(?)")) {
            ps.setInt(1, capacity);

            return executeQueryAndBuildWorkspaceDtoList(ps);
        }
    }

    public ArrayList<WorkspaceDto> getWorkspacesByHourlyRate(BigDecimal rate) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_workspaces_by_hourly_rate(?)")) {
            ps.setBigDecimal(1, rate);

            return executeQueryAndBuildWorkspaceDtoList(ps);
        }
    }

    public ArrayList<WorkspaceDto> getWorkspacesByName(String name) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_workspaces_by_name(?)")) {
            ps.setString(1, name);

            return executeQueryAndBuildWorkspaceDtoList(ps);
        }
    }

    public ArrayList<WorkspaceDto> getWorkspacesByStatus(boolean is_active) throws SQLException {
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_workspaces_by_status(?)")) {
            ps.setBoolean(1, is_active);

            return executeQueryAndBuildWorkspaceDtoList(ps);
        }
    }

    /**
     * Выполняет запрос к базе данных и составляет результирующий список рабочих пространств.
     * Предназначен для выполнения хранимых функций, возвращающих рабочие пространства в формате {@link WorkspaceDto}.
     * @param ps подготовленный запрос к хранимой процедуре.
     * @return список рабочих пространств, удовлетворяющих условию.
     * @throws SQLException в случае возникновения ошибки на уровне баз данных.
     */
    private ArrayList<WorkspaceDto> executeQueryAndBuildWorkspaceDtoList(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            ArrayList<WorkspaceDto> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new WorkspaceDto(
                        rs.getString("type"),
                        rs.getString("name"),
                        rs.getInt("capacity"),
                        rs.getBigDecimal("hourly_rate"),
                        rs.getString("status")));
            }
            logger.debug("Из БД извлечено {} записей", result.size());
            return result;
        } catch (SQLTimeoutException e) {
            throw new SQLTimeoutException("Время выполнения превысило установленный лимит и запрос был прерван.", e);
        }
    }
    //endregion

    //region Bookings

    public ArrayList<BookingDto> getBookingsByUserId(int id) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_bookings_by_user_id(?)")) {
            ps.setInt(1, id);

            return executeQueryAndBuildBookingDtoList(ps);
        }
    }

    public ArrayList<BookingDto> getBookingsByStatus(BookingStatuses status) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_bookings_by_status(?)")) {
            ps.setLong(1, status.getId());

            return executeQueryAndBuildBookingDtoList(ps);
        }
    }

    public ArrayList<BookingDto> getUserBookingsByTime(long userId, Date start, Date end) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_user_bookings_by_time(?, ?, ?)")) {
            ps.setLong(1, userId);
            ps.setDate(2, start);
            ps.setDate(3, end);

            return executeQueryAndBuildBookingDtoList(ps);
        }
    }

    /**
     * Выполняет запрос к базе данных и составляет результирующий список броней.
     * Предназначен для выполнения хранимых функций, возвращающих рабочие пространства в формате {@link BookingDto}.
     * @param ps подготовленный запрос к хранимой процедуре.
     * @return список броней, удовлетворяющих условию.
     * @throws SQLException в случае возникновения ошибки на уровне баз данных.
     */
    private ArrayList<BookingDto> executeQueryAndBuildBookingDtoList(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            ArrayList<BookingDto> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new BookingDto(
                        rs.getString("type"),
                        rs.getString("workspace_name"),
                        rs.getDate("start_time"),
                        rs.getDate("end_time"),
                        rs.getInt("participants_count"),
                        rs.getString("status"),
                        rs.getBigDecimal("price"),
                        rs.getDate("created_at")
                ));
            }
            logger.debug("Из БД извлечено {} записей", result.size());
            return result;
        }
    }


    //endregion
}
