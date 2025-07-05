-- public.ultraman definition

-- Drop table

-- DROP TABLE public.ultraman;

CREATE TABLE public.ultraman (
	id serial4 NOT NULL,
	fullname varchar NULL,
	birthday varchar NULL,
	"rank" varchar NULL,
	planet jsonb NULL,
	details jsonb NULL,
	created_at timestamp NULL,
	created_by varchar NULL
);
