package com.ddnik.db;

import com.ddnik.db.dto.WorkspaceDto;
import com.ddnik.db.entity.Users;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IRepository {

    public ArrayList<WorkspaceDto> getWorkspacesById(int id) throws SQLException;

    public Long addUser(Users user) throws SQLException;

}
