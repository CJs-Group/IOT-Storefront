DROP TABLE IF EXISTS "Users";

CREATE TABLE "Users" (
	"UserID"	INTEGER,
	"Name"	TEXT NOT NULL,
	"Email"	TEXT UNIQUE NOT NULL,
	"PasswordHash"	TEXT NOT NULL,
	"PhoneNumber"	TEXT,
	"Type" TEXT CHECK("Type" IN ('Customer', 'Staff', 'Admin')) NOT NULL,
	"AccountType" TEXT CHECK("AccountType" IN ('Individual', 'Business', 'Enterprise')),
	"StaffRole" TEXT,
	"ShippingAddress" TEXT,
	"Activated" BOOLEAN NOT NULL DEFAULT 1,
	"LastLoginDate" DATETIME,
	CHECK (
        ("Type" != 'Customer' AND  "ShippingAddress" IS NULL) OR 
        ("Type" = 'Customer')
    ),
	PRIMARY KEY("UserID" AUTOINCREMENT)
);

-- CREATE TABLE "OrderItems" (
-- 	"OrderItemID"	INTEGER,
-- 	"UnitID"	INTEGER,
-- 	"Quantity"	INTEGER,
-- 	"PriceAtPurchase"	REAL,
-- 	PRIMARY KEY("OrderItemID" AUTOINCREMENT)
-- );