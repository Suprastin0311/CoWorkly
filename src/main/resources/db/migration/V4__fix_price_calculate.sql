/*
 Navicat Premium Data Transfer

 Source Server         : HomeUbuntuServer
 Source Server Type    : PostgreSQL
 Source Server Version : 180004 (180004)
 Source Host           : 192.168.0.102:5432
 Source Catalog        : CoWorklyDB
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 180004 (180004)
 File Encoding         : 65001

 Date: 04/09/2026 22:43:42
*/


-- ----------------------------
-- Sequence structure for booking_statuses_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "booking_statuses_id_seq" CASCADE;
CREATE SEQUENCE "booking_statuses_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for bookings_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "bookings_id_seq" CASCADE;
CREATE SEQUENCE "bookings_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for day_types_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "day_types_id_seq" CASCADE;
CREATE SEQUENCE "day_types_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for tariffs_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "tariffs_id_seq" CASCADE;
CREATE SEQUENCE "tariffs_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for user_roles_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "user_roles_id_seq" CASCADE;
CREATE SEQUENCE "user_roles_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for users_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "users_id_seq" CASCADE;
CREATE SEQUENCE "users_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for users_role_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "users_role_seq" CASCADE;
CREATE SEQUENCE "users_role_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for workspace_types_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "workspace_types_id_seq" CASCADE;
CREATE SEQUENCE "workspace_types_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for workspaces_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "workspaces_id_seq" CASCADE;
CREATE SEQUENCE "workspaces_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Table structure for booking_statuses
-- ----------------------------
DROP TABLE IF EXISTS "booking_statuses" CASCADE;
CREATE TABLE "booking_statuses" (
  "id" int8 NOT NULL DEFAULT nextval('booking_statuses_id_seq'::regclass),
  "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "sort_order" int4 NOT NULL
)
;

-- ----------------------------
-- Records of booking_statuses
-- ----------------------------
BEGIN;
INSERT INTO "booking_statuses" ("id", "name", "sort_order") VALUES (2, 'CONFIRMED', 20), (3, 'EXPIRED', 30), (4, 'LATE_CANCELLED', 40), (5, 'CANCELLED', 50), (1, 'PENDING_PAYMENT', 10);
COMMIT;

-- ----------------------------
-- Table structure for bookings
-- ----------------------------
DROP TABLE IF EXISTS "bookings" CASCADE;
CREATE TABLE "bookings" (
  "id" int8 NOT NULL DEFAULT nextval('bookings_id_seq'::regclass),
  "user_id" int8 NOT NULL,
  "workspace_id" int8 NOT NULL,
  "start_time" timestamp(6) NOT NULL,
  "end_time" timestamp(6) NOT NULL,
  "participants_count" int4 NOT NULL DEFAULT 1,
  "status_id" int8 NOT NULL,
  "price" numeric(10,2) NOT NULL,
  "created_at" timestamp(6) NOT NULL DEFAULT now()
)
;

-- ----------------------------
-- Records of bookings
-- ----------------------------
BEGIN;
INSERT INTO "bookings" ("id", "user_id", "workspace_id", "start_time", "end_time", "participants_count", "status_id", "price", "created_at") VALUES (4, 2, 8, '2026-09-02 10:00:00', '2026-09-02 13:25:00', 3, 1, 12800.00, '2026-08-30 17:53:05.171555'), (5, 2, 16, '2026-09-02 10:00:00', '2026-09-02 11:15:00', 1, 1, 670.00, '2026-08-30 17:53:31.420531'), (6, 2, 1, '2026-09-02 10:00:00', '2026-09-02 15:40:00', 1, 1, 4500.00, '2026-08-30 17:53:53.244787'), (3, 2, 4, '2026-09-02 10:00:00', '2026-09-02 12:30:00', 1, 5, 1800.00, '2026-08-30 17:52:42.2507'), (7, 3, 4, '2026-09-08 10:00:00', '2026-09-08 13:00:00', 1, 1, 120000.00, '2026-09-04 21:51:07.28278'), (8, 3, 5, '2026-09-08 10:00:00', '2026-09-08 13:00:00', 1, 1, 630.00, '2026-09-04 21:53:00.004768'), (9, 3, 4, '2026-09-04 22:32:00', '2026-09-04 22:35:00', 1, 1, 6.00, '2026-09-04 22:31:34.743715');
COMMIT;

-- ----------------------------
-- Table structure for day_types
-- ----------------------------
DROP TABLE IF EXISTS "day_types" CASCADE;
CREATE TABLE "day_types" (
  "id" int8 NOT NULL DEFAULT nextval('day_types_id_seq'::regclass),
  "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of day_types
-- ----------------------------
BEGIN;
INSERT INTO "day_types" ("id", "name") VALUES (1, 'WEEKDAY'), (2, 'WEEKEND');
COMMIT;

-- ----------------------------
-- Table structure for tariffs
-- ----------------------------
DROP TABLE IF EXISTS "tariffs" CASCADE;
CREATE TABLE "tariffs" (
  "id" int8 NOT NULL DEFAULT nextval('tariffs_id_seq'::regclass),
  "workspace_type" int8 NOT NULL,
  "day_type" int8 NOT NULL,
  "multiplier" numeric(4,2) NOT NULL
)
;

-- ----------------------------
-- Records of tariffs
-- ----------------------------
BEGIN;
INSERT INTO "tariffs" ("id", "workspace_type", "day_type", "multiplier") VALUES (1, 8, 1, 1.00), (2, 8, 2, 1.25), (3, 9, 1, 2.00), (4, 9, 2, 2.50);
COMMIT;

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS "user_roles" CASCADE;
CREATE TABLE "user_roles" (
  "id" int8 NOT NULL DEFAULT nextval('user_roles_id_seq'::regclass),
  "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
BEGIN;
INSERT INTO "user_roles" ("id", "name") VALUES (1, 'Admin'), (2, 'User');
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS "users" CASCADE;
CREATE TABLE "users" (
  "id" int8 NOT NULL DEFAULT nextval('users_id_seq'::regclass),
  "email" text COLLATE "pg_catalog"."default" NOT NULL,
  "password_hash" text COLLATE "pg_catalog"."default" NOT NULL,
  "full_name" text COLLATE "pg_catalog"."default" NOT NULL,
  "role" int8 NOT NULL DEFAULT nextval('users_role_seq'::regclass),
  "is_blocked" bool NOT NULL DEFAULT false,
  "created_at" timestamp(6) NOT NULL DEFAULT now()
)
;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO "users" ("id", "email", "password_hash", "full_name", "role", "is_blocked", "created_at") VALUES (1, 'dd.nikolaenko@gmail.com', '$2a$12$yu7io7as/LNscjJTdBlet.HjCR6F9TPxKetvjfn9DNGftYI5fBrN6', 'Николаенко Дмитрий Денисович', 1, 'f', '2026-08-02 18:15:17.158'), (2, '1@user.test', '$2a$12$dfiDQYUuiJpSX0oXrq48IOw2I.1.fggf4gll0bLi1RI8k7IUa4gKm', 'Первый Тестовый Пользователь', 2, 't', '2026-08-02 18:17:42.808'), (3, '2@user.test', '$2a$12$sZ8GiOtVbePScH7xsCW91eD/FumDoWuhsPfv9P/A.cpJqm6BY9gnq', 'Второй Тестовый Пользователь', 2, 'f', '2026-08-27 00:02:15.809'), (4, '3@user.test', '$2a$12$Nl3dEcbTXoujpcmSkx1xO.i.APhhg6AobQgVFqObPI55PgpSQTgoW', 'Третий Тестовый Пользователь', 2, 'f', '2026-08-27 00:04:11.101'), (5, '4@user.test', '$2a$12$WXAjVTlOEvLgwxQxyVMjK.c3SJflL6MGObHs/BPE7L5iyFTveGvt6', '������� ���⮢� ���짮��⥫�', 2, 't', '2026-08-27 00:26:05.09'), (6, '5@user.test', '$2a$12$vuqAK.lIuMs0lq.c/L2kL.1or6nsAwXTTipB3Y7fSjNVbEsOt8SSC', 'Пятый Тестовый Пользователь', 2, 'f', '2026-09-04 16:10:33.452');
COMMIT;

-- ----------------------------
-- Table structure for workspace_types
-- ----------------------------
DROP TABLE IF EXISTS "workspace_types" CASCADE;
CREATE TABLE "workspace_types" (
  "id" int8 NOT NULL DEFAULT nextval('workspace_types_id_seq'::regclass),
  "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "max_participants_count" int4,
  "min_participants_count" int4 NOT NULL,
  "name_rus" varchar(50) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of workspace_types
-- ----------------------------
BEGIN;
INSERT INTO "workspace_types" ("id", "name", "max_participants_count", "min_participants_count", "name_rus") VALUES (8, 'DESC', 1, 1, 'Одиночное место'), (9, 'MEETING_ROOM', NULL, 2, 'Комната для собраний');
COMMIT;

-- ----------------------------
-- Table structure for workspaces
-- ----------------------------
DROP TABLE IF EXISTS "workspaces" CASCADE;
CREATE TABLE "workspaces" (
  "id" int8 NOT NULL DEFAULT nextval('workspaces_id_seq'::regclass),
  "type" int8 NOT NULL,
  "name" text COLLATE "pg_catalog"."default" NOT NULL,
  "capacity" int4 NOT NULL,
  "hourly_rate" numeric(10,2) NOT NULL,
  "is_active" bool NOT NULL DEFAULT true
)
;

-- ----------------------------
-- Records of workspaces
-- ----------------------------
BEGIN;
INSERT INTO "workspaces" ("id", "type", "name", "capacity", "hourly_rate", "is_active") VALUES (3, 8, 'Недострой', 1, 100.00, 'f'), (4, 8, 'Винтажный мастурбационный уголок', 1, 120.00, 't'), (5, 8, 'Со всеми удобствами', 1, 210.00, 't'), (6, 9, 'Каюта с голландским шутрвалом', 5, 1340.00, 't'), (7, 8, 'Пики точёные', 1, 75.00, 't'), (8, 9, 'Двухместная комната для просмотра аниме', 2, 320.00, 't'), (10, 9, 'Комната для совещаний с проектором', 3, 470.00, 't'), (11, 9, 'Комната для совещаний с доской и мелком', 3, 420.00, 't'), (12, 9, 'Построили на остатки бюджета', 4, 450.00, 't'), (13, 9, 'В стиле "Звёздных Войн"', 4, 730.00, 't'), (16, 8, 'CИКС СЭВЭН', 1, 67.00, 't'), (17, 9, 'Для группового просмотра Reels', 5, 1100.00, 't'), (14, 9, 'Для брейншторма или свиданий вслепую', 2, 340.00, 't'), (1, 8, 'Просто стул без кондиционера', 1, 150.00, 't'), (2, 8, 'Возле параши', 1, 40.00, 't');
COMMIT;

-- ----------------------------
-- Function structure for bookings_by_user_id
-- ----------------------------
DROP FUNCTION IF EXISTS "bookings_by_user_id"("user_id_param" int8) CASCADE;
CREATE OR REPLACE FUNCTION "bookings_by_user_id"("user_id_param" int8)
  RETURNS TABLE("booking_id" int8, "workspace_id" int8, "wtype_id" int8, "user_id" int8, "user_email" text, "user_full_name" text, "workspace_type" varchar, "workspace_name" text, "start_time" timestamp, "end_time" timestamp, "participants_count" int4, "status" varchar, "price" numeric, "created_at" timestamp) AS $BODY$BEGIN
	RETURN query (
		SELECT
			b.id,
			w."id",
			w.type,
			u.id,
			u.email,
			u.full_name,
			t."name",
			w."name",
			b.start_time,
			b.end_time,
			b.participants_count,
			s."name",
			b.price,
			b.created_at
			FROM bookings b
			JOIN workspaces w ON b.workspace_id = w."id"
			JOIN booking_statuses s ON b.status_id = s."id"
			JOIN workspace_types t ON w."type" = t."id"
			WHERE b.user_id = "user_id_param"
	);

	RETURN;
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

-- ----------------------------
-- Function structure for confirm_booking
-- ----------------------------
DROP FUNCTION IF EXISTS "confirm_booking"("booking_id_param" int8) CASCADE;
CREATE OR REPLACE FUNCTION "confirm_booking"("booking_id_param" int8)
  RETURNS "pg_catalog"."bool" AS $BODY$BEGIN
	UPDATE bookings
	SET status_id = (SELECT id FROM statuses WHERE name = 'CONFRIMED')
	WHERE "id" = "booking_id_param";
	RETURN TRUE;
	
	-- если запись нашлась и была изменена, возврат true
	IF FOUND THEN
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;	
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for delete_workspace
-- ----------------------------
DROP FUNCTION IF EXISTS "delete_workspace"("id_param" int8) CASCADE;
CREATE OR REPLACE FUNCTION "delete_workspace"("id_param" int8)
  RETURNS "pg_catalog"."bool" AS $BODY$BEGIN
	DELETE FROM workspaces w
	WHERE w."id" = "id_param";

	IF FOUND THEN
		RETURN TRUE;
	ELSE RETURN FALSE;
	END IF;
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for get_bookings
-- ----------------------------
DROP FUNCTION IF EXISTS "get_bookings"("user_id_param" int8, "workspace_id_param" int8, "status_id_param" int8, "start_time_param" timestamp, "end_time_param" timestamp) CASCADE;
CREATE OR REPLACE FUNCTION "get_bookings"("user_id_param" int8=NULL::bigint, "workspace_id_param" int8=NULL::bigint, "status_id_param" int8=NULL::bigint, "start_time_param" timestamp=NULL::timestamp without time zone, "end_time_param" timestamp=NULL::timestamp without time zone)
  RETURNS TABLE("booking_id" int8, "workspace_id" int8, "wtype_id" int8, "user_id" int8, "user_email" text, "user_full_name" text, "workspace_type" varchar, "workspace_name" text, "start_time" timestamp, "end_time" timestamp, "participants_count" int4, "status" varchar, "price" numeric, "created_at" timestamp) AS $BODY$
DECLARE query_text TEXT := $sql$
				SELECT
				b.id,
				w."id",
				w.type,
				u.id,
				u.email,
				u.full_name,
				t."name_rus",
				w."name",
				b.start_time,
				b.end_time,
				b.participants_count,
				s."name",
				b.price,
				b.created_at
				FROM bookings b
				JOIN booking_statuses s ON b.status_id = s."id"
				JOIN workspaces w ON b.workspace_id = w."id"
				JOIN workspace_types t ON w."type" = t."id"
				JOIN users u ON b.user_id = u."id"
				WHERE 1=1
				$sql$;
BEGIN
	-- Фильтр по пользователю
	IF "user_id_param" IS NOT NULL THEN 
		query_text := query_text || format(' AND b.user_id = %L', "user_id_param");
	END IF;
	-- Фильтр по рабочему пространству
	IF "workspace_id_param" IS NOT NULL THEN 
		query_text := query_text || format(' AND w."id" = %L', "workspace_id_param");
	END IF;
	-- Фильтр по статусу
	IF "status_id_param" IS NOT NULL THEN 
		query_text := query_text || format(' AND s."id" = %L', "status_id_param");
	END IF;
	-- Фильтр по дате начала
	IF "start_time_param" IS NOT NULL THEN 
		query_text := query_text || format(' AND b.created_at <= %L', "start_time_param");
	END IF;
	-- Фильтр по дате окончания
	IF "end_time_param" IS NOT NULL THEN 
		query_text := query_text || format(' AND b.created_at <= %L', "end_time_param");
	END IF;
	
	query_text := query_text || format(' ORDER BY s.sort_order');
	
	RETURN QUERY EXECUTE query_text;
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

-- ----------------------------
-- Function structure for get_bookings_by_workspace_id
-- ----------------------------
DROP FUNCTION IF EXISTS "get_bookings_by_workspace_id"("w_id_param" int8) CASCADE;
CREATE OR REPLACE FUNCTION "get_bookings_by_workspace_id"("w_id_param" int8)
  RETURNS TABLE("booking_id" int8, "workspace_id" int8, "wtype_id" int8, "user_id" int8, "user_email" text, "user_full_name" text, "workspace_type" varchar, "workspace_name" text, "start_time" timestamp, "end_time" timestamp, "participants_count" int4, "status" varchar, "price" numeric, "created_at" timestamp) AS $BODY$BEGIN
	RETURN query (
		SELECT
			b.id,
			w."id",
			w.type,
			u.id,
			u.email,
			u.full_name,
			t."name",
			w."name",
			b.start_time,
			b.end_time,
			b.participants_count,
			s."name",
			b.price,
			b.created_at
			FROM bookings b
			JOIN workspaces w ON b.workspace_id = w."id"
			JOIN booking_statuses s ON b.status_id = s."id"
			JOIN workspace_types t ON w."type" = t."id"
			WHERE b.workspace_id = "w_id_param"
	);

	RETURN;
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

-- ----------------------------
-- Function structure for get_user_by_email_for_auth
-- ----------------------------
DROP FUNCTION IF EXISTS "get_user_by_email_for_auth"("email_param" text) CASCADE;
CREATE OR REPLACE FUNCTION "get_user_by_email_for_auth"("email_param" text)
  RETURNS TABLE("id" int8, "email" text, "password_hash" text, "full_name" text, "role" varchar, "role_id" int8, "is_blocked" bool, "created_at" timestamp) AS $BODY$BEGIN
		RETURN query (
			SELECT 
				u.id, 
				u.email,
				u.password_hash,
				u.full_name,
				r.name,
				u."role",
				u.is_blocked,
				u.created_at
			FROM users u
			JOIN user_roles r ON u."role" = r."id"
			WHERE u.email = "email_param" LIMIT 1
		);
	RETURN;
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

-- ----------------------------
-- Function structure for get_users
-- ----------------------------
DROP FUNCTION IF EXISTS "get_users"("id_param" int8, "email_param" text, "full_name_param" text, "role_id_param" int8, "is_blocked_param" bool, "min_created_at_param" timestamp, "max_created_at_param" timestamp) CASCADE;
CREATE OR REPLACE FUNCTION "get_users"("id_param" int8=NULL::bigint, "email_param" text=NULL::text, "full_name_param" text=NULL::text, "role_id_param" int8=NULL::bigint, "is_blocked_param" bool=NULL::boolean, "min_created_at_param" timestamp=NULL::timestamp without time zone, "max_created_at_param" timestamp=NULL::timestamp without time zone)
  RETURNS TABLE("id" int8, "email" text, "full_name" text, "role" varchar, "role_id" int8, "is_blocked" bool, "created_at" timestamp) AS $BODY$
	DECLARE query_text TEXT := $sql$
		SELECT 
				u.id, 
				u.email,
				u.full_name,
				r.name,
				u."role",
				u.is_blocked,
				u.created_at
			FROM users u
			JOIN user_roles r ON u."role" = r."id"
			WHERE 1=1
		$sql$;
	BEGIN
		-- Фильтр по коду пользователя
		IF "id_param" IS NOT NULL THEN
			query_text := query_text || format(' AND u."id" = %L', "id_param");
		END IF;
		-- Фильтр по email
		IF "email_param" IS NOT NULL THEN
			query_text := query_text || format(' AND u."email" ~* %L', "email_param");
		END IF;
		-- Фильтр по ФИО
		IF "full_name_param" IS NOT NULL THEN
			query_text := query_text || format(' AND u."full_name" ~* %L', "full_name_param");
		END IF;
		-- Фильтр по коду роли
		IF "role_id_param" IS NOT NULL THEN
			query_text := query_text || format(' AND u."role" = %L', "role_id_param");
		END IF;
		-- Фильтр по статусу
		IF "is_blocked_param" IS NOT NULL THEN
			query_text := query_text || format(' AND u."is_blocked" = %L', "is_blocked_param");
		END IF;
		-- Фильтр по нижней границе даты регистрации
		IF "min_created_at_param" IS NOT NULL THEN
			query_text := query_text || format(' AND u."created_at" > %L', "min_created_at_param");
		END IF;
		-- Фильтр по верхней границе даты регистрации
		IF "max_created_at_param" IS NOT NULL THEN
			query_text := query_text || format(' AND u."created_at" < %L', "max_created_at_param");
		END IF;
		
		query_text := query_text || format(' ORDER BY u.is_blocked, u.role, u.full_name, u.email, u.created_at');

	RETURN QUERY EXECUTE query_text;
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

-- ----------------------------
-- Function structure for get_workspace_by_id
-- ----------------------------
DROP FUNCTION IF EXISTS "get_workspace_by_id"("id_param" int8) CASCADE;
CREATE OR REPLACE FUNCTION "get_workspace_by_id"("id_param" int8)
  RETURNS TABLE("id" int8, "type_id" int8, "name" text, "capacity" int4, "hourly_rate" numeric, "is_active" bool, "status" text, "type" varchar, "min_participants_count" int4, "max_participants_count" int4) AS $BODY$BEGIN
		RETURN query(
			SELECT
				w."id",
				w."type",
				w."name",
				w.capacity,
				w.hourly_rate,
				w.is_active,
				CASE
					WHEN w.is_active = true THEN 'Активно'
					WHEN w.is_active = false THEN 'Заблокировано'
				END as "status",
				t."name_rus",
				t.min_participants_count,
				t.max_participants_count
				FROM workspaces w
				JOIN workspace_types t ON w.type = t.id
				WHERE w.id = "id_param"
		);

	RETURN;
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

-- ----------------------------
-- Function structure for get_workspaces
-- ----------------------------
DROP FUNCTION IF EXISTS "get_workspaces"("id_param" int8, "capacity_param" int4, "min_hourly_rate_param" numeric, "max_hourly_rate_param" numeric, "name_param" text, "is_active_param" bool, "type_id_param" int8) CASCADE;
CREATE OR REPLACE FUNCTION "get_workspaces"("id_param" int8=NULL::bigint, "capacity_param" int4=NULL::integer, "min_hourly_rate_param" numeric=NULL::numeric, "max_hourly_rate_param" numeric=NULL::numeric, "name_param" text=NULL::text, "is_active_param" bool=NULL::boolean, "type_id_param" int8=NULL::bigint)
  RETURNS TABLE("id" int8, "type_id" int8, "name" text, "capacity" int4, "hourly_rate" numeric, "is_active" bool, "status" text, "type" varchar, "min_participants_count" int4, "max_participants_count" int4) AS $BODY$
	DECLARE query_text TEXT := $sql$
				SELECT
				w."id",
				w."type",
				w."name",
				w.capacity,
				w.hourly_rate,
				w.is_active,
				CASE
					WHEN w.is_active = true THEN 'Активно'
					WHEN w.is_active = false THEN 'Заблокировано'
				END as "status",
				t."name_rus",
				t.min_participants_count,
				t.max_participants_count
				FROM workspaces w
				JOIN workspace_types t ON w.type = t.id
				WHERE 1=1
				$sql$;
	BEGIN
	-- Фильтр по коду рабочего пространства
	IF "id_param" IS NOT NULL THEN
		query_text := query_text || format(' AND w."id" = %L', "id_param");
	END IF;
	-- Фильтр по вместимости
	IF "capacity_param" IS NOT NULL THEN
		query_text := query_text || format(' AND w."capacity" = %L', "capacity_param");
	END IF;
	-- Фильтр по минмальной часовой стоимости
	IF "min_hourly_rate_param" IS NOT NULL THEN
		query_text := query_text || format(' AND w.hourly_rate >= %L', "min_hourly_rate_param");
	END IF;
	-- Фильтр по максимальной часовой стоимости
	IF "max_hourly_rate_param" IS NOT NULL THEN
		query_text := query_text || format(' AND w.hourly_rate <= %L', "max_hourly_rate_param");
	END IF;
	-- Фильтр по названию
	IF "name_param" IS NOT NULL THEN
		query_text := query_text || format(' AND w."name" ~* %L', "name_param");
	END IF;
	-- Фильтр по статусу
	IF "is_active_param" IS NOT NULL THEN
		query_text := query_text || format(' AND w."is_active" = %L', "is_active_param");
	END IF;
	-- Фильтр по типу
	IF "type_id_param" IS NOT NULL THEN
		query_text := query_text || format(' AND t."id" = %L', "type_id_param");
	END IF;
	
	query_text := query_text || format(' ORDER BY t.id, w.capacity, w.hourly_rate');
	RETURN QUERY EXECUTE query_text;
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

-- ----------------------------
-- Function structure for get_workspaces_available_for_booking
-- ----------------------------
DROP FUNCTION IF EXISTS "get_workspaces_available_for_booking"("start_time_param" timestamp, "end_time_param" timestamp, "workspace_type_id_param" int8, "participants_count_param" int4) CASCADE;
CREATE OR REPLACE FUNCTION "get_workspaces_available_for_booking"("start_time_param" timestamp, "end_time_param" timestamp, "workspace_type_id_param" int8, "participants_count_param" int4)
  RETURNS TABLE("id" int8, "type_id" int8, "type_name" varchar, "type_name_rus" varchar, "name" text, "capacity" int4, "min_participants_count" int4, "max_participants_count" int4, "hourly_rate" numeric, "price" numeric) AS $BODY$BEGIN

	RETURN query (
		SELECT
			w."id",
			w."type" as "type_id",
			t."name" as "type_name",
			t."name_rus" as "type_name_rus",
			w.name,
			w.capacity,
			t.min_participants_count,
			t.max_participants_count,
			w.hourly_rate,
			-- Рассчёт стоимости бронирования --
			-- Количество минут --
			ROUND(EXTRACT(EPOCH from (end_time_param - start_time_param)) / 60.0
				-- Множитель тарифа --
				* (SELECT multiplier FROM tariffs t
					WHERE t.workspace_type = workspace_type_id_param 
					AND day_type = CASE 
										WHEN DATE_PART('dow', start_time_param) IN (1, 2, 3, 4, 5) THEN 1 -- Будни --
										WHEN DATE_PART('dow', start_time_param) IN (6, 0) THEN 2 -- Выходные --
									END)
				-- Цена за минуту --
				* (w.hourly_rate / 60.0), 2) as price
			FROM workspaces w
			JOIN workspace_types t ON w."type" = t."id"
			WHERE
				-- Только доступные --
				w.is_active = true
				AND w.type = workspace_type_id_param
				-- Выборка по вместимости --
				AND t.min_participants_count <= participants_count_param
				AND (t.max_participants_count IS NULL OR t.max_participants_count >= participants_count_param)
				-- Выборка по времени (рабочее пространство не должно быть уже забронировано) --
				AND w.id NOT IN (SELECT workspace_id FROM bookings 
										 WHERE start_time < end_time_param
										 AND end_time > start_time_param
										 AND status_id IN (SELECT bs."id" FROM booking_statuses bs 
										 WHERE bs."name" IN ('PENDING_PAYMENT', 'CONFIRMED')))
	);
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

-- ----------------------------
-- Function structure for insert_booking
-- ----------------------------
DROP FUNCTION IF EXISTS "insert_booking"("user_id_param" int8, "workspace_id_param" int8, "start_time_param" timestamp, "end_time_param" timestamp, "participants_count_param" int4, "price_param" numeric) CASCADE;
CREATE OR REPLACE FUNCTION "insert_booking"("user_id_param" int8, "workspace_id_param" int8, "start_time_param" timestamp, "end_time_param" timestamp, "participants_count_param" int4, "price_param" numeric)
  RETURNS "pg_catalog"."int8" AS $BODY$
	DECLARE
		new_booking_id int8;
	BEGIN
		INSERT INTO bookings (user_id, workspace_id, start_time, end_time, participants_count, status_id, price, created_at)
		VALUES 
			(
				user_id_param,
				workspace_id_param,
				start_time_param,
				end_time_param,
				participants_count_param,
				(SELECT id FROM booking_statuses bs WHERE bs."name" = 'PENDING_PAYMENT'),
				price_param,
				NOW()
			)
		RETURNING id INTO new_booking_id;
	RETURN new_booking_id;
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for insert_user
-- ----------------------------
DROP FUNCTION IF EXISTS "insert_user"("email_param" text, "password_hash_param" text, "full_name_param" text, "role_param" int8, "is_blocked_param" bool, "created_at_param" timestamp) CASCADE;
CREATE OR REPLACE FUNCTION "insert_user"("email_param" text, "password_hash_param" text, "full_name_param" text, "role_param" int8, "is_blocked_param" bool, "created_at_param" timestamp)
  RETURNS "pg_catalog"."int8" AS $BODY$
	DECLARE 
		new_user_id int8;
	BEGIN
		INSERT INTO users (email, password_hash, full_name, "role", is_blocked, created_at)
		VALUES (email_param, password_hash_param, full_name_param, role_param, is_blocked_param, created_at_param) RETURNING id INTO new_user_id;
	RETURN new_user_id;
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for insert_workspace
-- ----------------------------
DROP FUNCTION IF EXISTS "insert_workspace"("type_id_param" int8, "name_param" text, "capacity_param" int4, "hourly_rate_param" numeric, "is_active_param" bool) CASCADE;
CREATE OR REPLACE FUNCTION "insert_workspace"("type_id_param" int8, "name_param" text, "capacity_param" int4, "hourly_rate_param" numeric, "is_active_param" bool)
  RETURNS "pg_catalog"."int8" AS $BODY$
	DECLARE
		"new_workspace_id" int8;
	BEGIN
		INSERT INTO workspaces (type, name, capacity, hourly_rate, is_active)
		VALUES ("type_id_param", "name_param", "capacity_param", "hourly_rate_param", "is_active_param")
		RETURNING id INTO "new_workspace_id";
	RETURN "new_workspace_id";
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for set_booking_cancelled
-- ----------------------------
DROP FUNCTION IF EXISTS "set_booking_cancelled"("id_param" int8) CASCADE;
CREATE OR REPLACE FUNCTION "set_booking_cancelled"("id_param" int8)
  RETURNS "pg_catalog"."bool" AS $BODY$
	BEGIN	
			UPDATE bookings
			SET status_id = (
				SELECT id FROM booking_statuses
				WHERE "name" = CASE
					-- если бронь отменяется меньше чем за 3 часа до начала, то установить признак LATE_CANCELLED
					WHEN start_time - now() < interval '3 hours' THEN 'LATE_CANCELLED' 
					-- иначе просто CANCELLED
					ELSE 'CANCELLED'
				END
			)
			WHERE "id" = "id_param";
			
			-- если запись нашлась и была изменена, возврат true
			IF FOUND THEN
				RETURN TRUE;
			ELSE
				RETURN FALSE;
			END IF;					
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for toggle_user_active_status
-- ----------------------------
DROP FUNCTION IF EXISTS "toggle_user_active_status"("id_param" int8) CASCADE;
CREATE OR REPLACE FUNCTION "toggle_user_active_status"("id_param" int8)
  RETURNS "pg_catalog"."bool" AS $BODY$BEGIN
	UPDATE users
	SET is_blocked = NOT is_blocked
	WHERE id = "id_param";
	
	-- если запись нашлась и была изменена, возврат true
	IF FOUND THEN
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for toggle_workspace_active_status
-- ----------------------------
DROP FUNCTION IF EXISTS "toggle_workspace_active_status"("id_param" int8) CASCADE;
CREATE OR REPLACE FUNCTION "toggle_workspace_active_status"("id_param" int8)
  RETURNS "pg_catalog"."bool" AS $BODY$BEGIN
	UPDATE workspaces
	SET is_active = NOT is_active;
	
	-- если запись нашлась и была изменена, возврат true
	IF FOUND THEN
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;	
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for update_workspace
-- ----------------------------
DROP FUNCTION IF EXISTS "update_workspace"("id_param" int8, "type_id_param" int8, "name_param" text, "capacity_param" int4, "hourly_rate_param" numeric, "is_active_param" bool) CASCADE;
CREATE OR REPLACE FUNCTION "update_workspace"("id_param" int8, "type_id_param" int8, "name_param" text, "capacity_param" int4, "hourly_rate_param" numeric, "is_active_param" bool)
  RETURNS "pg_catalog"."bool" AS $BODY$BEGIN
	UPDATE workspaces 
	SET "type" = "type_id_param",
		"name" = "name_param",
		capacity = "capacity_param",
		hourly_rate = "hourly_rate_param",
		is_active = "is_active_param"
	 WHERE "id" = "id_param";

	-- если запись нашлась и была изменена, возврат true
	IF FOUND THEN
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;	
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "booking_statuses_id_seq"
OWNED BY "booking_statuses"."id";
SELECT setval('"booking_statuses_id_seq"', 5, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "bookings_id_seq"
OWNED BY "bookings"."id";
SELECT setval('"bookings_id_seq"', 9, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "day_types_id_seq"
OWNED BY "day_types"."id";
SELECT setval('"day_types_id_seq"', 2, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "tariffs_id_seq"
OWNED BY "tariffs"."id";
SELECT setval('"tariffs_id_seq"', 4, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "user_roles_id_seq"
OWNED BY "user_roles"."id";
SELECT setval('"user_roles_id_seq"', 2, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "users_id_seq"
OWNED BY "users"."id";
SELECT setval('"users_id_seq"', 6, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "users_role_seq"
OWNED BY "users"."role";
SELECT setval('"users_role_seq"', 2, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "workspace_types_id_seq"
OWNED BY "workspace_types"."id";
SELECT setval('"workspace_types_id_seq"', 9, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "workspaces_id_seq"
OWNED BY "workspaces"."id";
SELECT setval('"workspaces_id_seq"', 17, true);

-- ----------------------------
-- Uniques structure for table booking_statuses
-- ----------------------------
ALTER TABLE "booking_statuses" ADD CONSTRAINT "booking_statuses_name_key" UNIQUE ("name");
ALTER TABLE "booking_statuses" ADD CONSTRAINT "booking_statuses_sort_order_key" UNIQUE ("sort_order");

-- ----------------------------
-- Primary Key structure for table booking_statuses
-- ----------------------------
ALTER TABLE "booking_statuses" ADD CONSTRAINT "booking_statuses_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bookings
-- ----------------------------
ALTER TABLE "bookings" ADD CONSTRAINT "bookings_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table day_types
-- ----------------------------
ALTER TABLE "day_types" ADD CONSTRAINT "day_types_name_key" UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table day_types
-- ----------------------------
ALTER TABLE "day_types" ADD CONSTRAINT "day_types_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tariffs
-- ----------------------------
ALTER TABLE "tariffs" ADD CONSTRAINT "tariffs_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table user_roles
-- ----------------------------
ALTER TABLE "user_roles" ADD CONSTRAINT "user_roles_name_key" UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table user_roles
-- ----------------------------
ALTER TABLE "user_roles" ADD CONSTRAINT "user_roles_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table users
-- ----------------------------
ALTER TABLE "users" ADD CONSTRAINT "users_email_key" UNIQUE ("email");

-- ----------------------------
-- Primary Key structure for table users
-- ----------------------------
ALTER TABLE "users" ADD CONSTRAINT "users_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table workspace_types
-- ----------------------------
ALTER TABLE "workspace_types" ADD CONSTRAINT "workspace_types_name_key" UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table workspace_types
-- ----------------------------
ALTER TABLE "workspace_types" ADD CONSTRAINT "workspace_types_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table workspaces
-- ----------------------------
ALTER TABLE "workspaces" ADD CONSTRAINT "workspaces_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table bookings
-- ----------------------------
ALTER TABLE "bookings" ADD CONSTRAINT "bookings_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE "bookings" ADD CONSTRAINT "bookings_workspace_id_fkey" FOREIGN KEY ("workspace_id") REFERENCES "workspaces" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- ----------------------------
-- Foreign Keys structure for table tariffs
-- ----------------------------
ALTER TABLE "tariffs" ADD CONSTRAINT "tariffs_day_type_fkey" FOREIGN KEY ("day_type") REFERENCES "day_types" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE "tariffs" ADD CONSTRAINT "tariffs_workspace_type_fkey" FOREIGN KEY ("workspace_type") REFERENCES "workspace_types" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- ----------------------------
-- Foreign Keys structure for table users
-- ----------------------------
ALTER TABLE "users" ADD CONSTRAINT "users_role_fkey" FOREIGN KEY ("role") REFERENCES "user_roles" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- ----------------------------
-- Foreign Keys structure for table workspaces
-- ----------------------------
ALTER TABLE "workspaces" ADD CONSTRAINT "workspaces_type_fkey" FOREIGN KEY ("type") REFERENCES "workspace_types" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;
