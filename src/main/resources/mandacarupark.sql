CREATE TABLE IF NOT EXISTS entrada
(
  id UUID NOT NULL,
  placa VARCHAR(8) NOT NULL,
  datahora TIMESTAMP NOT NULL,
  CONSTRAINT pk_entrada PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS saida
(
  id UUID NOT NULL,
  placa VARCHAR(8) NOT NULL,
  datahora TIMESTAMP NOT NULL,
  CONSTRAINT pk_saida PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS pagamento
(
  id UUID NOT NULL,
  datahora TIMESTAMP NOT NULL,
  valor DECIMAL(20, 2),
  CONSTRAINT pk_pagamento PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS mensalista
(
  id UUID NOT NULL,
  inicio TIMESTAMP NOT NULL,
  termino TIMESTAMP NOT NULL,
  pago BOOLEAN NOT NULL,
  valor DECIMAL(20, 2),
  CONSTRAINT pk_mensalista PRIMARY KEY (id)
);

MERGE
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
),
(
  'f72e14f5-4e6c-480e-b402-abf1c11b3a0e',
  'XYZ7890',
  '2022-07-21 12:08:53'
),
(
  '3b6e4c3c-ef36-4cfd-b101-9bd0b3507297',
  'NHK6D98',
  '2023-06-21 13:53:18'
),
(
  '1aa6d3bc-bee0-4fce-850f-8775b5ddd446',
  'HJW0D44',
  '2023-07-26 19:54:12'
),
(
  'c30165af-bb93-4f3e-ad9f-b592cbcc412b',
  'UJN4N14',
  '2023-02-20 18:31:56'
),
(
  '12330f07-88c1-4639-8d04-484183a3ee14',
  'XAZ3X53',
  '2023-03-14 23:04:38'
),
(
  'd1eb2d3e-9c4b-479c-a0e3-fddf4d76b1dc',
  'BMZ3C22',
  '2023-05-29 16:25:35'
),
(
  '32140fc8-1088-440a-b044-cdeb7b12946f',
  'ARY4F98',
  '2023-05-27 15:46:06'
),
(
  'ee9c5a3d-d2c6-4a2c-adb8-e8f3607cf99e',
  'KTK6D48',
  '2023-05-19 02:02:16'
),
(
  'cda6ff9b-9552-4bab-945c-8ef50ca67b86',
  'CUX3145',
  '2023-03-08 19:26:04'
),
(
  '4e27dd54-d323-4c14-9278-384af71fe322',
  'WTU6H93',
  '2023-06-26 01:40:54'
),
(
  '6eea45ae-7e89-494b-b55c-21931be606bb',
  'OUK2W72',
  '2023-04-24 16:56:21'
),
(
  '7461cf67-8110-4691-9562-6280b79cb20b',
  'IBW5C72',
  '2023-05-11 19:58:43'
),
(
  '57d49b34-75d3-4581-a484-520c7e8ba0d6',
  'MKE5364',
  '2023-02-05 21:06:05'
),
(
  '1a31632b-4759-4ef3-af8a-3d74220b8096',
  'HWM3L26',
  '2023-02-08 11:06:50'
),
(
  'ba663fa0-e1b6-4a24-979b-e0e96e5df92c',
  'CEP5L29',
  '2023-02-18 19:23:45'
),
(
  'c198dce2-d007-4beb-b4bc-f2714a95b0e3',
  'IDQ7C83',
  '2023-05-26 18:41:18'
),
(
  'f699336e-00da-4991-afb3-bd24d40e0261',
  'FEE5T68',
  '2023-01-21 02:01:22'
),
(
  '041c112a-5f9a-45f0-931c-52f5cfce25ac',
  'SHD7T25',
  '2023-06-05 14:48:22'
),
(
  'dc4db427-c3dd-4cc7-95a7-486052ebe5d2',
  'SZX3M03',
  '2023-06-04 05:49:19'
),
(
  '4c7d612a-2572-47b7-b6e0-31153ba6d0ac',
  'MSS3B49',
  '2023-05-25 07:23:59'
),
(
  '5259a26b-c6fb-48ea-a30e-4e6ace5d3bf6',
  'QNZ1G79',
  '2023-04-01 12:07:37'
),
(
  '3017a9a5-76ad-4045-8179-946c0415e2c5',
  'YRK1152',
  '2023-04-26 04:30:53'
),
(
  '62191862-be6a-4840-abc1-2710245e6896',
  'KQX0L80',
  '2023-06-09 02:15:05'
),
(
  '4248202b-88a6-47b4-9513-0465e5ef2c7e',
  'WRE0Z19',
  '2023-05-13 00:11:53'
),
(
  '4c47e7a6-45bc-4dcb-b436-ff0a5f9a4595',
  'HKD6D19',
  '2023-03-25 14:38:44'
),
(
  '9749c099-19ad-43ae-8443-0225fac54e0c',
  'VML6F91',
  '2023-05-15 07:35:53'
),
(
  'f0f7b7f6-bd39-49f3-9fd2-75d43f351f52',
  'SLB6Q58',
  '2023-07-12 09:07:48'
),
(
  '2c92688a-7189-4aa0-9dbf-2dfe5e85e17e',
  'WHH7859',
  '2023-03-16 16:37:04'
),
(
  '17a389c4-8352-4be0-88ca-291e820dd9f2',
  'OEF6F73',
  '2023-01-07 09:20:04'
),
(
  '8c448ad0-10f1-49ff-a3ff-f6ac74a33ea0',
  'CTS4902',
  '2023-02-23 15:54:13'
),
(
  '1efded0c-e72c-4112-91de-8041b70207e4',
  'UQH1P17',
  '2023-05-30 02:03:50'
),
(
  '8caaf82e-361e-488b-8cf0-ee10df01e79f',
  'FFN9R98',
  '2023-07-26 11:36:25'
),
(
  '1eb7b0b4-18c2-4ec0-a807-adba23266492',
  'CZX4R98',
  '2023-04-09 13:27:59'
),
(
  'e1cb6159-59b6-475a-b52a-2064a1743207',
  'SOD5K47',
  '2023-02-12 10:12:33'
),
(
  '7b6acc7d-d2fe-4396-8734-57a6b1093115',
  'HTB9170',
  '2023-06-14 17:05:09'
),
(
  '50b7d983-0e31-4097-bb8f-ac45c103b5f1',
  'WFA8R73',
  '2023-01-01 01:33:53'
),
(
  'c74b4ae5-96b2-4161-aba7-e27bd027c60f',
  'XIS0663',
  '2023-01-01 12:34:03'
),
(
  'c1d07307-7aaa-4e54-b821-77fecd63dc0a',
  'RVQ7837',
  '2023-02-03 22:16:20'
),
(
  '73d40ef5-3c9c-4a8e-bde8-ab77ccbb50ee',
  'EGP0M83',
  '2023-04-25 09:42:36'
),
(
  '7945e936-1dcf-42e8-b03e-63a015b3faa3',
  'DVS9S08',
  '2023-05-27 04:52:59'
),
(
  'a3ab6639-d813-455e-bb12-222e8f517234',
  'LCZ4R12',
  '2023-04-26 06:34:40'
),
(
  '5bdb7058-094b-4971-bf25-42afb2efdbe8',
  'QUX5L38',
  '2023-03-02 20:14:29'
),
(
  'b51f73f0-ed65-47bc-8de3-7d5bffa0fdec',
  'MAF8982',
  '2023-03-21 04:27:16'
),
(
  '1d6daedd-8c44-4d96-baba-3fdfc2bd68ea',
  'XQO0B15',
  '2023-07-21 07:10:18'
),
(
  'a80fe723-db43-4cbf-9068-26fddfe4672f',
  'BZB1120',
  '2023-04-20 21:01:45'
),
(
  '8bf7b74f-2369-4423-a3bd-84822f3c62b6',
  'TKU1L99',
  '2023-04-04 05:08:42'
),
(
  'abf9b21a-ec14-4400-945e-d158f548afe7',
  'CIB2801',
  '2023-01-02 08:45:52'
),
(
  '996905de-9ba5-439a-9b0d-05681ead8c73',
  'XBC4V83',
  '2023-04-14 14:28:54'
),
(
  'b0c581cb-3e9c-4abe-9089-6c7974432a0a',
  'EWS9H77',
  '2023-01-03 03:12:40'
),
(
  'ac962a8e-00d6-45bf-93c4-40218d08c3a1',
  'KOZ6V81',
  '2023-06-14 17:56:20'
),
(
  '7e12ab28-d2a0-4148-80e9-1c154b8c165b',
  'GKF8N61',
  '2023-04-05 21:13:03'
);

MERGE
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

MERGE
  INTO mensalista (
  id,
  inicio,
  termino,
  pago,
  valor
)
VALUES (
  'e4c1f93a-a00d-4ae2-b3f9-2bdc2cbe23e7',
  '2023-01-01 10:00:00',
  '2023-01-31 22:00:00',
  TRUE,
  '50.00'
);

CREATE VIEW IF NOT EXISTS locacao
AS
SELECT entrada.id  AS id,
  entrada.placa    AS placa,
  entrada.datahora AS entrada,
  saida.datahora   AS saida,
  pagamento.valor  AS valor
FROM entrada
INNER JOIN saida ON
  entrada.id = saida.id
INNER JOIN pagamento ON
  pagamento.id = entrada.id;
