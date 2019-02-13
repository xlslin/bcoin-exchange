DROP TABLE BATCH_JOB;
DROP TABLE BLOCK_CHAIN_SYNC;
DROP TABLE BLOCK_CHAIN;
DROP TABLE TRANSACTION;
DROP TABLE TRANSACTION_BUSINESS;
DROP TABLE TRANSACTION_BUSINESS_ACCOUNT;
DROP TABLE ACCOUNT;
DROP TABLE ACCOUNT_JNL;
DROP TABLE ACCOUNT_FROZEN_JNL;
DROP TABLE WITHDRAWAL;
DROP TABLE WITHDRAWAL_TRANSACTION;
DROP TABLE WITHDRAWAL_VIN;
DROP TABLE WITHDRAWAL_VOUT;
DROP TABLE ADDRESS_LISTENER;

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

CREATE TABLE BLOCK_CHAIN_SYNC
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  BLOCK_NUMBER          BIGINT NOT NULL COMMENT '区块数',
  TYPE                  CHAR(2) NOT NULL COMMENT '类型(00:区块同步)',
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
  STATUS                VARCHAR(20) NOT NULL COMMENT '状态(WCL:未处理、WYZ:未验证、QKYZYX:区块验证有效、QKYZWX:区块验证无效)',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE BLOCK_CHAIN COMMENT '区块表';

CREATE TABLE TRANSACTION
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  BLOCK_NUMBER          BIGINT COMMENT '区块号',
  BLOCK_HASH            VARCHAR(100) NOT NULL COMMENT '区块hash',
  TX_HASH               VARCHAR(100) NOT NULL COMMENT '交易hash',
  TX_FEE                BIGINT COMMENT '交易手续费',
  TX_TIME               TIMESTAMP NULL COMMENT '交易时间',
  CONFIRM_BLOCK_NUMBER  INT COMMENT '确认区块数',
  TX_STATUS             VARCHAR(20) NOT NULL COMMENT '交易状态(WYZ:未验证、QKYZYX:区块验证有效、QKYZWX:区块验证无效)',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE TRANSACTION COMMENT 'ETH交易表';

CREATE TABLE TRANSACTION_BUSINESS
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  BLOCK_NUMBER          BIGINT NOT NULL COMMENT '区块号',
  BLOCK_HASH            VARCHAR(100) NOT NULL COMMENT '区块hash',
  TX_HASH               VARCHAR(100) NOT NULL COMMENT '交易hash',
  TRANSACTION_ID        CHAR(32) NOT NULL COMMENT '交易id',
  VIO_INDEX              BIGINT COMMENT 'vin/vout index',
  COIN_TYPE             VARCHAR(20) NOT NULL COMMENT '币种',
  TX_FROM               VARCHAR(100) COMMENT 'FORM地址',
  TX_TO                 VARCHAR(100) COMMENT 'TO地址',
  AMOUNT                DECIMAL(65,0) NOT NULL COMMENT '金额',
  FEE                   DECIMAL(65,0) NOT NULL COMMENT '手续费',
  TYPE                  VARCHAR(100) NOT NULL COMMENT '交易类型(DEPOSIT:充值、WITHDRAWAL:提现)',
  STATUS                VARCHAR(20) NOT NULL COMMENT '处理状态(WCL:未处理、CSHTZZ:初始化通知中、CSHTZCG:初始化通知成功、JYWCTZZ:交易完成通知中、JYWCTZCG:交易完成通知成功)',
  SETTLE_STATUS         VARCHAR(20) NOT NULL COMMENT '处理状态(WQS:未清算、ZBQS:准备清算、QSZ:清算中、QSWC:清算完成)',
  TX_STATUS             VARCHAR(20) NOT NULL COMMENT '交易状态(WYZ:未验证、QKYZYX:区块验证有效、QKYZWX:区块验证无效)',
  TX_TIME               TIMESTAMP NULL COMMENT '交易时间',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE TRANSACTION_BUSINESS COMMENT '交易业务表';

CREATE TABLE TRANSACTION_BUSINESS_ACCOUNT
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  ADDRESS               VARCHAR(100) NOT NULL COMMENT '地址',
  COIN_TYPE             VARCHAR(20) NOT NULL COMMENT '币种',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID),
  UNIQUE(ADDRESS,COIN_TYPE)
);
ALTER TABLE TRANSACTION_BUSINESS COMMENT '交易业务账号表';

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
  PRIMARY KEY (ID),
  UNIQUE(ADDRESS,COIN_TYPE)
);
ALTER TABLE ACCOUNT COMMENT '账户表';

CREATE TABLE ACCOUNT_JNL
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  ACCOUNT_FROM          VARCHAR(100) COMMENT 'from地址',
  ACCOUNT_TO            VARCHAR(100) COMMENT 'to地址',
  COIN_TYPE             VARCHAR(20) COMMENT '币种',
  BALANCE               DECIMAL(65,0) NOT NULL COMMENT '金额',
  TYPE                  CHAR(2) COMMENT '类型(00:充值、01:提现、02:充值冲正、03:提现冲正、04:提现手续费、05:提现手续费冲正)',
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
  TYPE                  CHAR(2) COMMENT '类型(00:冻结、01:解冻)',
  TX_ID                 VARCHAR(32) COMMENT '交易id',
  TRANS_TIME            TIMESTAMP NULL COMMENT '交易时间',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE ACCOUNT_FROZEN_JNL COMMENT '账户冻结流水表';

CREATE TABLE WITHDRAWAL
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  WITHDRAWAL_ID         CHAR(32) NOT NULL COMMENT '取现唯一编号',
  TX_HASH               VARCHAR(100) COMMENT '交易hash',
  COIN_TYPE             VARCHAR(20) NOT NULL COMMENT '币种',
  TX_TO                 VARCHAR(100) NOT NULL COMMENT 'TO地址',
  AMOUNT                DECIMAL(65,0) NOT NULL COMMENT '金额',
  FEE                   DECIMAL(65,0) COMMENT '手续费',
  STATUS                VARCHAR(20) NOT NULL COMMENT '处理状态(TXWCL:提现未处理、TXCLZ:提现处理中、CSHTZZ:初始化通知中、CSHTZCG:初始化通知成功、TXCG:提现成功、TXSB:提现失败、TXTZZ:提现通知中、TXCGTZ:提现成功通知、TXSBTZ:提现失败通知)',
  TX_TIME               TIMESTAMP NULL COMMENT '交易时间',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID),
  UNIQUE(WITHDRAWAL_ID)
);
ALTER TABLE WITHDRAWAL COMMENT '提现表';

CREATE TABLE WITHDRAWAL_TRANSACTION
(
  TX_HASH               VARCHAR(100) NOT NULL COMMENT '交易hash',
  FEE                   DECIMAL(65,0) NOT NULL COMMENT '手续费',
  STATUS                VARCHAR(20) NOT NULL COMMENT '处理状态(TXCLZ:提现处理中、CSHTZZ:初始化通知中、CSHTZCG:初始化通知成功、TXCG:提现成功、TXSB:提现失败、TXTZZ:提现通知中、TXCGTZ:提现成功通知、TXSBTZ:提现失败通知)',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (TX_HASH)
);
ALTER TABLE WITHDRAWAL_TRANSACTION COMMENT '提现交易表';

CREATE TABLE WITHDRAWAL_VIN
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  TX_HASH               VARCHAR(100) NOT NULL COMMENT '交易hash',
  ADDRESS               VARCHAR(100) NOT NULL COMMENT '地址',
  TX_ID                 VARCHAR(100) NOT NULL COMMENT '交易id',
  VOUT                  INT COMMENT '交易输出位置',
  AMOUNT                DECIMAL(65,0) NOT NULL COMMENT '金额',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE WITHDRAWAL_VIN COMMENT '提现输入表';

CREATE TABLE WITHDRAWAL_VOUT
(
  ID                    CHAR(32) NOT NULL COMMENT 'id',
  WITHDRAWAL_ID         CHAR(32) NOT NULL COMMENT 'withdrawal id',
  TX_HASH               VARCHAR(100) NOT NULL COMMENT '交易hash',
  ADDRESS               VARCHAR(100) NOT NULL COMMENT '地址',
  VOUT                  INT COMMENT '交易输出位置',
  AMOUNT                DECIMAL(65,0) NOT NULL COMMENT '金额',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ID)
);
ALTER TABLE WITHDRAWAL_VOUT COMMENT '提现输出表';

CREATE TABLE ADDRESS_LISTENER
(
  ADDRESS               VARCHAR(100) COMMENT '地址',
  CREATE_TIME           TIMESTAMP NULL COMMENT '创建时间',
  MODIFY_TIME           TIMESTAMP NULL COMMENT '修改时间',
  PRIMARY KEY (ADDRESS)
);
ALTER TABLE ADDRESS_LISTENER COMMENT '地址监听表';