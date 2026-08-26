package com.ddnik.db;

import com.ddnik.db.dto.*;
import com.ddnik.db.entity.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface IRepository {

    Optional<Long> insertUser(Users user) throws SQLException;

    Optional<Long> insertBooking(Bookings booking) throws SQLException;

    Optional<Long> insertWorkspace(Workspaces workspace) throws SQLException;

    Optional<Boolean> setBookingCancelled(long id) throws SQLException;

    Optional<Boolean> confirmBooking(long id) throws SQLException;

    Optional<Boolean> toggleWorkspaceActiveStatus(long id) throws SQLException;

    Optional<Boolean> updateWorkspace(Workspaces workspace) throws SQLException;

    Optional<Boolean> deleteWorkspace(long id) throws SQLException;

    Optional<UsersDto> getUserByEmail(String email) throws SQLException;

    List<UsersDto> getUsersById(long id) throws SQLException;

    List<UsersDto> getUsersByEmail(String email) throws SQLException;

    List<UsersDto> getUsersByRole(long id) throws SQLException;

    List<UsersDto> getUsersByCreatedAt(Date minDate, Date maxDate) throws SQLException;

    List<UsersDto> getUsersByStatus(boolean is_active) throws SQLException;

    List<UsersDto> getUsersByName(String name) throws SQLException;

    Optional<Boolean> toggleUserActiveStatus(long id) throws SQLException;

    List<UserRolesDto> getUserRoles() throws SQLException;

    Optional<WorkspaceDto> getWorkspaceById(long id) throws SQLException;

    ArrayList<WorkspaceDto> getWorkspacesByCapacity(int capacity) throws SQLException;

    ArrayList<WorkspaceDto> getWorkspacesByHourlyRate(BigDecimal minRate, BigDecimal maxRate) throws SQLException;

    ArrayList<WorkspaceDto> getWorkspacesByName(String name) throws SQLException;

    ArrayList<WorkspaceDto> getWorkspacesByStatus(boolean is_active) throws SQLException;

    ArrayList<WorkspaceDto> getWorkspacesByType(long typeId) throws SQLException;

    ArrayList<WorkspaceAvailableDto> getWorkspacesAvailableForBooking(Date startTime, Date endTime, long workspaceTypeId, int participantsCount) throws SQLException;

    ArrayList<BookingDto> getBookings() throws SQLException;

    ArrayList<BookingDto> getBookingsByUserId(long id) throws SQLException;

    ArrayList<BookingDto> getBookingsByWorkspaceId(long id) throws SQLException;

    ArrayList<BookingDto> getBookingsByStatus(long id) throws SQLException;

    ArrayList<BookingDto> getBookingsByCreatedAt(Date minDate, Date maxDate) throws SQLException;

    ArrayList<BookingDto> getUserBookingsByCreatedAt(long user_id, Date startTime, Date endTime) throws SQLException;

    ArrayList<WorkspaceTypesDto> getWorkspaceTypes() throws SQLException;

    List<BookingStatusesDto> getBookingStatuses() throws SQLException;
}
