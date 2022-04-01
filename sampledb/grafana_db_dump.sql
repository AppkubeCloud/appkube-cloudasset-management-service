--
-- PostgreSQL database dump
--

-- Dumped from database version 12.5
-- Dumped by pg_dump version 12.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: alert; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.alert (
    id integer NOT NULL,
    version bigint NOT NULL,
    dashboard_id bigint NOT NULL,
    panel_id bigint NOT NULL,
    org_id bigint NOT NULL,
    name character varying(255) NOT NULL,
    message text NOT NULL,
    state character varying(190) NOT NULL,
    settings text NOT NULL,
    frequency bigint NOT NULL,
    handler bigint NOT NULL,
    severity text NOT NULL,
    silenced boolean NOT NULL,
    execution_error text NOT NULL,
    eval_data text,
    eval_date timestamp without time zone,
    new_state_date timestamp without time zone NOT NULL,
    state_changes integer NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    "for" bigint
);


ALTER TABLE public.alert OWNER TO postgres;

--
-- Name: alert_configuration; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.alert_configuration (
    id integer NOT NULL,
    alertmanager_configuration text NOT NULL,
    configuration_version character varying(3) NOT NULL,
    created_at integer NOT NULL,
    "default" boolean DEFAULT false NOT NULL,
    org_id bigint DEFAULT 0 NOT NULL
);


ALTER TABLE public.alert_configuration OWNER TO postgres;

--
-- Name: alert_configuration_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.alert_configuration_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.alert_configuration_id_seq OWNER TO postgres;

--
-- Name: alert_configuration_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.alert_configuration_id_seq OWNED BY public.alert_configuration.id;


--
-- Name: alert_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.alert_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.alert_id_seq OWNER TO postgres;

--
-- Name: alert_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.alert_id_seq OWNED BY public.alert.id;


--
-- Name: alert_instance; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.alert_instance (
    rule_org_id bigint NOT NULL,
    rule_uid character varying(40) DEFAULT 0 NOT NULL,
    labels text NOT NULL,
    labels_hash character varying(190) NOT NULL,
    current_state character varying(190) NOT NULL,
    current_state_since bigint NOT NULL,
    last_eval_time bigint NOT NULL,
    current_state_end bigint DEFAULT 0 NOT NULL
);


ALTER TABLE public.alert_instance OWNER TO postgres;

--
-- Name: alert_notification; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.alert_notification (
    id integer NOT NULL,
    org_id bigint NOT NULL,
    name character varying(190) NOT NULL,
    type character varying(255) NOT NULL,
    settings text NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    is_default boolean DEFAULT false NOT NULL,
    frequency bigint,
    send_reminder boolean DEFAULT false,
    disable_resolve_message boolean DEFAULT false NOT NULL,
    uid character varying(40),
    secure_settings text
);


ALTER TABLE public.alert_notification OWNER TO postgres;

--
-- Name: alert_notification_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.alert_notification_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.alert_notification_id_seq OWNER TO postgres;

--
-- Name: alert_notification_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.alert_notification_id_seq OWNED BY public.alert_notification.id;


--
-- Name: alert_notification_state; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.alert_notification_state (
    id integer NOT NULL,
    org_id bigint NOT NULL,
    alert_id bigint NOT NULL,
    notifier_id bigint NOT NULL,
    state character varying(50) NOT NULL,
    version bigint NOT NULL,
    updated_at bigint NOT NULL,
    alert_rule_state_updated_version bigint NOT NULL
);


ALTER TABLE public.alert_notification_state OWNER TO postgres;

--
-- Name: alert_notification_state_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.alert_notification_state_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.alert_notification_state_id_seq OWNER TO postgres;

--
-- Name: alert_notification_state_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.alert_notification_state_id_seq OWNED BY public.alert_notification_state.id;


--
-- Name: alert_rule; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.alert_rule (
    id integer NOT NULL,
    org_id bigint NOT NULL,
    title character varying(190) NOT NULL,
    condition character varying(190) NOT NULL,
    data text NOT NULL,
    updated timestamp without time zone NOT NULL,
    interval_seconds bigint DEFAULT 60 NOT NULL,
    version integer DEFAULT 0 NOT NULL,
    uid character varying(40) DEFAULT 0 NOT NULL,
    namespace_uid character varying(40) NOT NULL,
    rule_group character varying(190) NOT NULL,
    no_data_state character varying(15) DEFAULT 'NoData'::character varying NOT NULL,
    exec_err_state character varying(15) DEFAULT 'Alerting'::character varying NOT NULL,
    "for" bigint DEFAULT 0 NOT NULL,
    annotations text,
    labels text,
    dashboard_uid character varying(40),
    panel_id bigint
);


ALTER TABLE public.alert_rule OWNER TO postgres;

--
-- Name: alert_rule_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.alert_rule_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.alert_rule_id_seq OWNER TO postgres;

--
-- Name: alert_rule_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.alert_rule_id_seq OWNED BY public.alert_rule.id;


--
-- Name: alert_rule_tag; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.alert_rule_tag (
    id integer NOT NULL,
    alert_id bigint NOT NULL,
    tag_id bigint NOT NULL
);


ALTER TABLE public.alert_rule_tag OWNER TO postgres;

--
-- Name: alert_rule_tag_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.alert_rule_tag_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.alert_rule_tag_id_seq OWNER TO postgres;

--
-- Name: alert_rule_tag_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.alert_rule_tag_id_seq OWNED BY public.alert_rule_tag.id;


--
-- Name: alert_rule_version; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.alert_rule_version (
    id integer NOT NULL,
    rule_org_id bigint NOT NULL,
    rule_uid character varying(40) DEFAULT 0 NOT NULL,
    rule_namespace_uid character varying(40) NOT NULL,
    rule_group character varying(190) NOT NULL,
    parent_version integer NOT NULL,
    restored_from integer NOT NULL,
    version integer NOT NULL,
    created timestamp without time zone NOT NULL,
    title character varying(190) NOT NULL,
    condition character varying(190) NOT NULL,
    data text NOT NULL,
    interval_seconds bigint NOT NULL,
    no_data_state character varying(15) DEFAULT 'NoData'::character varying NOT NULL,
    exec_err_state character varying(15) DEFAULT 'Alerting'::character varying NOT NULL,
    "for" bigint DEFAULT 0 NOT NULL,
    annotations text,
    labels text
);


ALTER TABLE public.alert_rule_version OWNER TO postgres;

--
-- Name: alert_rule_version_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.alert_rule_version_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.alert_rule_version_id_seq OWNER TO postgres;

--
-- Name: alert_rule_version_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.alert_rule_version_id_seq OWNED BY public.alert_rule_version.id;


--
-- Name: annotation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.annotation (
    id integer NOT NULL,
    org_id bigint NOT NULL,
    alert_id bigint,
    user_id bigint,
    dashboard_id bigint,
    panel_id bigint,
    category_id bigint,
    type character varying(25) NOT NULL,
    title text NOT NULL,
    text text NOT NULL,
    metric character varying(255),
    prev_state character varying(25) NOT NULL,
    new_state character varying(25) NOT NULL,
    data text NOT NULL,
    epoch bigint NOT NULL,
    region_id bigint DEFAULT 0,
    tags character varying(500),
    created bigint DEFAULT 0,
    updated bigint DEFAULT 0,
    epoch_end bigint DEFAULT 0 NOT NULL
);


ALTER TABLE public.annotation OWNER TO postgres;

--
-- Name: annotation_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.annotation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.annotation_id_seq OWNER TO postgres;

--
-- Name: annotation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.annotation_id_seq OWNED BY public.annotation.id;


--
-- Name: annotation_tag; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.annotation_tag (
    id integer NOT NULL,
    annotation_id bigint NOT NULL,
    tag_id bigint NOT NULL
);


ALTER TABLE public.annotation_tag OWNER TO postgres;

--
-- Name: annotation_tag_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.annotation_tag_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.annotation_tag_id_seq OWNER TO postgres;

--
-- Name: annotation_tag_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.annotation_tag_id_seq OWNED BY public.annotation_tag.id;


--
-- Name: api_key; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.api_key (
    id integer NOT NULL,
    org_id bigint NOT NULL,
    name character varying(190) NOT NULL,
    key character varying(190) NOT NULL,
    role character varying(255) NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    expires bigint,
    service_account_id bigint
);


ALTER TABLE public.api_key OWNER TO postgres;

--
-- Name: api_key_id_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.api_key_id_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.api_key_id_seq1 OWNER TO postgres;

--
-- Name: api_key_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.api_key_id_seq1 OWNED BY public.api_key.id;


--
-- Name: builtin_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.builtin_role (
    id integer NOT NULL,
    role character varying(190) NOT NULL,
    role_id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    org_id bigint DEFAULT 0 NOT NULL
);


ALTER TABLE public.builtin_role OWNER TO postgres;

--
-- Name: builtin_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.builtin_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.builtin_role_id_seq OWNER TO postgres;

--
-- Name: builtin_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.builtin_role_id_seq OWNED BY public.builtin_role.id;


--
-- Name: cache_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cache_data (
    cache_key character varying(168) NOT NULL,
    data bytea NOT NULL,
    expires integer NOT NULL,
    created_at integer NOT NULL
);


ALTER TABLE public.cache_data OWNER TO postgres;

--
-- Name: dashboard; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dashboard (
    id integer NOT NULL,
    version integer NOT NULL,
    slug character varying(189) NOT NULL,
    title character varying(189) NOT NULL,
    data text NOT NULL,
    org_id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    updated_by integer,
    created_by integer,
    gnet_id bigint,
    plugin_id character varying(189),
    folder_id bigint DEFAULT 0 NOT NULL,
    is_folder boolean DEFAULT false NOT NULL,
    has_acl boolean DEFAULT false NOT NULL,
    uid character varying(40),
    uuid character varying(40),
    source_json_ref character varying(1000),
    input_source_id character varying(255),
    tenant_id character varying(255),
    account_id character varying(255),
    is_cloud boolean DEFAULT false NOT NULL,
    cloud_name character varying(255),
    element_type character varying(255),
    file_name character varying(255),
    input_type character varying(255)
);


ALTER TABLE public.dashboard OWNER TO postgres;

--
-- Name: dashboard_acl; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dashboard_acl (
    id integer NOT NULL,
    org_id bigint NOT NULL,
    dashboard_id bigint NOT NULL,
    user_id bigint,
    team_id bigint,
    permission smallint DEFAULT 4 NOT NULL,
    role character varying(20),
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


ALTER TABLE public.dashboard_acl OWNER TO postgres;

--
-- Name: dashboard_acl_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.dashboard_acl_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.dashboard_acl_id_seq OWNER TO postgres;

--
-- Name: dashboard_acl_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.dashboard_acl_id_seq OWNED BY public.dashboard_acl.id;


--
-- Name: dashboard_id_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.dashboard_id_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.dashboard_id_seq1 OWNER TO postgres;

--
-- Name: dashboard_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.dashboard_id_seq1 OWNED BY public.dashboard.id;


--
-- Name: dashboard_provisioning; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dashboard_provisioning (
    id integer NOT NULL,
    dashboard_id bigint,
    name character varying(150) NOT NULL,
    external_id text NOT NULL,
    updated integer DEFAULT 0 NOT NULL,
    check_sum character varying(32)
);


ALTER TABLE public.dashboard_provisioning OWNER TO postgres;

--
-- Name: dashboard_provisioning_id_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.dashboard_provisioning_id_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.dashboard_provisioning_id_seq1 OWNER TO postgres;

--
-- Name: dashboard_provisioning_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.dashboard_provisioning_id_seq1 OWNED BY public.dashboard_provisioning.id;


--
-- Name: dashboard_snapshot; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dashboard_snapshot (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    key character varying(190) NOT NULL,
    delete_key character varying(190) NOT NULL,
    org_id bigint NOT NULL,
    user_id bigint NOT NULL,
    external boolean NOT NULL,
    external_url character varying(255) NOT NULL,
    dashboard text NOT NULL,
    expires timestamp without time zone NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    external_delete_url character varying(255),
    dashboard_encrypted bytea
);


ALTER TABLE public.dashboard_snapshot OWNER TO postgres;

--
-- Name: dashboard_snapshot_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.dashboard_snapshot_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.dashboard_snapshot_id_seq OWNER TO postgres;

--
-- Name: dashboard_snapshot_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.dashboard_snapshot_id_seq OWNED BY public.dashboard_snapshot.id;


--
-- Name: dashboard_tag; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dashboard_tag (
    id integer NOT NULL,
    dashboard_id bigint NOT NULL,
    term character varying(50) NOT NULL
);


ALTER TABLE public.dashboard_tag OWNER TO postgres;

--
-- Name: dashboard_tag_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.dashboard_tag_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.dashboard_tag_id_seq OWNER TO postgres;

--
-- Name: dashboard_tag_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.dashboard_tag_id_seq OWNED BY public.dashboard_tag.id;


--
-- Name: dashboard_version; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dashboard_version (
    id integer NOT NULL,
    dashboard_id bigint NOT NULL,
    parent_version integer NOT NULL,
    restored_from integer NOT NULL,
    version integer NOT NULL,
    created timestamp without time zone NOT NULL,
    created_by bigint NOT NULL,
    message text NOT NULL,
    data text NOT NULL
);


ALTER TABLE public.dashboard_version OWNER TO postgres;

--
-- Name: dashboard_version_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.dashboard_version_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.dashboard_version_id_seq OWNER TO postgres;

--
-- Name: dashboard_version_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.dashboard_version_id_seq OWNED BY public.dashboard_version.id;


--
-- Name: data_keys; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.data_keys (
    name character varying(100) NOT NULL,
    active boolean NOT NULL,
    scope character varying(30) NOT NULL,
    provider character varying(50) NOT NULL,
    encrypted_data bytea NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


ALTER TABLE public.data_keys OWNER TO postgres;

--
-- Name: data_source; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.data_source (
    id integer NOT NULL,
    org_id bigint NOT NULL,
    version integer NOT NULL,
    type character varying(255) NOT NULL,
    name character varying(190) NOT NULL,
    access character varying(255) NOT NULL,
    url character varying(255) NOT NULL,
    password character varying(255),
    "user" character varying(255),
    database character varying(255),
    basic_auth boolean NOT NULL,
    basic_auth_user character varying(255),
    basic_auth_password character varying(255),
    is_default boolean NOT NULL,
    json_data text,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    with_credentials boolean DEFAULT false NOT NULL,
    secure_json_data text,
    read_only boolean,
    uid character varying(40) DEFAULT 0 NOT NULL,
    tenant_id character varying(255),
    account_id character varying(255)
);


ALTER TABLE public.data_source OWNER TO postgres;

--
-- Name: data_source_id_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.data_source_id_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.data_source_id_seq1 OWNER TO postgres;

--
-- Name: data_source_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.data_source_id_seq1 OWNED BY public.data_source.id;


--
-- Name: kv_store; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.kv_store (
    id integer NOT NULL,
    org_id bigint NOT NULL,
    namespace character varying(190) NOT NULL,
    key character varying(190) NOT NULL,
    value text NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


ALTER TABLE public.kv_store OWNER TO postgres;

--
-- Name: kv_store_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.kv_store_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.kv_store_id_seq OWNER TO postgres;

--
-- Name: kv_store_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.kv_store_id_seq OWNED BY public.kv_store.id;


--
-- Name: library_element; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.library_element (
    id integer NOT NULL,
    org_id bigint NOT NULL,
    folder_id bigint NOT NULL,
    uid character varying(40) NOT NULL,
    name character varying(150) NOT NULL,
    kind bigint NOT NULL,
    type character varying(40) NOT NULL,
    description character varying(255) NOT NULL,
    model text NOT NULL,
    created timestamp without time zone NOT NULL,
    created_by bigint NOT NULL,
    updated timestamp without time zone NOT NULL,
    updated_by bigint NOT NULL,
    version bigint NOT NULL
);


ALTER TABLE public.library_element OWNER TO postgres;

--
-- Name: library_element_connection; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.library_element_connection (
    id integer NOT NULL,
    element_id bigint NOT NULL,
    kind bigint NOT NULL,
    connection_id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    created_by bigint NOT NULL
);


ALTER TABLE public.library_element_connection OWNER TO postgres;

--
-- Name: library_element_connection_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.library_element_connection_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.library_element_connection_id_seq OWNER TO postgres;

--
-- Name: library_element_connection_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.library_element_connection_id_seq OWNED BY public.library_element_connection.id;


--
-- Name: library_element_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.library_element_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.library_element_id_seq OWNER TO postgres;

--
-- Name: library_element_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.library_element_id_seq OWNED BY public.library_element.id;


--
-- Name: login_attempt; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.login_attempt (
    id integer NOT NULL,
    username character varying(190) NOT NULL,
    ip_address character varying(30) NOT NULL,
    created integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.login_attempt OWNER TO postgres;

--
-- Name: login_attempt_id_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.login_attempt_id_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.login_attempt_id_seq1 OWNER TO postgres;

--
-- Name: login_attempt_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.login_attempt_id_seq1 OWNED BY public.login_attempt.id;


--
-- Name: migration_log; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.migration_log (
    id integer NOT NULL,
    migration_id character varying(255) NOT NULL,
    sql text NOT NULL,
    success boolean NOT NULL,
    error text NOT NULL,
    "timestamp" timestamp without time zone NOT NULL
);


ALTER TABLE public.migration_log OWNER TO postgres;

--
-- Name: migration_log_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.migration_log_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.migration_log_id_seq OWNER TO postgres;

--
-- Name: migration_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.migration_log_id_seq OWNED BY public.migration_log.id;


--
-- Name: ngalert_configuration; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ngalert_configuration (
    id integer NOT NULL,
    org_id bigint NOT NULL,
    alertmanagers text,
    created_at integer NOT NULL,
    updated_at integer NOT NULL,
    send_alerts_to smallint DEFAULT 0 NOT NULL
);


ALTER TABLE public.ngalert_configuration OWNER TO postgres;

--
-- Name: ngalert_configuration_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ngalert_configuration_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ngalert_configuration_id_seq OWNER TO postgres;

--
-- Name: ngalert_configuration_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ngalert_configuration_id_seq OWNED BY public.ngalert_configuration.id;


--
-- Name: org; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.org (
    id integer NOT NULL,
    version integer NOT NULL,
    name character varying(190) NOT NULL,
    address1 character varying(255),
    address2 character varying(255),
    city character varying(255),
    state character varying(255),
    zip_code character varying(50),
    country character varying(255),
    billing_email character varying(255),
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


ALTER TABLE public.org OWNER TO postgres;

--
-- Name: org_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.org_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.org_id_seq OWNER TO postgres;

--
-- Name: org_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.org_id_seq OWNED BY public.org.id;


--
-- Name: org_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.org_user (
    id integer NOT NULL,
    org_id bigint NOT NULL,
    user_id bigint NOT NULL,
    role character varying(20) NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


ALTER TABLE public.org_user OWNER TO postgres;

--
-- Name: org_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.org_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.org_user_id_seq OWNER TO postgres;

--
-- Name: org_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.org_user_id_seq OWNED BY public.org_user.id;


--
-- Name: permission; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.permission (
    id integer NOT NULL,
    role_id bigint NOT NULL,
    action character varying(190) NOT NULL,
    scope character varying(190) NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


ALTER TABLE public.permission OWNER TO postgres;

--
-- Name: permission_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.permission_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.permission_id_seq OWNER TO postgres;

--
-- Name: permission_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.permission_id_seq OWNED BY public.permission.id;


--
-- Name: playlist; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.playlist (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    "interval" character varying(255) NOT NULL,
    org_id bigint NOT NULL
);


ALTER TABLE public.playlist OWNER TO postgres;

--
-- Name: playlist_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.playlist_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.playlist_id_seq OWNER TO postgres;

--
-- Name: playlist_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.playlist_id_seq OWNED BY public.playlist.id;


--
-- Name: playlist_item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.playlist_item (
    id integer NOT NULL,
    playlist_id bigint NOT NULL,
    type character varying(255) NOT NULL,
    value text NOT NULL,
    title text NOT NULL,
    "order" integer NOT NULL
);


ALTER TABLE public.playlist_item OWNER TO postgres;

--
-- Name: playlist_item_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.playlist_item_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.playlist_item_id_seq OWNER TO postgres;

--
-- Name: playlist_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.playlist_item_id_seq OWNED BY public.playlist_item.id;


--
-- Name: plugin_setting; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.plugin_setting (
    id integer NOT NULL,
    org_id bigint,
    plugin_id character varying(190) NOT NULL,
    enabled boolean NOT NULL,
    pinned boolean NOT NULL,
    json_data text,
    secure_json_data text,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    plugin_version character varying(50)
);


ALTER TABLE public.plugin_setting OWNER TO postgres;

--
-- Name: plugin_setting_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.plugin_setting_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.plugin_setting_id_seq OWNER TO postgres;

--
-- Name: plugin_setting_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.plugin_setting_id_seq OWNED BY public.plugin_setting.id;


--
-- Name: preferences; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.preferences (
    id integer NOT NULL,
    org_id bigint NOT NULL,
    user_id bigint NOT NULL,
    version integer NOT NULL,
    home_dashboard_id bigint NOT NULL,
    timezone character varying(50) NOT NULL,
    theme character varying(20) NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    team_id bigint,
    week_start character varying(10)
);


ALTER TABLE public.preferences OWNER TO postgres;

--
-- Name: preferences_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.preferences_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.preferences_id_seq OWNER TO postgres;

--
-- Name: preferences_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.preferences_id_seq OWNED BY public.preferences.id;


--
-- Name: provenance_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.provenance_type (
    id integer NOT NULL,
    org_id bigint NOT NULL,
    record_key character varying(190) NOT NULL,
    record_type character varying(190) NOT NULL,
    provenance character varying(190) NOT NULL
);


ALTER TABLE public.provenance_type OWNER TO postgres;

--
-- Name: provenance_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.provenance_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.provenance_type_id_seq OWNER TO postgres;

--
-- Name: provenance_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.provenance_type_id_seq OWNED BY public.provenance_type.id;


--
-- Name: query_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.query_history (
    id integer NOT NULL,
    uid character varying(40) NOT NULL,
    org_id bigint NOT NULL,
    datasource_uid character varying(40) NOT NULL,
    created_by integer NOT NULL,
    created_at integer NOT NULL,
    comment text NOT NULL,
    queries text NOT NULL
);


ALTER TABLE public.query_history OWNER TO postgres;

--
-- Name: query_history_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.query_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.query_history_id_seq OWNER TO postgres;

--
-- Name: query_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.query_history_id_seq OWNED BY public.query_history.id;


--
-- Name: quota; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.quota (
    id integer NOT NULL,
    org_id bigint,
    user_id bigint,
    target character varying(190) NOT NULL,
    "limit" bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


ALTER TABLE public.quota OWNER TO postgres;

--
-- Name: quota_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.quota_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.quota_id_seq OWNER TO postgres;

--
-- Name: quota_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.quota_id_seq OWNED BY public.quota.id;


--
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
    id integer NOT NULL,
    name character varying(190) NOT NULL,
    description text,
    version bigint NOT NULL,
    org_id bigint NOT NULL,
    uid character varying(40) NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    display_name character varying(190),
    group_name character varying(190)
);


ALTER TABLE public.role OWNER TO postgres;

--
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.role_id_seq OWNER TO postgres;

--
-- Name: role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.role_id_seq OWNED BY public.role.id;


--
-- Name: seed_assignment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.seed_assignment (
    builtin_role character varying(190) NOT NULL,
    role_name character varying(190) NOT NULL
);


ALTER TABLE public.seed_assignment OWNER TO postgres;

--
-- Name: server_lock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.server_lock (
    id integer NOT NULL,
    operation_uid character varying(100) NOT NULL,
    version bigint NOT NULL,
    last_execution bigint NOT NULL
);


ALTER TABLE public.server_lock OWNER TO postgres;

--
-- Name: server_lock_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.server_lock_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.server_lock_id_seq OWNER TO postgres;

--
-- Name: server_lock_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.server_lock_id_seq OWNED BY public.server_lock.id;


--
-- Name: session; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.session (
    key character(16) NOT NULL,
    data bytea NOT NULL,
    expiry integer NOT NULL
);


ALTER TABLE public.session OWNER TO postgres;

--
-- Name: short_url; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.short_url (
    id integer NOT NULL,
    org_id bigint NOT NULL,
    uid character varying(40) NOT NULL,
    path text NOT NULL,
    created_by integer NOT NULL,
    created_at integer NOT NULL,
    last_seen_at integer
);


ALTER TABLE public.short_url OWNER TO postgres;

--
-- Name: short_url_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.short_url_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.short_url_id_seq OWNER TO postgres;

--
-- Name: short_url_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.short_url_id_seq OWNED BY public.short_url.id;


--
-- Name: star; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.star (
    id integer NOT NULL,
    user_id bigint NOT NULL,
    dashboard_id bigint NOT NULL
);


ALTER TABLE public.star OWNER TO postgres;

--
-- Name: star_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.star_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.star_id_seq OWNER TO postgres;

--
-- Name: star_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.star_id_seq OWNED BY public.star.id;


--
-- Name: tag; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tag (
    id integer NOT NULL,
    key character varying(100) NOT NULL,
    value character varying(100) NOT NULL
);


ALTER TABLE public.tag OWNER TO postgres;

--
-- Name: tag_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tag_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tag_id_seq OWNER TO postgres;

--
-- Name: tag_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tag_id_seq OWNED BY public.tag.id;


--
-- Name: team; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.team (
    id integer NOT NULL,
    name character varying(190) NOT NULL,
    org_id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    email character varying(190)
);


ALTER TABLE public.team OWNER TO postgres;

--
-- Name: team_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.team_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.team_id_seq OWNER TO postgres;

--
-- Name: team_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.team_id_seq OWNED BY public.team.id;


--
-- Name: team_member; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.team_member (
    id integer NOT NULL,
    org_id bigint NOT NULL,
    team_id bigint NOT NULL,
    user_id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    external boolean,
    permission smallint
);


ALTER TABLE public.team_member OWNER TO postgres;

--
-- Name: team_member_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.team_member_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.team_member_id_seq OWNER TO postgres;

--
-- Name: team_member_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.team_member_id_seq OWNED BY public.team_member.id;


--
-- Name: team_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.team_role (
    id integer NOT NULL,
    org_id bigint NOT NULL,
    team_id bigint NOT NULL,
    role_id bigint NOT NULL,
    created timestamp without time zone NOT NULL
);


ALTER TABLE public.team_role OWNER TO postgres;

--
-- Name: team_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.team_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.team_role_id_seq OWNER TO postgres;

--
-- Name: team_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.team_role_id_seq OWNED BY public.team_role.id;


--
-- Name: temp_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.temp_user (
    id integer NOT NULL,
    org_id bigint NOT NULL,
    version integer NOT NULL,
    email character varying(190) NOT NULL,
    name character varying(255),
    role character varying(20),
    code character varying(190) NOT NULL,
    status character varying(20) NOT NULL,
    invited_by_user_id bigint,
    email_sent boolean NOT NULL,
    email_sent_on timestamp without time zone,
    remote_addr character varying(255),
    created integer DEFAULT 0 NOT NULL,
    updated integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.temp_user OWNER TO postgres;

--
-- Name: temp_user_id_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.temp_user_id_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.temp_user_id_seq1 OWNER TO postgres;

--
-- Name: temp_user_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.temp_user_id_seq1 OWNED BY public.temp_user.id;


--
-- Name: test_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.test_data (
    id integer NOT NULL,
    metric1 character varying(20),
    metric2 character varying(150),
    value_big_int bigint,
    value_double double precision,
    value_float real,
    value_int integer,
    time_epoch bigint NOT NULL,
    time_date_time timestamp without time zone NOT NULL,
    time_time_stamp timestamp without time zone NOT NULL
);


ALTER TABLE public.test_data OWNER TO postgres;

--
-- Name: test_data_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.test_data_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.test_data_id_seq OWNER TO postgres;

--
-- Name: test_data_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.test_data_id_seq OWNED BY public.test_data.id;


--
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    id integer NOT NULL,
    version integer NOT NULL,
    login character varying(190) NOT NULL,
    email character varying(190) NOT NULL,
    name character varying(255),
    password character varying(255),
    salt character varying(50),
    rands character varying(50),
    company character varying(255),
    org_id bigint NOT NULL,
    is_admin boolean NOT NULL,
    email_verified boolean,
    theme character varying(255),
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    help_flags1 bigint DEFAULT 0 NOT NULL,
    last_seen_at timestamp without time zone,
    is_disabled boolean DEFAULT false NOT NULL,
    is_service_account boolean DEFAULT false
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- Name: user_auth; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_auth (
    id integer NOT NULL,
    user_id bigint NOT NULL,
    auth_module character varying(190) NOT NULL,
    auth_id character varying(190) NOT NULL,
    created timestamp without time zone NOT NULL,
    o_auth_access_token text,
    o_auth_refresh_token text,
    o_auth_token_type text,
    o_auth_expiry timestamp without time zone,
    o_auth_id_token text
);


ALTER TABLE public.user_auth OWNER TO postgres;

--
-- Name: user_auth_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_auth_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_auth_id_seq OWNER TO postgres;

--
-- Name: user_auth_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_auth_id_seq OWNED BY public.user_auth.id;


--
-- Name: user_auth_token; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_auth_token (
    id integer NOT NULL,
    user_id bigint NOT NULL,
    auth_token character varying(100) NOT NULL,
    prev_auth_token character varying(100) NOT NULL,
    user_agent character varying(255) NOT NULL,
    client_ip character varying(255) NOT NULL,
    auth_token_seen boolean NOT NULL,
    seen_at integer,
    rotated_at integer NOT NULL,
    created_at integer NOT NULL,
    updated_at integer NOT NULL,
    revoked_at integer
);


ALTER TABLE public.user_auth_token OWNER TO postgres;

--
-- Name: user_auth_token_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_auth_token_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_auth_token_id_seq OWNER TO postgres;

--
-- Name: user_auth_token_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_auth_token_id_seq OWNED BY public.user_auth_token.id;


--
-- Name: user_id_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_id_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_id_seq1 OWNER TO postgres;

--
-- Name: user_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_id_seq1 OWNED BY public."user".id;


--
-- Name: user_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_role (
    id integer NOT NULL,
    org_id bigint NOT NULL,
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    created timestamp without time zone NOT NULL
);


ALTER TABLE public.user_role OWNER TO postgres;

--
-- Name: user_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_role_id_seq OWNER TO postgres;

--
-- Name: user_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_role_id_seq OWNED BY public.user_role.id;


--
-- Name: alert id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alert ALTER COLUMN id SET DEFAULT nextval('public.alert_id_seq'::regclass);


--
-- Name: alert_configuration id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alert_configuration ALTER COLUMN id SET DEFAULT nextval('public.alert_configuration_id_seq'::regclass);


--
-- Name: alert_notification id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alert_notification ALTER COLUMN id SET DEFAULT nextval('public.alert_notification_id_seq'::regclass);


--
-- Name: alert_notification_state id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alert_notification_state ALTER COLUMN id SET DEFAULT nextval('public.alert_notification_state_id_seq'::regclass);


--
-- Name: alert_rule id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alert_rule ALTER COLUMN id SET DEFAULT nextval('public.alert_rule_id_seq'::regclass);


--
-- Name: alert_rule_tag id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alert_rule_tag ALTER COLUMN id SET DEFAULT nextval('public.alert_rule_tag_id_seq'::regclass);


--
-- Name: alert_rule_version id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alert_rule_version ALTER COLUMN id SET DEFAULT nextval('public.alert_rule_version_id_seq'::regclass);


--
-- Name: annotation id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.annotation ALTER COLUMN id SET DEFAULT nextval('public.annotation_id_seq'::regclass);


--
-- Name: annotation_tag id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.annotation_tag ALTER COLUMN id SET DEFAULT nextval('public.annotation_tag_id_seq'::regclass);


--
-- Name: api_key id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.api_key ALTER COLUMN id SET DEFAULT nextval('public.api_key_id_seq1'::regclass);


--
-- Name: builtin_role id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.builtin_role ALTER COLUMN id SET DEFAULT nextval('public.builtin_role_id_seq'::regclass);


--
-- Name: dashboard id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard ALTER COLUMN id SET DEFAULT nextval('public.dashboard_id_seq1'::regclass);


--
-- Name: dashboard_acl id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard_acl ALTER COLUMN id SET DEFAULT nextval('public.dashboard_acl_id_seq'::regclass);


--
-- Name: dashboard_provisioning id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard_provisioning ALTER COLUMN id SET DEFAULT nextval('public.dashboard_provisioning_id_seq1'::regclass);


--
-- Name: dashboard_snapshot id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard_snapshot ALTER COLUMN id SET DEFAULT nextval('public.dashboard_snapshot_id_seq'::regclass);


--
-- Name: dashboard_tag id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard_tag ALTER COLUMN id SET DEFAULT nextval('public.dashboard_tag_id_seq'::regclass);


--
-- Name: dashboard_version id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard_version ALTER COLUMN id SET DEFAULT nextval('public.dashboard_version_id_seq'::regclass);


--
-- Name: data_source id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.data_source ALTER COLUMN id SET DEFAULT nextval('public.data_source_id_seq1'::regclass);


--
-- Name: kv_store id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.kv_store ALTER COLUMN id SET DEFAULT nextval('public.kv_store_id_seq'::regclass);


--
-- Name: library_element id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.library_element ALTER COLUMN id SET DEFAULT nextval('public.library_element_id_seq'::regclass);


--
-- Name: library_element_connection id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.library_element_connection ALTER COLUMN id SET DEFAULT nextval('public.library_element_connection_id_seq'::regclass);


--
-- Name: login_attempt id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.login_attempt ALTER COLUMN id SET DEFAULT nextval('public.login_attempt_id_seq1'::regclass);


--
-- Name: migration_log id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.migration_log ALTER COLUMN id SET DEFAULT nextval('public.migration_log_id_seq'::regclass);


--
-- Name: ngalert_configuration id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ngalert_configuration ALTER COLUMN id SET DEFAULT nextval('public.ngalert_configuration_id_seq'::regclass);


--
-- Name: org id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.org ALTER COLUMN id SET DEFAULT nextval('public.org_id_seq'::regclass);


--
-- Name: org_user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.org_user ALTER COLUMN id SET DEFAULT nextval('public.org_user_id_seq'::regclass);


--
-- Name: permission id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permission ALTER COLUMN id SET DEFAULT nextval('public.permission_id_seq'::regclass);


--
-- Name: playlist id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist ALTER COLUMN id SET DEFAULT nextval('public.playlist_id_seq'::regclass);


--
-- Name: playlist_item id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist_item ALTER COLUMN id SET DEFAULT nextval('public.playlist_item_id_seq'::regclass);


--
-- Name: plugin_setting id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plugin_setting ALTER COLUMN id SET DEFAULT nextval('public.plugin_setting_id_seq'::regclass);


--
-- Name: preferences id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.preferences ALTER COLUMN id SET DEFAULT nextval('public.preferences_id_seq'::regclass);


--
-- Name: provenance_type id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.provenance_type ALTER COLUMN id SET DEFAULT nextval('public.provenance_type_id_seq'::regclass);


--
-- Name: query_history id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.query_history ALTER COLUMN id SET DEFAULT nextval('public.query_history_id_seq'::regclass);


--
-- Name: quota id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quota ALTER COLUMN id SET DEFAULT nextval('public.quota_id_seq'::regclass);


--
-- Name: role id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role ALTER COLUMN id SET DEFAULT nextval('public.role_id_seq'::regclass);


--
-- Name: server_lock id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.server_lock ALTER COLUMN id SET DEFAULT nextval('public.server_lock_id_seq'::regclass);


--
-- Name: short_url id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.short_url ALTER COLUMN id SET DEFAULT nextval('public.short_url_id_seq'::regclass);


--
-- Name: star id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.star ALTER COLUMN id SET DEFAULT nextval('public.star_id_seq'::regclass);


--
-- Name: tag id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tag ALTER COLUMN id SET DEFAULT nextval('public.tag_id_seq'::regclass);


--
-- Name: team id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team ALTER COLUMN id SET DEFAULT nextval('public.team_id_seq'::regclass);


--
-- Name: team_member id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_member ALTER COLUMN id SET DEFAULT nextval('public.team_member_id_seq'::regclass);


--
-- Name: team_role id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_role ALTER COLUMN id SET DEFAULT nextval('public.team_role_id_seq'::regclass);


--
-- Name: temp_user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.temp_user ALTER COLUMN id SET DEFAULT nextval('public.temp_user_id_seq1'::regclass);


--
-- Name: test_data id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.test_data ALTER COLUMN id SET DEFAULT nextval('public.test_data_id_seq'::regclass);


--
-- Name: user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user" ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq1'::regclass);


--
-- Name: user_auth id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_auth ALTER COLUMN id SET DEFAULT nextval('public.user_auth_id_seq'::regclass);


--
-- Name: user_auth_token id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_auth_token ALTER COLUMN id SET DEFAULT nextval('public.user_auth_token_id_seq'::regclass);


--
-- Name: user_role id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role ALTER COLUMN id SET DEFAULT nextval('public.user_role_id_seq'::regclass);


--
-- Data for Name: alert; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.alert (id, version, dashboard_id, panel_id, org_id, name, message, state, settings, frequency, handler, severity, silenced, execution_error, eval_data, eval_date, new_state_date, state_changes, created, updated, "for") FROM stdin;
\.


--
-- Data for Name: alert_configuration; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.alert_configuration (id, alertmanager_configuration, configuration_version, created_at, "default", org_id) FROM stdin;
1	{\n\t"alertmanager_config": {\n\t\t"route": {\n\t\t\t"receiver": "grafana-default-email"\n\t\t},\n\t\t"receivers": [{\n\t\t\t"name": "grafana-default-email",\n\t\t\t"grafana_managed_receiver_configs": [{\n\t\t\t\t"uid": "",\n\t\t\t\t"name": "email receiver",\n\t\t\t\t"type": "email",\n\t\t\t\t"isDefault": true,\n\t\t\t\t"settings": {\n\t\t\t\t\t"addresses": "<example@email.com>"\n\t\t\t\t}\n\t\t\t}]\n\t\t}]\n\t}\n}\n	v1	1647508873	t	1
\.


--
-- Data for Name: alert_instance; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.alert_instance (rule_org_id, rule_uid, labels, labels_hash, current_state, current_state_since, last_eval_time, current_state_end) FROM stdin;
\.


--
-- Data for Name: alert_notification; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.alert_notification (id, org_id, name, type, settings, created, updated, is_default, frequency, send_reminder, disable_resolve_message, uid, secure_settings) FROM stdin;
\.


--
-- Data for Name: alert_notification_state; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.alert_notification_state (id, org_id, alert_id, notifier_id, state, version, updated_at, alert_rule_state_updated_version) FROM stdin;
\.


--
-- Data for Name: alert_rule; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.alert_rule (id, org_id, title, condition, data, updated, interval_seconds, version, uid, namespace_uid, rule_group, no_data_state, exec_err_state, "for", annotations, labels, dashboard_uid, panel_id) FROM stdin;
\.


--
-- Data for Name: alert_rule_tag; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.alert_rule_tag (id, alert_id, tag_id) FROM stdin;
\.


--
-- Data for Name: alert_rule_version; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.alert_rule_version (id, rule_org_id, rule_uid, rule_namespace_uid, rule_group, parent_version, restored_from, version, created, title, condition, data, interval_seconds, no_data_state, exec_err_state, "for", annotations, labels) FROM stdin;
\.


--
-- Data for Name: annotation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.annotation (id, org_id, alert_id, user_id, dashboard_id, panel_id, category_id, type, title, text, metric, prev_state, new_state, data, epoch, region_id, tags, created, updated, epoch_end) FROM stdin;
\.


--
-- Data for Name: annotation_tag; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.annotation_tag (id, annotation_id, tag_id) FROM stdin;
\.


--
-- Data for Name: api_key; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.api_key (id, org_id, name, key, role, created, updated, expires, service_account_id) FROM stdin;
\.


--
-- Data for Name: builtin_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.builtin_role (id, role, role_id, created, updated, org_id) FROM stdin;
\.


--
-- Data for Name: cache_data; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cache_data (cache_key, data, expires, created_at) FROM stdin;
\.


--
-- Data for Name: dashboard; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dashboard (id, version, slug, title, data, org_id, created, updated, updated_by, created_by, gnet_id, plugin_id, folder_id, is_folder, has_acl, uid, uuid, source_json_ref, input_source_id, tenant_id, account_id, is_cloud, cloud_name, element_type, file_name, input_type) FROM stdin;
168	1	aws_vpc_ec2-single-panel_testawscloudwatch_00	AWS_VPC_ec2 single panel_TestAwsCloudwatch_00	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TestAwsCloudwatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_ec2 single panel_TestAwsCloudwatch_00","uid":"rkPVNvN7z","version":1}	1	2021-09-22 15:43:44	2021-09-22 15:43:44	1	1	0		0	f	f	rkPVNvN7z	uuid-1		TestAwsCloudwatch	78	1234	t	AWS	VPC	test_ds.json	Performance
169	1	aws_vpc_ec2-single-panel_testawscloudwatch_01	AWS_VPC_ec2 single panel_TestAwsCloudwatch_01	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TestAwsCloudwatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_ec2 single panel_TestAwsCloudwatch_01","uid":"OtEVNDH7z","version":1}	1	2021-09-22 15:43:44	2021-09-22 15:43:44	1	1	0		0	f	f	OtEVNDH7z	uuid-2		TestAwsCloudwatch	78	1234	t	AWS	VPC	test_ds.json	Performance
\.


--
-- Data for Name: dashboard_acl; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dashboard_acl (id, org_id, dashboard_id, user_id, team_id, permission, role, created, updated) FROM stdin;
1	-1	-1	\N	\N	1	Viewer	2017-06-20 00:00:00	2017-06-20 00:00:00
2	-1	-1	\N	\N	2	Editor	2017-06-20 00:00:00	2017-06-20 00:00:00
\.


--
-- Data for Name: dashboard_provisioning; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dashboard_provisioning (id, dashboard_id, name, external_id, updated, check_sum) FROM stdin;
\.


--
-- Data for Name: dashboard_snapshot; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dashboard_snapshot (id, name, key, delete_key, org_id, user_id, external, external_url, dashboard, expires, created, updated, external_delete_url, dashboard_encrypted) FROM stdin;
\.


--
-- Data for Name: dashboard_tag; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dashboard_tag (id, dashboard_id, term) FROM stdin;
\.


--
-- Data for Name: dashboard_version; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dashboard_version (id, dashboard_id, parent_version, restored_from, version, created, created_by, message, data) FROM stdin;
1	1	0	0	1	2021-05-24 11:33:12	1		{"annotations":{"list":[{"builtIn":1,"datasource":"-- Grafana --","enable":true,"hide":true,"iconColor":"rgba(0, 211, 255, 1)","name":"Annotations \\u0026 Alerts","type":"dashboard"}]},"editable":true,"gnetId":null,"graphTooltip":0,"hideControls":false,"id":null,"links":[],"panels":[{"aliasColors":{},"bars":false,"dashLength":10,"dashes":false,"datasource":null,"fieldConfig":{"defaults":{"custom":{}},"overrides":[]},"fill":1,"fillGradient":0,"gridPos":{"h":9,"w":12,"x":0,"y":0},"hiddenSeries":false,"id":2,"legend":{"avg":false,"current":false,"max":false,"min":false,"show":true,"total":false,"values":false},"lines":true,"linewidth":1,"nullPointMode":"null","options":{"alertThreshold":true},"percentage":false,"pluginVersion":"5.0.0","pointradius":2,"points":false,"renderer":"flot","seriesOverrides":[],"spaceLength":10,"stack":false,"steppedLine":false,"thresholds":[],"timeFrom":null,"timeRegions":[],"timeShift":null,"title":"Panel Title","tooltip":{"shared":true,"sort":0,"value_type":"individual"},"type":"graph","xaxis":{"buckets":null,"mode":"time","name":null,"show":true,"values":[]},"yaxes":[{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true},{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true}],"yaxis":{"align":false,"alignLevel":null}}],"schemaVersion":26,"style":"dark","tags":[],"templating":{"list":[]},"time":{"from":"now-6h","to":"now"},"timepicker":{},"timezone":"","title":"Test_Cloud","uid":"ux68I13Gz","version":1}
2	1	1	0	2	2021-05-24 11:33:44	1		{"annotations":{"list":[{"builtIn":1,"datasource":"-- Grafana --","enable":true,"hide":true,"iconColor":"rgba(0, 211, 255, 1)","name":"Annotations \\u0026 Alerts","type":"dashboard"}]},"editable":true,"gnetId":null,"graphTooltip":0,"id":1,"links":[],"panels":[{"aliasColors":{},"bars":false,"dashLength":10,"dashes":false,"datasource":null,"fieldConfig":{"defaults":{"custom":{}},"overrides":[]},"fill":1,"fillGradient":0,"gridPos":{"h":9,"w":12,"x":0,"y":0},"hiddenSeries":false,"id":2,"legend":{"avg":false,"current":false,"max":false,"min":false,"show":true,"total":false,"values":false},"lines":true,"linewidth":1,"nullPointMode":"null","options":{"alertThreshold":true},"percentage":false,"pluginVersion":"5.0.0","pointradius":2,"points":false,"renderer":"flot","seriesOverrides":[],"spaceLength":10,"stack":false,"steppedLine":false,"thresholds":[],"timeFrom":null,"timeRegions":[],"timeShift":null,"title":"Panel Title","tooltip":{"shared":true,"sort":0,"value_type":"individual"},"type":"graph","xaxis":{"buckets":null,"mode":"time","name":null,"show":true,"values":[]},"yaxes":[{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true},{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true}],"yaxis":{"align":false,"alignLevel":null}}],"schemaVersion":26,"style":"dark","tags":[],"templating":{"list":[]},"time":{"from":"now-6h","to":"now"},"timepicker":{"refresh_intervals":["5s","10s","30s","1m","5m","15m","30m","1h","2h","1d"]},"timezone":"","title":"Test_Cloud","uid":"ux68I13Gz","version":2}
3	1	2	0	3	2021-05-24 11:35:03	1		{"annotations":{"list":[{"builtIn":1,"datasource":"-- Grafana --","enable":true,"hide":true,"iconColor":"rgba(0, 211, 255, 1)","name":"Annotations \\u0026 Alerts","type":"dashboard"}]},"editable":true,"gnetId":null,"graphTooltip":0,"id":1,"links":[],"panels":[{"aliasColors":{},"bars":true,"dashLength":10,"dashes":false,"datasource":null,"description":"This dashboard source is stored at AWS S3 bucket","fieldConfig":{"defaults":{"custom":{}},"overrides":[]},"fill":1,"fillGradient":0,"gridPos":{"h":9,"w":12,"x":0,"y":0},"hiddenSeries":false,"id":2,"legend":{"avg":false,"current":false,"max":false,"min":false,"show":true,"total":false,"values":false},"lines":true,"linewidth":1,"nullPointMode":"null","options":{"alertThreshold":true},"percentage":false,"pluginVersion":"5.0.0","pointradius":2,"points":false,"renderer":"flot","seriesOverrides":[],"spaceLength":10,"stack":false,"steppedLine":false,"thresholds":[],"timeFrom":null,"timeRegions":[],"timeShift":null,"title":"Test_Cloud","tooltip":{"shared":true,"sort":0,"value_type":"individual"},"type":"graph","xaxis":{"buckets":null,"mode":"time","name":null,"show":true,"values":[]},"yaxes":[{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true},{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true}],"yaxis":{"align":false,"alignLevel":null}}],"schemaVersion":26,"style":"dark","tags":[],"templating":{"list":[]},"time":{"from":"now-6h","to":"now"},"timepicker":{"refresh_intervals":["5s","10s","30s","1m","5m","15m","30m","1h","2h","1d"]},"timezone":"","title":"Test_Cloud","uid":"ux68I13Gz","version":3}
4	1	3	0	4	2021-05-24 15:35:48	1		{"annotations":{"list":[{"builtIn":1,"datasource":"-- Grafana --","enable":true,"hide":true,"iconColor":"rgba(0, 211, 255, 1)","name":"Annotations \\u0026 Alerts","type":"dashboard"}]},"editable":true,"gnetId":null,"graphTooltip":0,"id":1,"links":[],"panels":[{"aliasColors":{},"bars":true,"dashLength":10,"dashes":false,"datasource":null,"description":"This dashboard source is stored at AWS S3 bucket","fieldConfig":{"defaults":{"custom":{}},"overrides":[]},"fill":1,"fillGradient":0,"gridPos":{"h":9,"w":12,"x":0,"y":0},"hiddenSeries":false,"id":2,"legend":{"avg":false,"current":false,"max":false,"min":false,"show":true,"total":false,"values":false},"lines":true,"linewidth":1,"nullPointMode":"null","options":{"alertThreshold":true},"percentage":false,"pluginVersion":"5.0.0","pointradius":2,"points":false,"renderer":"flot","seriesOverrides":[],"spaceLength":10,"stack":false,"steppedLine":false,"thresholds":[],"timeFrom":null,"timeRegions":[],"timeShift":null,"title":"Test_Cloud","tooltip":{"shared":true,"sort":0,"value_type":"individual"},"type":"graph","xaxis":{"buckets":null,"mode":"time","name":null,"show":true,"values":[]},"yaxes":[{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true},{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true}],"yaxis":{"align":false,"alignLevel":null}}],"schemaVersion":26,"style":"dark","tags":[],"templating":{"list":[]},"time":{"from":"now-6h","to":"now"},"timepicker":{"refresh_intervals":["5s","10s","30s","1m","5m","15m","30m","1h","2h","1d"]},"timezone":"","title":"Test_Cloud","uid":"ux68I13Gz","version":4}
5	2	0	0	1	2021-07-07 14:22:43	1	HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH	{"AccountId":"0002","Created":"2021-05-24 11:33:12.0","CreatedBy":"1","Data":"","FolderId":0,"GnetId ":"0","HasAcl":false,"InputSourceId":"","IsFolder":false,"OrgId":"1","PluginId":"","Slug":"oooooo","SourceJsonRef":"https://s3.amazonaws.com/xformation.synectiks.com/test_ds.jsonooooooo","TenantId":"1","Title":"qaswedfrftgyh","Uid":"111","Updated":"2021-05-24 15:35:48.0","UpdatedBy":"1","Uuid":"qqqqqqq","Version":"4","title":"qaswedfrftgyh","uid":"111","version":1}
6	3	0	0	1	2021-07-07 14:31:14	1	HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH	{"AccountId":"0002","Created":"2021-05-24 11:33:12.0","CreatedBy":"1","Data":"","FolderId":0,"GnetId ":"0","HasAcl":false,"InputSourceId":"","IsFolder":false,"OrgId":"1","PluginId":"","Slug":"PURE_TEST","SourceJsonRef":"https://s3.amazonaws.com/xformation.synectiks.com/test_ds.jsonooooooo","TenantId":"1","Title":"PURE_TEST","Uid":"2222","Updated":"2021-05-24 15:35:48.0","UpdatedBy":"1","Uuid":"2222","Version":"4","title":"PURE_TEST","uid":"2222","version":1}
7	4	0	0	1	2021-07-07 14:42:17	1	HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH	{"AccountId":"0002","Created":"2021-05-24 11:33:12.0","CreatedBy":"1","Data":"","FolderId":0,"GnetId ":"0","HasAcl":false,"InputSourceId":"","IsFolder":false,"OrgId":"1","PluginId":"","Slug":"TODOOOOO","SourceJsonRef":"https://s3.amazonaws.com/xformation.synectiks.com/test_ds.jsonooooooo","TenantId":"1","Title":"TODOOOOO","Uid":"3333","Updated":"2021-05-24 15:35:48.0","UpdatedBy":"1","Uuid":"3333","Version":"4","title":"TODOOOOO","uid":"3333","version":1}
8	6	0	0	1	2021-07-07 14:48:19	1	HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH	{"AccountId":"0002","Created":"2021-05-24 11:33:12.0","CreatedBy":"1","Data":"","FolderId":0,"GnetId ":"0","HasAcl":false,"InputSourceId":"","IsFolder":false,"OrgId":"1","PluginId":"","Slug":"eeeeeeeeeeeeeeeee","SourceJsonRef":"https://s3.amazonaws.com/xformation.synectiks.com/test_ds.jsonooooooo","TenantId":"1","Title":"eeeeeeeeeeeeeeeee","Uid":"4444","Updated":"2021-05-24 15:35:48.0","UpdatedBy":"1","Uuid":"4444","Version":"4","title":"eeeeeeeeeeeeeeeee","uid":"4444","version":1}
9	7	0	0	1	2021-07-07 14:55:29	1	HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH	{"AccountId":"0002","Created":"2021-05-24 11:33:12.0","CreatedBy":"1","Data":"","FolderId":0,"GnetId ":"0","HasAcl":false,"InputSourceId":"","IsFolder":false,"OrgId":"1","PluginId":"","Slug":"fffffffff","SourceJsonRef":"https://s3.amazonaws.com/xformation.synectiks.com/test_ds.jsonooooooo","TenantId":"1","Title":"fffffffff","Uid":"5555","Updated":"2021-05-24 15:35:48.0","UpdatedBy":"1","Uuid":"5555","Version":"4","title":"fffffffff","uid":"5555","version":1}
10	8	0	0	1	2021-07-07 15:09:49	1	HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH	{"AccountId":"0002","Created":"2021-05-24 11:33:12.0","CreatedBy":"1","Data":"","FolderId":0,"GnetId ":"0","HasAcl":false,"InputSourceId":"","IsFolder":false,"OrgId":"1","PluginId":"","Slug":"wert","SourceJsonRef":"https://s3.amazonaws.com/xformation.synectiks.com/test_ds.jsonooooooo","TenantId":"1","Title":"wert","Uid":"66","Updated":"2021-05-24 15:35:48.0","UpdatedBy":"1","Uuid":"66","Version":"4","title":"wert","uid":"66","version":1}
11	9	0	0	1	2021-07-07 15:11:33	1	HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH	{"AccountId":"0002","Created":"2021-05-24 11:33:12.0","CreatedBy":"1","Data":"","FolderId":0,"GnetId ":"0","HasAcl":false,"InputSourceId":"","IsFolder":false,"OrgId":"1","PluginId":"","Slug":"iiiii","SourceJsonRef":"https://s3.amazonaws.com/xformation.synectiks.com/test_ds.jsonooooooo","TenantId":"1","Title":"iiiii","Uid":"77","Updated":"2021-05-24 15:35:48.0","UpdatedBy":"1","Uuid":"77","Version":"4","title":"iiiii","uid":"77","version":1}
12	10	0	0	1	2021-07-07 16:14:15	1	HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH	{"AccountId":"0002","Created":"2021-05-24 11:33:12.0","CreatedBy":"1","Data":"","FolderId":0,"GnetId ":"0","HasAcl":false,"InputSourceId":"","IsFolder":false,"OrgId":"1","PluginId":"","Slug":"popopo","SourceJsonRef":"https://s3.amazonaws.com/xformation.synectiks.com/test_ds.jsonooooooo","TenantId":"1","Title":"popopo","Uid":"8888","Updated":"2021-05-24 15:35:48.0","UpdatedBy":"1","Uuid":"8888","Version":"4","title":"popopo","uid":"8888","version":1}
13	11	0	0	1	2021-07-07 16:21:48	1	HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH	
15	13	0	0	1	2021-07-07 16:52:19	1	HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH	{}
16	14	0	0	1	2021-07-07 22:07:03	1	HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH	{}
17	15	0	0	1	2021-07-28 19:20:58	1	HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH	{}
18	16	0	0	1	2021-07-29 00:17:44	1	Performance Monitoring	{}
19	17	0	0	1	2021-07-29 00:37:22	1	Performance Monitoring	{}
20	18	0	0	1	2021-07-29 11:38:42	1	Performance Monitoring	{}
21	19	0	0	1	2021-07-29 11:41:20	1	Performance Monitoring	{}
22	20	0	0	1	2021-07-29 11:41:20	1	Performance Monitoring	{}
23	21	0	0	1	2021-07-29 11:41:20	1	Performance Monitoring	{}
24	23	0	0	1	2021-07-29 21:59:58	1	Performance Monitoring	{}
25	22	0	0	1	2021-07-29 21:59:58	1	Performance Monitoring	{}
26	24	0	0	1	2021-07-29 21:59:58	1	Performance Monitoring	{}
27	26	0	0	1	2021-07-29 22:26:51	1	Performance Monitoring	{}
28	25	0	0	1	2021-07-29 22:26:51	1	Performance Monitoring	{}
29	27	0	0	1	2021-07-29 22:26:51	1	Performance Monitoring	{}
30	28	0	0	1	2021-07-29 22:32:33	1	Performance Monitoring	{}
31	29	0	0	1	2021-07-29 22:32:33	1	Performance Monitoring	{}
32	30	0	0	1	2021-07-29 22:32:33	1	Performance Monitoring	{}
33	33	0	0	1	2021-07-29 23:24:03	1	Performance Monitoring	{}
34	34	0	0	1	2021-07-29 23:24:03	1	Performance Monitoring	{}
35	35	0	0	1	2021-07-29 23:29:51	1	Performance Monitoring	{}
36	36	0	0	1	2021-07-29 23:32:01	1	Performance Monitoring	{}
37	38	0	0	1	2021-07-29 23:37:57	1	Performance Monitoring	{}
38	40	0	0	1	2021-07-29 23:41:07	1	Performance Monitoring	{}
39	41	0	0	1	2021-07-30 15:11:24	1	Performance Monitoring	{}
40	42	0	0	1	2021-07-30 15:13:55	1	Performance Monitoring	{}
41	43	0	0	1	2021-07-30 15:16:33	1	Performance Monitoring	{}
42	44	0	0	1	2021-07-30 15:26:39	1	Performance Monitoring	{}
43	45	0	0	1	2021-07-30 15:38:11	1	Performance Monitoring	{}
44	46	0	0	1	2021-07-30 15:40:18	1	Performance Monitoring	{}
45	47	0	0	1	2021-07-30 15:45:53	1	Performance Monitoring	{}
46	48	0	0	1	2021-07-30 15:45:53	1	Performance Monitoring	{}
47	49	0	0	1	2021-07-30 15:45:53	1	Performance Monitoring	{}
48	50	0	0	1	2021-07-30 16:10:03	1	Performance Monitoring	{}
49	51	0	0	1	2021-07-30 16:10:03	1	Performance Monitoring	{}
50	52	0	0	1	2021-07-30 16:10:03	1	Performance Monitoring	{}
51	53	0	0	1	2021-07-30 16:27:15	1	Performance Monitoring	{}
52	54	0	0	1	2021-07-30 17:39:35	1	Performance Monitoring	{"AccountId":"","CreatedBy":"","Data":"","FolderId":"","GnetId":"","GnetId ":"0","HasAcl":"","InputSourceId":"","IsFolder":"","OrgId":"1","PluginId":"","Slug":"","SourceJsonRef":"","TenantId":"","Title":"","Uid":"","UpdatedBy":"","Uuid":"","Version":"","title":"RDS","uid":"uuid-3","version":1}
53	55	0	0	1	2021-07-30 17:39:35	1	Performance Monitoring	{"AccountId":"","CreatedBy":"","Data":"","FolderId":"","GnetId":"","GnetId ":"0","HasAcl":"","InputSourceId":"","IsFolder":"","OrgId":"1","PluginId":"","Slug":"","SourceJsonRef":"","TenantId":"","Title":"","Uid":"","UpdatedBy":"","Uuid":"","Version":"","title":"VPC","uid":"uuid-1","version":1}
54	56	0	0	1	2021-07-30 17:53:35	1	Performance Monitoring	{}
55	56	1	0	2	2021-07-30 17:54:50	1	Performance Monitoring	{}
56	56	2	0	3	2021-07-30 17:55:14	1	Performance Monitoring	{}
57	57	0	0	1	2021-07-30 23:02:29	1	Performance Monitoring	{}
58	58	0	0	1	2021-08-04 14:33:34	1	Performance Monitoring	{}
59	61	0	0	1	2021-08-05 14:30:10	1	Performance Monitoring	{}
60	63	0	0	1	2021-08-05 15:02:30	1	Performance Monitoring	{}
61	64	0	0	1	2021-08-05 15:02:30	1	Performance Monitoring	{}
62	65	0	0	1	2021-08-05 15:02:30	1	Performance Monitoring	{}
63	66	0	0	1	2021-08-05 15:08:18	1	Performance Monitoring	{}
64	67	0	0	1	2021-08-05 15:08:18	1	Performance Monitoring	{}
65	68	0	0	1	2021-08-05 15:08:18	1	Performance Monitoring	{}
66	69	0	0	1	2021-08-06 00:29:25	1	Performance Monitoring	{}
67	70	0	0	1	2021-08-06 00:29:25	1	Performance Monitoring	{}
68	71	0	0	1	2021-08-06 00:40:26	1	Performance Monitoring	{}
69	72	0	0	1	2021-08-08 23:11:01	1	Performance Monitoring	{}
70	69	1	0	2	2021-08-09 10:41:49	1	Performance Monitoring	{}
71	73	0	0	1	2021-08-09 10:43:50	1	Performance Monitoring	{}
72	74	0	0	1	2021-08-10 14:05:56	1		{"annotations":{"list":[{"builtIn":1,"datasource":"-- Grafana --","enable":true,"hide":true,"iconColor":"rgba(0, 211, 255, 1)","name":"Annotations \\u0026 Alerts","type":"dashboard"}]},"editable":true,"gnetId":null,"graphTooltip":0,"hideControls":false,"id":null,"links":[],"panels":[{"aliasColors":{},"bars":false,"dashLength":10,"dashes":false,"datasource":"InfluxDB","fieldConfig":{"defaults":{"custom":{}},"overrides":[]},"fill":1,"fillGradient":0,"gridPos":{"h":9,"w":12,"x":0,"y":0},"hiddenSeries":false,"id":2,"legend":{"avg":false,"current":false,"max":false,"min":false,"show":true,"total":false,"values":false},"lines":true,"linewidth":1,"nullPointMode":"null","options":{"alertThreshold":true},"percentage":false,"pluginVersion":"5.0.0","pointradius":2,"points":false,"renderer":"flot","seriesOverrides":[],"spaceLength":10,"stack":false,"steppedLine":false,"targets":[{"groupBy":[{"params":["$__interval"],"type":"time"},{"params":["null"],"type":"fill"}],"orderByTime":"ASC","policy":"default","refId":"A","resultFormat":"time_series","select":[[{"params":["value"],"type":"field"},{"params":[],"type":"mean"}]],"tags":[]}],"thresholds":[],"timeFrom":null,"timeRegions":[],"timeShift":null,"title":"Panel Title","tooltip":{"shared":true,"sort":0,"value_type":"individual"},"type":"graph","xaxis":{"buckets":null,"mode":"time","name":null,"show":true,"values":[]},"yaxes":[{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true},{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true}],"yaxis":{"align":false,"alignLevel":null}}],"schemaVersion":26,"style":"dark","tags":[],"templating":{"list":[]},"time":{"from":"now-6h","to":"now"},"timepicker":{},"timezone":"","title":"New dashboard Copy","uid":"sXE0KlGnk","version":1}
73	74	1	0	2	2021-08-10 14:07:53	1		{"annotations":{"list":[{"builtIn":1,"datasource":"-- Grafana --","enable":true,"hide":true,"iconColor":"rgba(0, 211, 255, 1)","name":"Annotations \\u0026 Alerts","type":"dashboard"}]},"editable":true,"gnetId":null,"graphTooltip":0,"id":74,"links":[],"panels":[{"aliasColors":{},"bars":false,"dashLength":10,"dashes":false,"datasource":"InfluxDB","fieldConfig":{"defaults":{"custom":{}},"overrides":[]},"fill":1,"fillGradient":0,"gridPos":{"h":9,"w":12,"x":0,"y":0},"hiddenSeries":false,"id":2,"legend":{"avg":false,"current":false,"max":false,"min":false,"show":true,"total":false,"values":false},"lines":true,"linewidth":1,"nullPointMode":"null","options":{"alertThreshold":true},"percentage":false,"pluginVersion":"5.0.0","pointradius":2,"points":false,"renderer":"flot","seriesOverrides":[],"spaceLength":10,"stack":false,"steppedLine":false,"targets":[{"groupBy":[{"params":["$__interval"],"type":"time"},{"params":["null"],"type":"fill"}],"orderByTime":"ASC","policy":"default","refId":"A","resultFormat":"time_series","select":[[{"params":["value"],"type":"field"},{"params":[],"type":"mean"}]],"tags":[]}],"thresholds":[],"timeFrom":null,"timeRegions":[],"timeShift":null,"title":"Panel Title","tooltip":{"shared":true,"sort":0,"value_type":"individual"},"type":"graph","xaxis":{"buckets":null,"mode":"time","name":null,"show":true,"values":[]},"yaxes":[{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true},{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true}],"yaxis":{"align":false,"alignLevel":null}}],"schemaVersion":26,"style":"dark","tags":[],"templating":{"list":[]},"time":{"from":"now-6h","to":"now"},"timepicker":{"refresh_intervals":["5s","10s","30s","1m","5m","15m","30m","1h","2h","1d"]},"timezone":"","title":"New dashboard Copy","uid":"sXE0KlGnk","version":2}
74	74	2	0	3	2021-08-10 14:13:45	1		{"annotations":{"list":[{"builtIn":1,"datasource":"-- Grafana --","enable":true,"hide":true,"iconColor":"rgba(0, 211, 255, 1)","name":"Annotations \\u0026 Alerts","type":"dashboard"}]},"editable":true,"gnetId":null,"graphTooltip":0,"id":74,"links":[],"panels":[{"aliasColors":{},"bars":false,"dashLength":10,"dashes":false,"datasource":"Prometheus","fieldConfig":{"defaults":{"custom":{}},"overrides":[]},"fill":1,"fillGradient":0,"gridPos":{"h":8,"w":12,"x":0,"y":0},"hiddenSeries":false,"id":4,"legend":{"avg":false,"current":false,"max":false,"min":false,"show":true,"total":false,"values":false},"lines":true,"linewidth":1,"nullPointMode":"null","options":{"alertThreshold":true},"percentage":false,"pluginVersion":"5.0.0","pointradius":2,"points":false,"renderer":"flot","seriesOverrides":[],"spaceLength":10,"stack":false,"steppedLine":false,"thresholds":[],"timeFrom":null,"timeRegions":[],"timeShift":null,"title":"Panel Title","tooltip":{"shared":true,"sort":0,"value_type":"individual"},"type":"graph","xaxis":{"buckets":null,"mode":"time","name":null,"show":true,"values":[]},"yaxes":[{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true},{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true}],"yaxis":{"align":false,"alignLevel":null}},{"aliasColors":{},"bars":false,"dashLength":10,"dashes":false,"datasource":"InfluxDB","fieldConfig":{"defaults":{"custom":{}},"overrides":[]},"fill":1,"fillGradient":0,"gridPos":{"h":9,"w":12,"x":0,"y":8},"hiddenSeries":false,"id":2,"legend":{"avg":false,"current":false,"max":false,"min":false,"show":true,"total":false,"values":false},"lines":true,"linewidth":1,"nullPointMode":"null","options":{"alertThreshold":true},"percentage":false,"pluginVersion":"5.0.0","pointradius":2,"points":false,"renderer":"flot","seriesOverrides":[],"spaceLength":10,"stack":false,"steppedLine":false,"targets":[{"groupBy":[{"params":["$__interval"],"type":"time"},{"params":["null"],"type":"fill"}],"orderByTime":"ASC","policy":"default","refId":"A","resultFormat":"time_series","select":[[{"params":["value"],"type":"field"},{"params":[],"type":"mean"}]],"tags":[]}],"thresholds":[],"timeFrom":null,"timeRegions":[],"timeShift":null,"title":"Panel Title","tooltip":{"shared":true,"sort":0,"value_type":"individual"},"type":"graph","xaxis":{"buckets":null,"mode":"time","name":null,"show":true,"values":[]},"yaxes":[{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true},{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true}],"yaxis":{"align":false,"alignLevel":null}}],"schemaVersion":26,"style":"dark","tags":[],"templating":{"list":[]},"time":{"from":"now-6h","to":"now"},"timepicker":{"refresh_intervals":["5s","10s","30s","1m","5m","15m","30m","1h","2h","1d"]},"timezone":"","title":"New dashboard Copy","uid":"sXE0KlGnk","version":3}
76	76	0	0	1	2021-08-16 13:04:12	1	Performance Monitoring	{}
77	77	0	0	1	2021-08-16 13:23:36	1	Performance Monitoring	{}
78	78	0	0	1	2021-08-16 13:23:36	1	Performance Monitoring	{}
79	79	0	0	1	2021-08-16 13:23:36	1	Performance Monitoring	{}
80	80	0	0	1	2021-08-16 13:23:36	1	Performance Monitoring	{}
81	81	0	0	1	2021-08-16 14:18:23	1	Performance Monitoring	{}
94	90	0	0	1	2021-08-17 16:40:52	1	Performance Monitoring	{}
95	91	0	0	1	2021-08-18 11:41:45	1	Performance Monitoring	{}
96	92	0	0	1	2021-08-18 15:01:49	1	Performance Monitoring	{}
97	93	0	0	1	2021-08-18 15:08:56	1	Performance Monitoring	{}
98	94	0	0	1	2021-08-18 15:31:12	1	Performance Monitoring	{}
99	95	0	0	1	2021-08-18 15:44:05	1	Performance Monitoring	{}
100	96	0	0	1	2021-08-18 15:45:45	1	Performance Monitoring	{}
101	97	0	0	1	2021-08-18 15:47:14	1	Performance Monitoring	{}
102	98	0	0	1	2021-08-18 15:47:14	1	Performance Monitoring	{}
103	99	0	0	1	2021-08-18 15:56:19	1	Performance Monitoring	{}
104	100	0	0	1	2021-08-18 15:56:41	1	Performance Monitoring	{}
105	101	3	0	1	2021-08-18 18:40:53	1	Performance Monitoring	{"annotations":{"list":[{"builtIn":1,"datasource":"-- Grafana --","enable":true,"hide":true,"iconColor":"rgba(0, 211, 255, 1)","name":"Annotations \\u0026 Alerts","type":"dashboard"}]},"editable":true,"gnetId":null,"graphTooltip":0,"id":null,"links":[],"panels":[{"aliasColors":{},"bars":true,"dashLength":10,"dashes":false,"datasource":null,"description":"This dashboard source is stored at AWS S3 bucket","fieldConfig":{"defaults":{"custom":{}},"overrides":[]},"fill":1,"fillGradient":0,"gridPos":{"h":9,"w":12,"x":0,"y":0},"hiddenSeries":false,"id":2,"legend":{"avg":false,"current":false,"max":false,"min":false,"show":true,"total":false,"values":false},"lines":true,"linewidth":1,"nullPointMode":"null","options":{"alertThreshold":true},"percentage":false,"pluginVersion":"5.0.0","pointradius":2,"points":false,"renderer":"flot","seriesOverrides":[],"spaceLength":10,"stack":false,"steppedLine":false,"thresholds":[],"timeFrom":null,"timeRegions":[],"timeShift":null,"title":"Dashboard on AWS S3","tooltip":{"shared":true,"sort":0,"value_type":"individual"},"type":"graph","xaxis":{"buckets":null,"mode":"time","name":null,"show":true,"values":[]},"yaxes":[{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true},{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true}],"yaxis":{"align":false,"alignLevel":null}}],"schemaVersion":26,"style":"dark","tags":[],"templating":{"list":[]},"time":{"from":"now-6h","to":"now"},"timepicker":{"refresh_intervals":["5s","10s","30s","1m","5m","15m","30m","1h","2h","1d"]},"timezone":"","title":"Test_Cloud","uid":"ux68I13Gz","version":1}
106	102	3	0	1	2021-08-19 00:39:25	1	Performance Monitoring	{"accountId":"1234","annotations":{"list":[{"builtIn":1,"datasource":"-- Grafana --","enable":true,"hide":true,"iconColor":"rgba(0, 211, 255, 1)","name":"Annotations \\u0026 Alerts","type":"dashboard"}]},"cloudName":"AWS","editable":true,"elementType":"VPC","fileName":"test_ds.json","gnetId":null,"graphTooltip":0,"id":null,"inputSourceId":"Test_CloudWatch","isCloud":true,"links":[],"panels":[{"aliasColors":{},"bars":true,"dashLength":10,"dashes":false,"datasource":null,"description":"This dashboard source is stored at AWS S3 bucket","fieldConfig":{"defaults":{"custom":{}},"overrides":[]},"fill":1,"fillGradient":0,"gridPos":{"h":9,"w":12,"x":0,"y":0},"hiddenSeries":false,"id":2,"legend":{"avg":false,"current":false,"max":false,"min":false,"show":true,"total":false,"values":false},"lines":true,"linewidth":1,"nullPointMode":"null","options":{"alertThreshold":true},"percentage":false,"pluginVersion":"5.0.0","pointradius":2,"points":false,"renderer":"flot","seriesOverrides":[],"spaceLength":10,"stack":false,"steppedLine":false,"thresholds":[],"timeFrom":null,"timeRegions":[],"timeShift":null,"title":"Dashboard on AWS S3","tooltip":{"shared":true,"sort":0,"value_type":"individual"},"type":"graph","xaxis":{"buckets":null,"mode":"time","name":null,"show":true,"values":[]},"yaxes":[{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true},{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true}],"yaxis":{"align":false,"alignLevel":null}}],"schemaVersion":26,"style":"dark","tags":[],"templating":{"list":[]},"tenantId":"78","time":{"from":"now-6h","to":"now"},"timepicker":{"refresh_intervals":["5s","10s","30s","1m","5m","15m","30m","1h","2h","1d"]},"timezone":"","title":"Test_Cloud","uid":"ux68I13Gz","uuid":"uuid-1","version":1}
120	108	0	0	1	2021-08-19 23:25:22	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"test_ds.json","Uid":"uuid-1","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud","uid":"ux68I13Gzyyymmddhhmmss","version":1}
121	108	1	0	2	2021-08-19 23:25:23	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"test_ds.json","Uid":"uuid-2","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","id":108,"title":"AWS_VPC_Test_Cloud","uid":"ux68I13Gzyyymmddhhmmss","version":2}
107	103	3	0	1	2021-08-19 01:01:56	1	Performance Monitoring	{"accountId":"1234","annotations":{"list":[{"builtIn":1,"datasource":"-- Grafana --","enable":true,"hide":true,"iconColor":"rgba(0, 211, 255, 1)","name":"Annotations \\u0026 Alerts","type":"dashboard"}]},"cloudName":"AWS","editable":true,"elementType":"VPC","fileName":"test_ds.json","gnetId":null,"graphTooltip":0,"id":null,"inputSourceId":"Test_CloudWatch","isCloud":true,"links":[],"panels":[{"aliasColors":{},"bars":true,"dashLength":10,"dashes":false,"datasource":null,"description":"This dashboard source is stored at AWS S3 bucket","fieldConfig":{"defaults":{"custom":{}},"overrides":[]},"fill":1,"fillGradient":0,"gridPos":{"h":9,"w":12,"x":0,"y":0},"hiddenSeries":false,"id":2,"legend":{"avg":false,"current":false,"max":false,"min":false,"show":true,"total":false,"values":false},"lines":true,"linewidth":1,"nullPointMode":"null","options":{"alertThreshold":true},"percentage":false,"pluginVersion":"5.0.0","pointradius":2,"points":false,"renderer":"flot","seriesOverrides":[],"spaceLength":10,"stack":false,"steppedLine":false,"thresholds":[],"timeFrom":null,"timeRegions":[],"timeShift":null,"title":"Dashboard on AWS S3","tooltip":{"shared":true,"sort":0,"value_type":"individual"},"type":"graph","xaxis":{"buckets":null,"mode":"time","name":null,"show":true,"values":[]},"yaxes":[{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true},{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true}],"yaxis":{"align":false,"alignLevel":null}}],"schemaVersion":26,"style":"dark","tags":[],"templating":{"list":[]},"tenantId":"78","time":{"from":"now-6h","to":"now"},"timepicker":{"refresh_intervals":["5s","10s","30s","1m","5m","15m","30m","1h","2h","1d"]},"timezone":"","title":"Test_Cloud","uid":"ux68I13Gz","uuid":"ux68I13Gz","version":1}
108	103	1	0	2	2021-08-19 01:10:49	1	Performance Monitoring	{"accountId":"1234","annotations":{"list":[{"builtIn":1,"datasource":"-- Grafana --","enable":true,"hide":true,"iconColor":"rgba(0, 211, 255, 1)","name":"Annotations \\u0026 Alerts","type":"dashboard"}]},"cloudName":"AWS","editable":true,"elementType":"VPC","fileName":"test_ds.json","gnetId":null,"graphTooltip":0,"id":103,"inputSourceId":"Test_CloudWatch","isCloud":true,"links":[],"panels":[{"aliasColors":{},"bars":true,"dashLength":10,"dashes":false,"datasource":null,"description":"This dashboard source is stored at AWS S3 bucket","fieldConfig":{"defaults":{"custom":{}},"overrides":[]},"fill":1,"fillGradient":0,"gridPos":{"h":9,"w":12,"x":0,"y":0},"hiddenSeries":false,"id":2,"legend":{"avg":false,"current":false,"max":false,"min":false,"show":true,"total":false,"values":false},"lines":true,"linewidth":1,"nullPointMode":"null","options":{"alertThreshold":true},"percentage":false,"pluginVersion":"5.0.0","pointradius":2,"points":false,"renderer":"flot","seriesOverrides":[],"spaceLength":10,"stack":false,"steppedLine":false,"thresholds":[],"timeFrom":null,"timeRegions":[],"timeShift":null,"title":"Dashboard on AWS S3","tooltip":{"shared":true,"sort":0,"value_type":"individual"},"type":"graph","xaxis":{"buckets":null,"mode":"time","name":null,"show":true,"values":[]},"yaxes":[{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true},{"format":"short","label":null,"logBase":1,"max":null,"min":null,"show":true}],"yaxis":{"align":false,"alignLevel":null}}],"schemaVersion":26,"style":"dark","tags":[],"templating":{"list":[]},"tenantId":"78","time":{"from":"now-6h","to":"now"},"timepicker":{"refresh_intervals":["5s","10s","30s","1m","5m","15m","30m","1h","2h","1d"]},"timezone":"","title":"Test_Cloud","uid":"ux68I13Gz","uuid":"ux68I13Gz","version":2}
113	106	0	0	1	2021-08-19 23:05:06	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"test_ds.json","Uid":"uuid-1","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud","uid":"78_1234_AWS_VPC","version":1}
114	106	1	0	2	2021-08-19 23:08:39	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"test_ds.json","Uid":"uuid-2","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","id":106,"title":"AWS_VPC_Test_Cloud","uid":"78_1234_AWS_VPC","version":2}
115	106	2	0	3	2021-08-19 23:08:40	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"test_ds.json","Uid":"uuid-2","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","id":106,"title":"AWS_VPC_Test_Cloud","uid":"78_1234_AWS_VPC","version":3}
116	107	0	0	1	2021-08-19 23:10:23	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"test_ds.json","Uid":"uuid-1","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud","uid":"78_1234_AWS_VPC","version":1}
117	107	1	0	2	2021-08-19 23:10:23	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"test_ds.json","Uid":"uuid-2","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","id":107,"title":"AWS_VPC_Test_Cloud","uid":"78_1234_AWS_VPC","version":2}
118	107	2	0	3	2021-08-19 23:10:24	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"test_ds.json","Uid":"uuid-1","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","id":107,"title":"AWS_VPC_Test_Cloud","uid":"78_1234_AWS_VPC","version":3}
119	107	3	0	4	2021-08-19 23:10:24	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"test_ds.json","Uid":"uuid-2","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","id":107,"title":"AWS_VPC_Test_Cloud","uid":"78_1234_AWS_VPC","version":4}
122	108	2	0	3	2021-08-19 23:25:23	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"test_ds.json","Uid":"uuid-1","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","id":108,"title":"AWS_VPC_Test_Cloud","uid":"ux68I13Gzyyymmddhhmmss","version":3}
123	108	3	0	4	2021-08-19 23:25:24	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"test_ds.json","Uid":"uuid-2","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","id":108,"title":"AWS_VPC_Test_Cloud","uid":"ux68I13Gzyyymmddhhmmss","version":4}
124	109	0	0	1	2021-08-20 00:00:08	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"test_ds.json","Uid":"uuid-1","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud","uid":"uuid-1","version":1}
125	109	1	0	2	2021-08-20 00:00:09	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"test_ds.json","Uid":"uuid-1","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","id":109,"title":"AWS_VPC_Test_Cloud","uid":"uuid-1","version":2}
126	112	0	0	1	2021-08-20 11:28:18	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"test_ds.json","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud","uid":"MVHTlan7z","version":1}
127	116	0	0	1	2021-08-20 11:44:14	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_00","uid":"yihcX-7nz","version":1}
128	117	0	0	1	2021-08-20 11:44:14	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_01","uid":"ehhcuan7k","version":1}
129	118	0	0	1	2021-08-20 11:44:15	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"10","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_10","uid":"Nu25Xannk","version":1}
130	119	0	0	1	2021-08-20 11:44:15	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"11","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_11","uid":"ADT5u-77z","version":1}
131	120	0	0	1	2021-08-20 11:51:26	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_00","uid":"DtBQu-77k","version":1}
132	121	0	0	1	2021-08-20 11:51:27	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_01","uid":"AuBwuan7k","version":1}
133	122	0	0	1	2021-08-20 11:51:27	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"10","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_Test_CloudWatch_10","uid":"IOYwu-nnz","version":1}
134	123	0	0	1	2021-08-20 11:51:28	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"11","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_Test_CloudWatch_11","uid":"48Lwuan7k","version":1}
135	124	0	0	1	2021-08-20 12:18:42	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_00","uid":"7mbKC-77z","version":1}
136	125	0	0	1	2021-08-20 12:18:43	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_01","uid":"IAxKj-77z","version":1}
137	126	0	0	1	2021-08-20 12:18:43	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"10","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_Test_CloudWatch_10","uid":"6CxKja77k","version":1}
138	127	0	0	1	2021-08-20 12:18:44	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"11","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_Test_CloudWatch_11","uid":"6p-KC-n7z","version":1}
140	129	0	0	1	2021-08-20 12:54:47	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test3_CloudWatch","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_Test3_CloudWatch_01","uid":"ZHfp6a77z","version":1}
139	128	0	0	1	2021-08-20 12:54:47	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test3_CloudWatch","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_Test3_CloudWatch_00","uid":"GXate-7nk","version":1}
141	130	0	0	1	2021-08-20 15:44:55	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test3_CloudWatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_Test3_CloudWatch_00","uid":"QfGnDB7nk","version":1}
142	131	0	0	1	2021-08-20 15:44:55	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test3_CloudWatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_Test3_CloudWatch_01","uid":"Vzn7DB7nk","version":1}
143	132	0	0	1	2021-08-20 15:44:56	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"10","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_Test_CloudWatch_10","uid":"3h77DBn7k","version":1}
144	133	0	0	1	2021-08-20 15:44:56	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"11","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_Test_CloudWatch_11","uid":"eu7nDf7nk","version":1}
145	134	0	0	1	2021-08-30 12:55:54	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_00","uid":"VtX2ytV7z","version":1}
146	136	0	0	1	2021-09-06 13:48:53	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_01","uid":"ncCNX64nz","version":1}
147	141	0	0	1	2021-09-06 13:56:03	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_00","uid":"u3gPu64nz","version":1}
148	142	0	0	1	2021-09-06 13:56:04	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_01","uid":"c5kEXeV7k","version":1}
149	143	0	0	1	2021-09-06 14:00:01	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_00","uid":"eYvCue4nk","version":1}
150	144	0	0	1	2021-09-06 14:00:02	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_01","uid":"RWdCu6Vnz","version":1}
151	145	0	0	1	2021-09-06 14:02:34	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_00","uid":"Bkek96Vnk","version":1}
152	146	0	0	1	2021-09-06 14:02:35	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_01","uid":"IT6kr6Vnz","version":1}
153	147	0	0	1	2021-09-06 14:16:39	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_00","uid":"TPsu9e47z","version":1}
154	148	0	0	1	2021-09-06 14:16:39	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_01","uid":"cn8ur6Vnk","version":1}
155	149	0	0	1	2021-09-06 16:22:15	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_Test_CloudWatch_00","uid":"HhykGgInk","version":1}
156	150	0	0	1	2021-09-06 16:22:16	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_Test_CloudWatch_01","uid":"M6szGgSnz","version":1}
157	151	0	0	1	2021-09-07 13:45:46	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_00","uid":"FC9opmS7z","version":1}
158	152	0	0	1	2021-09-07 13:45:46	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"10","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_Test_CloudWatch_10","uid":"ATjotiI7k","version":1}
159	153	0	0	1	2021-09-07 13:51:00	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_Test_CloudWatch_00","uid":"GuTwpiS7k","version":1}
160	154	0	0	1	2021-09-07 13:58:26	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_00","uid":"YdZS2iSnz","version":1}
161	155	0	0	1	2021-09-07 13:58:26	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_01","uid":"0yWShiI7z","version":1}
162	156	0	0	1	2021-09-07 13:58:27	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"10","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_Test_CloudWatch_10","uid":"T4GS2iInz","version":1}
163	157	0	0	1	2021-09-07 13:58:27	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"11","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_Test_CloudWatch_11","uid":"YxGShmInz","version":1}
164	158	0	0	1	2021-09-07 14:06:36	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test3_CloudWatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_Test3_CloudWatch_00","uid":"ItiPhiSnk","version":1}
165	159	0	0	1	2021-09-07 14:06:37	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test3_CloudWatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_Test3_CloudWatch_01","uid":"1QmE2iInk","version":1}
166	160	0	0	1	2021-09-07 14:12:54	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_00","uid":"IS-kTiI7k","version":1}
167	161	0	0	1	2021-09-07 14:12:54	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_01","uid":"ZaazTiS7k","version":1}
168	162	0	0	1	2021-09-07 14:32:38	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_00","uid":"gKs70iSnz","version":1}
169	163	0	0	1	2021-09-07 14:32:38	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TEst_2_clw","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_TEst_2_clw_01","uid":"ossn0iSnk","version":1}
170	164	0	0	1	2021-09-07 14:35:54	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_Test_CloudWatch_00","uid":"mGsK0mInk","version":1}
171	165	0	0	1	2021-09-07 14:35:55	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test_CloudWatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_Test_CloudWatch_01","uid":"UfyKAmI7z","version":1}
172	166	0	0	1	2021-09-07 14:43:09	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test3_CloudWatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_Test_Cloud_Test3_CloudWatch_00","uid":"lA68AmS7z","version":1}
173	167	0	0	1	2021-09-07 14:43:10	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"Test3_CloudWatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_Test_Cloud_Test3_CloudWatch_01","uid":"YvRw0mI7k","version":1}
174	168	0	0	1	2021-09-22 15:43:44	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TestAwsCloudwatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"00","Uid":"","UpdatedBy":"1","Uuid":"uuid-1","Version":"1","title":"AWS_VPC_ec2 single panel_TestAwsCloudwatch_00","uid":"rkPVNvN7z","version":1}
175	169	0	0	1	2021-09-22 15:43:44	1	Performance Monitoring	{"AccountId":"1234","CloudName":"AWS","CreatedBy":"1","Data":"","ElementType":"VPC","FileName":"test_ds.json","FolderId":0,"GnetId ":0,"HasAcl":false,"InputSourceId":"TestAwsCloudwatch","InputType":"Performance","IsCloud":true,"IsFolder":false,"OrgId":1,"PluginId":"","Slug":"test_ds.json","SourceJsonRef":"","TenantId":"78","Title":"01","Uid":"","UpdatedBy":"1","Uuid":"uuid-2","Version":"1","title":"AWS_VPC_ec2 single panel_TestAwsCloudwatch_01","uid":"OtEVNDH7z","version":1}
\.


--
-- Data for Name: data_keys; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.data_keys (name, active, scope, provider, encrypted_data, created, updated) FROM stdin;
\.


--
-- Data for Name: data_source; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.data_source (id, org_id, version, type, name, access, url, password, "user", database, basic_auth, basic_auth_user, basic_auth_password, is_default, json_data, created, updated, with_credentials, secure_json_data, read_only, uid, tenant_id, account_id) FROM stdin;
2	1	1	prometheus	Prometheus	proxy					f			t	{}	2021-08-09 13:14:36	2021-08-09 13:14:36	f	{}	f	KmRVPsG7z		
3	1	1	graphite	Graphite	proxy					f			f	{}	2021-08-09 14:27:43	2021-08-09 14:27:43	f	{}	f	VO9K_yM7z		
4	1	1	opentsdb	OpenTSDB	proxy					f			f	{}	2021-08-09 14:34:43	2021-08-09 14:34:43	f	{}	f	YjEU_yM7k		
5	1	1	influxdb	InfluxDB	proxy					f			f	{}	2021-08-09 15:22:49	2021-08-09 15:22:49	f	{}	f	93UACsGnk		
6	1	1	loki	Loki	proxy					f			f	{}	2021-08-09 15:24:30	2021-08-09 15:24:30	f	{}	f	1ju-CsG7k		
37	1	1	cloudwatch	www	proxy					f			f	{"authType":"keys","defaultRegion":"us-gov-east-1"}	2021-08-18 15:30:39	2021-08-18 15:30:39	f	{"accessKey":"cGF1eGhaZHUfOjUXPGVH6eq/rOoPg4Rt+LEZ","secretKey":"T0MxUm9xNXrdFUtgcOT4zJD6d4E0MuNRNhb/"}	f	q0bJZTnnz	78	9876
40	1	1	grafana-azure-monitor-datasource	Azure Monitor	proxy					f			f	{}	2021-10-04 11:53:35	2021-10-04 11:53:35	f	{}	f	d6rwsSD7z		
39	1	3	cloudwatch	TestAwsCloudwatch	proxy					f			f	{"authType":"keys","defaultRegion":"us-east-1"}	2021-09-22 15:40:55	2022-03-24 16:48:37	f	{"accessKey":"RzRoN0pSeDPWnb459JC0NNRez9n92tYGRMnIrDJimxFaslGvOv8/W3InSR4=","secretKey":"bnlENEhuTGjS1weZ43LRywHBofy+cT8i9DuEEZAZhnhcasoVRGjiHfrBgg39sa/e4QOoyp1ABH6YtpQ8wE4Jxg=="}	f	_q0zHDN7z	78	1234
\.


--
-- Data for Name: kv_store; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.kv_store (id, org_id, namespace, key, value, created, updated) FROM stdin;
1	1	alertmanager	notifications		2022-03-17 14:52:44	2022-03-17 14:52:44
2	1	alertmanager	silences		2022-03-17 14:52:44	2022-03-17 14:52:44
3	0	infra.usagestats	anonymous_id	afae8099-55f5-4a99-9e79-423d2773bf06	2022-03-21 10:31:54	2022-03-21 10:31:54
4	0	infra.usagestats	last_sent	2022-03-29T11:48:06+05:30	2022-03-21 10:31:57	2022-03-29 11:48:06
\.


--
-- Data for Name: library_element; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.library_element (id, org_id, folder_id, uid, name, kind, type, description, model, created, created_by, updated, updated_by, version) FROM stdin;
\.


--
-- Data for Name: library_element_connection; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.library_element_connection (id, element_id, kind, connection_id, created, created_by) FROM stdin;
\.


--
-- Data for Name: login_attempt; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.login_attempt (id, username, ip_address, created) FROM stdin;
\.


--
-- Data for Name: migration_log; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.migration_log (id, migration_id, sql, success, error, "timestamp") FROM stdin;
1	create migration_log table	CREATE TABLE IF NOT EXISTS "migration_log" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "migration_id" VARCHAR(255) NOT NULL\n, "sql" TEXT NOT NULL\n, "success" BOOL NOT NULL\n, "error" TEXT NOT NULL\n, "timestamp" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:57
2	create user table	CREATE TABLE IF NOT EXISTS "user" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "version" INTEGER NOT NULL\n, "login" VARCHAR(190) NOT NULL\n, "email" VARCHAR(190) NOT NULL\n, "name" VARCHAR(255) NULL\n, "password" VARCHAR(255) NULL\n, "salt" VARCHAR(50) NULL\n, "rands" VARCHAR(50) NULL\n, "company" VARCHAR(255) NULL\n, "account_id" BIGINT NOT NULL\n, "is_admin" BOOL NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:57
3	add unique index user.login	CREATE UNIQUE INDEX "UQE_user_login" ON "user" ("login");	t		2021-05-13 23:42:57
4	add unique index user.email	CREATE UNIQUE INDEX "UQE_user_email" ON "user" ("email");	t		2021-05-13 23:42:57
5	drop index UQE_user_login - v1	DROP INDEX "UQE_user_login" CASCADE	t		2021-05-13 23:42:57
6	drop index UQE_user_email - v1	DROP INDEX "UQE_user_email" CASCADE	t		2021-05-13 23:42:57
7	Rename table user to user_v1 - v1	ALTER TABLE "user" RENAME TO "user_v1"	t		2021-05-13 23:42:57
8	create user table v2	CREATE TABLE IF NOT EXISTS "user" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "version" INTEGER NOT NULL\n, "login" VARCHAR(190) NOT NULL\n, "email" VARCHAR(190) NOT NULL\n, "name" VARCHAR(255) NULL\n, "password" VARCHAR(255) NULL\n, "salt" VARCHAR(50) NULL\n, "rands" VARCHAR(50) NULL\n, "company" VARCHAR(255) NULL\n, "org_id" BIGINT NOT NULL\n, "is_admin" BOOL NOT NULL\n, "email_verified" BOOL NULL\n, "theme" VARCHAR(255) NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:57
9	create index UQE_user_login - v2	CREATE UNIQUE INDEX "UQE_user_login" ON "user" ("login");	t		2021-05-13 23:42:57
10	create index UQE_user_email - v2	CREATE UNIQUE INDEX "UQE_user_email" ON "user" ("email");	t		2021-05-13 23:42:57
11	copy data_source v1 to v2	INSERT INTO "user" ("id"\n, "login"\n, "name"\n, "password"\n, "rands"\n, "is_admin"\n, "updated"\n, "version"\n, "email"\n, "salt"\n, "company"\n, "org_id"\n, "created") SELECT "id"\n, "login"\n, "name"\n, "password"\n, "rands"\n, "is_admin"\n, "updated"\n, "version"\n, "email"\n, "salt"\n, "company"\n, "account_id"\n, "created" FROM "user_v1"	t		2021-05-13 23:42:57
12	Drop old table user_v1	DROP TABLE IF EXISTS "user_v1"	t		2021-05-13 23:42:57
13	Add column help_flags1 to user table	alter table "user" ADD COLUMN "help_flags1" BIGINT NOT NULL DEFAULT 0 	t		2021-05-13 23:42:57
14	Update user table charset	ALTER TABLE "user" ALTER "login" TYPE VARCHAR(190), ALTER "email" TYPE VARCHAR(190), ALTER "name" TYPE VARCHAR(255), ALTER "password" TYPE VARCHAR(255), ALTER "salt" TYPE VARCHAR(50), ALTER "rands" TYPE VARCHAR(50), ALTER "company" TYPE VARCHAR(255), ALTER "theme" TYPE VARCHAR(255);	t		2021-05-13 23:42:57
15	Add last_seen_at column to user	alter table "user" ADD COLUMN "last_seen_at" TIMESTAMP NULL 	t		2021-05-13 23:42:57
16	Add missing user data	code migration	t		2021-05-13 23:42:57
17	Add is_disabled column to user	alter table "user" ADD COLUMN "is_disabled" BOOL NOT NULL DEFAULT FALSE 	t		2021-05-13 23:42:57
18	Add index user.login/user.email	CREATE INDEX "IDX_user_login_email" ON "user" ("login","email");	t		2021-05-13 23:42:57
19	create temp user table v1-7	CREATE TABLE IF NOT EXISTS "temp_user" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "version" INTEGER NOT NULL\n, "email" VARCHAR(190) NOT NULL\n, "name" VARCHAR(255) NULL\n, "role" VARCHAR(20) NULL\n, "code" VARCHAR(190) NOT NULL\n, "status" VARCHAR(20) NOT NULL\n, "invited_by_user_id" BIGINT NULL\n, "email_sent" BOOL NOT NULL\n, "email_sent_on" TIMESTAMP NULL\n, "remote_addr" VARCHAR(255) NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:57
20	create index IDX_temp_user_email - v1-7	CREATE INDEX "IDX_temp_user_email" ON "temp_user" ("email");	t		2021-05-13 23:42:57
21	create index IDX_temp_user_org_id - v1-7	CREATE INDEX "IDX_temp_user_org_id" ON "temp_user" ("org_id");	t		2021-05-13 23:42:57
22	create index IDX_temp_user_code - v1-7	CREATE INDEX "IDX_temp_user_code" ON "temp_user" ("code");	t		2021-05-13 23:42:57
23	create index IDX_temp_user_status - v1-7	CREATE INDEX "IDX_temp_user_status" ON "temp_user" ("status");	t		2021-05-13 23:42:57
24	Update temp_user table charset	ALTER TABLE "temp_user" ALTER "email" TYPE VARCHAR(190), ALTER "name" TYPE VARCHAR(255), ALTER "role" TYPE VARCHAR(20), ALTER "code" TYPE VARCHAR(190), ALTER "status" TYPE VARCHAR(20), ALTER "remote_addr" TYPE VARCHAR(255);	t		2021-05-13 23:42:57
25	create star table	CREATE TABLE IF NOT EXISTS "star" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "user_id" BIGINT NOT NULL\n, "dashboard_id" BIGINT NOT NULL\n);	t		2021-05-13 23:42:57
26	add unique index star.user_id_dashboard_id	CREATE UNIQUE INDEX "UQE_star_user_id_dashboard_id" ON "star" ("user_id","dashboard_id");	t		2021-05-13 23:42:57
27	create org table v1	CREATE TABLE IF NOT EXISTS "org" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "version" INTEGER NOT NULL\n, "name" VARCHAR(190) NOT NULL\n, "address1" VARCHAR(255) NULL\n, "address2" VARCHAR(255) NULL\n, "city" VARCHAR(255) NULL\n, "state" VARCHAR(255) NULL\n, "zip_code" VARCHAR(50) NULL\n, "country" VARCHAR(255) NULL\n, "billing_email" VARCHAR(255) NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:57
28	create index UQE_org_name - v1	CREATE UNIQUE INDEX "UQE_org_name" ON "org" ("name");	t		2021-05-13 23:42:57
29	create org_user table v1	CREATE TABLE IF NOT EXISTS "org_user" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "user_id" BIGINT NOT NULL\n, "role" VARCHAR(20) NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:57
30	create index IDX_org_user_org_id - v1	CREATE INDEX "IDX_org_user_org_id" ON "org_user" ("org_id");	t		2021-05-13 23:42:57
31	create index UQE_org_user_org_id_user_id - v1	CREATE UNIQUE INDEX "UQE_org_user_org_id_user_id" ON "org_user" ("org_id","user_id");	t		2021-05-13 23:42:57
32	Update org table charset	ALTER TABLE "org" ALTER "name" TYPE VARCHAR(190), ALTER "address1" TYPE VARCHAR(255), ALTER "address2" TYPE VARCHAR(255), ALTER "city" TYPE VARCHAR(255), ALTER "state" TYPE VARCHAR(255), ALTER "zip_code" TYPE VARCHAR(50), ALTER "country" TYPE VARCHAR(255), ALTER "billing_email" TYPE VARCHAR(255);	t		2021-05-13 23:42:57
33	Update org_user table charset	ALTER TABLE "org_user" ALTER "role" TYPE VARCHAR(20);	t		2021-05-13 23:42:57
34	Migrate all Read Only Viewers to Viewers	UPDATE org_user SET role = 'Viewer' WHERE role = 'Read Only Editor'	t		2021-05-13 23:42:57
35	create dashboard table	CREATE TABLE IF NOT EXISTS "dashboard" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "version" INTEGER NOT NULL\n, "slug" VARCHAR(189) NOT NULL\n, "title" VARCHAR(255) NOT NULL\n, "data" TEXT NOT NULL\n, "account_id" BIGINT NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:57
36	add index dashboard.account_id	CREATE INDEX "IDX_dashboard_account_id" ON "dashboard" ("account_id");	t		2021-05-13 23:42:57
288	Rename table annotation_tag to annotation_tag_v2 - v2	ALTER TABLE "annotation_tag" RENAME TO "annotation_tag_v2"	t		2022-03-17 14:51:11
37	add unique index dashboard_account_id_slug	CREATE UNIQUE INDEX "UQE_dashboard_account_id_slug" ON "dashboard" ("account_id","slug");	t		2021-05-13 23:42:57
38	create dashboard_tag table	CREATE TABLE IF NOT EXISTS "dashboard_tag" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "dashboard_id" BIGINT NOT NULL\n, "term" VARCHAR(50) NOT NULL\n);	t		2021-05-13 23:42:57
39	add unique index dashboard_tag.dasboard_id_term	CREATE UNIQUE INDEX "UQE_dashboard_tag_dashboard_id_term" ON "dashboard_tag" ("dashboard_id","term");	t		2021-05-13 23:42:57
40	drop index UQE_dashboard_tag_dashboard_id_term - v1	DROP INDEX "UQE_dashboard_tag_dashboard_id_term" CASCADE	t		2021-05-13 23:42:57
41	Rename table dashboard to dashboard_v1 - v1	ALTER TABLE "dashboard" RENAME TO "dashboard_v1"	t		2021-05-13 23:42:57
42	create dashboard v2	CREATE TABLE IF NOT EXISTS "dashboard" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "version" INTEGER NOT NULL\n, "slug" VARCHAR(189) NOT NULL\n, "title" VARCHAR(255) NOT NULL\n, "data" TEXT NOT NULL\n, "org_id" BIGINT NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:57
43	create index IDX_dashboard_org_id - v2	CREATE INDEX "IDX_dashboard_org_id" ON "dashboard" ("org_id");	t		2021-05-13 23:42:57
44	create index UQE_dashboard_org_id_slug - v2	CREATE UNIQUE INDEX "UQE_dashboard_org_id_slug" ON "dashboard" ("org_id","slug");	t		2021-05-13 23:42:57
45	copy dashboard v1 to v2	INSERT INTO "dashboard" ("slug"\n, "title"\n, "data"\n, "org_id"\n, "created"\n, "updated"\n, "id"\n, "version") SELECT "slug"\n, "title"\n, "data"\n, "account_id"\n, "created"\n, "updated"\n, "id"\n, "version" FROM "dashboard_v1"	t		2021-05-13 23:42:57
46	drop table dashboard_v1	DROP TABLE IF EXISTS "dashboard_v1"	t		2021-05-13 23:42:57
47	alter dashboard.data to mediumtext v1	SELECT 0;	t		2021-05-13 23:42:57
48	Add column updated_by in dashboard - v2	alter table "dashboard" ADD COLUMN "updated_by" INTEGER NULL 	t		2021-05-13 23:42:57
49	Add column created_by in dashboard - v2	alter table "dashboard" ADD COLUMN "created_by" INTEGER NULL 	t		2021-05-13 23:42:57
50	Add column gnetId in dashboard	alter table "dashboard" ADD COLUMN "gnet_id" BIGINT NULL 	t		2021-05-13 23:42:57
51	Add index for gnetId in dashboard	CREATE INDEX "IDX_dashboard_gnet_id" ON "dashboard" ("gnet_id");	t		2021-05-13 23:42:57
52	Add column plugin_id in dashboard	alter table "dashboard" ADD COLUMN "plugin_id" VARCHAR(189) NULL 	t		2021-05-13 23:42:57
53	Add index for plugin_id in dashboard	CREATE INDEX "IDX_dashboard_org_id_plugin_id" ON "dashboard" ("org_id","plugin_id");	t		2021-05-13 23:42:57
54	Add index for dashboard_id in dashboard_tag	CREATE INDEX "IDX_dashboard_tag_dashboard_id" ON "dashboard_tag" ("dashboard_id");	t		2021-05-13 23:42:57
55	Update dashboard table charset	ALTER TABLE "dashboard" ALTER "slug" TYPE VARCHAR(189), ALTER "title" TYPE VARCHAR(255), ALTER "plugin_id" TYPE VARCHAR(189), ALTER "data" TYPE TEXT;	t		2021-05-13 23:42:57
56	Update dashboard_tag table charset	ALTER TABLE "dashboard_tag" ALTER "term" TYPE VARCHAR(50);	t		2021-05-13 23:42:57
57	Add column folder_id in dashboard	alter table "dashboard" ADD COLUMN "folder_id" BIGINT NOT NULL DEFAULT 0 	t		2021-05-13 23:42:57
58	Add column isFolder in dashboard	alter table "dashboard" ADD COLUMN "is_folder" BOOL NOT NULL DEFAULT FALSE 	t		2021-05-13 23:42:57
59	Add column has_acl in dashboard	alter table "dashboard" ADD COLUMN "has_acl" BOOL NOT NULL DEFAULT FALSE 	t		2021-05-13 23:42:57
60	Add column uid in dashboard	alter table "dashboard" ADD COLUMN "uid" VARCHAR(40) NULL 	t		2021-05-13 23:42:57
61	Add column uuid in dashboard	alter table "dashboard" ADD COLUMN "uuid" VARCHAR(40) NULL 	t		2021-05-13 23:42:57
62	Add column source_json_ref in dashboard	alter table "dashboard" ADD COLUMN "source_json_ref" VARCHAR(1000) NULL 	t		2021-05-13 23:42:57
63	Add column input_source_id in dashboard	alter table "dashboard" ADD COLUMN "input_source_id" VARCHAR(255) NULL 	t		2021-05-13 23:42:57
64	Add column tenant_id in dashboard	alter table "dashboard" ADD COLUMN "tenant_id" VARCHAR(255) NULL 	t		2021-05-13 23:42:57
65	Update uid column values in dashboard	UPDATE dashboard SET uid=lpad('' || id::text,9,'0') WHERE uid IS NULL;	t		2021-05-13 23:42:57
66	Add unique index dashboard_org_id_uid	CREATE UNIQUE INDEX "UQE_dashboard_org_id_uid" ON "dashboard" ("org_id","uid");	t		2021-05-13 23:42:57
67	Remove unique index org_id_slug	DROP INDEX "UQE_dashboard_org_id_slug" CASCADE	t		2021-05-13 23:42:57
68	Update dashboard title length	ALTER TABLE "dashboard" ALTER "title" TYPE VARCHAR(189);	t		2021-05-13 23:42:57
69	Add unique index for dashboard_org_id_title_folder_id	CREATE UNIQUE INDEX "UQE_dashboard_org_id_folder_id_title" ON "dashboard" ("org_id","folder_id","title");	t		2021-05-13 23:42:57
70	create dashboard_provisioning	CREATE TABLE IF NOT EXISTS "dashboard_provisioning" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "dashboard_id" BIGINT NULL\n, "name" VARCHAR(150) NOT NULL\n, "external_id" TEXT NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:57
71	Rename table dashboard_provisioning to dashboard_provisioning_tmp_qwerty - v1	ALTER TABLE "dashboard_provisioning" RENAME TO "dashboard_provisioning_tmp_qwerty"	t		2021-05-13 23:42:57
72	create dashboard_provisioning v2	CREATE TABLE IF NOT EXISTS "dashboard_provisioning" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "dashboard_id" BIGINT NULL\n, "name" VARCHAR(150) NOT NULL\n, "external_id" TEXT NOT NULL\n, "updated" INTEGER NOT NULL DEFAULT 0\n);	t		2021-05-13 23:42:57
73	create index IDX_dashboard_provisioning_dashboard_id - v2	CREATE INDEX "IDX_dashboard_provisioning_dashboard_id" ON "dashboard_provisioning" ("dashboard_id");	t		2021-05-13 23:42:57
74	create index IDX_dashboard_provisioning_dashboard_id_name - v2	CREATE INDEX "IDX_dashboard_provisioning_dashboard_id_name" ON "dashboard_provisioning" ("dashboard_id","name");	t		2021-05-13 23:42:57
75	copy dashboard_provisioning v1 to v2	INSERT INTO "dashboard_provisioning" ("id"\n, "dashboard_id"\n, "name"\n, "external_id") SELECT "id"\n, "dashboard_id"\n, "name"\n, "external_id" FROM "dashboard_provisioning_tmp_qwerty"	t		2021-05-13 23:42:57
76	drop dashboard_provisioning_tmp_qwerty	DROP TABLE IF EXISTS "dashboard_provisioning_tmp_qwerty"	t		2021-05-13 23:42:57
77	Add check_sum column	alter table "dashboard_provisioning" ADD COLUMN "check_sum" VARCHAR(32) NULL 	t		2021-05-13 23:42:57
78	Add index for dashboard_title	CREATE INDEX "IDX_dashboard_title" ON "dashboard" ("title");	t		2021-05-13 23:42:57
79	create data_source table	CREATE TABLE IF NOT EXISTS "data_source" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "account_id" BIGINT NOT NULL\n, "version" INTEGER NOT NULL\n, "type" VARCHAR(255) NOT NULL\n, "name" VARCHAR(190) NOT NULL\n, "access" VARCHAR(255) NOT NULL\n, "url" VARCHAR(255) NOT NULL\n, "password" VARCHAR(255) NULL\n, "user" VARCHAR(255) NULL\n, "database" VARCHAR(255) NULL\n, "basic_auth" BOOL NOT NULL\n, "basic_auth_user" VARCHAR(255) NULL\n, "basic_auth_password" VARCHAR(255) NULL\n, "is_default" BOOL NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:57
80	add index data_source.account_id	CREATE INDEX "IDX_data_source_account_id" ON "data_source" ("account_id");	t		2021-05-13 23:42:57
81	add unique index data_source.account_id_name	CREATE UNIQUE INDEX "UQE_data_source_account_id_name" ON "data_source" ("account_id","name");	t		2021-05-13 23:42:57
82	drop index IDX_data_source_account_id - v1	DROP INDEX "IDX_data_source_account_id" CASCADE	t		2021-05-13 23:42:57
83	drop index UQE_data_source_account_id_name - v1	DROP INDEX "UQE_data_source_account_id_name" CASCADE	t		2021-05-13 23:42:57
84	Rename table data_source to data_source_v1 - v1	ALTER TABLE "data_source" RENAME TO "data_source_v1"	t		2021-05-13 23:42:57
85	create data_source table v2	CREATE TABLE IF NOT EXISTS "data_source" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "version" INTEGER NOT NULL\n, "type" VARCHAR(255) NOT NULL\n, "name" VARCHAR(190) NOT NULL\n, "access" VARCHAR(255) NOT NULL\n, "url" VARCHAR(255) NOT NULL\n, "password" VARCHAR(255) NULL\n, "user" VARCHAR(255) NULL\n, "database" VARCHAR(255) NULL\n, "basic_auth" BOOL NOT NULL\n, "basic_auth_user" VARCHAR(255) NULL\n, "basic_auth_password" VARCHAR(255) NULL\n, "is_default" BOOL NOT NULL\n, "json_data" TEXT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:57
86	create index IDX_data_source_org_id - v2	CREATE INDEX "IDX_data_source_org_id" ON "data_source" ("org_id");	t		2021-05-13 23:42:57
87	create index UQE_data_source_org_id_name - v2	CREATE UNIQUE INDEX "UQE_data_source_org_id_name" ON "data_source" ("org_id","name");	t		2021-05-13 23:42:57
88	copy data_source v1 to v2	INSERT INTO "data_source" ("id"\n, "type"\n, "access"\n, "database"\n, "basic_auth"\n, "org_id"\n, "url"\n, "user"\n, "password"\n, "basic_auth_user"\n, "is_default"\n, "version"\n, "basic_auth_password"\n, "name"\n, "created"\n, "updated") SELECT "id"\n, "type"\n, "access"\n, "database"\n, "basic_auth"\n, "account_id"\n, "url"\n, "user"\n, "password"\n, "basic_auth_user"\n, "is_default"\n, "version"\n, "basic_auth_password"\n, "name"\n, "created"\n, "updated" FROM "data_source_v1"	t		2021-05-13 23:42:57
89	Drop old table data_source_v1 #2	DROP TABLE IF EXISTS "data_source_v1"	t		2021-05-13 23:42:57
90	Add column with_credentials	alter table "data_source" ADD COLUMN "with_credentials" BOOL NOT NULL DEFAULT FALSE 	t		2021-05-13 23:42:58
91	Add secure json data column	alter table "data_source" ADD COLUMN "secure_json_data" TEXT NULL 	t		2021-05-13 23:42:58
92	Update data_source table charset	ALTER TABLE "data_source" ALTER "type" TYPE VARCHAR(255), ALTER "name" TYPE VARCHAR(190), ALTER "access" TYPE VARCHAR(255), ALTER "url" TYPE VARCHAR(255), ALTER "password" TYPE VARCHAR(255), ALTER "user" TYPE VARCHAR(255), ALTER "database" TYPE VARCHAR(255), ALTER "basic_auth_user" TYPE VARCHAR(255), ALTER "basic_auth_password" TYPE VARCHAR(255), ALTER "json_data" TYPE TEXT, ALTER "secure_json_data" TYPE TEXT;	t		2021-05-13 23:42:58
93	Update initial version to 1	UPDATE data_source SET version = 1 WHERE version = 0	t		2021-05-13 23:42:58
94	Add read_only data column	alter table "data_source" ADD COLUMN "read_only" BOOL NULL 	t		2021-05-13 23:42:58
95	Migrate logging ds to loki ds	UPDATE data_source SET type = 'loki' WHERE type = 'logging'	t		2021-05-13 23:42:58
96	Update json_data with nulls	UPDATE data_source SET json_data = '{}' WHERE json_data is null	t		2021-05-13 23:42:58
97	Add uid column	alter table "data_source" ADD COLUMN "uid" VARCHAR(40) NOT NULL DEFAULT 0 	t		2021-05-13 23:42:58
98	Update uid value	UPDATE data_source SET uid=lpad('' || id::text,9,'0');	t		2021-05-13 23:42:58
99	Add unique index datasource_org_id_uid	CREATE UNIQUE INDEX "UQE_data_source_org_id_uid" ON "data_source" ("org_id","uid");	t		2021-05-13 23:42:58
100	create api_key table	CREATE TABLE IF NOT EXISTS "api_key" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "account_id" BIGINT NOT NULL\n, "name" VARCHAR(190) NOT NULL\n, "key" VARCHAR(64) NOT NULL\n, "role" VARCHAR(255) NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:58
101	add index api_key.account_id	CREATE INDEX "IDX_api_key_account_id" ON "api_key" ("account_id");	t		2021-05-13 23:42:58
102	add index api_key.key	CREATE UNIQUE INDEX "UQE_api_key_key" ON "api_key" ("key");	t		2021-05-13 23:42:58
103	add index api_key.account_id_name	CREATE UNIQUE INDEX "UQE_api_key_account_id_name" ON "api_key" ("account_id","name");	t		2021-05-13 23:42:58
104	drop index IDX_api_key_account_id - v1	DROP INDEX "IDX_api_key_account_id" CASCADE	t		2021-05-13 23:42:58
105	drop index UQE_api_key_key - v1	DROP INDEX "UQE_api_key_key" CASCADE	t		2021-05-13 23:42:58
106	drop index UQE_api_key_account_id_name - v1	DROP INDEX "UQE_api_key_account_id_name" CASCADE	t		2021-05-13 23:42:58
107	Rename table api_key to api_key_v1 - v1	ALTER TABLE "api_key" RENAME TO "api_key_v1"	t		2021-05-13 23:42:58
108	create api_key table v2	CREATE TABLE IF NOT EXISTS "api_key" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "name" VARCHAR(190) NOT NULL\n, "key" VARCHAR(190) NOT NULL\n, "role" VARCHAR(255) NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:58
109	create index IDX_api_key_org_id - v2	CREATE INDEX "IDX_api_key_org_id" ON "api_key" ("org_id");	t		2021-05-13 23:42:58
110	create index UQE_api_key_key - v2	CREATE UNIQUE INDEX "UQE_api_key_key" ON "api_key" ("key");	t		2021-05-13 23:42:58
111	create index UQE_api_key_org_id_name - v2	CREATE UNIQUE INDEX "UQE_api_key_org_id_name" ON "api_key" ("org_id","name");	t		2021-05-13 23:42:58
112	copy api_key v1 to v2	INSERT INTO "api_key" ("updated"\n, "id"\n, "org_id"\n, "name"\n, "key"\n, "role"\n, "created") SELECT "updated"\n, "id"\n, "account_id"\n, "name"\n, "key"\n, "role"\n, "created" FROM "api_key_v1"	t		2021-05-13 23:42:58
113	Drop old table api_key_v1	DROP TABLE IF EXISTS "api_key_v1"	t		2021-05-13 23:42:58
114	Update api_key table charset	ALTER TABLE "api_key" ALTER "name" TYPE VARCHAR(190), ALTER "key" TYPE VARCHAR(190), ALTER "role" TYPE VARCHAR(255);	t		2021-05-13 23:42:58
115	Add expires to api_key table	alter table "api_key" ADD COLUMN "expires" BIGINT NULL 	t		2021-05-13 23:42:58
116	create dashboard_snapshot table v4	CREATE TABLE IF NOT EXISTS "dashboard_snapshot" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "name" VARCHAR(255) NOT NULL\n, "key" VARCHAR(190) NOT NULL\n, "dashboard" TEXT NOT NULL\n, "expires" TIMESTAMP NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:58
117	drop table dashboard_snapshot_v4 #1	DROP TABLE IF EXISTS "dashboard_snapshot"	t		2021-05-13 23:42:58
118	create dashboard_snapshot table v5 #2	CREATE TABLE IF NOT EXISTS "dashboard_snapshot" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "name" VARCHAR(255) NOT NULL\n, "key" VARCHAR(190) NOT NULL\n, "delete_key" VARCHAR(190) NOT NULL\n, "org_id" BIGINT NOT NULL\n, "user_id" BIGINT NOT NULL\n, "external" BOOL NOT NULL\n, "external_url" VARCHAR(255) NOT NULL\n, "dashboard" TEXT NOT NULL\n, "expires" TIMESTAMP NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:58
119	create index UQE_dashboard_snapshot_key - v5	CREATE UNIQUE INDEX "UQE_dashboard_snapshot_key" ON "dashboard_snapshot" ("key");	t		2021-05-13 23:42:58
120	create index UQE_dashboard_snapshot_delete_key - v5	CREATE UNIQUE INDEX "UQE_dashboard_snapshot_delete_key" ON "dashboard_snapshot" ("delete_key");	t		2021-05-13 23:42:58
121	create index IDX_dashboard_snapshot_user_id - v5	CREATE INDEX "IDX_dashboard_snapshot_user_id" ON "dashboard_snapshot" ("user_id");	t		2021-05-13 23:42:58
122	alter dashboard_snapshot to mediumtext v2	SELECT 0;	t		2021-05-13 23:42:58
123	Update dashboard_snapshot table charset	ALTER TABLE "dashboard_snapshot" ALTER "name" TYPE VARCHAR(255), ALTER "key" TYPE VARCHAR(190), ALTER "delete_key" TYPE VARCHAR(190), ALTER "external_url" TYPE VARCHAR(255), ALTER "dashboard" TYPE TEXT;	t		2021-05-13 23:42:58
124	Add column external_delete_url to dashboard_snapshots table	alter table "dashboard_snapshot" ADD COLUMN "external_delete_url" VARCHAR(255) NULL 	t		2021-05-13 23:42:58
125	create quota table v1	CREATE TABLE IF NOT EXISTS "quota" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NULL\n, "user_id" BIGINT NULL\n, "target" VARCHAR(190) NOT NULL\n, "limit" BIGINT NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:58
126	create index UQE_quota_org_id_user_id_target - v1	CREATE UNIQUE INDEX "UQE_quota_org_id_user_id_target" ON "quota" ("org_id","user_id","target");	t		2021-05-13 23:42:58
127	Update quota table charset	ALTER TABLE "quota" ALTER "target" TYPE VARCHAR(190);	t		2021-05-13 23:42:58
128	create plugin_setting table	CREATE TABLE IF NOT EXISTS "plugin_setting" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NULL\n, "plugin_id" VARCHAR(190) NOT NULL\n, "enabled" BOOL NOT NULL\n, "pinned" BOOL NOT NULL\n, "json_data" TEXT NULL\n, "secure_json_data" TEXT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:58
129	create index UQE_plugin_setting_org_id_plugin_id - v1	CREATE UNIQUE INDEX "UQE_plugin_setting_org_id_plugin_id" ON "plugin_setting" ("org_id","plugin_id");	t		2021-05-13 23:42:58
130	Add column plugin_version to plugin_settings	alter table "plugin_setting" ADD COLUMN "plugin_version" VARCHAR(50) NULL 	t		2021-05-13 23:42:58
131	Update plugin_setting table charset	ALTER TABLE "plugin_setting" ALTER "plugin_id" TYPE VARCHAR(190), ALTER "json_data" TYPE TEXT, ALTER "secure_json_data" TYPE TEXT, ALTER "plugin_version" TYPE VARCHAR(50);	t		2021-05-13 23:42:58
132	create session table	CREATE TABLE IF NOT EXISTS "session" (\n"key" CHAR(16) PRIMARY KEY NOT NULL\n, "data" BYTEA NOT NULL\n, "expiry" INTEGER NOT NULL\n);	t		2021-05-13 23:42:58
133	Drop old table playlist table	DROP TABLE IF EXISTS "playlist"	t		2021-05-13 23:42:58
134	Drop old table playlist_item table	DROP TABLE IF EXISTS "playlist_item"	t		2021-05-13 23:42:58
135	create playlist table v2	CREATE TABLE IF NOT EXISTS "playlist" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "name" VARCHAR(255) NOT NULL\n, "interval" VARCHAR(255) NOT NULL\n, "org_id" BIGINT NOT NULL\n);	t		2021-05-13 23:42:58
136	create playlist item table v2	CREATE TABLE IF NOT EXISTS "playlist_item" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "playlist_id" BIGINT NOT NULL\n, "type" VARCHAR(255) NOT NULL\n, "value" TEXT NOT NULL\n, "title" TEXT NOT NULL\n, "order" INTEGER NOT NULL\n);	t		2021-05-13 23:42:58
137	Update playlist table charset	ALTER TABLE "playlist" ALTER "name" TYPE VARCHAR(255), ALTER "interval" TYPE VARCHAR(255);	t		2021-05-13 23:42:58
138	Update playlist_item table charset	ALTER TABLE "playlist_item" ALTER "type" TYPE VARCHAR(255), ALTER "value" TYPE TEXT, ALTER "title" TYPE TEXT;	t		2021-05-13 23:42:58
139	drop preferences table v2	DROP TABLE IF EXISTS "preferences"	t		2021-05-13 23:42:58
140	drop preferences table v3	DROP TABLE IF EXISTS "preferences"	t		2021-05-13 23:42:58
141	create preferences table v3	CREATE TABLE IF NOT EXISTS "preferences" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "user_id" BIGINT NOT NULL\n, "version" INTEGER NOT NULL\n, "home_dashboard_id" BIGINT NOT NULL\n, "timezone" VARCHAR(50) NOT NULL\n, "theme" VARCHAR(20) NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:58
142	Update preferences table charset	ALTER TABLE "preferences" ALTER "timezone" TYPE VARCHAR(50), ALTER "theme" TYPE VARCHAR(20);	t		2021-05-13 23:42:58
143	Add column team_id in preferences	alter table "preferences" ADD COLUMN "team_id" BIGINT NULL 	t		2021-05-13 23:42:58
144	Update team_id column values in preferences	UPDATE preferences SET team_id=0 WHERE team_id IS NULL;	t		2021-05-13 23:42:58
145	create alert table v1	CREATE TABLE IF NOT EXISTS "alert" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "version" BIGINT NOT NULL\n, "dashboard_id" BIGINT NOT NULL\n, "panel_id" BIGINT NOT NULL\n, "org_id" BIGINT NOT NULL\n, "name" VARCHAR(255) NOT NULL\n, "message" TEXT NOT NULL\n, "state" VARCHAR(190) NOT NULL\n, "settings" TEXT NOT NULL\n, "frequency" BIGINT NOT NULL\n, "handler" BIGINT NOT NULL\n, "severity" TEXT NOT NULL\n, "silenced" BOOL NOT NULL\n, "execution_error" TEXT NOT NULL\n, "eval_data" TEXT NULL\n, "eval_date" TIMESTAMP NULL\n, "new_state_date" TIMESTAMP NOT NULL\n, "state_changes" INTEGER NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:58
146	add index alert org_id & id 	CREATE INDEX "IDX_alert_org_id_id" ON "alert" ("org_id","id");	t		2021-05-13 23:42:58
147	add index alert state	CREATE INDEX "IDX_alert_state" ON "alert" ("state");	t		2021-05-13 23:42:58
148	add index alert dashboard_id	CREATE INDEX "IDX_alert_dashboard_id" ON "alert" ("dashboard_id");	t		2021-05-13 23:42:58
149	Create alert_rule_tag table v1	CREATE TABLE IF NOT EXISTS "alert_rule_tag" (\n"alert_id" BIGINT NOT NULL\n, "tag_id" BIGINT NOT NULL\n);	t		2021-05-13 23:42:58
150	Add unique index alert_rule_tag.alert_id_tag_id	CREATE UNIQUE INDEX "UQE_alert_rule_tag_alert_id_tag_id" ON "alert_rule_tag" ("alert_id","tag_id");	t		2021-05-13 23:42:58
151	create alert_notification table v1	CREATE TABLE IF NOT EXISTS "alert_notification" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "name" VARCHAR(190) NOT NULL\n, "type" VARCHAR(255) NOT NULL\n, "settings" TEXT NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:58
152	Add column is_default	alter table "alert_notification" ADD COLUMN "is_default" BOOL NOT NULL DEFAULT FALSE 	t		2021-05-13 23:42:58
153	Add column frequency	alter table "alert_notification" ADD COLUMN "frequency" BIGINT NULL 	t		2021-05-13 23:42:58
154	Add column send_reminder	alter table "alert_notification" ADD COLUMN "send_reminder" BOOL NULL DEFAULT FALSE 	t		2021-05-13 23:42:58
155	Add column disable_resolve_message	alter table "alert_notification" ADD COLUMN "disable_resolve_message" BOOL NOT NULL DEFAULT FALSE 	t		2021-05-13 23:42:58
156	add index alert_notification org_id & name	CREATE UNIQUE INDEX "UQE_alert_notification_org_id_name" ON "alert_notification" ("org_id","name");	t		2021-05-13 23:42:58
157	Update alert table charset	ALTER TABLE "alert" ALTER "name" TYPE VARCHAR(255), ALTER "message" TYPE TEXT, ALTER "state" TYPE VARCHAR(190), ALTER "settings" TYPE TEXT, ALTER "severity" TYPE TEXT, ALTER "execution_error" TYPE TEXT, ALTER "eval_data" TYPE TEXT;	t		2021-05-13 23:42:58
158	Update alert_notification table charset	ALTER TABLE "alert_notification" ALTER "name" TYPE VARCHAR(190), ALTER "type" TYPE VARCHAR(255), ALTER "settings" TYPE TEXT;	t		2021-05-13 23:42:58
159	create notification_journal table v1	CREATE TABLE IF NOT EXISTS "alert_notification_journal" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "alert_id" BIGINT NOT NULL\n, "notifier_id" BIGINT NOT NULL\n, "sent_at" BIGINT NOT NULL\n, "success" BOOL NOT NULL\n);	t		2021-05-13 23:42:58
160	add index notification_journal org_id & alert_id & notifier_id	CREATE INDEX "IDX_alert_notification_journal_org_id_alert_id_notifier_id" ON "alert_notification_journal" ("org_id","alert_id","notifier_id");	t		2021-05-13 23:42:58
161	drop alert_notification_journal	DROP TABLE IF EXISTS "alert_notification_journal"	t		2021-05-13 23:42:58
162	create alert_notification_state table v1	CREATE TABLE IF NOT EXISTS "alert_notification_state" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "alert_id" BIGINT NOT NULL\n, "notifier_id" BIGINT NOT NULL\n, "state" VARCHAR(50) NOT NULL\n, "version" BIGINT NOT NULL\n, "updated_at" BIGINT NOT NULL\n, "alert_rule_state_updated_version" BIGINT NOT NULL\n);	t		2021-05-13 23:42:58
163	add index alert_notification_state org_id & alert_id & notifier_id	CREATE UNIQUE INDEX "UQE_alert_notification_state_org_id_alert_id_notifier_id" ON "alert_notification_state" ("org_id","alert_id","notifier_id");	t		2021-05-13 23:42:58
164	Add for to alert table	alter table "alert" ADD COLUMN "for" BIGINT NULL 	t		2021-05-13 23:42:58
165	Add column uid in alert_notification	alter table "alert_notification" ADD COLUMN "uid" VARCHAR(40) NULL 	t		2021-05-13 23:42:58
166	Update uid column values in alert_notification	UPDATE alert_notification SET uid=lpad('' || id::text,9,'0') WHERE uid IS NULL;	t		2021-05-13 23:42:58
167	Add unique index alert_notification_org_id_uid	CREATE UNIQUE INDEX "UQE_alert_notification_org_id_uid" ON "alert_notification" ("org_id","uid");	t		2021-05-13 23:42:58
168	Remove unique index org_id_name	DROP INDEX "UQE_alert_notification_org_id_name" CASCADE	t		2021-05-13 23:42:58
169	Add column secure_settings in alert_notification	alter table "alert_notification" ADD COLUMN "secure_settings" TEXT NULL 	t		2021-05-13 23:42:58
170	alter alert.settings to mediumtext	SELECT 0;	t		2021-05-13 23:42:58
171	Drop old annotation table v4	DROP TABLE IF EXISTS "annotation"	t		2021-05-13 23:42:58
172	create annotation table v5	CREATE TABLE IF NOT EXISTS "annotation" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "alert_id" BIGINT NULL\n, "user_id" BIGINT NULL\n, "dashboard_id" BIGINT NULL\n, "panel_id" BIGINT NULL\n, "category_id" BIGINT NULL\n, "type" VARCHAR(25) NOT NULL\n, "title" TEXT NOT NULL\n, "text" TEXT NOT NULL\n, "metric" VARCHAR(255) NULL\n, "prev_state" VARCHAR(25) NOT NULL\n, "new_state" VARCHAR(25) NOT NULL\n, "data" TEXT NOT NULL\n, "epoch" BIGINT NOT NULL\n);	t		2021-05-13 23:42:58
173	add index annotation 0 v3	CREATE INDEX "IDX_annotation_org_id_alert_id" ON "annotation" ("org_id","alert_id");	t		2021-05-13 23:42:58
174	add index annotation 1 v3	CREATE INDEX "IDX_annotation_org_id_type" ON "annotation" ("org_id","type");	t		2021-05-13 23:42:58
175	add index annotation 2 v3	CREATE INDEX "IDX_annotation_org_id_category_id" ON "annotation" ("org_id","category_id");	t		2021-05-13 23:42:58
176	add index annotation 3 v3	CREATE INDEX "IDX_annotation_org_id_dashboard_id_panel_id_epoch" ON "annotation" ("org_id","dashboard_id","panel_id","epoch");	t		2021-05-13 23:42:58
177	add index annotation 4 v3	CREATE INDEX "IDX_annotation_org_id_epoch" ON "annotation" ("org_id","epoch");	t		2021-05-13 23:42:58
178	Update annotation table charset	ALTER TABLE "annotation" ALTER "type" TYPE VARCHAR(25), ALTER "title" TYPE TEXT, ALTER "text" TYPE TEXT, ALTER "metric" TYPE VARCHAR(255), ALTER "prev_state" TYPE VARCHAR(25), ALTER "new_state" TYPE VARCHAR(25), ALTER "data" TYPE TEXT;	t		2021-05-13 23:42:58
179	Add column region_id to annotation table	alter table "annotation" ADD COLUMN "region_id" BIGINT NULL DEFAULT 0 	t		2021-05-13 23:42:58
180	Drop category_id index	DROP INDEX "IDX_annotation_org_id_category_id" CASCADE	t		2021-05-13 23:42:58
181	Add column tags to annotation table	alter table "annotation" ADD COLUMN "tags" VARCHAR(500) NULL 	t		2021-05-13 23:42:58
182	Create annotation_tag table v2	CREATE TABLE IF NOT EXISTS "annotation_tag" (\n"annotation_id" BIGINT NOT NULL\n, "tag_id" BIGINT NOT NULL\n);	t		2021-05-13 23:42:58
183	Add unique index annotation_tag.annotation_id_tag_id	CREATE UNIQUE INDEX "UQE_annotation_tag_annotation_id_tag_id" ON "annotation_tag" ("annotation_id","tag_id");	t		2021-05-13 23:42:58
184	Update alert annotations and set TEXT to empty	UPDATE annotation SET TEXT = '' WHERE alert_id > 0	t		2021-05-13 23:42:58
185	Add created time to annotation table	alter table "annotation" ADD COLUMN "created" BIGINT NULL DEFAULT 0 	t		2021-05-13 23:42:58
186	Add updated time to annotation table	alter table "annotation" ADD COLUMN "updated" BIGINT NULL DEFAULT 0 	t		2021-05-13 23:42:58
187	Add index for created in annotation table	CREATE INDEX "IDX_annotation_org_id_created" ON "annotation" ("org_id","created");	t		2021-05-13 23:42:58
188	Add index for updated in annotation table	CREATE INDEX "IDX_annotation_org_id_updated" ON "annotation" ("org_id","updated");	t		2021-05-13 23:42:58
189	Convert existing annotations from seconds to milliseconds	UPDATE annotation SET epoch = (epoch*1000) where epoch < 9999999999	t		2021-05-13 23:42:58
190	Add epoch_end column	alter table "annotation" ADD COLUMN "epoch_end" BIGINT NOT NULL DEFAULT 0 	t		2021-05-13 23:42:58
191	Add index for epoch_end	CREATE INDEX "IDX_annotation_org_id_epoch_epoch_end" ON "annotation" ("org_id","epoch","epoch_end");	t		2021-05-13 23:42:58
192	Make epoch_end the same as epoch	UPDATE annotation SET epoch_end = epoch	t		2021-05-13 23:42:58
193	Move region to single row	code migration	t		2021-05-13 23:42:58
194	Remove index org_id_epoch from annotation table	DROP INDEX "IDX_annotation_org_id_epoch" CASCADE	t		2021-05-13 23:42:58
195	Remove index org_id_dashboard_id_panel_id_epoch from annotation table	DROP INDEX "IDX_annotation_org_id_dashboard_id_panel_id_epoch" CASCADE	t		2021-05-13 23:42:58
196	Add index for org_id_dashboard_id_epoch_end_epoch on annotation table	CREATE INDEX "IDX_annotation_org_id_dashboard_id_epoch_end_epoch" ON "annotation" ("org_id","dashboard_id","epoch_end","epoch");	t		2021-05-13 23:42:58
197	Add index for org_id_epoch_end_epoch on annotation table	CREATE INDEX "IDX_annotation_org_id_epoch_end_epoch" ON "annotation" ("org_id","epoch_end","epoch");	t		2021-05-13 23:42:58
198	Remove index org_id_epoch_epoch_end from annotation table	DROP INDEX "IDX_annotation_org_id_epoch_epoch_end" CASCADE	t		2021-05-13 23:42:58
199	Add index for alert_id on annotation table	CREATE INDEX "IDX_annotation_alert_id" ON "annotation" ("alert_id");	t		2021-05-13 23:42:58
200	create test_data table	CREATE TABLE IF NOT EXISTS "test_data" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "metric1" VARCHAR(20) NULL\n, "metric2" VARCHAR(150) NULL\n, "value_big_int" BIGINT NULL\n, "value_double" DOUBLE PRECISION NULL\n, "value_float" REAL NULL\n, "value_int" INTEGER NULL\n, "time_epoch" BIGINT NOT NULL\n, "time_date_time" TIMESTAMP NOT NULL\n, "time_time_stamp" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:58
201	create dashboard_version table v1	CREATE TABLE IF NOT EXISTS "dashboard_version" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "dashboard_id" BIGINT NOT NULL\n, "parent_version" INTEGER NOT NULL\n, "restored_from" INTEGER NOT NULL\n, "version" INTEGER NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "created_by" BIGINT NOT NULL\n, "message" TEXT NOT NULL\n, "data" TEXT NOT NULL\n);	t		2021-05-13 23:42:58
202	add index dashboard_version.dashboard_id	CREATE INDEX "IDX_dashboard_version_dashboard_id" ON "dashboard_version" ("dashboard_id");	t		2021-05-13 23:42:58
203	add unique index dashboard_version.dashboard_id and dashboard_version.version	CREATE UNIQUE INDEX "UQE_dashboard_version_dashboard_id_version" ON "dashboard_version" ("dashboard_id","version");	t		2021-05-13 23:42:58
204	Set dashboard version to 1 where 0	UPDATE dashboard SET version = 1 WHERE version = 0	t		2021-05-13 23:42:58
205	save existing dashboard data in dashboard_version table v1	INSERT INTO dashboard_version\n(\n\tdashboard_id,\n\tversion,\n\tparent_version,\n\trestored_from,\n\tcreated,\n\tcreated_by,\n\tmessage,\n\tdata\n)\nSELECT\n\tdashboard.id,\n\tdashboard.version,\n\tdashboard.version,\n\tdashboard.version,\n\tdashboard.updated,\n\tCOALESCE(dashboard.updated_by, -1),\n\t'',\n\tdashboard.data\nFROM dashboard;	t		2021-05-13 23:42:58
206	alter dashboard_version.data to mediumtext v1	SELECT 0;	t		2021-05-13 23:42:58
207	create team table	CREATE TABLE IF NOT EXISTS "team" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "name" VARCHAR(190) NOT NULL\n, "org_id" BIGINT NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:58
208	add index team.org_id	CREATE INDEX "IDX_team_org_id" ON "team" ("org_id");	t		2021-05-13 23:42:58
209	add unique index team_org_id_name	CREATE UNIQUE INDEX "UQE_team_org_id_name" ON "team" ("org_id","name");	t		2021-05-13 23:42:58
210	create team member table	CREATE TABLE IF NOT EXISTS "team_member" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "team_id" BIGINT NOT NULL\n, "user_id" BIGINT NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:58
211	add index team_member.org_id	CREATE INDEX "IDX_team_member_org_id" ON "team_member" ("org_id");	t		2021-05-13 23:42:58
212	add unique index team_member_org_id_team_id_user_id	CREATE UNIQUE INDEX "UQE_team_member_org_id_team_id_user_id" ON "team_member" ("org_id","team_id","user_id");	t		2021-05-13 23:42:58
213	add index team_member.team_id	CREATE INDEX "IDX_team_member_team_id" ON "team_member" ("team_id");	t		2021-05-13 23:42:58
214	Add column email to team table	alter table "team" ADD COLUMN "email" VARCHAR(190) NULL 	t		2021-05-13 23:42:58
215	Add column external to team_member table	alter table "team_member" ADD COLUMN "external" BOOL NULL 	t		2021-05-13 23:42:58
216	Add column permission to team_member table	alter table "team_member" ADD COLUMN "permission" SMALLINT NULL 	t		2021-05-13 23:42:58
217	create dashboard acl table	CREATE TABLE IF NOT EXISTS "dashboard_acl" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "dashboard_id" BIGINT NOT NULL\n, "user_id" BIGINT NULL\n, "team_id" BIGINT NULL\n, "permission" SMALLINT NOT NULL DEFAULT 4\n, "role" VARCHAR(20) NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:58
218	add index dashboard_acl_dashboard_id	CREATE INDEX "IDX_dashboard_acl_dashboard_id" ON "dashboard_acl" ("dashboard_id");	t		2021-05-13 23:42:58
219	add unique index dashboard_acl_dashboard_id_user_id	CREATE UNIQUE INDEX "UQE_dashboard_acl_dashboard_id_user_id" ON "dashboard_acl" ("dashboard_id","user_id");	t		2021-05-13 23:42:58
220	add unique index dashboard_acl_dashboard_id_team_id	CREATE UNIQUE INDEX "UQE_dashboard_acl_dashboard_id_team_id" ON "dashboard_acl" ("dashboard_id","team_id");	t		2021-05-13 23:42:58
221	save default acl rules in dashboard_acl table	\nINSERT INTO dashboard_acl\n\t(\n\t\torg_id,\n\t\tdashboard_id,\n\t\tpermission,\n\t\trole,\n\t\tcreated,\n\t\tupdated\n\t)\n\tVALUES\n\t\t(-1,-1, 1,'Viewer','2017-06-20','2017-06-20'),\n\t\t(-1,-1, 2,'Editor','2017-06-20','2017-06-20')\n\t	t		2021-05-13 23:42:58
222	create tag table	CREATE TABLE IF NOT EXISTS "tag" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "key" VARCHAR(100) NOT NULL\n, "value" VARCHAR(100) NOT NULL\n);	t		2021-05-13 23:42:58
223	add index tag.key_value	CREATE UNIQUE INDEX "UQE_tag_key_value" ON "tag" ("key","value");	t		2021-05-13 23:42:58
224	create login attempt table	CREATE TABLE IF NOT EXISTS "login_attempt" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "username" VARCHAR(190) NOT NULL\n, "ip_address" VARCHAR(30) NOT NULL\n, "created" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:58
225	add index login_attempt.username	CREATE INDEX "IDX_login_attempt_username" ON "login_attempt" ("username");	t		2021-05-13 23:42:58
226	drop index IDX_login_attempt_username - v1	DROP INDEX "IDX_login_attempt_username" CASCADE	t		2021-05-13 23:42:58
227	Rename table login_attempt to login_attempt_tmp_qwerty - v1	ALTER TABLE "login_attempt" RENAME TO "login_attempt_tmp_qwerty"	t		2021-05-13 23:42:58
228	create login_attempt v2	CREATE TABLE IF NOT EXISTS "login_attempt" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "username" VARCHAR(190) NOT NULL\n, "ip_address" VARCHAR(30) NOT NULL\n, "created" INTEGER NOT NULL DEFAULT 0\n);	t		2021-05-13 23:42:58
229	create index IDX_login_attempt_username - v2	CREATE INDEX "IDX_login_attempt_username" ON "login_attempt" ("username");	t		2021-05-13 23:42:58
230	copy login_attempt v1 to v2	INSERT INTO "login_attempt" ("id"\n, "username"\n, "ip_address") SELECT "id"\n, "username"\n, "ip_address" FROM "login_attempt_tmp_qwerty"	t		2021-05-13 23:42:58
231	drop login_attempt_tmp_qwerty	DROP TABLE IF EXISTS "login_attempt_tmp_qwerty"	t		2021-05-13 23:42:58
232	create user auth table	CREATE TABLE IF NOT EXISTS "user_auth" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "user_id" BIGINT NOT NULL\n, "auth_module" VARCHAR(190) NOT NULL\n, "auth_id" VARCHAR(100) NOT NULL\n, "created" TIMESTAMP NOT NULL\n);	t		2021-05-13 23:42:58
233	create index IDX_user_auth_auth_module_auth_id - v1	CREATE INDEX "IDX_user_auth_auth_module_auth_id" ON "user_auth" ("auth_module","auth_id");	t		2021-05-13 23:42:58
234	alter user_auth.auth_id to length 190	ALTER TABLE user_auth ALTER COLUMN auth_id TYPE VARCHAR(190);	t		2021-05-13 23:42:58
235	Add OAuth access token to user_auth	alter table "user_auth" ADD COLUMN "o_auth_access_token" TEXT NULL 	t		2021-05-13 23:42:58
236	Add OAuth refresh token to user_auth	alter table "user_auth" ADD COLUMN "o_auth_refresh_token" TEXT NULL 	t		2021-05-13 23:42:58
237	Add OAuth token type to user_auth	alter table "user_auth" ADD COLUMN "o_auth_token_type" TEXT NULL 	t		2021-05-13 23:42:58
238	Add OAuth expiry to user_auth	alter table "user_auth" ADD COLUMN "o_auth_expiry" TIMESTAMP NULL 	t		2021-05-13 23:42:58
239	Add index to user_id column in user_auth	CREATE INDEX "IDX_user_auth_user_id" ON "user_auth" ("user_id");	t		2021-05-13 23:42:58
240	create server_lock table	CREATE TABLE IF NOT EXISTS "server_lock" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "operation_uid" VARCHAR(100) NOT NULL\n, "version" BIGINT NOT NULL\n, "last_execution" BIGINT NOT NULL\n);	t		2021-05-13 23:42:58
241	add index server_lock.operation_uid	CREATE UNIQUE INDEX "UQE_server_lock_operation_uid" ON "server_lock" ("operation_uid");	t		2021-05-13 23:42:58
242	create user auth token table	CREATE TABLE IF NOT EXISTS "user_auth_token" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "user_id" BIGINT NOT NULL\n, "auth_token" VARCHAR(100) NOT NULL\n, "prev_auth_token" VARCHAR(100) NOT NULL\n, "user_agent" VARCHAR(255) NOT NULL\n, "client_ip" VARCHAR(255) NOT NULL\n, "auth_token_seen" BOOL NOT NULL\n, "seen_at" INTEGER NULL\n, "rotated_at" INTEGER NOT NULL\n, "created_at" INTEGER NOT NULL\n, "updated_at" INTEGER NOT NULL\n);	t		2021-05-13 23:42:58
243	add unique index user_auth_token.auth_token	CREATE UNIQUE INDEX "UQE_user_auth_token_auth_token" ON "user_auth_token" ("auth_token");	t		2021-05-13 23:42:58
244	add unique index user_auth_token.prev_auth_token	CREATE UNIQUE INDEX "UQE_user_auth_token_prev_auth_token" ON "user_auth_token" ("prev_auth_token");	t		2021-05-13 23:42:58
245	create cache_data table	CREATE TABLE IF NOT EXISTS "cache_data" (\n"cache_key" VARCHAR(168) PRIMARY KEY NOT NULL\n, "data" BYTEA NOT NULL\n, "expires" INTEGER NOT NULL\n, "created_at" INTEGER NOT NULL\n);	t		2021-05-13 23:42:58
246	add unique index cache_data.cache_key	CREATE UNIQUE INDEX "UQE_cache_data_cache_key" ON "cache_data" ("cache_key");	t		2021-05-13 23:42:58
247	Add column account_id in dashboard	alter table "dashboard" ADD COLUMN "account_id" VARCHAR(255) NULL 	t		2021-05-22 14:19:15
248	Add column is_cloud in dashboard	alter table "dashboard" ADD COLUMN "is_cloud" BOOL NOT NULL DEFAULT FALSE 	t		2021-07-30 22:59:55
249	Add column cloud_name in dashboard	alter table "dashboard" ADD COLUMN "cloud_name" VARCHAR(255) NULL 	t		2021-08-06 00:24:03
250	Add column element_type in dashboard	alter table "dashboard" ADD COLUMN "element_type" VARCHAR(255) NULL 	t		2021-08-06 00:24:03
251	Add tenant_id column	alter table "data_source" ADD COLUMN "tenant_id" VARCHAR(255) NULL 	t		2021-08-08 23:24:13
252	Add account_id column	alter table "data_source" ADD COLUMN "account_id" VARCHAR(255) NULL 	t		2021-08-08 23:24:13
253	Add column file_name in dashboard	alter table "dashboard" ADD COLUMN "file_name" VARCHAR(255) NULL 	t		2021-08-16 14:15:09
254	Add column input_type in dashboard	alter table "dashboard" ADD COLUMN "input_type" VARCHAR(255) NULL 	t		2021-08-20 15:41:35
255	Add is_service_account column to user	alter table "user" ADD COLUMN "is_service_account" BOOL NOT NULL DEFAULT FALSE 	t		2022-03-17 14:51:10
256	Update is_service_account column to nullable	ALTER TABLE `user` ALTER COLUMN is_service_account DROP NOT NULL;	t		2022-03-17 14:51:10
257	drop index IDX_temp_user_email - v1	DROP INDEX "IDX_temp_user_email" CASCADE	t		2022-03-17 14:51:10
258	drop index IDX_temp_user_org_id - v1	DROP INDEX "IDX_temp_user_org_id" CASCADE	t		2022-03-17 14:51:10
259	drop index IDX_temp_user_code - v1	DROP INDEX "IDX_temp_user_code" CASCADE	t		2022-03-17 14:51:10
260	drop index IDX_temp_user_status - v1	DROP INDEX "IDX_temp_user_status" CASCADE	t		2022-03-17 14:51:10
261	Rename table temp_user to temp_user_tmp_qwerty - v1	ALTER TABLE "temp_user" RENAME TO "temp_user_tmp_qwerty"	t		2022-03-17 14:51:10
262	create temp_user v2	CREATE TABLE IF NOT EXISTS "temp_user" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "version" INTEGER NOT NULL\n, "email" VARCHAR(190) NOT NULL\n, "name" VARCHAR(255) NULL\n, "role" VARCHAR(20) NULL\n, "code" VARCHAR(190) NOT NULL\n, "status" VARCHAR(20) NOT NULL\n, "invited_by_user_id" BIGINT NULL\n, "email_sent" BOOL NOT NULL\n, "email_sent_on" TIMESTAMP NULL\n, "remote_addr" VARCHAR(255) NULL\n, "created" INTEGER NOT NULL DEFAULT 0\n, "updated" INTEGER NOT NULL DEFAULT 0\n);	t		2022-03-17 14:51:10
263	create index IDX_temp_user_email - v2	CREATE INDEX "IDX_temp_user_email" ON "temp_user" ("email");	t		2022-03-17 14:51:10
264	create index IDX_temp_user_org_id - v2	CREATE INDEX "IDX_temp_user_org_id" ON "temp_user" ("org_id");	t		2022-03-17 14:51:10
265	create index IDX_temp_user_code - v2	CREATE INDEX "IDX_temp_user_code" ON "temp_user" ("code");	t		2022-03-17 14:51:10
266	create index IDX_temp_user_status - v2	CREATE INDEX "IDX_temp_user_status" ON "temp_user" ("status");	t		2022-03-17 14:51:10
267	copy temp_user v1 to v2	INSERT INTO "temp_user" ("code"\n, "invited_by_user_id"\n, "name"\n, "org_id"\n, "version"\n, "email"\n, "role"\n, "status"\n, "email_sent"\n, "email_sent_on"\n, "id"\n, "remote_addr") SELECT "code"\n, "invited_by_user_id"\n, "name"\n, "org_id"\n, "version"\n, "email"\n, "role"\n, "status"\n, "email_sent"\n, "email_sent_on"\n, "id"\n, "remote_addr" FROM "temp_user_tmp_qwerty"	t		2022-03-17 14:51:10
268	drop temp_user_tmp_qwerty	DROP TABLE IF EXISTS "temp_user_tmp_qwerty"	t		2022-03-17 14:51:10
269	Set created for temp users that will otherwise prematurely expire	code migration	t		2022-03-17 14:51:10
270	create index IDX_org_user_user_id - v1	CREATE INDEX "IDX_org_user_user_id" ON "org_user" ("user_id");	t		2022-03-17 14:51:10
271	delete tags for deleted dashboards	DELETE FROM dashboard_tag WHERE dashboard_id NOT IN (SELECT id FROM dashboard)	t		2022-03-17 14:51:10
272	delete stars for deleted dashboards	DELETE FROM star WHERE dashboard_id NOT IN (SELECT id FROM dashboard)	t		2022-03-17 14:51:10
273	Add index for dashboard_is_folder	CREATE INDEX "IDX_dashboard_is_folder" ON "dashboard" ("is_folder");	t		2022-03-17 14:51:10
274	add unique index datasource_org_id_is_default	CREATE INDEX "IDX_data_source_org_id_is_default" ON "data_source" ("org_id","is_default");	t		2022-03-17 14:51:10
275	Add service account foreign key	alter table "api_key" ADD COLUMN "service_account_id" BIGINT NULL 	t		2022-03-17 14:51:10
276	Add encrypted dashboard json column	alter table "dashboard_snapshot" ADD COLUMN "dashboard_encrypted" BYTEA NULL 	t		2022-03-17 14:51:10
277	Change dashboard_encrypted column to MEDIUMBLOB	SELECT 0;	t		2022-03-17 14:51:11
278	Add column week_start in preferences	alter table "preferences" ADD COLUMN "week_start" VARCHAR(10) NULL 	t		2022-03-17 14:51:11
279	drop index UQE_alert_rule_tag_alert_id_tag_id - v1	DROP INDEX "UQE_alert_rule_tag_alert_id_tag_id" CASCADE	t		2022-03-17 14:51:11
280	Rename table alert_rule_tag to alert_rule_tag_v1 - v1	ALTER TABLE "alert_rule_tag" RENAME TO "alert_rule_tag_v1"	t		2022-03-17 14:51:11
281	Create alert_rule_tag table v2	CREATE TABLE IF NOT EXISTS "alert_rule_tag" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "alert_id" BIGINT NOT NULL\n, "tag_id" BIGINT NOT NULL\n);	t		2022-03-17 14:51:11
282	create index UQE_alert_rule_tag_alert_id_tag_id - Add unique index alert_rule_tag.alert_id_tag_id V2	CREATE UNIQUE INDEX "UQE_alert_rule_tag_alert_id_tag_id" ON "alert_rule_tag" ("alert_id","tag_id");	t		2022-03-17 14:51:11
283	copy alert_rule_tag v1 to v2	INSERT INTO "alert_rule_tag" ("tag_id"\n, "alert_id") SELECT "tag_id"\n, "alert_id" FROM "alert_rule_tag_v1"	t		2022-03-17 14:51:11
284	drop table alert_rule_tag_v1	DROP TABLE IF EXISTS "alert_rule_tag_v1"	t		2022-03-17 14:51:11
285	Add non-unique index alert_notification_state_alert_id	CREATE INDEX "IDX_alert_notification_state_alert_id" ON "alert_notification_state" ("alert_id");	t		2022-03-17 14:51:11
286	Add non-unique index alert_rule_tag_alert_id	CREATE INDEX "IDX_alert_rule_tag_alert_id" ON "alert_rule_tag" ("alert_id");	t		2022-03-17 14:51:11
287	drop index UQE_annotation_tag_annotation_id_tag_id - v2	DROP INDEX "UQE_annotation_tag_annotation_id_tag_id" CASCADE	t		2022-03-17 14:51:11
289	Create annotation_tag table v3	CREATE TABLE IF NOT EXISTS "annotation_tag" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "annotation_id" BIGINT NOT NULL\n, "tag_id" BIGINT NOT NULL\n);	t		2022-03-17 14:51:11
290	create index UQE_annotation_tag_annotation_id_tag_id - Add unique index annotation_tag.annotation_id_tag_id V3	CREATE UNIQUE INDEX "UQE_annotation_tag_annotation_id_tag_id" ON "annotation_tag" ("annotation_id","tag_id");	t		2022-03-17 14:51:11
291	copy annotation_tag v2 to v3	INSERT INTO "annotation_tag" ("annotation_id"\n, "tag_id") SELECT "annotation_id"\n, "tag_id" FROM "annotation_tag_v2"	t		2022-03-17 14:51:11
292	drop table annotation_tag_v2	DROP TABLE IF EXISTS "annotation_tag_v2"	t		2022-03-17 14:51:11
293	add index dashboard_acl_user_id	CREATE INDEX "IDX_dashboard_acl_user_id" ON "dashboard_acl" ("user_id");	t		2022-03-17 14:51:11
294	add index dashboard_acl_team_id	CREATE INDEX "IDX_dashboard_acl_team_id" ON "dashboard_acl" ("team_id");	t		2022-03-17 14:51:11
295	add index dashboard_acl_org_id_role	CREATE INDEX "IDX_dashboard_acl_org_id_role" ON "dashboard_acl" ("org_id","role");	t		2022-03-17 14:51:11
296	add index dashboard_permission	CREATE INDEX "IDX_dashboard_acl_permission" ON "dashboard_acl" ("permission");	t		2022-03-17 14:51:11
297	delete acl rules for deleted dashboards and folders	DELETE FROM dashboard_acl WHERE dashboard_id NOT IN (SELECT id FROM dashboard) AND dashboard_id != -1	t		2022-03-17 14:51:11
298	Add OAuth ID token to user_auth	alter table "user_auth" ADD COLUMN "o_auth_id_token" TEXT NULL 	t		2022-03-17 14:51:11
299	add index user_auth_token.user_id	CREATE INDEX "IDX_user_auth_token_user_id" ON "user_auth_token" ("user_id");	t		2022-03-17 14:51:11
300	Add revoked_at to the user auth token	alter table "user_auth_token" ADD COLUMN "revoked_at" INTEGER NULL 	t		2022-03-17 14:51:11
301	create short_url table v1	CREATE TABLE IF NOT EXISTS "short_url" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "uid" VARCHAR(40) NOT NULL\n, "path" TEXT NOT NULL\n, "created_by" INTEGER NOT NULL\n, "created_at" INTEGER NOT NULL\n, "last_seen_at" INTEGER NULL\n);	t		2022-03-17 14:51:11
302	add index short_url.org_id-uid	CREATE UNIQUE INDEX "UQE_short_url_org_id_uid" ON "short_url" ("org_id","uid");	t		2022-03-17 14:51:11
303	delete alert_definition table	DROP TABLE IF EXISTS "alert_definition"	t		2022-03-17 14:51:11
304	recreate alert_definition table	CREATE TABLE IF NOT EXISTS "alert_definition" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "title" VARCHAR(190) NOT NULL\n, "condition" VARCHAR(190) NOT NULL\n, "data" TEXT NOT NULL\n, "updated" TIMESTAMP NOT NULL\n, "interval_seconds" BIGINT NOT NULL DEFAULT 60\n, "version" INTEGER NOT NULL DEFAULT 0\n, "uid" VARCHAR(40) NOT NULL DEFAULT 0\n);	t		2022-03-17 14:51:11
305	add index in alert_definition on org_id and title columns	CREATE INDEX "IDX_alert_definition_org_id_title" ON "alert_definition" ("org_id","title");	t		2022-03-17 14:51:11
306	add index in alert_definition on org_id and uid columns	CREATE INDEX "IDX_alert_definition_org_id_uid" ON "alert_definition" ("org_id","uid");	t		2022-03-17 14:51:11
307	alter alert_definition table data column to mediumtext in mysql	SELECT 0;	t		2022-03-17 14:51:11
308	drop index in alert_definition on org_id and title columns	DROP INDEX "IDX_alert_definition_org_id_title" CASCADE	t		2022-03-17 14:51:11
309	drop index in alert_definition on org_id and uid columns	DROP INDEX "IDX_alert_definition_org_id_uid" CASCADE	t		2022-03-17 14:51:11
310	add unique index in alert_definition on org_id and title columns	CREATE UNIQUE INDEX "UQE_alert_definition_org_id_title" ON "alert_definition" ("org_id","title");	t		2022-03-17 14:51:11
311	add unique index in alert_definition on org_id and uid columns	CREATE UNIQUE INDEX "UQE_alert_definition_org_id_uid" ON "alert_definition" ("org_id","uid");	t		2022-03-17 14:51:11
312	Add column paused in alert_definition	alter table "alert_definition" ADD COLUMN "paused" BOOL NOT NULL DEFAULT FALSE 	t		2022-03-17 14:51:11
313	drop alert_definition table	DROP TABLE IF EXISTS "alert_definition"	t		2022-03-17 14:51:11
314	delete alert_definition_version table	DROP TABLE IF EXISTS "alert_definition_version"	t		2022-03-17 14:51:11
315	recreate alert_definition_version table	CREATE TABLE IF NOT EXISTS "alert_definition_version" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "alert_definition_id" BIGINT NOT NULL\n, "alert_definition_uid" VARCHAR(40) NOT NULL DEFAULT 0\n, "parent_version" INTEGER NOT NULL\n, "restored_from" INTEGER NOT NULL\n, "version" INTEGER NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "title" VARCHAR(190) NOT NULL\n, "condition" VARCHAR(190) NOT NULL\n, "data" TEXT NOT NULL\n, "interval_seconds" BIGINT NOT NULL\n);	t		2022-03-17 14:51:11
316	add index in alert_definition_version table on alert_definition_id and version columns	CREATE UNIQUE INDEX "UQE_alert_definition_version_alert_definition_id_version" ON "alert_definition_version" ("alert_definition_id","version");	t		2022-03-17 14:51:11
317	add index in alert_definition_version table on alert_definition_uid and version columns	CREATE UNIQUE INDEX "UQE_alert_definition_version_alert_definition_uid_version" ON "alert_definition_version" ("alert_definition_uid","version");	t		2022-03-17 14:51:11
318	alter alert_definition_version table data column to mediumtext in mysql	SELECT 0;	t		2022-03-17 14:51:11
319	drop alert_definition_version table	DROP TABLE IF EXISTS "alert_definition_version"	t		2022-03-17 14:51:11
320	create alert_instance table	CREATE TABLE IF NOT EXISTS "alert_instance" (\n"def_org_id" BIGINT NOT NULL\n, "def_uid" VARCHAR(40) NOT NULL DEFAULT 0\n, "labels" TEXT NOT NULL\n, "labels_hash" VARCHAR(190) NOT NULL\n, "current_state" VARCHAR(190) NOT NULL\n, "current_state_since" BIGINT NOT NULL\n, "last_eval_time" BIGINT NOT NULL\n, PRIMARY KEY ( "def_org_id","def_uid","labels_hash" ));	t		2022-03-17 14:51:11
321	add index in alert_instance table on def_org_id, def_uid and current_state columns	CREATE INDEX "IDX_alert_instance_def_org_id_def_uid_current_state" ON "alert_instance" ("def_org_id","def_uid","current_state");	t		2022-03-17 14:51:11
322	add index in alert_instance table on def_org_id, current_state columns	CREATE INDEX "IDX_alert_instance_def_org_id_current_state" ON "alert_instance" ("def_org_id","current_state");	t		2022-03-17 14:51:11
323	add column current_state_end to alert_instance	alter table "alert_instance" ADD COLUMN "current_state_end" BIGINT NOT NULL DEFAULT 0 	t		2022-03-17 14:51:11
324	remove index def_org_id, def_uid, current_state on alert_instance	DROP INDEX "IDX_alert_instance_def_org_id_def_uid_current_state" CASCADE	t		2022-03-17 14:51:11
325	remove index def_org_id, current_state on alert_instance	DROP INDEX "IDX_alert_instance_def_org_id_current_state" CASCADE	t		2022-03-17 14:51:11
326	rename def_org_id to rule_org_id in alert_instance	ALTER TABLE alert_instance RENAME COLUMN def_org_id TO rule_org_id;	t		2022-03-17 14:51:11
327	rename def_uid to rule_uid in alert_instance	ALTER TABLE alert_instance RENAME COLUMN def_uid TO rule_uid;	t		2022-03-17 14:51:11
328	add index rule_org_id, rule_uid, current_state on alert_instance	CREATE INDEX "IDX_alert_instance_rule_org_id_rule_uid_current_state" ON "alert_instance" ("rule_org_id","rule_uid","current_state");	t		2022-03-17 14:51:11
329	add index rule_org_id, current_state on alert_instance	CREATE INDEX "IDX_alert_instance_rule_org_id_current_state" ON "alert_instance" ("rule_org_id","current_state");	t		2022-03-17 14:51:11
330	create alert_rule table	CREATE TABLE IF NOT EXISTS "alert_rule" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "title" VARCHAR(190) NOT NULL\n, "condition" VARCHAR(190) NOT NULL\n, "data" TEXT NOT NULL\n, "updated" TIMESTAMP NOT NULL\n, "interval_seconds" BIGINT NOT NULL DEFAULT 60\n, "version" INTEGER NOT NULL DEFAULT 0\n, "uid" VARCHAR(40) NOT NULL DEFAULT 0\n, "namespace_uid" VARCHAR(40) NOT NULL\n, "rule_group" VARCHAR(190) NOT NULL\n, "no_data_state" VARCHAR(15) NOT NULL DEFAULT 'NoData'\n, "exec_err_state" VARCHAR(15) NOT NULL DEFAULT 'Alerting'\n);	t		2022-03-17 14:51:11
331	add index in alert_rule on org_id and title columns	CREATE UNIQUE INDEX "UQE_alert_rule_org_id_title" ON "alert_rule" ("org_id","title");	t		2022-03-17 14:51:11
332	add index in alert_rule on org_id and uid columns	CREATE UNIQUE INDEX "UQE_alert_rule_org_id_uid" ON "alert_rule" ("org_id","uid");	t		2022-03-17 14:51:11
333	add index in alert_rule on org_id, namespace_uid, group_uid columns	CREATE INDEX "IDX_alert_rule_org_id_namespace_uid_rule_group" ON "alert_rule" ("org_id","namespace_uid","rule_group");	t		2022-03-17 14:51:11
334	alter alert_rule table data column to mediumtext in mysql	SELECT 0;	t		2022-03-17 14:51:11
335	add column for to alert_rule	alter table "alert_rule" ADD COLUMN "for" BIGINT NOT NULL DEFAULT 0 	t		2022-03-17 14:51:11
336	add column annotations to alert_rule	alter table "alert_rule" ADD COLUMN "annotations" TEXT NULL 	t		2022-03-17 14:51:11
337	add column labels to alert_rule	alter table "alert_rule" ADD COLUMN "labels" TEXT NULL 	t		2022-03-17 14:51:11
338	remove unique index from alert_rule on org_id, title columns	DROP INDEX "UQE_alert_rule_org_id_title" CASCADE	t		2022-03-17 14:51:11
339	add index in alert_rule on org_id, namespase_uid and title columns	CREATE UNIQUE INDEX "UQE_alert_rule_org_id_namespace_uid_title" ON "alert_rule" ("org_id","namespace_uid","title");	t		2022-03-17 14:51:11
340	add dashboard_uid column to alert_rule	alter table "alert_rule" ADD COLUMN "dashboard_uid" VARCHAR(40) NULL 	t		2022-03-17 14:51:11
341	add panel_id column to alert_rule	alter table "alert_rule" ADD COLUMN "panel_id" BIGINT NULL 	t		2022-03-17 14:51:11
342	add index in alert_rule on org_id, dashboard_uid and panel_id columns	CREATE INDEX "IDX_alert_rule_org_id_dashboard_uid_panel_id" ON "alert_rule" ("org_id","dashboard_uid","panel_id");	t		2022-03-17 14:51:11
343	create alert_rule_version table	CREATE TABLE IF NOT EXISTS "alert_rule_version" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "rule_org_id" BIGINT NOT NULL\n, "rule_uid" VARCHAR(40) NOT NULL DEFAULT 0\n, "rule_namespace_uid" VARCHAR(40) NOT NULL\n, "rule_group" VARCHAR(190) NOT NULL\n, "parent_version" INTEGER NOT NULL\n, "restored_from" INTEGER NOT NULL\n, "version" INTEGER NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "title" VARCHAR(190) NOT NULL\n, "condition" VARCHAR(190) NOT NULL\n, "data" TEXT NOT NULL\n, "interval_seconds" BIGINT NOT NULL\n, "no_data_state" VARCHAR(15) NOT NULL DEFAULT 'NoData'\n, "exec_err_state" VARCHAR(15) NOT NULL DEFAULT 'Alerting'\n);	t		2022-03-17 14:51:11
344	add index in alert_rule_version table on rule_org_id, rule_uid and version columns	CREATE UNIQUE INDEX "UQE_alert_rule_version_rule_org_id_rule_uid_version" ON "alert_rule_version" ("rule_org_id","rule_uid","version");	t		2022-03-17 14:51:12
345	add index in alert_rule_version table on rule_org_id, rule_namespace_uid and rule_group columns	CREATE INDEX "IDX_alert_rule_version_rule_org_id_rule_namespace_uid_rule_group" ON "alert_rule_version" ("rule_org_id","rule_namespace_uid","rule_group");	t		2022-03-17 14:51:12
346	alter alert_rule_version table data column to mediumtext in mysql	SELECT 0;	t		2022-03-17 14:51:12
347	add column for to alert_rule_version	alter table "alert_rule_version" ADD COLUMN "for" BIGINT NOT NULL DEFAULT 0 	t		2022-03-17 14:51:12
348	add column annotations to alert_rule_version	alter table "alert_rule_version" ADD COLUMN "annotations" TEXT NULL 	t		2022-03-17 14:51:12
349	add column labels to alert_rule_version	alter table "alert_rule_version" ADD COLUMN "labels" TEXT NULL 	t		2022-03-17 14:51:12
350	create_alert_configuration_table	CREATE TABLE IF NOT EXISTS "alert_configuration" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "alertmanager_configuration" TEXT NOT NULL\n, "configuration_version" VARCHAR(3) NOT NULL\n, "created_at" INTEGER NOT NULL\n);	t		2022-03-17 14:51:12
351	Add column default in alert_configuration	alter table "alert_configuration" ADD COLUMN "default" BOOL NOT NULL DEFAULT FALSE 	t		2022-03-17 14:51:12
352	alert alert_configuration alertmanager_configuration column from TEXT to MEDIUMTEXT if mysql	SELECT 0;	t		2022-03-17 14:51:12
353	add column org_id in alert_configuration	alter table "alert_configuration" ADD COLUMN "org_id" BIGINT NOT NULL DEFAULT 0 	t		2022-03-17 14:51:12
354	add index in alert_configuration table on org_id column	CREATE INDEX "IDX_alert_configuration_org_id" ON "alert_configuration" ("org_id");	t		2022-03-17 14:51:12
355	create_ngalert_configuration_table	CREATE TABLE IF NOT EXISTS "ngalert_configuration" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "alertmanagers" TEXT NULL\n, "created_at" INTEGER NOT NULL\n, "updated_at" INTEGER NOT NULL\n);	t		2022-03-17 14:51:12
356	add index in ngalert_configuration on org_id column	CREATE UNIQUE INDEX "UQE_ngalert_configuration_org_id" ON "ngalert_configuration" ("org_id");	t		2022-03-17 14:51:12
357	add column send_alerts_to in ngalert_configuration	alter table "ngalert_configuration" ADD COLUMN "send_alerts_to" SMALLINT NOT NULL DEFAULT 0 	t		2022-03-17 14:51:12
358	create provenance_type table	CREATE TABLE IF NOT EXISTS "provenance_type" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "record_key" VARCHAR(190) NOT NULL\n, "record_type" VARCHAR(190) NOT NULL\n, "provenance" VARCHAR(190) NOT NULL\n);	t		2022-03-17 14:51:12
359	add index to uniquify (record_key, record_type, org_id) columns	CREATE UNIQUE INDEX "UQE_provenance_type_record_type_record_key_org_id" ON "provenance_type" ("record_type","record_key","org_id");	t		2022-03-17 14:51:12
360	move dashboard alerts to unified alerting	code migration	t		2022-03-17 14:51:12
361	create library_element table v1	CREATE TABLE IF NOT EXISTS "library_element" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "folder_id" BIGINT NOT NULL\n, "uid" VARCHAR(40) NOT NULL\n, "name" VARCHAR(150) NOT NULL\n, "kind" BIGINT NOT NULL\n, "type" VARCHAR(40) NOT NULL\n, "description" VARCHAR(255) NOT NULL\n, "model" TEXT NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "created_by" BIGINT NOT NULL\n, "updated" TIMESTAMP NOT NULL\n, "updated_by" BIGINT NOT NULL\n, "version" BIGINT NOT NULL\n);	t		2022-03-17 14:51:12
362	add index library_element org_id-folder_id-name-kind	CREATE UNIQUE INDEX "UQE_library_element_org_id_folder_id_name_kind" ON "library_element" ("org_id","folder_id","name","kind");	t		2022-03-17 14:51:12
363	create library_element_connection table v1	CREATE TABLE IF NOT EXISTS "library_element_connection" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "element_id" BIGINT NOT NULL\n, "kind" BIGINT NOT NULL\n, "connection_id" BIGINT NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "created_by" BIGINT NOT NULL\n);	t		2022-03-17 14:51:12
364	add index library_element_connection element_id-kind-connection_id	CREATE UNIQUE INDEX "UQE_library_element_connection_element_id_kind_connection_id" ON "library_element_connection" ("element_id","kind","connection_id");	t		2022-03-17 14:51:12
365	add unique index library_element org_id_uid	CREATE UNIQUE INDEX "UQE_library_element_org_id_uid" ON "library_element" ("org_id","uid");	t		2022-03-17 14:51:12
366	clone move dashboard alerts to unified alerting	code migration	t		2022-03-17 14:51:12
367	create data_keys table	CREATE TABLE IF NOT EXISTS "data_keys" (\n"name" VARCHAR(100) PRIMARY KEY NOT NULL\n, "active" BOOL NOT NULL\n, "scope" VARCHAR(30) NOT NULL\n, "provider" VARCHAR(50) NOT NULL\n, "encrypted_data" BYTEA NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2022-03-17 14:51:12
368	create kv_store table v1	CREATE TABLE IF NOT EXISTS "kv_store" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "namespace" VARCHAR(190) NOT NULL\n, "key" VARCHAR(190) NOT NULL\n, "value" TEXT NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2022-03-17 14:51:12
369	add index kv_store.org_id-namespace-key	CREATE UNIQUE INDEX "UQE_kv_store_org_id_namespace_key" ON "kv_store" ("org_id","namespace","key");	t		2022-03-17 14:51:12
370	update dashboard_uid and panel_id from existing annotations	set dashboard_uid and panel_id migration	t		2022-03-17 14:51:12
371	create permission table	CREATE TABLE IF NOT EXISTS "permission" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "role_id" BIGINT NOT NULL\n, "action" VARCHAR(190) NOT NULL\n, "scope" VARCHAR(190) NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2022-03-17 14:51:12
372	add unique index permission.role_id	CREATE INDEX "IDX_permission_role_id" ON "permission" ("role_id");	t		2022-03-17 14:51:12
373	add unique index role_id_action_scope	CREATE UNIQUE INDEX "UQE_permission_role_id_action_scope" ON "permission" ("role_id","action","scope");	t		2022-03-17 14:51:12
374	create role table	CREATE TABLE IF NOT EXISTS "role" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "name" VARCHAR(190) NOT NULL\n, "description" TEXT NULL\n, "version" BIGINT NOT NULL\n, "org_id" BIGINT NOT NULL\n, "uid" VARCHAR(40) NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2022-03-17 14:51:12
375	add column display_name	alter table "role" ADD COLUMN "display_name" VARCHAR(190) NULL 	t		2022-03-17 14:51:12
376	add column group_name	alter table "role" ADD COLUMN "group_name" VARCHAR(190) NULL 	t		2022-03-17 14:51:12
377	add index role.org_id	CREATE INDEX "IDX_role_org_id" ON "role" ("org_id");	t		2022-03-17 14:51:12
378	add unique index role_org_id_name	CREATE UNIQUE INDEX "UQE_role_org_id_name" ON "role" ("org_id","name");	t		2022-03-17 14:51:12
379	add index role_org_id_uid	CREATE UNIQUE INDEX "UQE_role_org_id_uid" ON "role" ("org_id","uid");	t		2022-03-17 14:51:12
380	create team role table	CREATE TABLE IF NOT EXISTS "team_role" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "team_id" BIGINT NOT NULL\n, "role_id" BIGINT NOT NULL\n, "created" TIMESTAMP NOT NULL\n);	t		2022-03-17 14:51:12
381	add index team_role.org_id	CREATE INDEX "IDX_team_role_org_id" ON "team_role" ("org_id");	t		2022-03-17 14:51:12
382	add unique index team_role_org_id_team_id_role_id	CREATE UNIQUE INDEX "UQE_team_role_org_id_team_id_role_id" ON "team_role" ("org_id","team_id","role_id");	t		2022-03-17 14:51:12
383	add index team_role.team_id	CREATE INDEX "IDX_team_role_team_id" ON "team_role" ("team_id");	t		2022-03-17 14:51:12
384	create user role table	CREATE TABLE IF NOT EXISTS "user_role" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "org_id" BIGINT NOT NULL\n, "user_id" BIGINT NOT NULL\n, "role_id" BIGINT NOT NULL\n, "created" TIMESTAMP NOT NULL\n);	t		2022-03-17 14:51:12
385	add index user_role.org_id	CREATE INDEX "IDX_user_role_org_id" ON "user_role" ("org_id");	t		2022-03-17 14:51:12
386	add unique index user_role_org_id_user_id_role_id	CREATE UNIQUE INDEX "UQE_user_role_org_id_user_id_role_id" ON "user_role" ("org_id","user_id","role_id");	t		2022-03-17 14:51:12
387	add index user_role.user_id	CREATE INDEX "IDX_user_role_user_id" ON "user_role" ("user_id");	t		2022-03-17 14:51:12
388	create builtin role table	CREATE TABLE IF NOT EXISTS "builtin_role" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "role" VARCHAR(190) NOT NULL\n, "role_id" BIGINT NOT NULL\n, "created" TIMESTAMP NOT NULL\n, "updated" TIMESTAMP NOT NULL\n);	t		2022-03-17 14:51:12
389	add index builtin_role.role_id	CREATE INDEX "IDX_builtin_role_role_id" ON "builtin_role" ("role_id");	t		2022-03-17 14:51:12
390	add index builtin_role.name	CREATE INDEX "IDX_builtin_role_role" ON "builtin_role" ("role");	t		2022-03-17 14:51:12
391	Add column org_id to builtin_role table	alter table "builtin_role" ADD COLUMN "org_id" BIGINT NOT NULL DEFAULT 0 	t		2022-03-17 14:51:12
392	add index builtin_role.org_id	CREATE INDEX "IDX_builtin_role_org_id" ON "builtin_role" ("org_id");	t		2022-03-17 14:51:12
393	add unique index builtin_role_org_id_role_id_role	CREATE UNIQUE INDEX "UQE_builtin_role_org_id_role_id_role" ON "builtin_role" ("org_id","role_id","role");	t		2022-03-17 14:51:12
394	Remove unique index role_org_id_uid	DROP INDEX "UQE_role_org_id_uid" CASCADE	t		2022-03-17 14:51:12
395	add unique index role.uid	CREATE UNIQUE INDEX "UQE_role_uid" ON "role" ("uid");	t		2022-03-17 14:51:12
396	create seed assignment table	CREATE TABLE IF NOT EXISTS "seed_assignment" (\n"builtin_role" VARCHAR(190) NOT NULL\n, "role_name" VARCHAR(190) NOT NULL\n);	t		2022-03-17 14:51:12
397	add unique index builtin_role_role_name	CREATE UNIQUE INDEX "UQE_seed_assignment_builtin_role_role_name" ON "seed_assignment" ("builtin_role","role_name");	t		2022-03-17 14:51:12
398	create query_history table v1	CREATE TABLE IF NOT EXISTS "query_history" (\n"id" SERIAL PRIMARY KEY  NOT NULL\n, "uid" VARCHAR(40) NOT NULL\n, "org_id" BIGINT NOT NULL\n, "datasource_uid" VARCHAR(40) NOT NULL\n, "created_by" INTEGER NOT NULL\n, "created_at" INTEGER NOT NULL\n, "comment" TEXT NOT NULL\n, "queries" TEXT NOT NULL\n);	t		2022-03-17 14:51:12
399	add index query_history.org_id-created_by-datasource_uid	CREATE INDEX "IDX_query_history_org_id_created_by_datasource_uid" ON "query_history" ("org_id","created_by","datasource_uid");	t		2022-03-17 14:51:13
\.


--
-- Data for Name: ngalert_configuration; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ngalert_configuration (id, org_id, alertmanagers, created_at, updated_at, send_alerts_to) FROM stdin;
\.


--
-- Data for Name: org; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.org (id, version, name, address1, address2, city, state, zip_code, country, billing_email, created, updated) FROM stdin;
1	0	Main Org.							\N	2021-05-13 23:42:58	2021-05-13 23:42:58
\.


--
-- Data for Name: org_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.org_user (id, org_id, user_id, role, created, updated) FROM stdin;
1	1	1	Admin	2021-05-13 23:42:58	2021-05-13 23:42:58
\.


--
-- Data for Name: permission; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.permission (id, role_id, action, scope, created, updated) FROM stdin;
\.


--
-- Data for Name: playlist; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.playlist (id, name, "interval", org_id) FROM stdin;
\.


--
-- Data for Name: playlist_item; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.playlist_item (id, playlist_id, type, value, title, "order") FROM stdin;
\.


--
-- Data for Name: plugin_setting; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.plugin_setting (id, org_id, plugin_id, enabled, pinned, json_data, secure_json_data, created, updated, plugin_version) FROM stdin;
1	1	xformation-alertmanager-ui-plugin	t	t	null	null	2022-03-22 15:03:55	2022-03-22 15:03:55	1.0.0
2	1	xformation-assetmanager-ui-plugin	t	t	null	null	2022-03-22 15:04:09	2022-03-22 15:04:09	1.0.0
\.


--
-- Data for Name: preferences; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.preferences (id, org_id, user_id, version, home_dashboard_id, timezone, theme, created, updated, team_id, week_start) FROM stdin;
1	1	0	0	0		light	2021-05-31 15:00:20	2021-05-31 15:00:20	0	\N
\.


--
-- Data for Name: provenance_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.provenance_type (id, org_id, record_key, record_type, provenance) FROM stdin;
\.


--
-- Data for Name: query_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.query_history (id, uid, org_id, datasource_uid, created_by, created_at, comment, queries) FROM stdin;
\.


--
-- Data for Name: quota; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.quota (id, org_id, user_id, target, "limit", created, updated) FROM stdin;
\.


--
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.role (id, name, description, version, org_id, uid, created, updated, display_name, group_name) FROM stdin;
\.


--
-- Data for Name: seed_assignment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.seed_assignment (builtin_role, role_name) FROM stdin;
\.


--
-- Data for Name: server_lock; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.server_lock (id, operation_uid, version, last_execution) FROM stdin;
1	cleanup expired auth tokens	140	1648534686
2	delete old login attempts	3055	1648539635
\.


--
-- Data for Name: session; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.session (key, data, expiry) FROM stdin;
\.


--
-- Data for Name: short_url; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.short_url (id, org_id, uid, path, created_by, created_at, last_seen_at) FROM stdin;
\.


--
-- Data for Name: star; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.star (id, user_id, dashboard_id) FROM stdin;
\.


--
-- Data for Name: tag; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tag (id, key, value) FROM stdin;
\.


--
-- Data for Name: team; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.team (id, name, org_id, created, updated, email) FROM stdin;
\.


--
-- Data for Name: team_member; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.team_member (id, org_id, team_id, user_id, created, updated, external, permission) FROM stdin;
\.


--
-- Data for Name: team_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.team_role (id, org_id, team_id, role_id, created) FROM stdin;
\.


--
-- Data for Name: temp_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.temp_user (id, org_id, version, email, name, role, code, status, invited_by_user_id, email_sent, email_sent_on, remote_addr, created, updated) FROM stdin;
\.


--
-- Data for Name: test_data; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.test_data (id, metric1, metric2, value_big_int, value_double, value_float, value_int, time_epoch, time_date_time, time_time_stamp) FROM stdin;
\.


--
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."user" (id, version, login, email, name, password, salt, rands, company, org_id, is_admin, email_verified, theme, created, updated, help_flags1, last_seen_at, is_disabled, is_service_account) FROM stdin;
1	0	admin	admin@localhost		bb26fc7bc973d2292e3e5eb489752b9351769a5c3ce15fa3735c7a26c52b90afe43f064c7e098df4cef577bf75798adda652	rlZbZ20Vvi	ZIFB0ZsO8X		1	t	f		2021-05-13 23:42:58	2021-05-22 12:41:27	0	2022-03-29 13:07:26	f	f
\.


--
-- Data for Name: user_auth; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_auth (id, user_id, auth_module, auth_id, created, o_auth_access_token, o_auth_refresh_token, o_auth_token_type, o_auth_expiry, o_auth_id_token) FROM stdin;
\.


--
-- Data for Name: user_auth_token; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_auth_token (id, user_id, auth_token, prev_auth_token, user_agent, client_ip, auth_token_seen, seen_at, rotated_at, created_at, updated_at, revoked_at) FROM stdin;
110	1	ac7b5cf84bd0f3c4c1a4b21a5266e5c4a7a71b42d098c305e3d1b73e9a67fdf7	b6364f3666d09de850318007a0a0c9823d8f7c86ad96e7dada6414002df9a41c	Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.84 Safari/537.36	::1	t	1648539448	1648539446	1648536835	1648536835	0
97	1	5cc80e14ecab0c1b9669f3121b270bf8a3bb80cd03c028c450848fdfffda56a7	95bb2977f9ee9f1f44a5c0d7e42361e583ef2211603283f38907294aed2637c0	Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36	::1	t	1648015970	1648015967	1647947695	1647947695	0
101	1	b16ff2a914294103a4edc34d1bad3c82e00fa07ba8c74b508722dc0c6564d560	b16ff2a914294103a4edc34d1bad3c82e00fa07ba8c74b508722dc0c6564d560	Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36 Edg/99.0.1150.46	::1	t	1648122006	1648122006	1648122006	1648122006	0
\.


--
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_role (id, org_id, user_id, role_id, created) FROM stdin;
\.


--
-- Name: alert_configuration_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.alert_configuration_id_seq', 1, true);


--
-- Name: alert_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.alert_id_seq', 1, false);


--
-- Name: alert_notification_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.alert_notification_id_seq', 1, false);


--
-- Name: alert_notification_state_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.alert_notification_state_id_seq', 1, false);


--
-- Name: alert_rule_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.alert_rule_id_seq', 1, false);


--
-- Name: alert_rule_tag_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.alert_rule_tag_id_seq', 1, false);


--
-- Name: alert_rule_version_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.alert_rule_version_id_seq', 1, false);


--
-- Name: annotation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.annotation_id_seq', 1, false);


--
-- Name: annotation_tag_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.annotation_tag_id_seq', 1, false);


--
-- Name: api_key_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.api_key_id_seq1', 1, false);


--
-- Name: builtin_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.builtin_role_id_seq', 1, false);


--
-- Name: dashboard_acl_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.dashboard_acl_id_seq', 2, true);


--
-- Name: dashboard_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.dashboard_id_seq1', 171, true);


--
-- Name: dashboard_provisioning_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.dashboard_provisioning_id_seq1', 1, false);


--
-- Name: dashboard_snapshot_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.dashboard_snapshot_id_seq', 1, false);


--
-- Name: dashboard_tag_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.dashboard_tag_id_seq', 4, true);


--
-- Name: dashboard_version_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.dashboard_version_id_seq', 175, true);


--
-- Name: data_source_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.data_source_id_seq1', 40, true);


--
-- Name: kv_store_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.kv_store_id_seq', 4, true);


--
-- Name: library_element_connection_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.library_element_connection_id_seq', 1, false);


--
-- Name: library_element_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.library_element_id_seq', 1, false);


--
-- Name: login_attempt_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.login_attempt_id_seq1', 1, true);


--
-- Name: migration_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.migration_log_id_seq', 399, true);


--
-- Name: ngalert_configuration_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ngalert_configuration_id_seq', 1, false);


--
-- Name: org_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.org_id_seq', 1, true);


--
-- Name: org_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.org_user_id_seq', 1, true);


--
-- Name: permission_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.permission_id_seq', 1, false);


--
-- Name: playlist_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.playlist_id_seq', 1, false);


--
-- Name: playlist_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.playlist_item_id_seq', 1, false);


--
-- Name: plugin_setting_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.plugin_setting_id_seq', 2, true);


--
-- Name: preferences_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.preferences_id_seq', 1, true);


--
-- Name: provenance_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.provenance_type_id_seq', 1, false);


--
-- Name: query_history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.query_history_id_seq', 1, false);


--
-- Name: quota_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.quota_id_seq', 1, false);


--
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.role_id_seq', 1, false);


--
-- Name: server_lock_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.server_lock_id_seq', 2, true);


--
-- Name: short_url_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.short_url_id_seq', 1, false);


--
-- Name: star_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.star_id_seq', 1, false);


--
-- Name: tag_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tag_id_seq', 1, false);


--
-- Name: team_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.team_id_seq', 1, false);


--
-- Name: team_member_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.team_member_id_seq', 1, false);


--
-- Name: team_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.team_role_id_seq', 1, false);


--
-- Name: temp_user_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.temp_user_id_seq1', 1, false);


--
-- Name: test_data_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.test_data_id_seq', 1, false);


--
-- Name: user_auth_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_auth_id_seq', 1, false);


--
-- Name: user_auth_token_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_auth_token_id_seq', 110, true);


--
-- Name: user_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_id_seq1', 1, true);


--
-- Name: user_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_role_id_seq', 1, false);


--
-- Name: alert_configuration alert_configuration_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alert_configuration
    ADD CONSTRAINT alert_configuration_pkey PRIMARY KEY (id);


--
-- Name: alert_instance alert_instance_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alert_instance
    ADD CONSTRAINT alert_instance_pkey PRIMARY KEY (rule_org_id, rule_uid, labels_hash);


--
-- Name: alert_notification alert_notification_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alert_notification
    ADD CONSTRAINT alert_notification_pkey PRIMARY KEY (id);


--
-- Name: alert_notification_state alert_notification_state_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alert_notification_state
    ADD CONSTRAINT alert_notification_state_pkey PRIMARY KEY (id);


--
-- Name: alert alert_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alert
    ADD CONSTRAINT alert_pkey PRIMARY KEY (id);


--
-- Name: alert_rule alert_rule_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alert_rule
    ADD CONSTRAINT alert_rule_pkey PRIMARY KEY (id);


--
-- Name: alert_rule_tag alert_rule_tag_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alert_rule_tag
    ADD CONSTRAINT alert_rule_tag_pkey PRIMARY KEY (id);


--
-- Name: alert_rule_version alert_rule_version_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alert_rule_version
    ADD CONSTRAINT alert_rule_version_pkey PRIMARY KEY (id);


--
-- Name: annotation annotation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.annotation
    ADD CONSTRAINT annotation_pkey PRIMARY KEY (id);


--
-- Name: annotation_tag annotation_tag_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.annotation_tag
    ADD CONSTRAINT annotation_tag_pkey PRIMARY KEY (id);


--
-- Name: api_key api_key_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.api_key
    ADD CONSTRAINT api_key_pkey1 PRIMARY KEY (id);


--
-- Name: builtin_role builtin_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.builtin_role
    ADD CONSTRAINT builtin_role_pkey PRIMARY KEY (id);


--
-- Name: cache_data cache_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cache_data
    ADD CONSTRAINT cache_data_pkey PRIMARY KEY (cache_key);


--
-- Name: dashboard_acl dashboard_acl_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard_acl
    ADD CONSTRAINT dashboard_acl_pkey PRIMARY KEY (id);


--
-- Name: dashboard dashboard_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard
    ADD CONSTRAINT dashboard_pkey1 PRIMARY KEY (id);


--
-- Name: dashboard_provisioning dashboard_provisioning_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard_provisioning
    ADD CONSTRAINT dashboard_provisioning_pkey1 PRIMARY KEY (id);


--
-- Name: dashboard_snapshot dashboard_snapshot_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard_snapshot
    ADD CONSTRAINT dashboard_snapshot_pkey PRIMARY KEY (id);


--
-- Name: dashboard_tag dashboard_tag_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard_tag
    ADD CONSTRAINT dashboard_tag_pkey PRIMARY KEY (id);


--
-- Name: dashboard_version dashboard_version_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard_version
    ADD CONSTRAINT dashboard_version_pkey PRIMARY KEY (id);


--
-- Name: data_keys data_keys_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.data_keys
    ADD CONSTRAINT data_keys_pkey PRIMARY KEY (name);


--
-- Name: data_source data_source_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.data_source
    ADD CONSTRAINT data_source_pkey1 PRIMARY KEY (id);


--
-- Name: kv_store kv_store_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.kv_store
    ADD CONSTRAINT kv_store_pkey PRIMARY KEY (id);


--
-- Name: library_element_connection library_element_connection_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.library_element_connection
    ADD CONSTRAINT library_element_connection_pkey PRIMARY KEY (id);


--
-- Name: library_element library_element_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.library_element
    ADD CONSTRAINT library_element_pkey PRIMARY KEY (id);


--
-- Name: login_attempt login_attempt_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.login_attempt
    ADD CONSTRAINT login_attempt_pkey1 PRIMARY KEY (id);


--
-- Name: migration_log migration_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.migration_log
    ADD CONSTRAINT migration_log_pkey PRIMARY KEY (id);


--
-- Name: ngalert_configuration ngalert_configuration_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ngalert_configuration
    ADD CONSTRAINT ngalert_configuration_pkey PRIMARY KEY (id);


--
-- Name: org org_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT org_pkey PRIMARY KEY (id);


--
-- Name: org_user org_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.org_user
    ADD CONSTRAINT org_user_pkey PRIMARY KEY (id);


--
-- Name: permission permission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (id);


--
-- Name: playlist_item playlist_item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist_item
    ADD CONSTRAINT playlist_item_pkey PRIMARY KEY (id);


--
-- Name: playlist playlist_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist
    ADD CONSTRAINT playlist_pkey PRIMARY KEY (id);


--
-- Name: plugin_setting plugin_setting_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plugin_setting
    ADD CONSTRAINT plugin_setting_pkey PRIMARY KEY (id);


--
-- Name: preferences preferences_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.preferences
    ADD CONSTRAINT preferences_pkey PRIMARY KEY (id);


--
-- Name: provenance_type provenance_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.provenance_type
    ADD CONSTRAINT provenance_type_pkey PRIMARY KEY (id);


--
-- Name: query_history query_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.query_history
    ADD CONSTRAINT query_history_pkey PRIMARY KEY (id);


--
-- Name: quota quota_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quota
    ADD CONSTRAINT quota_pkey PRIMARY KEY (id);


--
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- Name: server_lock server_lock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.server_lock
    ADD CONSTRAINT server_lock_pkey PRIMARY KEY (id);


--
-- Name: session session_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.session
    ADD CONSTRAINT session_pkey PRIMARY KEY (key);


--
-- Name: short_url short_url_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.short_url
    ADD CONSTRAINT short_url_pkey PRIMARY KEY (id);


--
-- Name: star star_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.star
    ADD CONSTRAINT star_pkey PRIMARY KEY (id);


--
-- Name: tag tag_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);


--
-- Name: team_member team_member_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_member
    ADD CONSTRAINT team_member_pkey PRIMARY KEY (id);


--
-- Name: team team_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_pkey PRIMARY KEY (id);


--
-- Name: team_role team_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_role
    ADD CONSTRAINT team_role_pkey PRIMARY KEY (id);


--
-- Name: temp_user temp_user_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.temp_user
    ADD CONSTRAINT temp_user_pkey1 PRIMARY KEY (id);


--
-- Name: test_data test_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.test_data
    ADD CONSTRAINT test_data_pkey PRIMARY KEY (id);


--
-- Name: user_auth user_auth_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_auth
    ADD CONSTRAINT user_auth_pkey PRIMARY KEY (id);


--
-- Name: user_auth_token user_auth_token_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_auth_token
    ADD CONSTRAINT user_auth_token_pkey PRIMARY KEY (id);


--
-- Name: user user_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey1 PRIMARY KEY (id);


--
-- Name: user_role user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT user_role_pkey PRIMARY KEY (id);


--
-- Name: IDX_alert_configuration_org_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_alert_configuration_org_id" ON public.alert_configuration USING btree (org_id);


--
-- Name: IDX_alert_dashboard_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_alert_dashboard_id" ON public.alert USING btree (dashboard_id);


--
-- Name: IDX_alert_instance_rule_org_id_current_state; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_alert_instance_rule_org_id_current_state" ON public.alert_instance USING btree (rule_org_id, current_state);


--
-- Name: IDX_alert_instance_rule_org_id_rule_uid_current_state; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_alert_instance_rule_org_id_rule_uid_current_state" ON public.alert_instance USING btree (rule_org_id, rule_uid, current_state);


--
-- Name: IDX_alert_notification_state_alert_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_alert_notification_state_alert_id" ON public.alert_notification_state USING btree (alert_id);


--
-- Name: IDX_alert_org_id_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_alert_org_id_id" ON public.alert USING btree (org_id, id);


--
-- Name: IDX_alert_rule_org_id_dashboard_uid_panel_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_alert_rule_org_id_dashboard_uid_panel_id" ON public.alert_rule USING btree (org_id, dashboard_uid, panel_id);


--
-- Name: IDX_alert_rule_org_id_namespace_uid_rule_group; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_alert_rule_org_id_namespace_uid_rule_group" ON public.alert_rule USING btree (org_id, namespace_uid, rule_group);


--
-- Name: IDX_alert_rule_tag_alert_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_alert_rule_tag_alert_id" ON public.alert_rule_tag USING btree (alert_id);


--
-- Name: IDX_alert_rule_version_rule_org_id_rule_namespace_uid_rule_grou; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_alert_rule_version_rule_org_id_rule_namespace_uid_rule_grou" ON public.alert_rule_version USING btree (rule_org_id, rule_namespace_uid, rule_group);


--
-- Name: IDX_alert_state; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_alert_state" ON public.alert USING btree (state);


--
-- Name: IDX_annotation_alert_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_annotation_alert_id" ON public.annotation USING btree (alert_id);


--
-- Name: IDX_annotation_org_id_alert_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_annotation_org_id_alert_id" ON public.annotation USING btree (org_id, alert_id);


--
-- Name: IDX_annotation_org_id_created; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_annotation_org_id_created" ON public.annotation USING btree (org_id, created);


--
-- Name: IDX_annotation_org_id_dashboard_id_epoch_end_epoch; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_annotation_org_id_dashboard_id_epoch_end_epoch" ON public.annotation USING btree (org_id, dashboard_id, epoch_end, epoch);


--
-- Name: IDX_annotation_org_id_epoch_end_epoch; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_annotation_org_id_epoch_end_epoch" ON public.annotation USING btree (org_id, epoch_end, epoch);


--
-- Name: IDX_annotation_org_id_type; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_annotation_org_id_type" ON public.annotation USING btree (org_id, type);


--
-- Name: IDX_annotation_org_id_updated; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_annotation_org_id_updated" ON public.annotation USING btree (org_id, updated);


--
-- Name: IDX_api_key_org_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_api_key_org_id" ON public.api_key USING btree (org_id);


--
-- Name: IDX_builtin_role_org_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_builtin_role_org_id" ON public.builtin_role USING btree (org_id);


--
-- Name: IDX_builtin_role_role; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_builtin_role_role" ON public.builtin_role USING btree (role);


--
-- Name: IDX_builtin_role_role_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_builtin_role_role_id" ON public.builtin_role USING btree (role_id);


--
-- Name: IDX_dashboard_acl_dashboard_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_dashboard_acl_dashboard_id" ON public.dashboard_acl USING btree (dashboard_id);


--
-- Name: IDX_dashboard_acl_org_id_role; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_dashboard_acl_org_id_role" ON public.dashboard_acl USING btree (org_id, role);


--
-- Name: IDX_dashboard_acl_permission; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_dashboard_acl_permission" ON public.dashboard_acl USING btree (permission);


--
-- Name: IDX_dashboard_acl_team_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_dashboard_acl_team_id" ON public.dashboard_acl USING btree (team_id);


--
-- Name: IDX_dashboard_acl_user_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_dashboard_acl_user_id" ON public.dashboard_acl USING btree (user_id);


--
-- Name: IDX_dashboard_gnet_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_dashboard_gnet_id" ON public.dashboard USING btree (gnet_id);


--
-- Name: IDX_dashboard_is_folder; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_dashboard_is_folder" ON public.dashboard USING btree (is_folder);


--
-- Name: IDX_dashboard_org_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_dashboard_org_id" ON public.dashboard USING btree (org_id);


--
-- Name: IDX_dashboard_org_id_plugin_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_dashboard_org_id_plugin_id" ON public.dashboard USING btree (org_id, plugin_id);


--
-- Name: IDX_dashboard_provisioning_dashboard_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_dashboard_provisioning_dashboard_id" ON public.dashboard_provisioning USING btree (dashboard_id);


--
-- Name: IDX_dashboard_provisioning_dashboard_id_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_dashboard_provisioning_dashboard_id_name" ON public.dashboard_provisioning USING btree (dashboard_id, name);


--
-- Name: IDX_dashboard_snapshot_user_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_dashboard_snapshot_user_id" ON public.dashboard_snapshot USING btree (user_id);


--
-- Name: IDX_dashboard_tag_dashboard_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_dashboard_tag_dashboard_id" ON public.dashboard_tag USING btree (dashboard_id);


--
-- Name: IDX_dashboard_title; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_dashboard_title" ON public.dashboard USING btree (title);


--
-- Name: IDX_dashboard_version_dashboard_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_dashboard_version_dashboard_id" ON public.dashboard_version USING btree (dashboard_id);


--
-- Name: IDX_data_source_org_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_data_source_org_id" ON public.data_source USING btree (org_id);


--
-- Name: IDX_data_source_org_id_is_default; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_data_source_org_id_is_default" ON public.data_source USING btree (org_id, is_default);


--
-- Name: IDX_login_attempt_username; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_login_attempt_username" ON public.login_attempt USING btree (username);


--
-- Name: IDX_org_user_org_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_org_user_org_id" ON public.org_user USING btree (org_id);


--
-- Name: IDX_org_user_user_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_org_user_user_id" ON public.org_user USING btree (user_id);


--
-- Name: IDX_permission_role_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_permission_role_id" ON public.permission USING btree (role_id);


--
-- Name: IDX_query_history_org_id_created_by_datasource_uid; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_query_history_org_id_created_by_datasource_uid" ON public.query_history USING btree (org_id, created_by, datasource_uid);


--
-- Name: IDX_role_org_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_role_org_id" ON public.role USING btree (org_id);


--
-- Name: IDX_team_member_org_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_team_member_org_id" ON public.team_member USING btree (org_id);


--
-- Name: IDX_team_member_team_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_team_member_team_id" ON public.team_member USING btree (team_id);


--
-- Name: IDX_team_org_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_team_org_id" ON public.team USING btree (org_id);


--
-- Name: IDX_team_role_org_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_team_role_org_id" ON public.team_role USING btree (org_id);


--
-- Name: IDX_team_role_team_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_team_role_team_id" ON public.team_role USING btree (team_id);


--
-- Name: IDX_temp_user_code; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_temp_user_code" ON public.temp_user USING btree (code);


--
-- Name: IDX_temp_user_email; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_temp_user_email" ON public.temp_user USING btree (email);


--
-- Name: IDX_temp_user_org_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_temp_user_org_id" ON public.temp_user USING btree (org_id);


--
-- Name: IDX_temp_user_status; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_temp_user_status" ON public.temp_user USING btree (status);


--
-- Name: IDX_user_auth_auth_module_auth_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_user_auth_auth_module_auth_id" ON public.user_auth USING btree (auth_module, auth_id);


--
-- Name: IDX_user_auth_token_user_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_user_auth_token_user_id" ON public.user_auth_token USING btree (user_id);


--
-- Name: IDX_user_auth_user_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_user_auth_user_id" ON public.user_auth USING btree (user_id);


--
-- Name: IDX_user_login_email; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_user_login_email" ON public."user" USING btree (login, email);


--
-- Name: IDX_user_role_org_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_user_role_org_id" ON public.user_role USING btree (org_id);


--
-- Name: IDX_user_role_user_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "IDX_user_role_user_id" ON public.user_role USING btree (user_id);


--
-- Name: UQE_alert_notification_org_id_uid; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_alert_notification_org_id_uid" ON public.alert_notification USING btree (org_id, uid);


--
-- Name: UQE_alert_notification_state_org_id_alert_id_notifier_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_alert_notification_state_org_id_alert_id_notifier_id" ON public.alert_notification_state USING btree (org_id, alert_id, notifier_id);


--
-- Name: UQE_alert_rule_org_id_namespace_uid_title; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_alert_rule_org_id_namespace_uid_title" ON public.alert_rule USING btree (org_id, namespace_uid, title);


--
-- Name: UQE_alert_rule_org_id_uid; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_alert_rule_org_id_uid" ON public.alert_rule USING btree (org_id, uid);


--
-- Name: UQE_alert_rule_tag_alert_id_tag_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_alert_rule_tag_alert_id_tag_id" ON public.alert_rule_tag USING btree (alert_id, tag_id);


--
-- Name: UQE_alert_rule_version_rule_org_id_rule_uid_version; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_alert_rule_version_rule_org_id_rule_uid_version" ON public.alert_rule_version USING btree (rule_org_id, rule_uid, version);


--
-- Name: UQE_annotation_tag_annotation_id_tag_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_annotation_tag_annotation_id_tag_id" ON public.annotation_tag USING btree (annotation_id, tag_id);


--
-- Name: UQE_api_key_key; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_api_key_key" ON public.api_key USING btree (key);


--
-- Name: UQE_api_key_org_id_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_api_key_org_id_name" ON public.api_key USING btree (org_id, name);


--
-- Name: UQE_builtin_role_org_id_role_id_role; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_builtin_role_org_id_role_id_role" ON public.builtin_role USING btree (org_id, role_id, role);


--
-- Name: UQE_cache_data_cache_key; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_cache_data_cache_key" ON public.cache_data USING btree (cache_key);


--
-- Name: UQE_dashboard_acl_dashboard_id_team_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_dashboard_acl_dashboard_id_team_id" ON public.dashboard_acl USING btree (dashboard_id, team_id);


--
-- Name: UQE_dashboard_acl_dashboard_id_user_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_dashboard_acl_dashboard_id_user_id" ON public.dashboard_acl USING btree (dashboard_id, user_id);


--
-- Name: UQE_dashboard_org_id_folder_id_title; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_dashboard_org_id_folder_id_title" ON public.dashboard USING btree (org_id, folder_id, title);


--
-- Name: UQE_dashboard_org_id_uid; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_dashboard_org_id_uid" ON public.dashboard USING btree (org_id, uid);


--
-- Name: UQE_dashboard_snapshot_delete_key; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_dashboard_snapshot_delete_key" ON public.dashboard_snapshot USING btree (delete_key);


--
-- Name: UQE_dashboard_snapshot_key; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_dashboard_snapshot_key" ON public.dashboard_snapshot USING btree (key);


--
-- Name: UQE_dashboard_version_dashboard_id_version; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_dashboard_version_dashboard_id_version" ON public.dashboard_version USING btree (dashboard_id, version);


--
-- Name: UQE_data_source_org_id_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_data_source_org_id_name" ON public.data_source USING btree (org_id, name);


--
-- Name: UQE_data_source_org_id_uid; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_data_source_org_id_uid" ON public.data_source USING btree (org_id, uid);


--
-- Name: UQE_kv_store_org_id_namespace_key; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_kv_store_org_id_namespace_key" ON public.kv_store USING btree (org_id, namespace, key);


--
-- Name: UQE_library_element_connection_element_id_kind_connection_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_library_element_connection_element_id_kind_connection_id" ON public.library_element_connection USING btree (element_id, kind, connection_id);


--
-- Name: UQE_library_element_org_id_folder_id_name_kind; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_library_element_org_id_folder_id_name_kind" ON public.library_element USING btree (org_id, folder_id, name, kind);


--
-- Name: UQE_library_element_org_id_uid; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_library_element_org_id_uid" ON public.library_element USING btree (org_id, uid);


--
-- Name: UQE_ngalert_configuration_org_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_ngalert_configuration_org_id" ON public.ngalert_configuration USING btree (org_id);


--
-- Name: UQE_org_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_org_name" ON public.org USING btree (name);


--
-- Name: UQE_org_user_org_id_user_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_org_user_org_id_user_id" ON public.org_user USING btree (org_id, user_id);


--
-- Name: UQE_permission_role_id_action_scope; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_permission_role_id_action_scope" ON public.permission USING btree (role_id, action, scope);


--
-- Name: UQE_plugin_setting_org_id_plugin_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_plugin_setting_org_id_plugin_id" ON public.plugin_setting USING btree (org_id, plugin_id);


--
-- Name: UQE_provenance_type_record_type_record_key_org_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_provenance_type_record_type_record_key_org_id" ON public.provenance_type USING btree (record_type, record_key, org_id);


--
-- Name: UQE_quota_org_id_user_id_target; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_quota_org_id_user_id_target" ON public.quota USING btree (org_id, user_id, target);


--
-- Name: UQE_role_org_id_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_role_org_id_name" ON public.role USING btree (org_id, name);


--
-- Name: UQE_role_uid; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_role_uid" ON public.role USING btree (uid);


--
-- Name: UQE_seed_assignment_builtin_role_role_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_seed_assignment_builtin_role_role_name" ON public.seed_assignment USING btree (builtin_role, role_name);


--
-- Name: UQE_server_lock_operation_uid; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_server_lock_operation_uid" ON public.server_lock USING btree (operation_uid);


--
-- Name: UQE_short_url_org_id_uid; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_short_url_org_id_uid" ON public.short_url USING btree (org_id, uid);


--
-- Name: UQE_star_user_id_dashboard_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_star_user_id_dashboard_id" ON public.star USING btree (user_id, dashboard_id);


--
-- Name: UQE_tag_key_value; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_tag_key_value" ON public.tag USING btree (key, value);


--
-- Name: UQE_team_member_org_id_team_id_user_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_team_member_org_id_team_id_user_id" ON public.team_member USING btree (org_id, team_id, user_id);


--
-- Name: UQE_team_org_id_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_team_org_id_name" ON public.team USING btree (org_id, name);


--
-- Name: UQE_team_role_org_id_team_id_role_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_team_role_org_id_team_id_role_id" ON public.team_role USING btree (org_id, team_id, role_id);


--
-- Name: UQE_user_auth_token_auth_token; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_user_auth_token_auth_token" ON public.user_auth_token USING btree (auth_token);


--
-- Name: UQE_user_auth_token_prev_auth_token; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_user_auth_token_prev_auth_token" ON public.user_auth_token USING btree (prev_auth_token);


--
-- Name: UQE_user_email; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_user_email" ON public."user" USING btree (email);


--
-- Name: UQE_user_login; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_user_login" ON public."user" USING btree (login);


--
-- Name: UQE_user_role_org_id_user_id_role_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "UQE_user_role_org_id_user_id_role_id" ON public.user_role USING btree (org_id, user_id, role_id);


--
-- PostgreSQL database dump complete
--

