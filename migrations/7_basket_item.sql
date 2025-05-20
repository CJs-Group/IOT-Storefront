DROP TABLE IF EXISTS "BasketItems";

CREATE TABLE "BasketItems" (
    "BasketItemID" INTEGER PRIMARY KEY AUTOINCREMENT,
    "BasketID" INTEGER NOT NULL,
    "ItemTypeID" INTEGER NOT NULL,
    "Quantity" INTEGER NOT NULL DEFAULT 1 CHECK ("Quantity" > 0),
    "PriceAtPurchase" INTEGER,
    FOREIGN KEY ("BasketID") REFERENCES "Baskets"("BasketID"),
    FOREIGN KEY ("ItemTypeID") REFERENCES "ItemTypes"("ItemTypeID")
);