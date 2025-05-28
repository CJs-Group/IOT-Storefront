DROP TABLE IF EXISTS "ItemTypes";

CREATE TABLE "ItemTypes" (
    "ItemTypeID"    INTEGER,
    "Name"          TEXT NOT NULL,
    "Description"   TEXT,
    "Price"         INTEGER NOT NULL,
    "Type"          TEXT CHECK("Type" IN ('Networking', 'Security', 'Smart_Home', 'Assistants')) NOT NULL,
    "ImagePath"     TEXT,
    "Quantity"      INTEGER NOT NULL,
    PRIMARY KEY("ItemTypeID" AUTOINCREMENT)
);