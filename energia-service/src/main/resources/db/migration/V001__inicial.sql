CREATE SCHEMA auditoria AUTHORIZATION postgres;
CREATE TABLE auditoria.revinfo (
    rev integer NOT NULL,
    revtstmp bigint,
    CONSTRAINT revinfo_pkey PRIMARY KEY (rev)
);

CREATE TABLE auditoria.totais_auditoria (
    id bigint NOT NULL,
    revisao integer NOT NULL,
    tipo smallint,
    energia_gerada numeric(19,2),
    energia_injetada integer,
    energia_consumida_concessionaria integer,
    energia_consumida_total numeric(19,2),
    saldo_mes integer,
    tusd numeric(19,2),
    te numeric(19,2),
    bandeira numeric(19,2),
    iluminacao_publica numeric(19,2),
    desconto numeric(19,2),
    valor_total numeric(19,2),
    atualizado timestamp without time zone,
    CONSTRAINT auditoria_totais_pkey PRIMARY KEY (id, revisao),
    CONSTRAINT fksdtpf46bkhwav6k4h9atoouky FOREIGN KEY (revisao)
        REFERENCES auditoria.revinfo (rev) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE auditoria.acompanhamentos_auditoria (
    id bigint NOT NULL,
    revisao integer NOT NULL,
    tipo smallint,
    inicio date,
    fim date,
    dias integer,
    energia_gerada numeric(19,2),
    energia_injetada integer,
    energia_consumida_concessionaria integer,
    energia_consumida_total numeric(19,2),
    saldo_mes integer,
    tusd numeric(19,2),
    te numeric(19,2),
    bandeira numeric(19,2),
    iluminacao_publica numeric(19,2),
    desconto numeric(19,2),
    valor_total numeric(19,2),
    criado timestamp without time zone,
    atualizado timestamp without time zone,
    CONSTRAINT auditoria_acompanhamentos_pkey PRIMARY KEY (id, revisao),
    CONSTRAINT fkaj29q7ypyjie23e62t09ae4fx FOREIGN KEY (revisao)
        REFERENCES auditoria.revinfo (rev) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.acompanhamentos (
    id bigserial NOT NULL,
    inicio date NOT NULL,
    fim date NOT NULL,
    dias integer NOT NULL,
    energia_gerada numeric(19,2),
    energia_injetada integer NOT NULL,
    energia_consumida_concessionaria integer NOT NULL,
    energia_consumida_total numeric(19,2) NOT NULL,
    saldo_mes integer NOT NULL,
    tusd numeric(19,2) NOT NULL,
    te numeric(19,2) NOT NULL,
    bandeira numeric(19,2) NOT NULL,
    iluminacao_publica numeric(19,2) NOT NULL,
    desconto numeric(19,2) NOT NULL,
    valor_total numeric(19,2) NOT NULL,
    criado timestamp without time zone,
    atualizado timestamp without time zone NOT NULL,
    CONSTRAINT acompanhamentos_pkey PRIMARY KEY (id)
);

CREATE TABLE public.totais (
    id bigserial NOT NULL,
    energia_gerada numeric(19,2) NOT NULL,
    energia_injetada integer NOT NULL,
    energia_consumida_concessionaria integer NOT NULL,
    energia_consumida_total numeric(19,2) NOT NULL,
    saldo_mes integer NOT NULL,
    tusd numeric(19,2) NOT NULL,
    te numeric(19,2) NOT NULL,
    bandeira numeric(19,2) NOT NULL,
    iluminacao_publica numeric(19,2) NOT NULL,
    desconto numeric(19,2) NOT NULL,
    valor_total numeric(19,2) NOT NULL,
    atualizado timestamp without time zone NOT NULL,
    CONSTRAINT totais_pkey PRIMARY KEY (id)
);

INSERT INTO public.totais (energia_gerada, energia_injetada, energia_consumida_concessionaria, energia_consumida_total, saldo_mes, tusd, te, bandeira, iluminacao_publica, desconto, valor_total, atualizado) VALUES (0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, CURRENT_TIMESTAMP);

CREATE SEQUENCE public.hibernate_sequence
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;