DROP TABLE BATCH_JOB;
DROP TABLE CONTRACT;
DROP TABLE BLOCK_CHAIN_SYNC;
DROP TABLE BLOCK_CHAIN;
DROP TABLE TRANSACTION;
DROP TABLE TRANSACTION_BUSINESS;
DROP TABLE ACCOUNT;
DROP TABLE ACCOUNT_JNL;
DROP TABLE ACCOUNT_FROZEN_JNL;



CREATE TABLE BATCH_JOB (
   ID                       VARCHAR(40) NOT NULL COMMENT '主键ID',
   PARENT_JOB_ID            VARCHAR(40) COMMENT '父级JOB ID',
   LOOKUP_PATH              VARCHAR(200) NOT NULL COMMENT '交易名',
   PLAN_EXECUTE_TIME      	TIMESTAMP NULL COMMENT '计划执行时间',
   ACTUAL_EXECUTE_TIME      TIMESTAMP NULL COMMENT '实际执行时间',
   EXECUTE_COUNT            INT NOT NULL COMMENT '执行次数',
   DATA_ID                  VARCHAR(100) COMMENT '数据ID',
   STATUS      			        CHAR(1) NOT NULL COMMENT '状态(0:待处理、1:队列中、2:处理中、3:处理完成、4:处理失败)',
   ERROR_MESSAGE_CODE       VARCHAR(200) COMMENT '错误码',
   ERROR_LOCALIZED_MESSAGE  VARCHAR(500) COMMENT '本地错误消息',
   ERROR_CAUSE              VARCHAR(6000) COMMENT '错误原因',
   CREATE_TIME              TIMESTAMP NULL COMMENT '创建时间',
   MODIFY_TIME              TIMESTAMP NULL COMMENT '修改时间',
   PRIMARY KEY (ID)
);

CREATE TABLE CONTRACT
(
  CONTRACT_ADDRESS      VARCHAR(100) NOT NULL COMMENT '合约地址',
  NAME                  VARCHAR(100) COMMENT '名称',
  SYMBOL                VARCHAR(100) COMMENT '符号',
  DECIMALS              INT COMMENT '小数位',
  TOTALSUPPLY           DECIMAL(65,0) COMMENT '总发行量',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (CONTRACT_ADDRESS)
);
ALTER TABLE CONTRACT COMMENT 'Ether合约表';

CREATE TABLE BLOCK_CHAIN_SYNC
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  BLOCK_NUMBER          BIGINT NOT NULL COMMENT '区块数',
  TYPE                  CHAR(2) NOT NULL COMMENT '类型(00:区块同步、01:余额确认)',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);

CREATE TABLE BLOCK_CHAIN
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  BLOCK_NUMBER          BIGINT NOT NULL COMMENT '区块号',
  VERIFY_BLOCK_NUMBER   BIGINT NOT NULL COMMENT '验证区块号',
  HASH                  VARCHAR(100) NOT NULL COMMENT '区块hash',
  BLOCK_CREATE_TIME     TIMESTAMP NULL COMMENT '块创建时间',
  STATUS                VARCHAR(20) NOT NULL COMMENT '状态(WCL:未处理、QKTBZ:区块同步中、WYZ:未验证、QKYZYX:区块验证有效、QKYZWX:区块验证无效)',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE BLOCK_CHAIN COMMENT '区块表';

CREATE TABLE TRANSACTION
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  BLOCK_HASH            VARCHAR(100) NOT NULL COMMENT '区块hash',
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
  TX_RECEIPT_STATUS     CHAR(1) NOT NULL COMMENT '合约交易状态(S:成功、F:失败、P:处理中)',
  TX_TIME               TIMESTAMP NULL COMMENT '交易时间',
  CONFIRM_BLOCK_NUMBER  INT COMMENT '确认区块数',
  TX_STATUS             VARCHAR(20) NOT NULL COMMENT '交易状态(WCL:未处理、QKQRYX:区块确认有效、QKQRWX:区块确认无效)',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE TRANSACTION COMMENT 'ETH交易表';

CREATE TABLE TRANSACTION_BUSINESS
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  BLOCK_NUMBER          BIGINT NOT NULL COMMENT '区块数',
  TRANSACTION_ID        CHAR(32) NOT NULL COMMENT 'ETH交易id',
  TX_HASH               VARCHAR(100) COMMENT '交易hash',
  COIN_TYPE             VARCHAR(20) NOT NULL COMMENT '币种',
  TX_FROM               VARCHAR(100) NOT NULL COMMENT 'FORM地址',
  TX_TO                 VARCHAR(100) COMMENT 'TO地址',
  AMOUNT                DECIMAL(65,0) NOT NULL COMMENT '金额',
  FEE                   DECIMAL(65,0) NOT NULL COMMENT '手续费',
  TYPE                  VARCHAR(100) NOT NULL COMMENT '交易类型(DEPOSIT:充值、WITHDRAWAL:提现)',
  STATUS                VARCHAR(20) NOT NULL COMMENT '处理状态(WCL:未处理、CSHTZZ:初始化通知中、CSHTZCG:初始化通知成功、YX:有效、WX:无效)',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE TRANSACTION_BUSINESS COMMENT '交易业务表';

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
  TYPE                  CHAR(2) COMMENT '类型(00:转入、01:转出)',
  TX_ID                 VARCHAR(32) COMMENT '交易id',
  TRANS_TIME            TIMESTAMP NULL COMMENT '交易时间',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE ACCOUNT_JNL COMMENT '账户流水表';

CREATE TABLE ACCOUNT_FROZEN_JNL
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  ACCOUNT_FROM          VARCHAR(100) COMMENT 'from地址',
  ACCOUNT_TO            VARCHAR(100) COMMENT 'to地址',
  COIN_TYPE             VARCHAR(20) COMMENT '币种',
  BALANCE               DECIMAL(65,0) NOT NULL COMMENT '金额',
  TYPE                  CHAR(2) COMMENT '类型(10:冻结、11:解冻)',
  TX_ID                 VARCHAR(32) COMMENT '交易id',
  TRANS_TIME            TIMESTAMP NULL COMMENT '交易时间',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE ACCOUNT_FROZEN_JNL COMMENT '账户冻结流水表';