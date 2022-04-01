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
-- Name: organization; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.organization (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    created_by character varying(255),
    updated_at timestamp without time zone,
    updated_by character varying(255),
    address character varying(255),
    date_of_establishment timestamp without time zone,
    description character varying(5000),
    email character varying(255),
    fax character varying(255),
    name character varying(255),
    phone character varying(255),
    status character varying(255)
);


ALTER TABLE public.organization OWNER TO postgres;

--
-- Name: organizational_unit; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.organizational_unit (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    created_by character varying(255),
    updated_at timestamp without time zone,
    updated_by character varying(255),
    description character varying(5000),
    name character varying(255),
    status character varying(255),
    organization_id bigint
);


ALTER TABLE public.organizational_unit OWNER TO postgres;

--
-- Name: permission; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.permission (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    created_by character varying(255),
    updated_at timestamp without time zone,
    updated_by character varying(255),
    description character varying(255),
    name character varying(255),
    permission character varying(255),
    version bigint
);


ALTER TABLE public.permission OWNER TO postgres;

--
-- Name: psql_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.psql_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.psql_seq OWNER TO postgres;

--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    created_by character varying(255),
    updated_at timestamp without time zone,
    updated_by character varying(255),
    description character varying(255),
    grp boolean NOT NULL,
    name character varying(255),
    version bigint
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- Name: roles_permissions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles_permissions (
    role_id bigint NOT NULL,
    permissions_id bigint NOT NULL
);


ALTER TABLE public.roles_permissions OWNER TO postgres;

--
-- Name: roles_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles_roles (
    role_id bigint NOT NULL,
    roles_id bigint NOT NULL
);


ALTER TABLE public.roles_roles OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    created_by character varying(255),
    updated_at timestamp without time zone,
    updated_by character varying(255),
    active boolean NOT NULL,
    email character varying(255),
    google_mfa_key character varying(255),
    invite_code character varying(255),
    invite_link character varying(1000),
    invite_sent_on timestamp without time zone,
    invite_status character varying(255),
    is_mfa_enable character varying(255),
    mfa_qr_image_file_path character varying(255),
    password character varying(255),
    temp_password character varying(255),
    type character varying(255),
    username character varying(255),
    organization_id bigint,
    owner_id bigint
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users_roles (
    user_id bigint NOT NULL,
    roles_id bigint NOT NULL
);


ALTER TABLE public.users_roles OWNER TO postgres;

--
-- Data for Name: organization; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.organization (id, created_at, created_by, updated_at, updated_by, address, date_of_establishment, description, email, fax, name, phone, status) FROM stdin;
78	2021-07-21 14:03:36.106	admin	2021-07-21 14:03:36.106	admin	\N	\N	\N	\N	\N	Synectiks	\N	\N
\.


--
-- Data for Name: organizational_unit; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.organizational_unit (id, created_at, created_by, updated_at, updated_by, description, name, status, organization_id) FROM stdin;
\.


--
-- Data for Name: permission; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.permission (id, created_at, created_by, updated_at, updated_by, description, name, permission, version) FROM stdin;
1	\N	\N	\N	\N	Team menu	Team	Team	1
8	\N	\N	\N	\N	Overview menu	Overview	Overview	1
9	\N	\N	\N	\N	Assets menu	Assets	Assets	1
10	\N	\N	\N	\N	Environments sub menu of Assets	Environments	Environments	1
11	\N	\N	\N	\N	Discovered assets sub menu of Assets	Discovered Assets	Discovered Assets	1
12	\N	\N	\N	\N	Monitored assets sub menu of Assets	Monitored Assets	Monitored Assets	1
13	\N	\N	\N	\N	Org unit sub menu of Assets	Org Unit	Org Unit	1
14	\N	\N	\N	\N	Custom-Resources sub menu of Assets	Custom-Resources	Custom-Resources	1
15	\N	\N	\N	\N	App Catalogue menu	App Catalogue	App Catalogue	1
16	\N	\N	\N	\N	View And Search Catalogue sub menu of App Catalogue menu	View And Search Catalogue	View And Search Catalogue	1
17	\N	\N	\N	\N	Library sub menu of App Catalogue menu	Library	Library	1
18	\N	\N	\N	\N	Import Assets From Module Pack sub menu of App Catalogue menu	Import Assets From Module Pack	Import Assets From Module Pack	1
19	\N	\N	\N	\N	Create Or Import Module Packs sub menu of App Catalogue menu	Create Or Import Module Packs	Create Or Import Module Packs	1
20	\N	\N	\N	\N	Events menu	Events	Events	1
21	\N	\N	\N	\N	Dashboard sub menu of Events menu	Dashboard	Dashboard	1
22	\N	\N	\N	\N	New Alert Rule sub menu of Events menu	New Alert Rule	New Alert Rule	1
23	\N	\N	\N	\N	All Alerts Rule sub menu of Events menu	All Alert Rules	All Alert Rules	1
24	\N	\N	\N	\N	Manage Alert Rule sub menu of Events menu	Manage Alert Rule	Manage Alert Rule	1
25	\N	\N	\N	\N	Manage Workflows sub menu of Events menu	Manage Workflows	Manage Workflows	1
26	\N	\N	\N	\N	Analytics menu	Analytics	Analytics	1
27	\N	\N	\N	\N	Manage Dashboards sub menu of Analytics menu	Manage Dashboards	Manage Dashboards	1
28	\N	\N	\N	\N	Manage Views sub menu of Analytics menu	Manage Views	Manage Views	1
29	\N	\N	\N	\N	Task Manager sub menu of Analytics menu	Task Manager	Task Manager	1
30	\N	\N	\N	\N	Performance & Availability menu	Performance & Availability	Performance & Availability	1
31	\N	\N	\N	\N	Dashboard sub menu of Performance & Availability menu	Dashboard	Dashboard	1
32	\N	\N	\N	\N	Collection sub menu of Performance & Availability menu	Collection	Collection	1
33	\N	\N	\N	\N	Rule sub menu of Performance & Availability menu	Rule	Rule	1
34	\N	\N	\N	\N	Preferences sub menu of Performance & Availability menu	Preferences	Preferences	1
35	\N	\N	\N	\N	Discovery sub menu of Performance & Availability menu	Discovery	Discovery	1
36	\N	\N	\N	\N	View sub menu of Performance & Availability menu	View	View	1
37	\N	\N	\N	\N	Logs menu	Logs	Logs	1
38	\N	\N	\N	\N	Overview sub menu of Logs menu	Overview	Overview	1
39	\N	\N	\N	\N	Dashboard sub menu of Logs menu	Dashboard	Dashboard	1
40	\N	\N	\N	\N	Alerts sub menu of Logs menu	Alerts	Alerts	1
41	\N	\N	\N	\N	Preferences sub menu of Logs menu	Preferences	Preferences	1
42	\N	\N	\N	\N	Preference sub menu of Logs menu	Preference	Preference	1
43	\N	\N	\N	\N	Compliance menu	Compliance	Compliance	1
44	\N	\N	\N	\N	Overview sub menu of Compliance menu	Overview	Overview	1
45	\N	\N	\N	\N	Rulesets sub menu of Compliance menu	Rulesets	Rulesets	1
46	\N	\N	\N	\N	Rule Builder sub menu of Compliance menu	Rule Builder	Rule Builder	1
47	\N	\N	\N	\N	Audits sub menu of Compliance menu	Audits	Audits	1
48	\N	\N	\N	\N	Policies sub menu of Compliance menu	Policies	Policies	1
49	\N	\N	\N	\N	Remediation sub menu of Compliance menu	Remediation	Remediation	1
50	\N	\N	\N	\N	Assessment History sub menu of Compliance menu	Assessment History	Assessment History	1
51	\N	\N	\N	\N	Exclusions sub menu of Compliance menu	Exclusions	Exclusions	1
52	\N	\N	\N	\N	Preference sub menu of Compliance menu	Preference	Preference	1
53	\N	\N	\N	\N	Service desk menu	Service desk	Service desk	1
54	\N	\N	\N	\N	Dashboard sub menu of Service desk menu	Dashboard	Dashboard	1
55	\N	\N	\N	\N	Contacts sub menu of Service desk menu	Contacts	Contacts	1
56	\N	\N	\N	\N	Companies sub menu of Service desk menu	Companies	Companies	1
57	\N	\N	\N	\N	Reports sub menu of Service desk menu	Reports	Reports	1
58	\N	\N	\N	\N	Preferences sub menu of Service desk menu	Preferences	Preferences	1
59	\N	\N	\N	\N	Automation menu	Automation	Automation	1
60	\N	\N	\N	\N	Generators menu	Generators	Generators	1
61	\N	\N	\N	\N	Delivery menu	Delivery	Delivery	1
62	\N	\N	\N	\N	Quality menu	Quality	Quality	1
63	\N	\N	\N	\N	Test menu	Test	Test	1
64	\N	\N	\N	\N	Change Manager menu	Change Manager	Change Manager	1
65	\N	\N	\N	\N	Explorer menu	Explorer	Explorer	1
66	\N	\N	\N	\N	RCA menu	RCA	RCA	1
67	\N	\N	\N	\N	Search & Act menu	Search & Act	Search & Act	1
68	\N	\N	\N	\N	Script Manager menu	Script Manager	Script Manager	1
69	\N	\N	\N	\N	Optimizer menu	Optimizer	Optimizer	1
70	\N	\N	\N	\N	Migration Manager menu	Migration Manager	Migration Manager	1
71	\N	\N	\N	\N	Preference menu	Preference	Preference	1
72	\N	\N	\N	\N	RBAC Settings menu	RBAC Settings	RBAC Settings	1
73	\N	\N	\N	\N	Permissions sub menu of RBAC Settings menu	Permissions	Permissions	1
74	\N	\N	\N	\N	Roles sub menu of RBAC Settings menu	Roles	Roles	1
75	\N	\N	\N	\N	Groups sub menu of RBAC Settings menu	Groups	Groups	1
76	\N	\N	\N	\N	Users sub menu of RBAC Settings menu	Users	Users	1
77	\N	\N	\N	\N	Resource menu	Resource	Resource	1
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, created_at, created_by, updated_at, updated_by, description, grp, name, version) FROM stdin;
6	\N	\N	\N	\N	Admin	f	Admin	1
7	\N	\N	\N	\N	Admin role group	t	Admin_Role_Group	1
82	\N	\N	\N	\N	Team menu access role	f	Team Menu	1
83	\N	\N	\N	\N	Team Access Role	t	Team Menu Role	1
\.


--
-- Data for Name: roles_permissions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles_permissions (role_id, permissions_id) FROM stdin;
6	1
6	8
6	38
6	44
6	9
6	10
6	11
6	12
6	13
6	14
6	15
6	16
6	17
6	18
6	19
6	20
6	21
6	31
6	39
6	54
6	22
6	23
6	24
6	25
6	26
6	27
6	28
6	29
6	30
6	32
6	33
6	34
6	41
6	58
6	35
6	36
6	37
6	40
6	42
6	52
6	71
6	43
6	45
6	46
6	47
6	48
6	49
6	50
6	51
6	53
6	55
6	56
6	57
6	59
6	60
6	61
6	62
6	63
6	64
6	65
6	66
6	67
6	68
6	69
6	70
6	72
6	73
6	74
6	75
6	76
6	77
82	1
\.


--
-- Data for Name: roles_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles_roles (role_id, roles_id) FROM stdin;
7	6
83	82
7	82
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, created_at, created_by, updated_at, updated_by, active, email, google_mfa_key, invite_code, invite_link, invite_sent_on, invite_status, is_mfa_enable, mfa_qr_image_file_path, password, temp_password, type, username, organization_id, owner_id) FROM stdin;
79	2021-07-21 14:03:36.106	admin	2021-07-21 14:03:36.106	admin	t	admin.demomonitoring@synectiks.com	\N	\N	\N	\N	\N	\N	\N	$shiro1$SHA-256$500000$w/Ew2Thofl34aWUd/z7Msw==$mfzVawKIHAZQwU45zPA1Bg559oOHtGxI9vPsLIMlO4s=	\N	\N	admin	78	\N
81	2021-07-22 13:03:15.435	admin	2021-07-22 13:07:22.748	manojbpr@gmail.com	t	manojbpr@gmail.com	FL6KZM45RFHSF4FNXP763IQZF5EIVXRS	Zv0omiiDxn	http://100.64.107.25:8094/inviteaccept.html?activation_code=Zv0omiiDxn	2021-07-22 13:03:15.435	ACCEPTED	YES	C:\\mycode\\securityservice\\qrimages\\Synectiks\\manojbpr@gmail.com.png	$shiro1$SHA-256$500000$w/Ew2Thofl34aWUd/z7Msw==$mfzVawKIHAZQwU45zPA1Bg559oOHtGxI9vPsLIMlO4s=	password	\N	manojbpr@gmail.com	78	79
99	2021-07-27 23:19:03.439	admin	2021-07-27 23:19:03.439	admin	t	manoj.sharma@synectiks.com	\N	eljWUNLRWW	http://100.64.107.25:8094/inviteaccept.html?activation_code=eljWUNLRWW	2021-07-27 23:19:03.439	PENDING	NO	\N	\N	p45VBh4XqyJGxkV	\N	manoj.sharma@synectiks.com	78	79
\.


--
-- Data for Name: users_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users_roles (user_id, roles_id) FROM stdin;
79	7
79	83
81	83
99	83
\.


--
-- Name: psql_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.psql_seq', 99, true);


--
-- Name: organization organization_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.organization
    ADD CONSTRAINT organization_pkey PRIMARY KEY (id);


--
-- Name: organizational_unit organizational_unit_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.organizational_unit
    ADD CONSTRAINT organizational_unit_pkey PRIMARY KEY (id);


--
-- Name: permission permission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: roles_roles roles_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles_roles
    ADD CONSTRAINT roles_roles_pkey PRIMARY KEY (role_id, roles_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users_roles fk2o0jvgh89lemvvo17cbqvdxaa; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT fk2o0jvgh89lemvvo17cbqvdxaa FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: users fk9q8fdenwsqjwrjfivd5ovv5k3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk9q8fdenwsqjwrjfivd5ovv5k3 FOREIGN KEY (organization_id) REFERENCES public.organization(id);


--
-- Name: roles_permissions fka1duuqu7fiw2v9r5va5esghd5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles_permissions
    ADD CONSTRAINT fka1duuqu7fiw2v9r5va5esghd5 FOREIGN KEY (permissions_id) REFERENCES public.permission(id);


--
-- Name: users_roles fka62j07k5mhgifpp955h37ponj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT fka62j07k5mhgifpp955h37ponj FOREIGN KEY (roles_id) REFERENCES public.roles(id);


--
-- Name: roles_roles fkh0er2qrwpns8hej8uk95j4f6h; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles_roles
    ADD CONSTRAINT fkh0er2qrwpns8hej8uk95j4f6h FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- Name: organizational_unit fkjp8up3ysmx52e26hat4ddfvwc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.organizational_unit
    ADD CONSTRAINT fkjp8up3ysmx52e26hat4ddfvwc FOREIGN KEY (organization_id) REFERENCES public.organization(id);


--
-- Name: users fkntyuh06i5y3y6ir598luxy3k9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fkntyuh06i5y3y6ir598luxy3k9 FOREIGN KEY (owner_id) REFERENCES public.users(id);


--
-- Name: roles_roles fkqao0vjvqum05dlyy56wc7r5mi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles_roles
    ADD CONSTRAINT fkqao0vjvqum05dlyy56wc7r5mi FOREIGN KEY (roles_id) REFERENCES public.roles(id);


--
-- Name: roles_permissions fkqi9odri6c1o81vjox54eedwyh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles_permissions
    ADD CONSTRAINT fkqi9odri6c1o81vjox54eedwyh FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- PostgreSQL database dump complete
--

