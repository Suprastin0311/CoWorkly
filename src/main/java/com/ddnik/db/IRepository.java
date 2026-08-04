package com.ddnik.db;

import com.ddnik.db.dto.UsersDto;
import com.ddnik.db.dto.WorkspaceDto;
import com.ddnik.db.entity.Users;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface IRepository {

    Optional<WorkspaceDto> getWorkspacesById(int id) throws SQLException;

    Optional<Long> insertUser(Users user) throws SQLException;

    Optional<UsersDto> getUserByEmail(String email) throws SQLException;

}
