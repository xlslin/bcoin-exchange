DROP TABLE MNEMONIC;
DROP TABLE EXTENDED_KEY;
DROP TABLE SECRET_KEY;
DROP TABLE BLOCK_CHAIN_SYNC;
DROP TABLE BIT_COIN;
DROP TABLE ETHER_CONTRACT;
DROP TABLE ACCOUNT;
DROP TABLE ACCOUNT_JNL;
DROP TABLE ADDRESS_LISTENER;
DROP TABLE TRANSACTION_NOTICE;
DROP TABLE DEPOSIT_ETHER;
DROP TABLE WITHDRAWAL_ETHER;
DROP TABLE TRANSACTION_ETHER;

CREATE TABLE MNEMONIC
(
   ID                   CHAR(32) NOT NULL COMMENT 'id',
   NAME                VARCHAR(200) COMMENT '别名',
   PASSWORD             VARCHAR(500) NOT NULL COMMENT '密码',
   CREATE_TIME          TIMESTAMP NULL COMMENT '创建时间',
   MODIFY_TIME          TIMESTAMP NULL COMMENT '修改时间',
   PRIMARY KEY (ID)
);
ALTER TABLE MNEMONIC COMMENT '助记词表';

CREATE TABLE EXTENDED_KEY
(
   ID                   CHAR(32) NOT NULL COMMENT 'id',
   MNEMONIC_ID          CHAR(32) NOT NULL COMMENT '助记词id',
   EXTENDED_KEY_PATH    VARCHAR(200) COMMENT '扩展密钥路径',
   PASSWORD             VARCHAR(500) NOT NULL COMMENT '密码',
   CREATE_TIME          TIMESTAMP NULL COMMENT '创建时间',
   MODIFY_TIME          TIMESTAMP NULL COMMENT '修改时间',
   PRIMARY KEY (ID)
);
ALTER TABLE EXTENDED_KEY COMMENT '扩展密钥表';

CREATE TABLE SECRET_KEY
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  EXTENDED_KEY_ID       CHAR(32) NOT NULL COMMENT '扩展密钥id',
  ADDRESS               VARCHAR(100) COMMENT '地址',
  PASSWORD              VARCHAR(500) NOT NULL COMMENT '密码',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE SECRET_KEY COMMENT '密钥表';

CREATE TABLE SYS_PRM
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  PRM_NAME              VARCHAR(100) NOT NULL COMMENT '参数名称',
  PRM_VALUE             VARCHAR(200) NOT NULL COMMENT '参数值',
  PRM_DESC              VARCHAR(200) COMMENT '参数描述',
  PRM_STATUS            CHAR(2) NOT NULL COMMENT '参数状态(01:有效、02:失效)',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE SYS_PRM COMMENT '系统参数表';

CREATE TABLE BIT_COIN
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  BLOCK_TYPE            VARCHAR(20) COMMENT '区块类型(ETHER、BTC)',
  NAME                  VARCHAR(20) COMMENT '币种名称',
  DECIMALS              INT COMMENT '小数位',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE BIT_COIN COMMENT '币种表';

CREATE TABLE ETHER_CONTRACT
(
  CONTRACT_ADDRESS      VARCHAR(100) COMMENT '合约地址',
  BIT_COIN_ID           CHAR(32) NOT NULL COMMENT '币种id',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (CONTRACT_ADDRESS)
);
ALTER TABLE ETHER_CONTRACT COMMENT 'ether合约表';

CREATE TABLE ACCOUNT
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  ADDRESS               VARCHAR(100) NOT NULL COMMENT '地址',
  COIN_TYPE             VARCHAR(20) NOT NULL COMMENT '币种',
  TOTAL_IN              DECIMAL(65,0) NOT NULL COMMENT '入账总额',
  TOTAL_OUT             DECIMAL(65,0) NOT NULL COMMENT '出账总额',
  BALANCE               DECIMAL(65,0) NOT NULL COMMENT '余额',
  FROZEN_AMOUNT         DECIMAL(65,0) NOT NULL COMMENT '冻结金额',
  STATUS                VARCHAR(10) NOT NULL COMMENT '账户状态(NORMAL:正常、LOCK:锁定)',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE ACCOUNT COMMENT '账户表';

CREATE TABLE ACCOUNT_JNL
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  ACCOUNT_FROM          VARCHAR(100) COMMENT 'from地址',
  ACCOUNT_TO            VARCHAR(100) COMMENT 'to地址',
  COIN_TYPE             VARCHAR(20) COMMENT '币种',
  BALANCE               DECIMAL(65,0) NOT NULL COMMENT '金额',
  TYPE                  CHAR(2) COMMENT '类型(00:转入、01:转出、10:冻结、11:解冻)',
  TX_ID                 VARCHAR(32) COMMENT '交易id',
  TRANS_TIME            TIMESTAMP NULL COMMENT '交易时间',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE ACCOUNT_JNL COMMENT '账户流水表';

CREATE TABLE ADDRESS_LISTENER
(
  ADDRESS               VARCHAR(100) COMMENT '地址',
  SYSTEM_CODE           VARCHAR(20) COMMENT '系统编号',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ADDRESS)
);
ALTER TABLE ADDRESS_LISTENER COMMENT '地址监听表';

CREATE TABLE TRANSACTION_NOTICE
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  SYSTEM_CODE           VARCHAR(20) COMMENT '系统编号',
  BLOCK_TYPE            VARCHAR(50) NOT NULL COMMENT '区块类型(ETHER、BTC)',
  NOTICE_ADDRESS        VARCHAR(2000) NOT NULL COMMENT '通知地址',
  NOTICE_TYPE           VARCHAR(50) NOT NULL COMMENT '通知类型(DEPOSIT:充值、WITHDRAWAL:取现)',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE TRANSACTION_NOTICE COMMENT '交易通知表';

CREATE TABLE DEPOSIT_ETHER
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  COIN_TYPE             VARCHAR(20) NOT NULL COMMENT '币种',
  TX_FROM               VARCHAR(100) NOT NULL COMMENT 'FORM地址',
  TX_TO                 VARCHAR(100) NOT NULL COMMENT 'TO地址',
  AMOUNT                DECIMAL(65,0) NOT NULL COMMENT '金额',
  TX_HASH               VARCHAR(100) COMMENT '交易hash',
  STATUS                VARCHAR(20) NOT NULL COMMENT '处理状态(CZWCL:充值未处理、CZCLZ:充值处理中、SUCCESS:充值成功、FAIL:充值失败)',
  TASK_STATUS           VARCHAR(20) NOT NULL COMMENT '处理状态(WCL:未处理、CLZ:处理中、SUCCESS:处理成功、FAIL:处理失败)',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE DEPOSIT_ETHER COMMENT '充值ETH表';

CREATE TABLE WITHDRAWAL_ETHER
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  WITHDRAWAL_ID         CHAR(32) NOT NULL COMMENT '取现唯一编号',
  COIN_TYPE             VARCHAR(20) NOT NULL COMMENT '币种',
  TX_FROM               VARCHAR(100) NOT NULL COMMENT 'FORM地址',
  TX_TO                 VARCHAR(100) NOT NULL COMMENT 'TO地址',
  AMOUNT                DECIMAL(65,0) NOT NULL COMMENT '金额',
  FEE                   DECIMAL(65,0) NOT NULL COMMENT '手续费',
  GAS_LIMIT             BIGINT COMMENT 'gas limit',
  GAS_USED              BIGINT COMMENT 'gas used',
  GAS_PRICE             BIGINT COMMENT 'gas price',
  TX_HASH               VARCHAR(100) COMMENT '交易hash',
  STATUS                VARCHAR(20) NOT NULL COMMENT '处理状态(CZWCL:提现未处理、CZCLZ:提现处理中、SUCCESS:提现成功、FAIL:提现失败)',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE WITHDRAWAL_ETHER COMMENT '提现ETH表';

CREATE TABLE TRANSACTION_ETHER
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  TX_HASH               VARCHAR(100) NOT NULL COMMENT '交易hash',
  BLOCK_NUMBER          BIGINT COMMENT '区块号',
  TX_FROM               VARCHAR(100) COMMENT 'FORM地址',
  TX_TO                 VARCHAR(100) COMMENT 'TO地址',
  CONTRACT_ADDRESS      VARCHAR(100) COMMENT '合约地址',
  COIN_TYPE             VARCHAR(20) COMMENT '币种',
  TX_INPUT              VARCHAR(2000) COMMENT 'input',
  TX_VALUE              DECIMAL(65,0) COMMENT '交易值',
  TX_INDEX              BIGINT COMMENT 'tx index',
  GAS_LIMIT             BIGINT COMMENT 'gas limit',
  GAS_USED              BIGINT COMMENT 'gas used',
  GAS_PRICE             BIGINT COMMENT 'gas price',
  ACTUAL_FEE            BIGINT COMMENT 'actual fee',
  NONCE                 BIGINT COMMENT 'nonce',
  TX_RECEIPT_STATUS     CHAR(1) NOT NULL COMMENT '交易状态(S:成功、F:失败、P:处理中)',
  TX_TIME               TIMESTAMP NULL COMMENT '交易时间',
  CONFIRM_BLOCK_NUMBER  INT COMMENT '确认区块数',
  TX_TYPE               CHAR(1) NOT NULL COMMENT '交易类型(0:转入、1:转出)',
  TX_STATUS             VARCHAR(20) NOT NULL COMMENT '交易状态(WCL:未处理、QKQRSCLZ:区块确认数处理中、YEWQR:余额未确认、YEQRYC:余额确认异常、YX:有效、WX:无效)',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE TRANSACTION_ETHER COMMENT 'ETH交易表';