DROP TABLE IF EXISTS "Orders";

CREATE TABLE "Orders" (
    "OrderID" INTEGER PRIMARY KEY AUTOINCREMENT,
    "UserID" INTEGER NOT NULL,
    "OrderDate" DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "OrderStatus" TEXT NOT NULL CHECK ("OrderStatus" IN ('Pending', 'Processing', 'Shipped', 'Completed', 'Cancelled','Saved')),
    "ShippingAddress" TEXT NOT NULL,
    "Receipt" TEXT,
    "ETA" DATE,
    FOREIGN KEY ("UserID") REFERENCES "Users"("UserID")
);