/**
  V1.1.0 - Add initial default rates we intend to use
 */

INSERT INTO mortgage_rate (id, maturity_period, interest_rate, last_update, version)
VALUES (1, 15, 3.50, '2024-11-01 10:30:00', 0),
       (2, 30, 4.25, '2024-11-10 15:45:00', 1),
       (3, 10, 2.85, '2024-10-15 08:20:00', 0),
       (4, 20, 3.90, '2024-11-20 12:00:00', 2),
       (5, 25, 4.10, '2024-11-25 17:00:00', 1);