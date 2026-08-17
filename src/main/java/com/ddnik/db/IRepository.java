package com.ddnik.db;

import com.ddnik.db.dto.*;
import com.ddnik.db.entity.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface IRepository {

    Optional<Long> insertUser(Users user) throws SQLException;

    Optional<Long> insertBooking(Bookings booking) throws SQLException;

    Optional<Long> insertWorkspace(Workspaces workspace) throws SQLException;

    Optional<Boolean> setBookingCancelled(long id) throws SQLException;

    Optional<Boolean> confirmBooking(long id) throws SQLException;

    Optional<Boolean> toggleWorkspaceActiveStatus(long id) throws SQLException;

    Optional<Boolean> updateWorkspace(Workspaces workspace) throws SQLException;

    Optional<UsersDto> getUserByEmail(String email) throws SQLException;

    Optional<WorkspaceDto> getWorkspaceById(long id) throws SQLException;

    ArrayList<WorkspaceDto> getWorkspacesByCapacity(int capacity) throws SQLException;

    ArrayList<WorkspaceDto> getWorkspacesByHourlyRate(BigDecimal minRate, BigDecimal maxRate) throws SQLException;

    ArrayList<WorkspaceDto> getWorkspacesByName(String name) throws SQLException;

    ArrayList<WorkspaceDto> getWorkspacesByStatus(boolean is_active) throws SQLException;

    ArrayList<WorkspaceDto> getWorkspacesByType(long typeId) throws SQLException;

    ArrayList<WorkspaceAvailableDto> getWorkspacesAvailableForBooking(Date startTime, Date endTime, long workspaceTypeId, int participantsCount) throws SQLException;

    ArrayList<BookingDto> getBookingsByUserId(long id) throws SQLException;

    ArrayList<BookingDto> getBookingsByStatus(BookingStatuses status) throws SQLException;

    ArrayList<BookingDto> getUserBookingsByTime(long user_id, Date startTime, Date endTime) throws SQLException;

    ArrayList<WorkspaceTypesDto> getWorkspaceTypes() throws SQLException;

    ArrayList<BookingStatuses> getBookingStatuses() throws SQLException;
}
