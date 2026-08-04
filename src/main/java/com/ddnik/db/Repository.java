package com.ddnik.db;

import com.ddnik.db.dto.UsersDto;
import com.ddnik.db.dto.WorkspaceDto;
import com.ddnik.db.entity.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Optional;

public class Repository implements IRepository {

    private static final Logger logger = LoggerFactory.getLogger(Repository.class);

    public Optional<WorkspaceDto> getWorkspacesById(int id) throws SQLException {
        String sql = "SELECT * FROM get_workspaces_by_id(?)";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql);) {
            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
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
            } catch (Exception e) {
                throw e;
            }
        } catch (SQLTimeoutException e) {
            throw new SQLTimeoutException("Время выполнения превысило установленный лимит и запрос был прерван.", e);
        } catch (SQLException e) {
            throw e;
        }
    }

    public Optional<Long> insertUser(Users user) throws SQLException {
        String sql = "{call insert_user(?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DataSource.getConnection();
            CallableStatement cs = conn.prepareCall(sql);) {

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
        } catch (SQLException e) {
            throw e;
        }
    }

    public Optional<UsersDto> getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM get_user_by_email(?)";

        try (Connection conn = DataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);) {
            
            st.setString(1, email);

            try (ResultSet rs = st.executeQuery()) {
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
        } catch (SQLException e) {
            throw e;
        }
    }
}
