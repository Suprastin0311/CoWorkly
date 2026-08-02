package com.ddnik.db;

import com.ddnik.PasswordHasher;
import com.ddnik.db.dto.UsersDto;
import com.ddnik.db.dto.WorkspaceDto;
import com.ddnik.db.entity.Users;

import java.util.ArrayList;

public class Service implements IService {

    private IRepository repo;

    public Service() {
        repo = new Repository();
    }

    /**
     * Получает данные пользователя по email
     * @param email email пользователя
     * @return данные пользователя
     * @throws Exception в случае возникновения ошибки
     */
    public UsersDto getUserByEmail(String email) throws Exception {
        if (email == null || email.isEmpty()) {
            throw new Exception("Email равен null или пустой.");
        }

        try {
            return repo.getUserByEmail(email);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Получает данные рабочего пространства по id
     * @param id код рабочего пространства
     * @return рабочее пространства
     * @throws Exception в случае возникновения ошибки базы данных
     */
    public ArrayList<WorkspaceDto> getWorkspacesById(int id) throws Exception {
        try {
            return repo.getWorkspacesById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Добавить нового пользователя в БД
     * @param newUser новый пользователь
     * @return id созданного пользователя
     * @throws Exception в случае возникновения ошибки базы данных
     */
    public long createUser(Users newUser) throws Exception {
        String hashedPassword = PasswordHasher.hashPassword(newUser.getPassword());

        // Подготовленная запись с данными пользователя к сохранению в БД
        Users preparedUsersEntity = new Users(
                newUser.getId(),
                newUser.getEmail(),
                hashedPassword,
                newUser.getFullName(),
                newUser.getRole(),
                newUser.isBlocked(),
                newUser.getCreatedAt()
        );

        try {
            return repo.insertUser(preparedUsersEntity);
        } catch (Exception e) {
            throw e;
        }
    }
}
