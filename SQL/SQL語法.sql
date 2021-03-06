INSERT INTO COIN (ID, CODE, SYMBOL, RATE, DESCRIPTION, RATE_FLOAT, CREATE_DATE, MODIFY_DATE) VALUES (1, 'TWD', '&twd', 10200.12, '新台幣', 12345.11, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO COIN (ID, CODE, SYMBOL, RATE, DESCRIPTION, RATE_FLOAT, CREATE_DATE, MODIFY_DATE) VALUES (2, 'CNY', '&cny', 30200.00, '人民幣', 11344.00, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

DELETE COIN WHERE ID = 1;

UPDATE COIN SET DESCRIPTION = '台幣', MODIFY_DATE = CURRENT_TIMESTAMP() WHERE ID = 1;

SELECT * FROM COIN;