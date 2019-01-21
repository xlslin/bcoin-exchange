CREATE DATABASE blockchain_bitcoin CHARACTER SET UTF8 COLLATE UTF8_GENERAL_CI;

CREATE USER 'blockchain_bitcoin'@'%' IDENTIFIED BY 'zmSi(3ksdyz';

GRANT ALL PRIVILEGES ON blockchain_bitcoin.* TO 'blockchain_bitcoin'@'%';