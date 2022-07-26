CREATE TABLE entrada
(
  id UUID NOT NULL,
  placa VARCHAR(8) NOT NULL,
  datahora TIMESTAMP NOT NULL,
  CONSTRAINT pk_entrada PRIMARY KEY (id)
);

CREATE TABLE saida
(
  id UUID NOT NULL,
  placa VARCHAR(8) NOT NULL,
  datahora TIMESTAMP NOT NULL,
  CONSTRAINT pk_saida PRIMARY KEY (id)
);

CREATE TABLE pagamento
(
  id UUID NOT NULL,
  datahora TIMESTAMP NOT NULL,
  valor DECIMAL(20, 2),
  CONSTRAINT pk_pagamento PRIMARY KEY (id)
);

INSERT
  INTO entrada (
  id,
  placa,
  datahora
)
VALUES (
  '8c878e6f-ee13-4a37-a208-7510c2638944',
  'ABC1234',
  '2022-07-21 12:01:15'
),
  (
    '07d8e078-5b47-4fb5-9fb4-dd2c80dd8036',
    'DEF5678',
    '2022-07-21 12:05:38'
  ),
  (
    '4c32b3dd-8636-43c0-9786-4804ca2b73f5',
    'GHI9012',
    '2022-07-21 12:06:51'
  );

INSERT
  INTO saida (
  id,
  placa,
  datahora
)
VALUES (
  '4c32b3dd-8636-43c0-9786-4804ca2b73f5',
  'GHI9012',
  '2022-07-21 13:11:23'
),
  (
    '07d8e078-5b47-4fb5-9fb4-dd2c80dd8036',
    'DEF5678',
    '2022-07-21 14:06:31'
  ),
  (
    '8c878e6f-ee13-4a37-a208-7510c2638944',
    'ABC1234',
    '2022-07-21 17:11:12'
  );

INSERT
  INTO pagamento (
  id,
  datahora,
  valor
)
VALUES (
  '4c32b3dd-8636-43c0-9786-4804ca2b73f5',
  '2022-07-21 13:08:12',
  '5.00'
),
  (
    '07d8e078-5b47-4fb5-9fb4-dd2c80dd8036',
    '2022-07-21 14:01:22',
    '5.00'
  ),
  (
    '8c878e6f-ee13-4a37-a208-7510c2638944',
    '2022-07-21 17:05:56',
    '5.00'
  );

CREATE VIEW locacao
AS
SELECT entrada.id  AS id,
  entrada.placa    AS placa,
  entrada.datahora AS entrada,
  saida.datahora   AS saida,
  pagamento.valor  AS valor
FROM entrada
       LEFT JOIN saida
                 ON
                   entrada.id = saida.id
       LEFT JOIN pagamento
                 ON
                   pagamento.id = entrada.id;