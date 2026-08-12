package com.ddnik.db;

import com.ddnik.db.dto.BookingDto;
import com.ddnik.db.dto.UsersDto;
import com.ddnik.db.dto.WorkspaceAvailableDto;
import com.ddnik.db.dto.WorkspaceDto;
import com.ddnik.db.entity.*;
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
            PreparedStatement ps = conn.prepareStatement("{SELECT insert_user(?, ?, ?, ?, ?, ?)}")) {

            // Вводим параметры
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());
            ps.setLong(4, user.getRole());
            ps.setBoolean(5, user.isBlocked());
            ps.setTimestamp(6, new Timestamp(user.getCreatedAt().getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.debug("Создана новая запись в таблице users, id - {}", rs.getLong(1));
                    return Optional.of(rs.getLong(1)); // возвращаем id новой записи
                }
                else {
                    logger.debug("Не удалось добавить запись в таблицу users.");
                    return Optional.empty(); // если execute() не выполнился
                }
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

    public Optional<Long> insertWorkspace(Workspaces workspace) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("{SELECT insert_workspace(?, ?, ?, ?, ?)}")) {
            ps.setLong(1, workspace.getType().getId());
            ps.setString(2, workspace.getName());
            ps.setInt(3, workspace.getCapacity());
            ps.setBigDecimal(4, workspace.getHourlyRate());
            ps.setBoolean(5, workspace.isActive());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.debug("Создана новая запись в таблице workspaces, id - {}", rs.getLong(1));
                    return Optional.of(rs.getLong(1));
                }
                else {
                    logger.debug("Не удалось добавить запись в таблицу workspaces.");
                    return Optional.empty();
                }
            }
        }
    }

    public Optional<Boolean> toggleWorkspaceActiveStatus(long id) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM toggle_workspace_active_status(?)")) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.debug("У записи в таблице workspaces c id - {} было инвертировано логическое значение атрибута is_active.", rs.getLong(1));
                    return Optional.of(rs.getBoolean(1));
                }
                else {
                    logger.debug("Не удалось инвертировать логическое значение атрибута is_active у записи в таблице workspaces c id - {}.", rs.getLong(1));
                    return Optional.empty();
                }
            }
        }
    }

    public Optional<Boolean> updateWorkspace(Workspaces workspace) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM update_workspace(?, ?, ?, ?, ?, ?)")) {
            ps.setLong(1, workspace.getId());
            ps.setLong(2, workspace.getType().getId());
            ps.setString(3, workspace.getName());
            ps.setInt(4, workspace.getCapacity());
            ps.setBigDecimal(5, workspace.getHourlyRate());
            ps.setBoolean(6, workspace.isActive());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.debug("Была обновлена запись в таблице workspaces c id - {}", rs.getLong(1));
                    return Optional.of(rs.getBoolean(1));
                }
                else {
                    logger.debug("Не удалось обновить запись в таблице workspaces c id - {}", rs.getLong(1));
                    return Optional.empty();
                }
            }
        }
    }

    public Optional<WorkspaceDto> getWorkspaceById(long id) throws SQLException {
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_workspaces_by_id(?)");) {
            ps.setLong(1, id);

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

    public ArrayList<WorkspaceDto> getWorkspacesByHourlyRate(BigDecimal minRate, BigDecimal maxRate) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_workspaces_by_hourly_rate(?, ?)")) {
            ps.setBigDecimal(1, minRate);
            ps.setBigDecimal(2, maxRate);

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
        } catch (SQLTimeoutException e) {
            throw new SQLTimeoutException("Время выполнения превысило установленный лимит и запрос был прерван.", e);
        }
    }

    public ArrayList<WorkspaceAvailableDto> getWorkspacesAvailableForBooking(Date startTime, Date endTime, long workspaceTypeId, int participantsCount) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_workspaces_available_for_booking(?, ?, ?, ?)")) {
            ps.setDate(1, startTime);
            ps.setDate(2, endTime);
            ps.setLong(3, workspaceTypeId);
            ps.setInt(4, participantsCount);

            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<WorkspaceAvailableDto> result = new ArrayList<>();

                while (rs.next()) {
                    result.add(new WorkspaceAvailableDto(
                            rs.getLong("id"),
                            rs.getString("workspace_type"),
                            rs.getString("workspace_name"),
                            rs.getInt("min_participants_count"),
                            rs.getInt("max_participants_count"),
                            rs.getBigDecimal("hourly_rate"),
                            rs.getBigDecimal("price")
                    ));
                }

                logger.debug("Из БД извлечено {} записей.", result.size());
                return result;
            } catch (SQLTimeoutException e) {
                throw new SQLTimeoutException("Время выполнения превысило установленный лимит и запрос был прерван.", e);
            }
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
            logger.debug("Из БД извлечено {} записей.", result.size());
            return result;
        } catch (SQLTimeoutException e) {
            throw new SQLTimeoutException("Время выполнения превысило установленный лимит и запрос был прерван.", e);
        }
    }

    //endregion

    //region Bookings

    public ArrayList<BookingDto> getBookingsByUserId(long id) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_bookings(?, ?, ?, ?)")) {
            ps.setLong(1, id);

            return executeQueryAndBuildBookingDtoList(ps);
        }
    }

    public ArrayList<BookingDto> getBookingsByStatus(BookingStatuses status) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_bookings(?, ?, ?, ?)")) {
            ps.setLong(2, status.getId());

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

    public Optional<Long> insertBooking(Bookings booking) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM insert_booking(?, ?, ?, ?, ?, ?, ?)")) {
            ps.setLong(1, booking.getUserId());
            ps.setLong(2, booking.getWorkspaceId());
            ps.setDate(3, booking.getStartTime());
            ps.setDate(4, booking.getEndTime());
            ps.setInt(5, booking.getParticipantsCount());

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.debug("В таблицу bookings добавлена запись с id = {}.", rs.getLong(1));
                    return Optional.of(rs.getLong(1));
                }
                else {
                    logger.debug("В таблицу bookings не удалось добавить запись.", rs.getLong(1));
                    return Optional.empty();
                }
            }
        } catch (SQLTimeoutException e) {
            throw new SQLTimeoutException("Время выполнения превысило установленный лимит и запрос был прерван.", e);
        }
    }

    public Optional<Boolean> setBookingCancelled(long id) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT set_booking_cancelled(?)")) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.debug("Статус записи с id = {} в таблице bookings изменён на \"CANCELLED/LATE_CANCELLED\".", id);
                    return Optional.of(rs.getBoolean(1));
                }
                else {
                    logger.debug("Не удалось установить статус \"CANCELLED/LATE_CANCELLED\" записи с id = {} в таблице bookings.", id);
                    return Optional.empty();
                }
            }
        } catch (SQLTimeoutException e) {
            throw new SQLTimeoutException("Время выполнения превысило установленный лимит и запрос был прерван.", e);
        }
    }

    public Optional<Boolean> confirmBooking(long id) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT confirm_booking(?)")) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.debug("Статус записи с id = {} в таблице bookings изменён на \"CONFIRMED\".", id);
                    return Optional.of(rs.getBoolean(1));
                }
                else {
                    logger.debug("Не удалось установить статус \"CONFIRMED\" записи с id = {} в таблице bookings.", id);
                    return Optional.of(rs.getBoolean(1));
                }
            }
        } catch (SQLTimeoutException e) {
            throw new SQLTimeoutException("Время выполнения превысило установленный лимит и запрос был прерван.", e);
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
            logger.debug("Из БД извлечено {} записей.", result.size());
            return result;
        }
    }

    //endregion

    //region Справочники

    @Override
    public ArrayList<WorkspaceTypes> getWorkspaceTypes() throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM workspace_type")) {

            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<WorkspaceTypes> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(new WorkspaceTypes(
                            rs.getLong(1),
                            rs.getString(2)
                    ));
                }
                logger.debug("Из БД извлечено {} записей.", result.size());
                return result;
            }
        }
    }

    @Override
    public ArrayList<BookingStatuses> getBookingStatuses() throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM booking_statuses")) {

            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<BookingStatuses> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(new BookingStatuses(
                            rs.getLong(1),
                            rs.getString(2)
                    ));
                }
                logger.debug("Из БД извлечено {} записей.", result.size());
                return result;
            }
        }
    }

    //endregion
}