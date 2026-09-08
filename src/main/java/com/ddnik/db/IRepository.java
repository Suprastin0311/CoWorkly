package com.ddnik.db;

import com.ddnik.db.dto.*;
import com.ddnik.db.entity.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public interface IRepository {

    Optional<Long> insertUser(Users user);

    Optional<Long> insertBooking(long userId, long workspaceId, Timestamp start, Timestamp end, int participantsCount, BigDecimal price);

    Optional<Long> insertWorkspace(Workspaces workspace);

    Optional<Boolean> setBookingCancelled(long id);

    Optional<Boolean> confirmBooking(long id);

    Optional<Boolean> toggleWorkspaceActiveStatus(long id);

    Optional<Boolean> updateWorkspace(Workspaces workspace);

    Optional<Boolean> deleteWorkspace(long id);

    Optional<UsersDto> getUserAuth(String email);

    List<UsersDto> getUsersById(long id);

    List<UsersDto> getUsersByEmail(String email);

    List<UsersDto> getUsersByRole(long id);

    List<UsersDto> getUsersByCreatedAt(Date minDate, Date maxDate);

    List<UsersDto> getUsersByStatus(boolean is_active);

    List<UsersDto> getUsersByName(String name);

    Optional<Boolean> toggleUserActiveStatus(long id);

    List<UserRolesDto> getUserRoles();

    Optional<WorkspaceDto> getWorkspaceById(long id);

    List<WorkspaceDto> getWorkspaces();

    List<WorkspaceDto> getWorkspacesByCapacity(int capacity);

    List<WorkspaceDto> getWorkspacesByHourlyRate(BigDecimal minRate, BigDecimal maxRate);

    List<WorkspaceDto> getWorkspacesByName(String name);

    List<WorkspaceDto> getWorkspacesByStatus(boolean is_active);

    List<WorkspaceDto> getWorkspacesByType(long typeId);

    List<WorkspaceAvailableDto> getWorkspacesAvailableForBooking(Timestamp startTime, Timestamp endTime, long workspaceTypeId, int participantsCount);

    List<BookingDto> getBookings();

    List<BookingDto> getBookingsByUserId(long id);

    List<BookingDto> getBookingsByWorkspaceId(long id);

    List<BookingDto> getBookingsByWorkspaceId(long userId, long workspaceId);

    List<BookingDto> getBookingsByStatus(long id);

    List<BookingDto> getBookingsByStatus(long userId, long statusId);

    List<BookingDto> getBookingsByCreatedAt(Date minDate, Date maxDate);

    List<BookingDto> getBookingsByCreatedAt(long userId, Date minDate, Date maxDate);

    List<BookingDto> getBookingsPendingPayment();

    List<BookingDto> getBookingsPendingPayment(long userId);

    List<WorkspaceTypesDto> getWorkspaceTypes();

    List<BookingStatusesDto> getBookingStatuses();

    Optional<Tariffs> getTariffByWorkspaceTypeId(long id);
}
