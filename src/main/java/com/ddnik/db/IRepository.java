package com.ddnik.db;

import com.ddnik.db.dto.*;
import com.ddnik.db.entity.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public interface IRepository {

    Optional<Long> insertUser(Users user) throws SQLException;

    Optional<Long> insertBooking(long userId, long workspaceId, Timestamp start, Timestamp end, int participantsCount, int price) throws SQLException;

    Optional<Long> insertWorkspace(Workspaces workspace) throws SQLException;

    Optional<Boolean> setBookingCancelled(long id) throws SQLException;

    Optional<Boolean> confirmBooking(long id) throws SQLException;

    Optional<Boolean> toggleWorkspaceActiveStatus(long id) throws SQLException;

    Optional<Boolean> updateWorkspace(Workspaces workspace) throws SQLException;

    Optional<Boolean> deleteWorkspace(long id) throws SQLException;

    Optional<UsersDto> getUserAuth(String email) throws SQLException;

    List<UsersDto> getUsersById(long id) throws SQLException;

    List<UsersDto> getUsersByEmail(String email) throws SQLException;

    List<UsersDto> getUsersByRole(long id) throws SQLException;

    List<UsersDto> getUsersByCreatedAt(Date minDate, Date maxDate) throws SQLException;

    List<UsersDto> getUsersByStatus(boolean is_active) throws SQLException;

    List<UsersDto> getUsersByName(String name) throws SQLException;

    Optional<Boolean> toggleUserActiveStatus(long id) throws SQLException;

    List<UserRolesDto> getUserRoles() throws SQLException;

    Optional<WorkspaceDto> getWorkspaceById(long id) throws SQLException;

    List<WorkspaceDto> getWorkspaces() throws SQLException;

    List<WorkspaceDto> getWorkspacesByCapacity(int capacity) throws SQLException;

    List<WorkspaceDto> getWorkspacesByHourlyRate(BigDecimal minRate, BigDecimal maxRate) throws SQLException;

    List<WorkspaceDto> getWorkspacesByName(String name) throws SQLException;

    List<WorkspaceDto> getWorkspacesByStatus(boolean is_active) throws SQLException;

    List<WorkspaceDto> getWorkspacesByType(long typeId) throws SQLException;

    List<WorkspaceAvailableDto> getWorkspacesAvailableForBooking(Timestamp startTime, Timestamp endTime, long workspaceTypeId, int participantsCount) throws SQLException;

    List<BookingDto> getBookings() throws SQLException;

    List<BookingDto> getBookingsByUserId(long id) throws SQLException;

    List<BookingDto> getBookingsByWorkspaceId(long id) throws SQLException;

    List<BookingDto> getBookingsByWorkspaceId(long userId, long workspaceId) throws SQLException;

    List<BookingDto> getBookingsByStatus(long id) throws SQLException;

    List<BookingDto> getBookingsByStatus(long userId, long statusId) throws SQLException;

    List<BookingDto> getBookingsByCreatedAt(Date minDate, Date maxDate) throws SQLException;

    List<BookingDto> getBookingsByCreatedAt(long userId, Date minDate, Date maxDate) throws SQLException;

    List<WorkspaceTypesDto> getWorkspaceTypes() throws SQLException;

    List<BookingStatusesDto> getBookingStatuses() throws SQLException;

    Optional<Tariffs> getTariffByWorkspaceTypeId(long id) throws SQLException;
}
