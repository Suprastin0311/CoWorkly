package com.ddnik.db;

import com.ddnik.db.dto.WorkspaceDto;
import com.ddnik.db.entity.Users;

import java.sql.*;
import java.util.ArrayList;

public class Repository implements IRepository {

    public ArrayList<WorkspaceDto> getWorkspacesById(int id) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/CoWorkly_Test";
        String user = "postgres";
        String password = "123654";

        String sql = "SELECT * FROM get_workspaces_by_id(?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement st = conn.prepareCall(sql);) {
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
                throw new SQLException("Запрос возвратил пустую таблицу.");
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public Long addUser(Users user) throws SQLException {
        return null;
    }
}
