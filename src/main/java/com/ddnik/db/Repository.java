package com.ddnik.db;

import com.ddnik.db.dto.UsersDto;
import com.ddnik.db.dto.WorkspaceDto;
import com.ddnik.db.entity.Users;

import java.sql.*;
import java.util.ArrayList;

public class Repository implements IRepository {

    public ArrayList<WorkspaceDto> getWorkspacesById(int id) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/CoWorkly_Test";
        String username = "postgres";
        String password = "12348765";

        String sql = "SELECT * FROM get_workspaces_by_id(?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement st = conn.prepareStatement(sql);) {
            st.setQueryTimeout(10);
            st.setInt(1, id);

            if (st.execute()) {
                System.out.println("Stored procedure executed successfully");
                ResultSet rs = st.getResultSet();

                ArrayList<WorkspaceDto> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(new WorkspaceDto(
                            rs.getString("type"),
                            rs.getString("name"),
                            rs.getInt("capacity"),
                            rs.getBigDecimal("hourly_rate"),
                            rs.getString("status")));
                }
                return result;
            }
            else {
                throw new Exception("Запрос возвратил пустую таблицу.");
            }
        } catch (SQLTimeoutException e) {
            throw new SQLException("Время выполнения запроса превысило 10 секунд и запрос был прерван.", e);
        } catch (SQLException e) {
            throw e;
        }
    }

    public long insertUser(Users user) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/CoWorkly_Test";
        String username = "postgres";
        String password = "12348765";

        String sql = "{call insert_user(?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DriverManager.getConnection(url, username, password);
            CallableStatement cs = conn.prepareCall(sql);) {
            cs.setQueryTimeout(10);
            // Указываем параметры
            cs.setString(1, user.getEmail());
            cs.setString(2, user.getPassword());
            cs.setString(3, user.getFullName());
            cs.setLong(4, user.getRole());
            cs.setBoolean(5, user.isBlocked());
            cs.setTimestamp(6, new Timestamp(user.getCreatedAt().getTime()));

            if(cs.execute()) {
                System.out.println("Stored procedure executed successfully");
                ResultSet rs = cs.getResultSet();
                return rs.getLong("new_user_id");
            }
            else {
                throw new Exception("Функция не вернула id нового пользователя.");
            }
        } catch (SQLTimeoutException e) {
            throw new SQLException("Время выполнения запроса превысило 10 секунд и запрос был прерван.", e);
        } catch (SQLException e) {
            throw e;
        }
    }

    public UsersDto getUserByEmail(String email) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/CoWorkly_Test";
        String username = "postgres";
        String password = "12348765";

        String sql = "SELECT * FROM get_user_by_email(?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement st = conn.prepareStatement(sql);) {
            st.setQueryTimeout(10);
            st.setString(1, email);

            if (st.execute()) {
                System.out.println("Stored procedure executed successfully");
                ResultSet rs = st.getResultSet();

                if(rs.next()) {
                    UsersDto user = new UsersDto(
                            rs.getLong("id"),
                            rs.getString("email"),
                            rs.getString("password_hash"),
                            rs.getString("full_name"),
                            rs.getString("role"),
                            rs.getBoolean("is_blocked"),
                            new Date(rs.getTimestamp("created_at").getTime())
                    );

                    return user;
                }
                else {
                    throw new Exception("Пользователь не найден.");
                }
            }
            else {
                throw new Exception("Запрос возвратил пустую таблицу.");
            }
        } catch (SQLTimeoutException e) {
            throw new SQLException("Время выполнения запроса превысило 10 секунд и запрос был прерван.", e);
        } catch (SQLException e) {
            throw e;
        }
    }
}
