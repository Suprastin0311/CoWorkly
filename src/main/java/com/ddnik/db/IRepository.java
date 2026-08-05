package com.ddnik.db;

import com.ddnik.db.dto.BookingDto;
import com.ddnik.db.dto.UsersDto;
import com.ddnik.db.dto.WorkspaceDto;
import com.ddnik.db.entity.BookingStatuses;
import com.ddnik.db.entity.Bookings;
import com.ddnik.db.entity.Users;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface IRepository {

    Optional<Long> insertUser(Users user) throws SQLException;

    Optional<UsersDto> getUserByEmail(String email) throws SQLException;

    Optional<WorkspaceDto> getWorkspacesById(int id) throws SQLException;

    ArrayList<WorkspaceDto> getWorkspacesByCapacity(int capacity) throws SQLException;

    ArrayList<WorkspaceDto> getWorkspacesByHourlyRate(BigDecimal rate) throws SQLException;

    ArrayList<WorkspaceDto> getWorkspacesByName(String name) throws SQLException;

    ArrayList<WorkspaceDto> getWorkspacesByStatus(boolean is_active) throws SQLException;

    ArrayList<BookingDto> getBookingsByUserId(int id) throws SQLException;

    ArrayList<BookingDto> getBookingsByStatus(BookingStatuses status) throws SQLException;

    ArrayList<BookingDto> getUserBookingsByTime(long user_id, Date start, Date end) throws SQLException;
A
    boolean setBookingCancelled(long id) throws SQLException;
}
