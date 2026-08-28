package com.ddnik.db;

import com.ddnik.AuthorizedUser;
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
        }
    }

    public Optional<WorkspaceDto> getWorkspaceById(long id) throws SQLException {
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_workspaces(?, ?, ?, ?, ?, ?, ?)")) {
            ps.setLong(1, id);
            ps.setNull(2, Types.INTEGER);
            ps.setNull(3, Types.NUMERIC);
            ps.setNull(4, Types.NUMERIC);
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.BOOLEAN);
            ps.setNull(7, Types.BIGINT);

            List<WorkspaceDto> workspaces = executeQueryAndBuildWorkspaceDtoList(ps);
            if (!workspaces.isEmpty()) return Optional.of(workspaces.getFirst());
            else return Optional.empty();
        }
    }

    public List<WorkspaceDto> getWorkspacesByCapacity(int capacity) throws SQLException {
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

    public List<WorkspaceDto> getWorkspacesByHourlyRate(BigDecimal minRate, BigDecimal maxRate) throws SQLException {
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

    public List<WorkspaceDto> getWorkspacesByName(String name) throws SQLException {
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

    public List<WorkspaceDto> getWorkspacesByStatus(boolean is_active) throws SQLException {
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
        }
    }

    public List<WorkspaceDto> getWorkspacesByType(long typeId) throws SQLException {
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
        }
    }

    public List<WorkspaceAvailableDto> getWorkspacesAvailableForBooking(Timestamp startTime, Timestamp endTime, long workspaceTypeId, int participantsCount) throws SQLException {
        try (Connection conn = DataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM get_workspaces_available_for_booking(?, ?, ?, ?)")) {
            ps.setTimestamp(1, startTime);
            ps.setTimestamp(2, endTime);
            ps.setLong(3, workspaceTypeId);
            ps.setInt(4, participantsCount);

            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<WorkspaceAvailableDto> result = new ArrayList<>();

                while (rs.next()) {
                    result.add(new WorkspaceAvailableDto(
                            rs.getLong("id"),
                            new WorkspaceTypesDto(
                                    rs.getLong("type_id"),
                                    rs.getString("type_name"),
                                    rs.getInt("min_participants_count"),
                                    rs.getInt("max_participants_count"),
                                    rs.getString("type_name_rus")),
                            rs.getString("name"),
                            rs.getInt("capacity"),
                            rs.getBigDecimal("hourly_rate"),
                            rs.getBigDecimal("price")
                    ));
                }

                logger.debug("Из БД извлечено {} записей.", result.size());
                return result;
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
    private List<WorkspaceDto> executeQueryAndBuildWorkspaceDtoList(PreparedStatement ps) throws SQLException {
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
        }
    }

    //endregion

    //region Bookings


    public List<BookingDto> getBookings() throws SQLException {
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

    public List<BookingDto> getBookingsByUserId(long id) throws SQLException {
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

    public List<BookingDto> getBookingsByWorkspaceId(long id) throws SQLException {
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

    public List<BookingDto> getBookingsByStatus(long id) throws SQLException {
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

    public List<BookingDto> getBookingsByCreatedAt(Date minDate, Date maxDate) throws SQLException {
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

    public List<BookingDto> getUserBookingsByCreatedAt(long userId, Date start, Date end) throws SQLException {
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
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM insert_booking(?, ?, ?, ?, ?)")) {
            ps.setLong(1, booking.userId());
            ps.setLong(2, booking.workspaceId());
            ps.setTimestamp(3, booking.startTime());
            ps.setTimestamp(4, booking.endTime());
            ps.setInt(5, booking.participantsCount());

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.debug("В таблицу bookings добавлена запись с id = {}.", rs.getLong(1));
                    return Optional.of(rs.getLong(1));
                }
                else {
                    logger.debug("В таблицу bookings не удалось добавить запись.");
                    return Optional.empty();
                }
            }
        }
    }

    public Optional<Long> createBooking(long userId, long workspaceId, Timestamp startTime, Timestamp endTime, int participantsCount) throws SQLException {
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM insert_booking(?, ?, ?, ?, ?)")) {
            ps.setLong(1, userId);
            ps.setLong(2, workspaceId);
            ps.setTimestamp(3, startTime);
            ps.setTimestamp(4, endTime);
            ps.setInt(5, participantsCount);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.debug("В таблицу bookings добавлена запись с id = {}.", rs.getLong(1));
                    return Optional.of(rs.getLong(1));
                }
                else {
                    logger.debug("В таблицу bookings не удалось добавить запись.");
                    return Optional.empty();
                }
            }
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
        }
    }

    /**
     * Выполняет запрос к базе данных и составляет результирующий список броней.
     * Предназначен для выполнения хранимых функций, возвращающих рабочие пространства в формате {@link BookingDto}.
     * @param ps подготовленный запрос к хранимой процедуре.
     * @return список броней, удовлетворяющих условию.
     * @throws SQLException в случае возникновения ошибки на уровне баз данных.
     */
    private List<BookingDto> executeQueryAndBuildBookingDtoList(PreparedStatement ps) throws SQLException {
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

    public List<WorkspaceTypesDto> getWorkspaceTypes() throws SQLException {
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