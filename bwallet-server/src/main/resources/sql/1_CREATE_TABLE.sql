DROP TABLE USER;
DROP TABLE USER_LOGIN_JNL;
DROP TABLE CIF;
DROP TABLE NOTICE_JNL;
DROP TABLE ACCOUNTS;
DROP TABLE RECHARGE;
DROP TABLE TRANSFER;
DROP TABLE WITHDRAW;
DROP TABLE SUMMARY;
DROP TABLE ADDRESSES;
DROP TABLE CONTACT;
DROP TABLE AUTH_APP_INFO;
DROP TABLE SYS_PARAMETER;
DROP TABLE PAYMENT;
DROP TABLE PREPAY;
DROP TABLE DIGITAL_ACCOUNT;
DROP TABLE DIGITAL_ACCOUNT_JNL;
DROP TABLE ORDERS;
DROP TABLE PUSH_JNL;
DROP TABLE JG_JNL;
DROP TABLE DISTRIBUTION;

CREATE TABLE USER
(
   USER_ID              CHAR(32) NOT NULL COMMENT '用户编号',
   USERNAME             VARCHAR(100) COMMENT '用户名',
   PASSWORD             VARCHAR(200) COMMENT '密码',
   PAYMENT_PASSWORD     VARCHAR(200) COMMENT '支付密码',
   NICK                 VARCHAR(100) COMMENT '昵称',
   MOBILE_PREFIX        VARCHAR(10) COMMENT '手机前缀',
   MOBILE               VARCHAR(11) COMMENT '手机',
   EMAIL                VARCHAR(200) COMMENT '邮箱',
   CIF_ID               CHAR(32) COMMENT '客户信息',
   USER_PICTURE         VARCHAR(200) COMMENT '用户图片',
   STATUS               VARCHAR(10) NOT NULL COMMENT '用户状态(初始化:CSH、YRZ:已认证、WRZ:未认证、SD:锁定、HMD:黑名单)',
   LOCK_EXPIRE_TIME		  TIMESTAMP NULL COMMENT '账户锁定失效时间',
   CREATE_CHANNEL       CHAR(2) COMMENT '创建渠道(00:系统注册,01:accountkit创建,02:facebook创建)',
   LOGIN_TOKEN          CHAR(32) COMMENT '登录TOKEN',
   LOGIN_TOKEN_EXPIRAT_TIME TIMESTAMP NULL COMMENT '登录TOKEN失效时间',
   LOGIN_DEVICE_CODE    VARCHAR(60) COMMENT '登录设备编号',
   LAST_LOGIN_TIME      TIMESTAMP NULL COMMENT '最后登录时间',
   LAST_LOGIN_IP        VARCHAR(40) COMMENT '最后登录IP',
   LAST_LOGIN_DEVICE    VARCHAR(40) COMMENT '最后登录设备类型',
   LAST_LOGIN_LOCALE    VARCHAR(40) COMMENT '最后登录时语言环境',
   FAIL_LOGIN_COUNTER   INT COMMENT '登录失败次数',
   PAYMENT_PASSWORD_FAIL_COUNTER    INT COMMENT '支付密码验证失败次数',
   CREATE_TIME          TIMESTAMP NULL COMMENT '创建时间',
   MODIFY_TIME          TIMESTAMP NULL COMMENT '修改时间',
   PRIMARY KEY (USER_ID),
   UNIQUE KEY UK_USERNAME(USERNAME)
);
ALTER TABLE USER COMMENT '用户表';

CREATE TABLE USER_LOGIN_JNL
(
   ID                   CHAR(32) NOT NULL COMMENT '编号',
   USERNAME             VARCHAR(100) COMMENT '用户名',
   LOGIN_IP             VARCHAR(40) COMMENT '登录IP',
   LOGIN_DEVICE_CODE    VARCHAR(60) COMMENT '登录设备编号',
   LOGIN_TYPE           CHAR(2) COMMENT '登录类型(00:邮箱验证码登录、01:TOKEN登录)',
   LOGIN_STATUS         CHAR(1) COMMENT '登录状态(S:成功、F:失败)',
   CREATE_TIME          TIMESTAMP NULL COMMENT '创建时间',
   MODIFY_TIME          TIMESTAMP NULL COMMENT '修改时间',
   PRIMARY KEY (ID)
);
ALTER TABLE USER_LOGIN_JNL COMMENT '用户登录日志表';

CREATE TABLE CIF
(
   ID                   CHAR(32) NOT NULL COMMENT 'ID',
   CIF_NAME             VARCHAR(100) COMMENT '姓名',
   SEX                  CHAR(1) COMMENT '性别(M:男、F:女)',
   BIRTHDAY             TIMESTAMP NULL COMMENT '出生日期',
   ID_TYPE              CHAR(2) COMMENT '证件类型(00:身份证)',
   ID_NUM               VARCHAR(200) COMMENT '证件号码',
   HALF_PHOTO           VARCHAR(200) COMMENT '半身照片',
   IDENTITY_PHOTOS_FORNT VARCHAR(200) COMMENT '身份正面照',
   IDENTITY_PHOTOS_REAR VARCHAR(200) COMMENT '身份正反面',
   IDENTITY_PHOTOS_HOLD VARCHAR(200) COMMENT '手持身份证',
   CREATE_TIME          TIMESTAMP NULL COMMENT '创建时间',
   MODIFY_TIME          TIMESTAMP NULL COMMENT '修改时间',
   PRIMARY KEY (ID)
);
ALTER TABLE CIF COMMENT '个人信息';

CREATE TABLE NOTICE_JNL (
   ID                       CHAR(32) NOT NULL COMMENT '编号',
   NOTICE_USER              VARCHAR(40) COMMENT '登录用户编号',
   NOTICE_TYPE              CHAR(2) NOT NULL COMMENT '通知类型：01-手机, 02-邮箱',
   NOTICE_EMAIL             VARCHAR(200) COMMENT '通知邮箱',
   NOTICE_MOBILE_PREFIX     VARCHAR(10) COMMENT '通知手机前缀',
   NOTICE_MOBILE            VARCHAR(20) COMMENT '通知手机',
   NOTICE_PURPOSE           VARCHAR(60) NOT NULL COMMENT '通知用途: 001-注册短信, 002-忘记登录密码, 003-忘记支付密码, 004-修改绑定邮箱, 005-修改绑定手机',
   NOTICE_TOKEN             VARCHAR(30) NOT NULL COMMENT '通知验证码',
   NOTICE_CONTENT           VARCHAR(500) COMMENT '通知内容',
   NOTICE_APPLY_TIME        TIMESTAMP NULL COMMENT '申请时间',
   NOTICE_DELIVER_TIME      TIMESTAMP NULL COMMENT '发送时间',
   NOTICE_EXPIRE_TIME       TIMESTAMP NULL COMMENT '失效时间',
   NOTICE_BUSSINESS_STATUS  CHAR(2) NOT NULL COMMENT '交易验证状态: 01-待验证, 02-成功, 03-失效',
   NOTICE_VERIFY_STATUS     CHAR(2) NOT NULL DEFAULT '01' COMMENT '通知验证状态: 01-待验证, 02-成功, 03-失败, 04-过期,  05-重置',
   NOTICE_VERIFY_COUNT      INT COMMENT '通知验证次数',
   NOTICE_VERIFY_TIME       TIMESTAMP NULL COMMENT '通知验证时间',
   CREATE_TIME              TIMESTAMP NULL COMMENT '创建时间',
   MODIFY_TIME              TIMESTAMP NULL COMMENT '修改时间',
   PRIMARY KEY (ID)
);
ALTER TABLE NOTICE_JNL COMMENT '通知消息流水表';
ALTER TABLE NOTICE_JNL ADD INDEX INDEX_UNION1 (NOTICE_MOBILE_PREFIX,NOTICE_MOBILE,NOTICE_TYPE,NOTICE_VERIFY_STATUS);
ALTER TABLE NOTICE_JNL ADD INDEX INDEX_UNION2 (NOTICE_MOBILE_PREFIX,NOTICE_MOBILE,NOTICE_TYPE,NOTICE_VERIFY_STATUS,NOTICE_PURPOSE);
ALTER TABLE NOTICE_JNL ADD INDEX INDEX_UNION3 (NOTICE_MOBILE_PREFIX,NOTICE_MOBILE,NOTICE_BUSSINESS_STATUS,NOTICE_PURPOSE);

CREATE TABLE ACCOUNTS(
   ACCOUNT_ID	           CHAR(32)       NOT NULL COMMENT '账户号',
   USER_ID  	           CHAR(32)       NOT NULL COMMENT '用户编号',
   CURRENCY	               VARCHAR(10)    NOT NULL COMMENT '币种',
   AMOUNT	               DECIMAL(56,0)  NOT NULL COMMENT '可用余额',
   LOCKED	               DECIMAL(56,0)  NOT NULL COMMENT '冻结金额',
   CREATE_TIME             TIMESTAMP          NULL COMMENT '创建时间',
   MODIFY_TIME             TIMESTAMP          NULL COMMENT '修改时间',
   PRIMARY KEY (ACCOUNT_ID)
);
ALTER TABLE ACCOUNTS COMMENT '账户表';

CREATE TABLE RECHARGE(                                                    
   ID	                   CHAR(32)       NOT NULL COMMENT  '交易流水ID',  
   USER_ID	               CHAR(32)       NOT NULL COMMENT  '用户编号',
   ACCOUNT_ID	           CHAR(32)       NOT NULL COMMENT  '账户编号',    
   CURRENCY	               VARCHAR(10)    NOT NULL COMMENT  '币种',
   AMOUNT	               DECIMAL(56,0)  NOT NULL COMMENT  '充值金额',    
   TX_ID                   VARCHAR(100)   NOT NULL COMMENT  '区块链交易Id',
   CHARGE_ADDRESS          VARCHAR(100)   NOT NULL COMMENT  '收款方地址',
   SEND_ADDRESS            VARCHAR(100)   NOT NULL COMMENT  '发款方地址',
   MINER_FEE               DECIMAL(56,0)  NOT NULL COMMENT  '矿工费用',
   BLOCK_HEIGHT            BIGINT             NULL COMMENT  '区块高度',
   STATUS	               VARCHAR(10)    NOT NULL COMMENT  '交易状态',    
   CONFIRM_BLOCK           BIGINT             NULL COMMENT  '确认块数',
   CONFIRM_TIME            TIMESTAMP          NULL COMMENT  '确认时间',
   CREATE_TIME             TIMESTAMP          NULL COMMENT  '创建时间',
   MODIFY_TIME             TIMESTAMP          NULL COMMENT  '修改时间',
   PRIMARY KEY (ID)                                                         
);                                                                          
ALTER TABLE RECHARGE COMMENT '充值明细表';

CREATE TABLE TRANSFER(
   ID	                   CHAR(32)       NOT NULL COMMENT  '交易流水ID',
   OUT_USER_ID	           CHAR(32)       NOT NULL COMMENT  '转出用户编号',
   OUT_ACCOUNT_ID          CHAR(32)       NOT NULL COMMENT  '转出账户编号',
   AMOUNT	               DECIMAL(56,0)  NOT NULL COMMENT  '转账金额',
   IN_USER_ID	           CHAR(32)       NOT NULL COMMENT  '转入用户编号',
   IN_ACCOUNT_ID           CHAR(32)       NOT NULL COMMENT  '转入账户编号',
   CURRENCY	               VARCHAR(10)    NOT NULL COMMENT  '币种',
   STATUS	               VARCHAR(10)    NOT NULL COMMENT  '状态',
   REMARK	               VARCHAR(200)       NULL COMMENT  '转账附言',
   CREATE_TIME             TIMESTAMP          NULL COMMENT  '创建时间',
   MODIFY_TIME             TIMESTAMP          NULL COMMENT  '修改时间',
   PRIMARY KEY (ID)
);
ALTER TABLE TRANSFER COMMENT '转账明细表';

CREATE TABLE WITHDRAW(
   ID	                   CHAR(32)        NOT NULL COMMENT  '交易流水ID',
   USER_ID	               CHAR(32)        NOT NULL COMMENT  '用户编号',
   ACCOUNT_ID	           CHAR(32)        NOT NULL COMMENT  '账户编号',
   CURRENCY	               VARCHAR(10)     NOT NULL COMMENT  '币种',
   AMOUNT	               DECIMAL(56,0)   NOT NULL COMMENT  '提币金额',
   FEE	                   DECIMAL(56,0)   NOT NULL COMMENT  '提币手续费',
   TX_ID                   VARCHAR(100)        NULL COMMENT  '区块链交易Id',
   BLOCK_FEE               DECIMAL(56,0)       NULL COMMENT  '区块手续费',
   FEE_ADDRESS	           VARCHAR(100)    NOT NULL COMMENT  '提币地址',
   BLOCK_HEIGHT            BIGINT              NULL COMMENT  '区块高度',
   STATUS	               VARCHAR(10)     NOT NULL COMMENT  '交易状态',
   CONFIRM_BLOCK           BIGINT              NULL COMMENT  '确认块数',
   CONFIRM_TIME            TIMESTAMP           NULL COMMENT  '确认时间',
   CREATE_TIME             TIMESTAMP           NULL COMMENT  '创建时间',
   MODIFY_TIME             TIMESTAMP           NULL COMMENT  '修改时间',
   PRIMARY KEY (ID)
);
ALTER TABLE WITHDRAW COMMENT '提币明细表';

CREATE TABLE SUMMARY(
   ID	                   CHAR(32)       NOT NULL COMMENT  '唯一ID',
   REFER_ID	               VARCHAR(32)    NOT NULL COMMENT  '交易流水ID',
   REFER_TYPE	           VARCHAR(10)    NOT NULL COMMENT  '交易类型',
   USER_ID	               CHAR(32)       NOT NULL COMMENT  '用户编号',
   ACCOUNT_ID	           CHAR(32)       NOT NULL COMMENT  '账户编号',
   CURRENCY	               VARCHAR(10)    NOT NULL COMMENT  '币种',
   AMOUNT	               DECIMAL(56,0)  NOT NULL COMMENT  '金额',
   IN_OUT_FLAG	           VARCHAR(32)    NOT NULL COMMENT  '进出账标识',
   STATUS	               VARCHAR(10)    NOT NULL COMMENT  '状态',
   TITLE	               VARCHAR(100)       NULL COMMENT  '标题',
   CREATE_TIME             TIMESTAMP          NULL COMMENT  '创建时间',
   MODIFY_TIME             TIMESTAMP          NULL COMMENT  '修改时间',
   PRIMARY KEY (ID)
);
ALTER TABLE SUMMARY COMMENT '交易汇总表';

CREATE TABLE ADDRESSES(
   ID	                   CHAR(32)      NOT NULL COMMENT '主键ID',
   USER_ID	               CHAR(32)      NOT NULL COMMENT '用户ID',
   ACCOUNT_ID	           CHAR(32)      NOT NULL COMMENT '账户ID',
   CURRENCY	               VARCHAR(10)   NOT NULL COMMENT '币种',
   ADDRESS	               VARCHAR(100)  NOT NULL COMMENT '充值/提币 地址',
   TYPE	                   VARCHAR(50)   NOT NULL COMMENT '交易类型（充值或提币）',
   ADDRESS_STATUS          VARCHAR(10)   NOT NULL COMMENT '地址状态（可判断地址是否有效）',
   REMARK	               VARCHAR(200)      NULL COMMENT '备注（提现地址别名，和提现地址一一对应，用户可自主设定）',
   CREATE_TIME	           TIMESTAMP         NULL COMMENT '创建时间',
   MODIFY_TIME	           TIMESTAMP         NULL COMMENT '更新时间',
   PRIMARY KEY (ID)
);
ALTER TABLE ADDRESSES COMMENT '地址总表';

CREATE TABLE CONTACT(
   ID     	      	       char(32)       NOT NULL COMMENT  '主键',
   USERID       	         char(32)       NOT NULL COMMENT  '联系人关联用户id',
   SURNAME                 VARCHAR(100)             COMMENT  '姓',
   GIVENNAME     	         VARCHAR(100)             COMMENT  '名',
   OLEID                   VARCHAR(12)    NOT NULL COMMENT  '联系人会员名',
   REMARKS                 varchar(200)             COMMENT  '备注',
   CREATE_TIME          	 timestamp         NULL  COMMENT  '创建时间',
   MODIFY_TIME             timestamp         NULL  COMMENT  '修改时间',
   PRIMARY KEY(ID)
);
ALTER TABLE CONTACT COMMENT '联系人表';

CREATE TABLE AUTH_APP_INFO(
   ID				               CHAR(32)       NOT NULL COMMENT  '主键',
   USER_ID			           CHAR(32)       NOT NULL COMMENT	 '用户id',
   APP_TYPE			           VARCHAR(10)              COMMENT  '应用类型',
   AUTH_USER		           VARCHAR(100)             COMMENT  '第三方授权用户id',
   CREATE_TIME		         TIMESTAMP NULL          COMMENT  '创建时间',
   MODIFY_TIME		         TIMESTAMP NULL          COMMENT  '修改时间',
   PRIMARY KEY(ID)
);
ALTER TABLE AUTH_APP_INFO COMMENT '授权应用信息表';

CREATE TABLE SYS_PARAMETER
(
	 ID					             CHAR(32)       NOT NULL COMMENT '编号',
	 PARAMETER_CLIENT	       CHAR(2)        NOT NULL COMMENT '参数应用类型：00-所有平台，01-SERVER，02-APP',
	 CLIENT_VERSION		       VARCHAR(20)    NOT NULL COMMENT '00-所有版本，或具体版本号',
 	 PARAMETER_NAME		       VARCHAR(200)    NOT NULL COMMENT '参数名称',
	 PARAMETER_VALUE	       VARCHAR(1000)  NOT NULL COMMENT '参数值',
	 PARAMETER_DESC	         VARCHAR(2000)   NOT NULL COMMENT '参数说明',
   CREATE_TIME			       TIMESTAMP          NULL COMMENT '创建时间',
   MODIFY_TIME			       TIMESTAMP          NULL COMMENT '修改时间',
	 PRIMARY KEY (ID)
);
ALTER TABLE SYS_PARAMETER COMMENT '系统参数表';

CREATE TABLE ORDERS(
   ID	                   CHAR(32)       NOT NULL COMMENT  '主键ID(用户订单号)',
   MERCHANT_ORDER_NO       CHAR(32)           NULL COMMENT  '商户订单号',
   USER_ID	               CHAR(32)       NOT NULL COMMENT  '用户编号',
   ORDER_ID                CHAR(32)           NULL COMMENT  '订单号',
   MERCHANT_ID	           CHAR(32)       NOT NULL COMMENT  '商户编号',
   MERCHANT_NAME           VARCHAR(50)    NOT NULL COMMENT  '商户名称',
   MERCHANT_SUBMIT_TIME    TIMESTAMP          NULL COMMENT  '商户订单提交时间',
   LEGAL_CURRENCY	       VARCHAR(10)        NULL COMMENT  '法币币种',
   LEGAL_AMOUNT	           DECIMAL(32,2)      NULL COMMENT  '法币金额',
   CODE_TYPE               VARCHAR(10)    NOT NULL COMMENT  '二维码类型',
   ORDER_TIMEOUT           TIMESTAMP          NULL COMMENT  '订单失效时间',
   ORDER_STATUS            VARCHAR(10)    NOT NULL COMMENT  '订单状态',
   REMARK                  VARCHAR(256)       NULL COMMENT  '备注',
   NOTICE_VERIFY_COUNT     INT                NULL COMMENT  '通知验证次数',
   NOTICE_VERIFY_STATUS    VARCHAR(10)        NULL COMMENT  '通知状态:  01-待发送  02-处理中 03-发送成功  04-发送失败',
   NOTICE_TIME             TIMESTAMP          NULL COMMENT  '通知时间',
   CREATE_TIME             TIMESTAMP          NULL COMMENT  '创建时间',
   MODIFY_TIME             TIMESTAMP          NULL COMMENT  '修改时间',
   PRIMARY KEY (ID)
);
ALTER TABLE ORDERS COMMENT '订单信息表';

CREATE TABLE PREPAY(
   ID	                   CHAR(32)       NOT NULL COMMENT  '试算ID',
   USER_ID	               CHAR(32)       NOT NULL COMMENT  '用户编号',
   ORDER_ID                CHAR(32)       NOT NULL COMMENT  '用户订单号',
   ACCOUNT_ID              CHAR(32)       NOT NULL COMMENT  '账户编号',
   CURRENCY	               VARCHAR(10)    NOT NULL COMMENT  '虚拟币种',
   AMOUNT	               DECIMAL(56,0)  NOT NULL COMMENT  '虚拟币金额',
   LEGAL_CURRENCY	       VARCHAR(10)    NOT NULL COMMENT  '法币币种',
   LEGAL_AMOUNT	           DECIMAL(32,2)  NOT NULL COMMENT  '法币金额',
   PAY_FEE                 DECIMAL(56,0)  NOT NULL COMMENT  '支付手续费',
   INVALID_TIME            TIMESTAMP          NULL COMMENT  '失效时间',
   STATUS	               VARCHAR(10)    NOT NULL COMMENT  '试算状态',
   CREATE_TIME             TIMESTAMP          NULL COMMENT  '创建时间',
   MODIFY_TIME             TIMESTAMP          NULL COMMENT  '修改时间',
   PRIMARY KEY (ID)
);
ALTER TABLE PREPAY COMMENT '支付试算表';

CREATE TABLE PAYMENT(
   ID	                   CHAR(32)       NOT NULL COMMENT  '交易流水ID',
   USER_ID	               CHAR(32)       NOT NULL COMMENT  '用户编号',
   ACCOUNT_ID              CHAR(32)       NOT NULL COMMENT  '账户编号',
   CURRENCY	               VARCHAR(10)    NOT NULL COMMENT  '虚拟币种',
   AMOUNT	               DECIMAL(56,0)  NOT NULL COMMENT  '虚拟币金额',
   LEGAL_CURRENCY	       VARCHAR(10)    NOT NULL COMMENT  '法币币种',
   LEGAL_AMOUNT	           DECIMAL(32,2)  NOT NULL COMMENT  '法币金额',
   MERCHANT_ID	           CHAR(32)       NOT NULL COMMENT  '商户编号',
   MERCHANT_NAME           VARCHAR(50)    NOT NULL COMMENT  '商户名称',
   ORDER_ID                CHAR(32)       NOT NULL COMMENT  '用户订单号',
   PREPAY_ID               CHAR(32)       NOT NULL COMMENT  '试算编号',
   ORDER_TIMEOUT           TIMESTAMP          NULL COMMENT  '订单失效时间',
   PAY_FEE                 DECIMAL(56,0)  NOT NULL COMMENT  '支付手续费',
   STATUS	               VARCHAR(10)    NOT NULL COMMENT  '支付状态',
   CREATE_TIME             TIMESTAMP          NULL COMMENT  '创建时间',
   MODIFY_TIME             TIMESTAMP          NULL COMMENT  '修改时间',
   PRIMARY KEY (ID)
);
ALTER TABLE PAYMENT COMMENT '支付明细表';

CREATE TABLE DIGITAL_ACCOUNT(
   ID	                   CHAR(32)       NOT NULL COMMENT  'ID',
   ACCOUNT_ID              CHAR(32)       NOT NULL COMMENT  '账户类型编号',  
   ACCOUNT_SUB_ID          CHAR(32)       NOT NULL COMMENT  '子账户号',
   CURRENCY	               VARCHAR(10)    NOT NULL COMMENT  '虚拟币种',
   AMOUNT	               DECIMAL(56,0)  NOT NULL COMMENT  '虚拟币金额',
   LOCKED                  DECIMAL(56,0)  NOT NULL COMMENT  '冻结金额',
   STATUS                  VARCHAR(10)    NOT NULL COMMENT  '账户状态:02冻结，01可用',
   CREATE_TIME             TIMESTAMP          NULL COMMENT  '创建时间',
   MODIFY_TIME             TIMESTAMP          NULL COMMENT  '修改时间',
   PRIMARY KEY (ID)
);
ALTER TABLE DIGITAL_ACCOUNT COMMENT '数字币账户表';


CREATE TABLE DIGITAL_ACCOUNT_JNL(
   ID	                   CHAR(32)       NOT NULL COMMENT  'ID',
   ACCOUNT_ID              CHAR(32)       NOT NULL COMMENT  '账户类型编号',  
   ACCOUNT_SUB_ID          CHAR(32)       NOT NULL COMMENT  '子账户号',
   USER_ID                 CHAR(32)       NOT NULL COMMENT  '用户编号',
   REFER_ID                CHAR(32)       NOT NULL COMMENT  '交易流水ID',
   REFER_TYPE              VARCHAR(10)    NOT NULL COMMENT  '交易类型',
   IN_OUT_FLAG             VARCHAR(10)    NOT NULL COMMENT  '进出账标志',
   CURRENCY	               VARCHAR(10)    NOT NULL COMMENT  '虚拟币种',
   AMOUNT	               DECIMAL(56,0)  NOT NULL COMMENT  '虚拟币金额',
   DECSRIBE                VARCHAR(225)       NULL COMMENT  '描述',
   CREATE_TIME             TIMESTAMP          NULL COMMENT  '创建时间',
   MODIFY_TIME             TIMESTAMP          NULL COMMENT  '修改时间',
   PRIMARY KEY (ID)
);
ALTER TABLE DIGITAL_ACCOUNT_JNL COMMENT '数字币账户流水表';

CREATE TABLE PUSH_JNL
(
	 ID					             CHAR(32)       NOT NULL COMMENT '主键',
	 PLATFORM                VARCHAR(100)   NOT NULL COMMENT '推送平台指定',
	 AUDIENCE                VARCHAR(100)   NOT NULL COMMENT '推送设备指定',
	 NOTIFICATION            VARCHAR(2000)      NULL COMMENT '通知内容体,与message二者必须有其一',
	 MESSAGE                 VARCHAR(2000)      NULL COMMENT '消息内容体,与notification二者必须有其一',
	 SMS_MESSAGE             VARCHAR(2000)      NULL COMMENT '短信渠道补充送达内容体',
	 OPTIONS                 VARCHAR(2000)      NULL COMMENT '推送参数',
	 CID                     VARCHAR(100)       NULL COMMENT '防止服务端的重复推送而定义的标识符',
	 ATTEMPTS	               INT            NOT NULL COMMENT '推送次数',
	 PUSH_STATUS             VARCHAR(10)    NOT NULL COMMENT '推送状态',
	 PUSH_TIME			         TIMESTAMP          NULL COMMENT '推送时间',
	 CREATE_TIME	           TIMESTAMP          NULL COMMENT '创建时间',
   MODIFY_TIME			       TIMESTAMP          NULL COMMENT '修改时间',
	 PRIMARY KEY (ID)
);
ALTER TABLE PUSH_JNL COMMENT '推送记录表';

CREATE TABLE JG_JNL
(
   USER_ID	               CHAR(32)        NOT NULL COMMENT '用户id',
   REGISTRATION_ID		     VARCHAR(20)               COMMENT '极光推送设备id',
   PRIMARY KEY (USER_ID)
);
ALTER TABLE JG_JNL COMMENT '用户设备绑定表';

CREATE TABLE DISTRIBUTION
(
   USER_ID                 CHAR(32)        NOT NULL COMMENT '分销人id',
   SUPERIOR_Id	           CHAR(32)                  COMMENT '上级分销人id',
   USER_SHARING_CODE	     VARCHAR(100)    NOT NULL COMMENT '用户分享码',
   SUPER_SHARING_CODE		   VARCHAR(100)              COMMENT '上级分销人分享码',
   PRIMARY KEY (USER_ID)
);
ALTER TABLE DISTRIBUTION COMMENT '分销表';
