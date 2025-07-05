-- public.cat definition

-- Drop table

-- DROP TABLE public.cat;

CREATE TABLE public.cat (
	id serial4 NOT NULL,
	fullname varchar NULL,
	birthday varchar NULL,
	color varchar NULL,
	"attributes" jsonb NULL,
	created_at timestamp NULL,
	created_by varchar NULL
);
