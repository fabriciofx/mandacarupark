CREATE TABLE entrada (
  id UUID NOT NULL,
  placa VARCHAR(8) NOT NULL,
  datahora TIMESTAMP NOT NULL,
  CONSTRAINT pk_entrada PRIMARY KEY(id)
);

CREATE TABLE saida (
  id UUID NOT NULL,
  placa VARCHAR(8) NOT NULL,
  datahora TIMESTAMP NOT NULL,
  CONSTRAINT pk_saida PRIMARY KEY(id)
);

CREATE TABLE locacao (
  entrada_id VARCHAR(32) NOT NULL,
  saida_id VARCHAR(32) NOT NULL,
  valor VARCHAR(10) NOT NULL,
  CONSTRAINT fk_locacao_entrada FOREIGN KEY(entrada_id) REFERENCES entrada(id),
  CONSTRAINT fk_locacao_saida FOREIGN KEY(saida_id) REFERENCES saida(id),
  CONSTRAINT pk_locacao PRIMARY KEY(entrada_id, saida_id)
);
