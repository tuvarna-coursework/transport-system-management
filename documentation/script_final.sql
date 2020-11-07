-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.2
-- PostgreSQL version: 12.0
-- Project Site: pgmodeler.io
-- Model Author: ---


-- Database creation must be done outside a multicommand file.
-- These commands were put in this file only as a convenience.
-- -- object: new_database | type: DATABASE --
-- -- DROP DATABASE IF EXISTS new_database;
-- CREATE DATABASE new_database;
-- -- ddl-end --
-- 

-- object: "TransportSystem" | type: SCHEMA --
-- DROP SCHEMA IF EXISTS "TransportSystem" CASCADE;
CREATE SCHEMA "TransportSystem";
-- ddl-end --
-- ALTER SCHEMA "TransportSystem" OWNER TO postgres;
-- ddl-end --

SET search_path TO pg_catalog,public,"TransportSystem";
-- ddl-end --

-- object: "TransportSystem"."Role" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."Role" CASCADE;
CREATE TABLE "TransportSystem"."Role" (
	role_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	role_name varchar(50) NOT NULL,
	CONSTRAINT "Role_pk" PRIMARY KEY (role_id)

);
-- ddl-end --
-- ALTER TABLE "TransportSystem"."Role" OWNER TO postgres;
-- ddl-end --

-- object: "TransportSystem"."Users" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."Users" CASCADE;
CREATE TABLE "TransportSystem"."Users" (
	user_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	user_fullname varchar(50) NOT NULL,
	user_loginname varchar(50) NOT NULL,
	user_password varchar(50) NOT NULL,
	userprofile_id integer,
	usertype_id integer,
	user_location_id integer NOT NULL,
	CONSTRAINT "User_pk" PRIMARY KEY (user_id)

);
-- ddl-end --
-- ALTER TABLE "TransportSystem"."Users" OWNER TO postgres;
-- ddl-end --

-- object: "TransportSystem"."UserRole" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."UserRole" CASCADE;
CREATE TABLE "TransportSystem"."UserRole" (
	user_id integer,
	role_id integer
);
-- ddl-end --
-- ALTER TABLE "TransportSystem"."UserRole" OWNER TO postgres;
-- ddl-end --

-- object: "TransportSystem"."UserTicket" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."UserTicket" CASCADE;
CREATE TABLE "TransportSystem"."UserTicket" (
	user_id integer,
	ticket_id integer
);
-- ddl-end --
-- ALTER TABLE "TransportSystem"."UserTicket" OWNER TO postgres;
-- ddl-end --

-- object: "TransportSystem"."TripType" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."TripType" CASCADE;
CREATE TABLE "TransportSystem"."TripType" (
	triptype_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	triptype_name varchar(50) NOT NULL,
	CONSTRAINT "TripType_pk" PRIMARY KEY (triptype_id)

);
-- ddl-end --
-- ALTER TABLE "TransportSystem"."TripType" OWNER TO postgres;
-- ddl-end --

-- object: "TransportSystem"."Location" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."Location" CASCADE;
CREATE TABLE "TransportSystem"."Location" (
	location_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	location_name varchar(50) NOT NULL,
	CONSTRAINT "TripLocation_pk" PRIMARY KEY (location_id)

);
-- ddl-end --
-- ALTER TABLE "TransportSystem"."Location" OWNER TO postgres;
-- ddl-end --

-- object: "TransportSystem"."TransportType" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."TransportType" CASCADE;
CREATE TABLE "TransportSystem"."TransportType" (
	transport_type_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	transport_type_name varchar(50) NOT NULL,
	CONSTRAINT "TransportType_pk" PRIMARY KEY (transport_type_id)
);
-- ddl-end --
-- ALTER TABLE "TransportSystem"."TransportType" OWNER TO postgres;
-- ddl-end --

-- object: "TransportSystem"."PurchaseRestriction" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."PurchaseRestriction" CASCADE;
CREATE TABLE "TransportSystem"."PurchaseRestriction" (
	purchase_restriction_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	purchase_restriction_name varchar(50) NOT NULL,
	CONSTRAINT "PurchaseRestriction_pk" PRIMARY KEY (purchase_restriction_id)

);
-- ddl-end --
-- ALTER TABLE "TransportSystem"."PurchaseRestriction" OWNER TO postgres;
-- ddl-end --

-- object: "TransportSystem"."Trip" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."Trip" CASCADE;
CREATE TABLE "TransportSystem"."Trip" (
	trip_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	trip_type_id integer,
	trip_departure_location_id integer NOT NULL,
	trip_arrival_location_id integer NOT NULL,
	trip_departure_date date NOT NULL,
	trip_arrival_date date NOT NULL,
	trip_capacity integer NOT NULL,
	trip_transporttype_id integer NOT NULL,
	trip_purchase_restriction_id integer NOT NULL,
	trip_ticket_availability integer NOT NULL,
	CONSTRAINT "Trip_pk" PRIMARY KEY (trip_id)
);
-- ddl-end --
-- ALTER TABLE "TransportSystem"."Trip" OWNER TO postgres;
-- ddl-end --

-- object: "TransportSystem"."UserProfile" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."UserProfile" CASCADE;
CREATE TABLE "TransportSystem"."UserProfile" (
	userprofile_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	userprofile_rating decimal NOT NULL,
	userprofile_honorarium decimal NOT NULL,
	CONSTRAINT "UserProfile_pk" PRIMARY KEY (userprofile_id)

);
-- ddl-end --
-- ALTER TABLE "TransportSystem"."UserProfile" OWNER TO postgres;
-- ddl-end --

-- object: "TransportSystem"."UserType" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."UserType" CASCADE;
CREATE TABLE "TransportSystem"."UserType" (
	usertype_id integer NOT NULL,
	usertype_name varchar(50) NOT NULL,
	CONSTRAINT "UserType_pk" PRIMARY KEY (usertype_id)

);
-- ddl-end --
-- ALTER TABLE "TransportSystem"."UserType" OWNER TO postgres;
-- ddl-end --

-- object: "TransportSystem"."UserTrip" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."UserTrip" CASCADE;
CREATE TABLE "TransportSystem"."UserTrip" (
	user_id integer NOT NULL,
	trip_id integer NOT NULL,
	"isOrganizer" boolean,
	"isDistributor" boolean
);
-- ddl-end --
-- ALTER TABLE "TransportSystem"."UserTrip" OWNER TO postgres;
-- ddl-end --

-- object: "TransportSystem"."Ticket" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."Ticket" CASCADE;
CREATE TABLE "TransportSystem"."Ticket" (
	ticket_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	ticket_purchasedate date NOT NULL,
	user_id integer NOT NULL,
	trip_id integer NOT NULL,
	CONSTRAINT "Ticket_pk" PRIMARY KEY (ticket_id)

);
-- ddl-end --
-- ALTER TABLE "TransportSystem"."Ticket" OWNER TO postgres;
-- ddl-end --

-- object: userprofile_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Users" DROP CONSTRAINT IF EXISTS userprofile_id CASCADE;
ALTER TABLE "TransportSystem"."Users" ADD CONSTRAINT userprofile_id FOREIGN KEY (userprofile_id)
REFERENCES "TransportSystem"."UserProfile" (userprofile_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: usertype_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Users" DROP CONSTRAINT IF EXISTS usertype_id CASCADE;
ALTER TABLE "TransportSystem"."Users" ADD CONSTRAINT usertype_id FOREIGN KEY (usertype_id)
REFERENCES "TransportSystem"."UserType" (usertype_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE RESTRICT, ADD CONSTRAINT user_location_id FOREIGN KEY (user_location_id)
REFERENCES "TransportSystem"."Location" (location_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: user_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."UserRole" DROP CONSTRAINT IF EXISTS user_id CASCADE;
ALTER TABLE "TransportSystem"."UserRole" ADD CONSTRAINT user_id FOREIGN KEY (user_id)
REFERENCES "TransportSystem"."Users" (user_id) MATCH FULL
ON DELETE NO ACTION ON UPDATE CASCADE;
-- ddl-end --

-- object: role_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."UserRole" DROP CONSTRAINT IF EXISTS role_id CASCADE;
ALTER TABLE "TransportSystem"."UserRole" ADD CONSTRAINT role_id FOREIGN KEY (role_id)
REFERENCES "TransportSystem"."Role" (role_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: user_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."UserTicket" DROP CONSTRAINT IF EXISTS user_id CASCADE;
ALTER TABLE "TransportSystem"."UserTicket" ADD CONSTRAINT user_id FOREIGN KEY (user_id)
REFERENCES "TransportSystem"."Users" (user_id) MATCH FULL
ON DELETE NO ACTION ON UPDATE CASCADE;
-- ddl-end --

-- object: role_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."UserTicket" DROP CONSTRAINT IF EXISTS ticket-id CASCADE;
ALTER TABLE "TransportSystem"."UserTicket" ADD CONSTRAINT ticket_id FOREIGN KEY (ticket_id)
REFERENCES "TransportSystem"."Ticket" (ticket_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: trip_type_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Trip" DROP CONSTRAINT IF EXISTS trip_type_id CASCADE;
ALTER TABLE "TransportSystem"."Trip" ADD CONSTRAINT trip_type_id FOREIGN KEY (trip_type_id)
REFERENCES "TransportSystem"."TripType" (triptype_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: trip_location_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Trip" DROP CONSTRAINT IF EXISTS trip_location_id CASCADE;
ALTER TABLE "TransportSystem"."Trip" ADD CONSTRAINT trip_departure_location_id FOREIGN KEY (trip_departure_location_id)
REFERENCES "TransportSystem"."Location" (location_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE, ADD CONSTRAINT trip_arrival_location_id FOREIGN KEY (trip_arrival_location_id)
REFERENCES "TransportSystem"."Location" (location_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: trip_transporttype_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Trip" DROP CONSTRAINT IF EXISTS trip_transporttype_id CASCADE;
ALTER TABLE "TransportSystem"."Trip" ADD CONSTRAINT trip_transporttype_id FOREIGN KEY (trip_transporttype_id)
REFERENCES "TransportSystem"."TransportType" (transport_type_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: trip_purchaserestriction_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Trip" DROP CONSTRAINT IF EXISTS trip_purchaserestriction_id CASCADE;
ALTER TABLE "TransportSystem"."Trip" ADD CONSTRAINT trip_purchaserestriction_id FOREIGN KEY (trip_purchase_restriction_id)
REFERENCES "TransportSystem"."PurchaseRestriction" (purchase_restriction_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: user_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."UserTrip" DROP CONSTRAINT IF EXISTS user_id CASCADE;
ALTER TABLE "TransportSystem"."UserTrip" ADD CONSTRAINT user_id FOREIGN KEY (user_id)
REFERENCES "TransportSystem"."Users" (user_id) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: trip_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."UserTrip" DROP CONSTRAINT IF EXISTS trip_id CASCADE;
ALTER TABLE "TransportSystem"."UserTrip" ADD CONSTRAINT trip_id FOREIGN KEY (trip_id)
REFERENCES "TransportSystem"."Trip" (trip_id) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: trip_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Ticket" DROP CONSTRAINT IF EXISTS trip_id CASCADE;
ALTER TABLE "TransportSystem"."Ticket" ADD CONSTRAINT trip_id FOREIGN KEY (trip_id)
REFERENCES "TransportSystem"."Trip" (trip_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE, ADD CONSTRAINT user_id FOREIGN KEY (user_id)
REFERENCES "TransportSystem"."Users" (user_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --


