DROP TABLE IF EXISTS "OrderItems";

CREATE TABLE "OrderItems" (
    "OrderItemID" INTEGER PRIMARY KEY AUTOINCREMENT,
    "OrderID" INTEGER NOT NULL,
    "UnitID" INTEGER NOT NULL,
    "Quantity" INTEGER NOT NULL DEFAULT 1 CHECK ("Quantity" = 1),
    "PriceAtPurchase" INTEGER NOT NULL,
    FOREIGN KEY ("OrderID") REFERENCES "Orders"("OrderID"),
    FOREIGN KEY ("UnitID") REFERENCES "Units"("UnitID")
);