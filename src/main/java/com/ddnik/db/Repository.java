package com.ddnik.db;

import com.ddnik.db.dto.*;
import com.ddnik.db.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Repository implements IRepository {

    private static final Logger logger = LoggerFactory.getLogger(Repository.class);

    //region Users
    public Optional<Long> insertUser(Users user) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT insert_user(?, ?, ?, ?, ?, ?)")) {

            // Вводим параметры
            ps.setString(1, user.email());
            ps.setString(2, user.password());
            ps.setString(3, user.fullName());
            ps.setLong(4, user.role());
            ps.setBoolean(5, user.isBlocked());
            ps.setTimestamp(6, new Timestamp(user.createdAt().getTime()));

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
                            new UserRolesDto(
                                    rs.getLong("role_id"),
                                    rs.getString("role")),
                            rs.getBoolean("is_blocked"),
                            new Date(rs.getTimestamp("created_at").getTime())
                    );
                    logger.debug("Из таблицы users извлечена 1 запись по email {}", user.email());
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

    public List<UsersDto> getUsersById(long id) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_users(?, ?, ?, ?, ?, ?, ?)")) {
            ps.setLong(1, id);
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.BIGINT);
            ps.setNull(5, Types.BOOLEAN);
            ps.setNull(6, Types.TIMESTAMP);
            ps.setNull(7, Types.TIMESTAMP);

            return executeQueryAndBuildUsersDtoList(ps);
        }
    }

    public List<UsersDto> getUsersByEmail(String email) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_users(?, ?, ?, ?, ?, ?, ?)")) {
            ps.setNull(1, Types.BIGINT);
            ps.setString(2, email);
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.BIGINT);
            ps.setNull(5, Types.BOOLEAN);
            ps.setNull(6, Types.TIMESTAMP);
            ps.setNull(7, Types.TIMESTAMP);

            return executeQueryAndBuildUsersDtoList(ps);
        }
    }

    public List<UsersDto> getUsersByRole(long id) throws SQLException {
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_users(?, ?, ?, ?, ?, ?, ?)")) {
            ps.setNull(1, Types.BIGINT);
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
            ps.setLong(4, id);
            ps.setNull(5, Types.BOOLEAN);
            ps.setNull(6, Types.TIMESTAMP);
            ps.setNull(7, Types.TIMESTAMP);

            return executeQueryAndBuildUsersDtoList(ps);
        }
    }

    public List<UsersDto> getUsersByCreatedAt(Date minDate, Date maxDate) throws SQLException {
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_users(?, ?, ?, ?, ?, ?, ?)")) {
            ps.setNull(1, Types.BIGINT);
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.BIGINT);
            ps.setNull(5, Types.BOOLEAN);
            ps.setDate(6, minDate);
            ps.setDate(7, maxDate);

            return executeQueryAndBuildUsersDtoList(ps);
        }
    }

    public List<UsersDto> getUsersByStatus(boolean is_active) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_users(?, ?, ?, ?, ?, ?, ?)")) {
            ps.setNull(1, Types.BIGINT);
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.BIGINT);
            ps.setBoolean(5, is_active);
            ps.setNull(6, Types.TIMESTAMP);
            ps.setNull(7, Types.TIMESTAMP);

            return executeQueryAndBuildUsersDtoList(ps);
        }
    }

    public List<UsersDto> getUsersByName(String name) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_users(?, ?, ?, ?, ?, ?, ?)")) {
            ps.setNull(1, Types.BIGINT);
            ps.setNull(2, Types.VARCHAR);
            ps.setString(3, name);
            ps.setNull(4, Types.BIGINT);
            ps.setNull(5, Types.BOOLEAN);
            ps.setNull(6, Types.TIMESTAMP);
            ps.setNull(7, Types.TIMESTAMP);

            return executeQueryAndBuildUsersDtoList(ps);
        }
    }

    public Optional<Boolean> toggleUserActiveStatus(long id) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM toggle_user_active_status(?)")) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rs.getBoolean(1));
                }
                else return Optional.empty();
            }
        } catch (SQLTimeoutException e) {
            logger.error(e.getMessage(), e);
            throw new SQLTimeoutException("Время выполнения превысило установленный лимит и запрос был прерван.", e);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new SQLException("Ошибка на уровне базы данных", e);
        }
    }

    /**
     * Выполняет запрос к базе данных и составляет результирующий список пользователей.
     * Предназначен для выполнения хранимых функций, возвращающих таблицу пользователей со структурой {@link UsersDto}.
     * @param ps подготовленный запрос к хранимой процедуре.
     * @return список пользователей, удовлетворяющих условию.
     * @throws SQLException в случае возникновения ошибки на уровне баз данных.
     */
    private List<UsersDto> executeQueryAndBuildUsersDtoList(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            List<UsersDto> result = new ArrayList<>();
            while(rs.next()) {
                result.add(new UsersDto(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getString("full_name"),
                        new UserRolesDto(
                                rs.getLong("role_id"),
                                rs.getString("role")),
                        rs.getBoolean("is_blocked"),
                        rs.getDate("created_at")
                ));
            }
            logger.debug("Из таблицы users извлечено {} записей.", result.size());
            return result;
        } catch (SQLTimeoutException e) {
            logger.error(e.getMessage(), e);
            throw new SQLTimeoutException("Время выполнения превысило установленный лимит и запрос был прерван.", e);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new SQLException("Ошибка на уровне базы данных", e);
        }
    }

    //endregion

    //region Workspaces

    public Optional<Long> insertWorkspace(Workspaces workspace) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT insert_workspace(?, ?, ?, ?, ?)")) {
            ps.setLong(1, workspace.type());
            ps.setString(2, workspace.name());
            ps.setInt(3, workspace.capacity());
            ps.setBigDecimal(4, workspace.hourlyRate());
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
            ps.setLong(1, workspace.id());
            ps.setLong(2, workspace.type());
            ps.setString(3, workspace.name());
            ps.setInt(4, workspace.capacity());
            ps.setBigDecimal(5, workspace.hourlyRate());
            ps.setBoolean(6, workspace.isActive());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.debug("Была обновлена запись в таблице workspaces c id - {}", workspace.id());
                    return Optional.of(rs.getBoolean(1));
                }
                else {
                    logger.debug("Не удалось обновить запись в таблице workspaces c id - {}", workspace.id());
                    return Optional.empty();
                }
            }
        }
    }

    public Optional<Boolean> deleteWorkspace(long id) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM delete_workspace(?)")) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rs.getBoolean(1));
                }
                else return Optional.empty();
            }
        } catch (SQLTimeoutException e) {
            throw new SQLTimeoutException("Время выполнения превысило установленный лимит и запрос был прерван.", e);
        }
    }

    public Optional<WorkspaceDto> getWorkspaceById(long id) throws SQLException, SQLException {
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_workspaces(?, ?, ?, ?, ?, ?, ?)")) {
            ps.setLong(1, id);
            ps.setNull(2, Types.INTEGER);
            ps.setNull(3, Types.NUMERIC);
            ps.setNull(4, Types.NUMERIC);
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.BOOLEAN);
            ps.setNull(7, Types.BIGINT);

            ArrayList<WorkspaceDto> workspaces = executeQueryAndBuildWorkspaceDtoList(ps);
            if (!workspaces.isEmpty()) return Optional.of(workspaces.getFirst());
            else return Optional.empty();
        }
    }

    public ArrayList<WorkspaceDto> getWorkspacesByCapacity(int capacity) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_workspaces(?, ?, ?, ?, ?, ?, ?)")) {
            ps.setNull(1, Types.BIGINT);
            ps.setInt(2, capacity);
            ps.setNull(3, Types.NUMERIC);
            ps.setNull(4, Types.NUMERIC);
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.BOOLEAN);
            ps.setNull(7, Types.BIGINT);

            return executeQueryAndBuildWorkspaceDtoList(ps);
        }
    }

    public ArrayList<WorkspaceDto> getWorkspacesByHourlyRate(BigDecimal minRate, BigDecimal maxRate) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_workspaces(?, ?, ?, ?, ?, ?, ?)")) {
            ps.setNull(1, Types.BIGINT);
            ps.setNull(2, Types.INTEGER);
            ps.setBigDecimal(3, minRate);
            ps.setBigDecimal(4, maxRate);
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.BOOLEAN);
            ps.setNull(7, Types.BIGINT);

            return executeQueryAndBuildWorkspaceDtoList(ps);
        }
    }

    public ArrayList<WorkspaceDto> getWorkspacesByName(String name) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_workspaces(?, ?, ?, ?, ?, ?, ?)")) {
            ps.setNull(1, Types.BIGINT);
            ps.setNull(2, Types.INTEGER);
            ps.setNull(3, Types.NUMERIC);
            ps.setNull(4, Types.NUMERIC);
            ps.setString(5, name);
            ps.setNull(6, Types.BOOLEAN);
            ps.setNull(7, Types.BIGINT);

            return executeQueryAndBuildWorkspaceDtoList(ps);
        }
    }

    public ArrayList<WorkspaceDto> getWorkspacesByStatus(boolean is_active) throws SQLException {
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_workspaces(?, ?, ?, ?, ?, ?, ?)")) {
            ps.setNull(1, Types.BIGINT);
            ps.setNull(2, Types.INTEGER);
            ps.setNull(3, Types.NUMERIC);
            ps.setNull(4, Types.NUMERIC);
            ps.setNull(5, Types.VARCHAR);
            ps.setBoolean(6, is_active);
            ps.setNull(7, Types.BIGINT);

            return executeQueryAndBuildWorkspaceDtoList(ps);
        } catch (SQLTimeoutException e) {
            throw new SQLTimeoutException("Время выполнения превысило установленный лимит и запрос был прерван.", e);
        }
    }

    public ArrayList<WorkspaceDto> getWorkspacesByType(long typeId) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_workspaces(?, ?, ?, ?, ?, ?, ?)")) {
            ps.setNull(1, Types.BIGINT);
            ps.setNull(2, Types.INTEGER);
            ps.setNull(3, Types.NUMERIC);
            ps.setNull(4, Types.NUMERIC);
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.BOOLEAN);
            ps.setLong(7, typeId);

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
     * Предназначен для выполнения хранимых функций, возвращающих таблицу рабочих пространств со структурой {@link WorkspaceDto}.
     * @param ps подготовленный запрос к хранимой процедуре.
     * @return список рабочих пространств, удовлетворяющих условию.
     * @throws SQLException в случае возникновения ошибки на уровне баз данных.
     */
    private ArrayList<WorkspaceDto> executeQueryAndBuildWorkspaceDtoList(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            ArrayList<WorkspaceDto> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new WorkspaceDto(
                        rs.getLong("id"),
                        new WorkspaceTypesDto(
                                rs.getLong("type_id"),
                               "",
                                rs.getInt("min_participants_count"),
                                rs.getInt("max_participants_count"),
                                rs.getString("type")
                        ),
                        rs.getString("name"),
                        rs.getInt("capacity"),
                        rs.getBigDecimal("hourly_rate"),
                        rs.getBoolean("is_active"),
                        rs.getString("status")));
            }
            logger.debug("Из таблицы workspaces извлечено {} записей.", result.size());
            return result;
        } catch (SQLTimeoutException e) {
            logger.error("Время выполнения превысило установленный лимит и запрос был прерван.", e);
            throw new SQLTimeoutException("Время выполнения превысило установленный лимит и запрос был прерван.", e);
        }
    }

    //endregion

    //region Bookings


    public ArrayList<BookingDto> getBookings() throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_bookings(?, ?, ?, ?, ?)")) {
            ps.setNull(1, Types.BIGINT);
            ps.setNull(2, Types.BIGINT);
            ps.setNull(3, Types.BIGINT);
            ps.setNull(4, Types.TIMESTAMP);
            ps.setNull(5, Types.TIMESTAMP);

            return executeQueryAndBuildBookingDtoList(ps);
        }
    }

    public ArrayList<BookingDto> getBookingsByUserId(long id) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_bookings(?, ?, ?, ?, ?)")) {
            ps.setLong(1, id);
            ps.setNull(2, Types.BIGINT);
            ps.setNull(3, Types.BIGINT);
            ps.setNull(4, Types.TIMESTAMP);
            ps.setNull(5, Types.TIMESTAMP);

            return executeQueryAndBuildBookingDtoList(ps);
        }
    }

    public ArrayList<BookingDto> getBookingsByWorkspaceId(long id) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_bookings(?, ?, ?, ?, ?)")) {
            ps.setNull(1, Types.BIGINT);
            ps.setLong(2, id);
            ps.setNull(3, Types.BIGINT);
            ps.setNull(4, Types.TIMESTAMP);
            ps.setNull(5, Types.TIMESTAMP);

            return executeQueryAndBuildBookingDtoList(ps);
        }
    }

    public ArrayList<BookingDto> getBookingsByStatus(long id) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_bookings(?, ?, ?, ?, ?)")) {
            ps.setNull(1, Types.BIGINT);
            ps.setNull(2, Types.BIGINT);
            ps.setLong(3, id);
            ps.setNull(4, Types.TIMESTAMP);
            ps.setNull(5, Types.TIMESTAMP);

            return executeQueryAndBuildBookingDtoList(ps);
        }
    }

    public ArrayList<BookingDto> getBookingsByCreatedAt(Date minDate, Date maxDate) throws SQLException {
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_bookings(?, ?, ?, ?, ?)")) {
            ps.setNull(1, Types.BIGINT);
            ps.setNull(2, Types.BIGINT);
            ps.setNull(3, Types.BIGINT);
            ps.setDate(4, minDate);
            ps.setDate(5, maxDate);

            return executeQueryAndBuildBookingDtoList(ps);
        }
    }

    public ArrayList<BookingDto> getUserBookingsByCreatedAt(long userId, Date start, Date end) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_bookings(?, ?, ?, ?, ?)")) {
            ps.setLong(1, userId);
            ps.setNull(2, Types.BIGINT);
            ps.setNull(3, Types.BIGINT);
            ps.setDate(4, start);
            ps.setDate(5, end);

            return executeQueryAndBuildBookingDtoList(ps);
        }
    }

    public Optional<Long> insertBooking(Bookings booking) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM insert_booking(?, ?, ?, ?, ?, ?, ?)")) {
            ps.setLong(1, booking.userId());
            ps.setLong(2, booking.workspaceId());
            ps.setDate(3, booking.startTime());
            ps.setDate(4, booking.endTime());
            ps.setInt(5, booking.participantsCount());

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
                        rs.getLong("booking_id"),
                        rs.getLong("workspace_id"),
                        rs.getLong("wtype_id"),
                        rs.getLong("user_id"),
                        rs.getString("user_email"),
                        rs.getString("user_full_name"),
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
            logger.debug("Из таблицы bookings извлечено {} записей.", result.size());
            return result;
        }
    }

    //endregion

    //region Справочники

    public ArrayList<WorkspaceTypesDto> getWorkspaceTypes() throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM workspace_types")) {

            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<WorkspaceTypesDto> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(new WorkspaceTypesDto(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getInt("min_participants_count"),
                            rs.getInt("max_participants_count"),
                            rs.getString("name_rus")
                    ));
                }
                logger.debug("Из таблицы workspace_types извлечено {} записей.", result.size());
                return result;
            }
        }
    }

    public List<BookingStatusesDto> getBookingStatuses() throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM booking_statuses")) {

            try (ResultSet rs = ps.executeQuery()) {
                List<BookingStatusesDto> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(new BookingStatusesDto(
                            rs.getLong(1),
                            rs.getString(2)
                    ));
                }
                logger.debug("Из таблицы booking_statuses извлечено {} записей.", result.size());
                return result;
            }
        }
    }

    public List<UserRolesDto> getUserRoles() throws SQLException {
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM user_roles")) {

            try (ResultSet rs = ps.executeQuery()) {
                List<UserRolesDto> result = new ArrayList<>();
                while(rs.next()) {
                    result.add(new UserRolesDto(
                            rs.getLong("id"),
                            rs.getString("name")));
                }
                logger.debug("Из таблицы user_roles извлечено {} записей.", result.size());
                return result;
            }
        }
    }

    //endregion
}