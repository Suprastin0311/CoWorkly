package com.ddnik.db;

import com.ddnik.db.dto.WorkspaceDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IService {

    public ArrayList<WorkspaceDto> getWorkspacesById(int id) throws SQLException;

}
