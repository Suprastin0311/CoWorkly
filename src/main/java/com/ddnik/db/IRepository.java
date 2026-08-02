package com.ddnik.db;

import com.ddnik.db.dto.UsersDto;
import com.ddnik.db.dto.WorkspaceDto;
import com.ddnik.db.entity.Users;

import java.util.ArrayList;

public interface IRepository {

    ArrayList<WorkspaceDto> getWorkspacesById(int id) throws Exception;

    long insertUser(Users user) throws Exception;

    UsersDto getUserByEmail(String email) throws Exception;

}
