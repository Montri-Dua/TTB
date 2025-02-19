# Getting Started

## 1.CREATE TABLE

CREATE TABLE crmdb.dbo.Customer (
	CustomerId int IDENTITY(1000,1) NOT NULL,
	Firstname nvarchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	Lastname nvarchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	CustomerDate date NOT NULL,
	IsVIP bit DEFAULT 0 NOT NULL,
	StatusCode nvarchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	CreatedOn datetime DEFAULT getdate() NOT NULL,
	ModifiedOn datetime NULL,
	CONSTRAINT PK__Customer__A4AE64D8F0554386 PRIMARY KEY (CustomerId)
);

CREATE TABLE crmdb.dbo.Sales (
	SaleId int IDENTITY(1,1) NOT NULL,
	CustomerId varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	SaleAmount decimal(10,2) NOT NULL,
	SaleDate date NOT NULL,
	CONSTRAINT PK__Sales__1EE3C3FF9C2C9FCE PRIMARY KEY (SaleId)
);

CREATE TABLE crmdb.dbo.users (
	id bigint IDENTITY(1,1) NOT NULL,
	username varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	password varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[role] varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	CONSTRAINT PK__users__3213E83F19EA2E69 PRIMARY KEY (id),
	CONSTRAINT UQ__users__F3DBC5720EAE0726 UNIQUE (username)
);

## Authen
 Use JWT
## EndPoint
Postman collection name : TTB-Question2.postman_collection

POST /api/auth/register - ลงทะเบียน
POST /api/auth/login  - Login
GET /api/customers - ดึงข้อมูลลูกค้าทั้งหมด
GET /api/customers/{id} - ดึงข้อมูลลูกค้าตาม ID
POST /api/customers - สร้างข้อมูลลูกค้าใหม่
PUT /api/customers/{id} - อัปเดตข้อมูลลูกค้า
DELETE /api/customers/{id} - ลบข้อมูลลูกค้า
