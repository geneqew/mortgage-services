/**
  V1.0.0 -  Initial version containing new tables to represent entity
 */

CREATE TABLE mortgage_rate
(
    id              BIGINT NOT NULL PRIMARY KEY,
    version         INT DEFAULT 0,
    maturity_period INT,
    interest_rate   DECIMAL(10, 2),
    last_update     TIMESTAMP
);