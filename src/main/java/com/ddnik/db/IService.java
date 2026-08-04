package com.ddnik.db;

import com.ddnik.db.dto.UsersDto;
import com.ddnik.db.dto.WorkspaceDto;
import com.ddnik.db.entity.Users;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface IService {

    /**
     * Создаёт нового пользователя.
     * @param newUser данные нового пользователя.
     * @return id созданной записи.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     */
    Optional<Long> createUser(Users newUser) throws SQLException;

    /**
     * Извлекает данные пользователя по email
     * @param email строка, содержащая email пользователя
     * @return данные пользователя, возвращает <c>Optional.empty()</c>, если пользователь не найден.
     * @throws SQLException
     */
    Optional<UsersDto> getUserByEmail(String email) throws SQLException;

    /**
     * Получает данные рабочего пространства по id.
     * @param id код рабочего пространства.
     * @return данные рабочего пространства. Возвращает {@link Optional#empty()}, если запрос не вернул данные.
     * @throws Exception в случае возникновения ошибки на уровне базы данных.
     */
    Optional<WorkspaceDto> getWorkspacesById(int id) throws SQLException;

    /**
     * Получает рабочие пространства по вместимости.
     * @param capacity вместимость рабочего пространства.
     * @return список рабочих пространств.
     * @throws SQLException в случае возникновения ошибки на уровне базы данных.
     */
    ArrayList<WorkspaceDto> getWorkspaceByCapacity(int capacity) throws SQLException;
}
