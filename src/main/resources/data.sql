INSERT INTO currency (id, name, code) VALUES ( 1, 'Euro', 'EUR' );
INSERT INTO currency (id, name, code) VALUES ( 2, 'United States Dollar', 'USD' );
INSERT INTO currency (id, name, code) VALUES ( 3, 'Renminbi', 'CNY' );

INSERT INTO account (id, name, currency_id, balance, treasury) VALUES ( 1, 'Central EUR', 1, 500, TRUE);
INSERT INTO account (id, name, currency_id, balance, treasury) VALUES ( 2, 'Central USD', 2, 500, TRUE);
INSERT INTO account (id, name, currency_id, balance, treasury) VALUES ( 3, 'Central CNY', 3, 500, TRUE);