DROP TABLE IF EXISTS "Units";

CREATE TABLE "Units" (
    "UnitID" INTEGER PRIMARY KEY AUTOINCREMENT,
    "ItemTypeID" INTEGER NOT NULL,
    "DateAdded" DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "Status" TEXT NOT NULL CHECK ("Status" IN ('In_Stock', 'Sold', 'Reserved', 'Out_for_Delivery', 'Delivered', 'Restocking')),
    "DatePurchased" DATETIME,
    "UserID" INTEGER,
    "ETA" DATETIME,
    FOREIGN KEY ("ItemTypeID") REFERENCES "ItemTypes"("ItemTypeID"),
    FOREIGN KEY ("UserID") REFERENCES "Users"("UserID")
);