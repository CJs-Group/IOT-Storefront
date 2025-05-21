DROP TABLE IF EXISTS "Users";

CREATE TABLE "Users" (
	"UserID"	INTEGER,
	"Name"	TEXT NOT NULL,
	"Email"	TEXT UNIQUE NOT NULL,
	"PasswordHash"	TEXT NOT NULL,
	"PhoneNumber"	TEXT,
	"Type" TEXT CHECK("Type" IN ('Customer', 'Staff', 'Admin')) NOT NULL,
	"ShippingAddress" TEXT CHECK (
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