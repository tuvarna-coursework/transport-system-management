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
	user_password varchar(255) NOT NULL,
	userprofile_id integer,
	usertype_id integer,
	user_location_id integer NOT NULL,
	CONSTRAINT "User_pk" PRIMARY KEY (user_id)

);
-- ddl-end --
-- ALTER TABLE "TransportSystem"."Users" OWNER TO postgres;
-- ddl-end --

-- object: "TransportSystem"."UsersRole" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."UsersRole" CASCADE;
CREATE TABLE "TransportSystem"."UsersRole" (
	user_id integer,
	role_id integer
);
-- ddl-end --
-- ALTER TABLE "TransportSystem"."UsersRole" OWNER TO postgres;
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

-- object: "TransportSystem"."Trip" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."Trip" CASCADE;
CREATE TABLE "TransportSystem"."Trip" (
	trip_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	trip_type_id integer,
	trip_route_id integer NOT NULL,
	trip_departure_date date NOT NULL,
	trip_arrival_date date NOT NULL,
	trip_capacity integer NOT NULL,
	trip_transporttype_id integer NOT NULL,
	trip_maxtickets_per_user integer NOT NULL,
	trip_ticket_availability integer DEFAULT 0,
	trip_ticket_price decimal DEFAULT 0.0,
	trip_duration integer NOT NULL,
	trip_hour_of_departure varchar(50) NOT NULL,
	CONSTRAINT "Trip_pk" PRIMARY KEY (trip_id)
);
-- ddl-end --
-- ALTER TABLE "TransportSystem"."Trip" OWNER TO postgres;
-- ddl-end --

-- object: "TransportSystem"."Request" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."Request" CASCADE;
CREATE TABLE "TransportSystem"."Request" (
	request_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	ticket_quantity integer NOT NULL,
	request_trip_id integer NOT NULL,
	request_status varchar(50) NOT NULL,
	CONSTRAINT "Request_pk" PRIMARY KEY (request_id)
);

-- object: "TransportSystem"."Notification" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."Notification" CASCADE;
CREATE TABLE "TransportSystem"."Notification" (
	notification_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	sender_id integer NOT NULL,
	receiver_id integer NOT NULL,
	notification_message TEXT NOT NULL,
	CONSTRAINT "Notification_pk" PRIMARY KEY (notification_id)
);

-- object: "TransportSystem"."Request" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."Request" CASCADE;
CREATE TABLE "TransportSystem"."Route" (
	route_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	route_departurelocation_id integer NOT NULL,
	route_arrivallocation_id integer NOT NULL,
	CONSTRAINT "Route_pk" PRIMARY KEY (route_id)
);

CREATE TABLE "TransportSystem"."RouteAttachment" (
	route_attachment_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	route_id integer NOT NULL,
	location_id integer NOT NULL,
	hour_of_arrival varchar(50) NOT NULL,
	CONSTRAINT "RouteAttachment_pk" PRIMARY KEY (route_attachment_id)
);


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
	usertype_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	usertype_name varchar(50) NOT NULL,
	CONSTRAINT "UserType_pk" PRIMARY KEY (usertype_id)

);
-- ddl-end --
-- ALTER TABLE "TransportSystem"."UserType" OWNER TO postgres;
-- ddl-end --

-- object: "TransportSystem"."UsersTrip" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."UsersTrip" CASCADE;
CREATE TABLE "TransportSystem"."UsersTrip" (
	user_id integer NOT NULL,
	trip_id integer NOT NULL
);
-- ddl-end --
-- ALTER TABLE "TransportSystem"."UsersTrip" OWNER TO postgres;
-- ddl-end --

-- object: "TransportSystem"."UsersTicket" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."UsersTicket" CASCADE;
CREATE TABLE "TransportSystem"."UsersTicket" (
	user_id integer NOT NULL,
	ticket_id integer NOT NULL
);

-- object: "TransportSystem"."CompanyCashier" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."CompanyCashier" CASCADE;
CREATE TABLE "TransportSystem"."CompanyCashier" (
	company_id integer NOT NULL,
	cashier_id integer NOT NULL
);

-- object: "TransportSystem"."TripCashier" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."TripCashier" CASCADE;
CREATE TABLE "TransportSystem"."TripCashier"(
	trip_id integer NOT NULL,
	cashier_id integer NOT NULL
);

-- object: "TransportSystem"."Ticket" | type: TABLE --
-- DROP TABLE IF EXISTS "TransportSystem"."Ticket" CASCADE;
CREATE TABLE "TransportSystem"."Ticket" (
	ticket_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	ticket_purchasedate date NOT NULL,
	trip_id integer NOT NULL,
	ticket_departurelocation_id integer NOT NULL,
	ticket_arrivallocation_id integer NOT NULL, 
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
-- ALTER TABLE "TransportSystem"."UsersRole" DROP CONSTRAINT IF EXISTS user_id CASCADE;
ALTER TABLE "TransportSystem"."UsersRole" ADD CONSTRAINT user_id FOREIGN KEY (user_id)
REFERENCES "TransportSystem"."Users" (user_id) MATCH FULL
ON DELETE NO ACTION ON UPDATE CASCADE;
-- ddl-end --

-- object: role_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."UsersRole" DROP CONSTRAINT IF EXISTS role_id CASCADE;
ALTER TABLE "TransportSystem"."UsersRole" ADD CONSTRAINT role_id FOREIGN KEY (role_id)
REFERENCES "TransportSystem"."Role" (role_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: user_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."UsersTicket" DROP CONSTRAINT IF EXISTS user_id CASCADE;
ALTER TABLE "TransportSystem"."UsersTicket" ADD CONSTRAINT user_id FOREIGN KEY (user_id)
REFERENCES "TransportSystem"."Users" (user_id) MATCH FULL
ON DELETE NO ACTION ON UPDATE CASCADE;

-- object: ticket_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."UsersTicket" DROP CONSTRAINT IF EXISTS ticket_id CASCADE;
ALTER TABLE "TransportSystem"."UsersTicket" ADD CONSTRAINT ticket_id FOREIGN KEY (ticket_id)
REFERENCES "TransportSystem"."Ticket" (ticket_id) MATCH FULL
ON DELETE NO ACTION ON UPDATE CASCADE;

-- object: company_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."CompanyCashier" DROP CONSTRAINT IF EXISTS company_id CASCADE;
ALTER TABLE "TransportSystem"."CompanyCashier" ADD CONSTRAINT company_id FOREIGN KEY (company_id)
REFERENCES "TransportSystem"."Users" (user_id) MATCH FULL
ON DELETE NO ACTION ON UPDATE CASCADE;

-- object: cashier_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."CompanyCashier" DROP CONSTRAINT IF EXISTS cashier_id CASCADE;
ALTER TABLE "TransportSystem"."CompanyCashier" ADD CONSTRAINT cashier_id FOREIGN KEY (cashier_id)
REFERENCES "TransportSystem"."Users" (user_id) MATCH FULL
ON DELETE NO ACTION ON UPDATE CASCADE;

-- object: trip_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."TripCashier" DROP CONSTRAINT IF EXISTS trip_id CASCADE;
ALTER TABLE "TransportSystem"."TripCashier" ADD CONSTRAINT trip_id FOREIGN KEY (trip_id)
REFERENCES "TransportSystem"."Trip" (trip_id) MATCH FULL
ON DELETE NO ACTION ON UPDATE CASCADE;

-- object: trip_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."TripCashier" DROP CONSTRAINT IF EXISTS trip_id CASCADE;
ALTER TABLE "TransportSystem"."TripCashier" ADD CONSTRAINT cashier_id FOREIGN KEY (cashier_id)
REFERENCES "TransportSystem"."Users" (user_id) MATCH FULL
ON DELETE NO ACTION ON UPDATE CASCADE;

-- object: sender_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Notification" DROP CONSTRAINT IF EXISTS sender_id CASCADE;
ALTER TABLE "TransportSystem"."Notification" ADD CONSTRAINT sender_id FOREIGN KEY (sender_id)
REFERENCES "TransportSystem"."Users"(user_id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;

-- object: receiver_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Notification" DROP CONSTRAINT IF EXISTS receiver_id CASCADE;
ALTER TABLE "TransportSystem"."Notification" ADD CONSTRAINT receiver_id FOREIGN KEY (receiver_id)
REFERENCES "TransportSystem"."Users"(user_id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;

-- object: trip_type_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Trip" DROP CONSTRAINT IF EXISTS trip_type_id CASCADE;
ALTER TABLE "TransportSystem"."Trip" ADD CONSTRAINT trip_type_id FOREIGN KEY (trip_type_id)
REFERENCES "TransportSystem"."TripType" (triptype_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --



-- object: trip_location_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Trip" DROP CONSTRAINT IF EXISTS trip_location_id CASCADE;
ALTER TABLE "TransportSystem"."Trip" ADD CONSTRAINT trip_route_id FOREIGN KEY (trip_route_id)
REFERENCES "TransportSystem"."Route" (route_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: trip_transporttype_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Trip" DROP CONSTRAINT IF EXISTS trip_transporttype_id CASCADE;
ALTER TABLE "TransportSystem"."Trip" ADD CONSTRAINT trip_transporttype_id FOREIGN KEY (trip_transporttype_id)
REFERENCES "TransportSystem"."TransportType" (transport_type_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: request_trip_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Request" DROP CONSTRAINT IF EXISTS request_trip_id CASCADE;
ALTER TABLE "TransportSystem"."Request" ADD CONSTRAINT request_trip_id FOREIGN KEY (request_trip_id)
REFERENCES "TransportSystem"."Trip" (trip_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: user_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."UsersTrip" DROP CONSTRAINT IF EXISTS user_id CASCADE;
ALTER TABLE "TransportSystem"."UsersTrip" ADD CONSTRAINT user_id FOREIGN KEY (user_id)
REFERENCES "TransportSystem"."Users" (user_id) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: trip_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."UsersTrip" DROP CONSTRAINT IF EXISTS trip_id CASCADE;
ALTER TABLE "TransportSystem"."UsersTrip" ADD CONSTRAINT trip_id FOREIGN KEY (trip_id)
REFERENCES "TransportSystem"."Trip" (trip_id) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: trip_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Ticket" DROP CONSTRAINT IF EXISTS trip_id CASCADE;
ALTER TABLE "TransportSystem"."Ticket" ADD CONSTRAINT trip_id FOREIGN KEY (trip_id)
REFERENCES "TransportSystem"."Trip" (trip_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: trip_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Ticket" DROP CONSTRAINT IF EXISTS trip_id CASCADE;
ALTER TABLE "TransportSystem"."Ticket" ADD CONSTRAINT ticket_departurelocation_id FOREIGN KEY (ticket_departurelocation_id)
REFERENCES "TransportSystem"."Location" (location_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: trip_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Ticket" DROP CONSTRAINT IF EXISTS trip_id CASCADE;
ALTER TABLE "TransportSystem"."Ticket" ADD CONSTRAINT ticket_arrivallocation_id FOREIGN KEY (ticket_arrivallocation_id)
REFERENCES "TransportSystem"."Location" (location_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: route_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Route" DROP CONSTRAINT IF EXISTS route_id CASCADE;
ALTER TABLE "TransportSystem"."Route" ADD CONSTRAINT route_id FOREIGN KEY (route_id)
REFERENCES "TransportSystem"."Route" (route_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: route_departurelocation_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Route" DROP CONSTRAINT IF EXISTS route_departurelocation_id CASCADE;
ALTER TABLE "TransportSystem"."Route" ADD CONSTRAINT route_departurelocation_id FOREIGN KEY (route_departurelocation_id)
REFERENCES "TransportSystem"."Location" (location_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: route_arrivallocation_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."Route" DROP CONSTRAINT IF EXISTS route_arrivallocation_id CASCADE;
ALTER TABLE "TransportSystem"."Route" ADD CONSTRAINT route_arrivallocation_id FOREIGN KEY (route_arrivallocation_id)
REFERENCES "TransportSystem"."Location" (location_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: route_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."RouteAttachment" DROP CONSTRAINT IF EXISTS route_id CASCADE;
ALTER TABLE "TransportSystem"."RouteAttachment" ADD CONSTRAINT route_id FOREIGN KEY (route_id)
REFERENCES "TransportSystem"."Route" (route_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end -- 

-- object: route_id | type: CONSTRAINT --
-- ALTER TABLE "TransportSystem"."RouteAttachment" DROP CONSTRAINT IF EXISTS route_id CASCADE;
ALTER TABLE "TransportSystem"."RouteAttachment" ADD CONSTRAINT location_id FOREIGN KEY (location_id)
REFERENCES "TransportSystem"."Location" (location_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end -- 




