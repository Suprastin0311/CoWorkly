package com.ddnik.db;

import com.ddnik.PasswordHasher;
import com.ddnik.db.dto.UsersDto;
import com.ddnik.db.dto.WorkspaceDto;
import com.ddnik.db.entity.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class Service implements IService {

    private static final Logger logger = LoggerFactory.getLogger(Service.class);
    private IRepository repo;

    public Service() {
        repo = new Repository();
    }

    /**
     * Получает данные пользователя по email
     *
     * @param email email пользователя
     * @return данные пользователя
     * @throws Exception в случае возникновения ошибки
     */
    public Optional<UsersDto> getUserByEmail(String email) throws Exception {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email равен null или пустой.");
        }

        try {
            Optional<UsersDto> user = repo.getUserByEmail(email);
            if (user.isPresent()) {
                logger.debug("Получены данные пользователя по email: {}", email);
                return user;
            }
            else {
                logger.debug("Пользователь по email {} не найден", email);
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Получает данные рабочего пространства по id
     * @param id код рабочего пространства
     * @return рабочее пространства
     * @throws Exception в случае возникновения ошибки базы данных
     */
    public Optional<WorkspaceDto> getWorkspacesById(int id) throws Exception {
        try {
            Optional<WorkspaceDto> workspace = repo.getWorkspacesById(id);
            if (workspace.isPresent()) {
                logger.debug("Получено рабочее пространство по id {}", id);
                return workspace;
            }
            else {
                logger.debug("Не найдено рабочее пространство с id {}", id);
                return Optional.empty();
            }
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
    public Optional<Long> createUser(Users newUser) throws Exception {
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
            Optional<Long> newRecordId = repo.insertUser(preparedUsersEntity);
            if (newRecordId.isPresent()) {
                logger.debug("Создан новый пользователь с id {}", newRecordId.get());
                return newRecordId;
            }
            else {
                logger.debug("Новый пользователь не создан");
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
}
