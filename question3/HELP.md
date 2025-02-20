# Getting Starter
ใน Project ขอ รวม Service ครับ เนื่องจากทำไม่ทันครับ
ที่ออกแบบไว่จะ Design ตาม Service ดังนี้
com.bank.request
com.bank.Workflow
com.bank.status

#install MS SQL Server 2022
## Pull
docker pull mcr.microsoft.com/mssql/server
## run
docker run -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=P@ssw0rd!23$" -p 1433:1433 -d mcr.microsoft.com/mssql/server:2022-latest

## Use Database
Use Database :  crmdb
## Create Table
CREATE TABLE crmdb.dbo.service_requests (
	id bigint IDENTITY(1,1) NOT NULL,
	assigned_department varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	created_at datetime2(6) NULL,
	customer_id varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	metadata nvarchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	priority varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[type] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	updated_at datetime2(6) NULL,
	CONSTRAINT PK__service___3213E83F4BB666CD PRIMARY KEY (id)
);
ALTER TABLE crmdb.dbo.service_requests WITH NOCHECK ADD CONSTRAINT CK__service_r__prior__160F4887 CHECK (([priority]='URGENT' OR [priority]='HIGH' OR [priority]='MEDIUM' OR [priority]='LOW'));
ALTER TABLE crmdb.dbo.service_requests WITH NOCHECK ADD CONSTRAINT CK__service_r__statu__17036CC0 CHECK (([status]='REJECTED' OR [status]='CANCELED' OR [status]='COMPLETED' OR [status]='PENDING' OR [status]='IN_PROGRESS' OR [status]='NEW'));
ALTER TABLE crmdb.dbo.service_requests WITH NOCHECK ADD CONSTRAINT CK__service_re__type__17F790F9 CHECK (([type]='GENERAL_INQUIRY' OR [type]='FRAUD_ALERT' OR [type]='CREDIT_CARD_ISSUE' OR [type]='ACCOUNT_ISSUE' OR [type]='PAYMENT_ISSUE'));


CREATE TABLE crmdb.dbo.status_history (
	id bigint IDENTITY(1,1) NOT NULL,
	comment varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	new_status varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	old_status varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	request_id bigint NOT NULL,
	updated_at datetime2 NULL,
	updated_by varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__status_h__3213E83F467C86BF PRIMARY KEY (id)
);
ALTER TABLE crmdb.dbo.status_history WITH NOCHECK ADD CONSTRAINT CK__status_hi__new_s__1AD3FDA4 CHECK (([new_status]='REJECTED' OR [new_status]='CANCELED' OR [new_status]='COMPLETED' OR [new_status]='PENDING' OR [new_status]='IN_PROGRESS' OR [new_status]='NEW'));
ALTER TABLE crmdb.dbo.status_history WITH NOCHECK ADD CONSTRAINT CK__status_hi__old_s__1BC821DD CHECK (([old_status]='REJECTED' OR [old_status]='CANCELED' OR [old_status]='COMPLETED' OR [old_status]='PENDING' OR [old_status]='IN_PROGRESS' OR [old_status]='NEW'));

---------------------------------------------
   
# viewing messages with timestamps:
## topic request-events
docker exec -it kafka kafka-console-consumer  --topic request-events  --bootstrap-server localhost:9092 --from-beginning --property print.timestamp=true
## topic status-events
 docker exec -it kafka kafka-console-consumer   --topic status-events  --bootstrap-server localhost:9092 --from-beginning --property print.timestamp=true
 
## topic status-events
 docker exec -it kafka kafka-console-consumer   --topic assignment-events  --bootstrap-server localhost:9092 --from-beginning --property print.timestamp=true