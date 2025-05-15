DROP TABLE IF EXISTS "Units";
DROP TABLE IF EXISTS "ItemTypes";

CREATE TABLE "ItemTypes" (
    "ItemTypeID"    INTEGER,
    "Name"          TEXT NOT NULL,
    "Description"   TEXT,
    "Price"         INTEGER NOT NULL,
    "Type"          TEXT CHECK("Type" IN ('Networking', 'Security', 'Smart_Home', 'Assistants')) NOT NULL,
    "ImagePath"     TEXT,
    PRIMARY KEY("ItemTypeID" AUTOINCREMENT)
);

CREATE TABLE "Units" (
    "UnitID"        INTEGER,
    "ItemTypeID"    INTEGER NOT NULL,
    "SerialNumber"  TEXT UNIQUE,
    "Status"        TEXT CHECK("Status" IN ('Available', 'Reserved', 'Sold', 'Defective')) NOT NULL DEFAULT 'Available',
    PRIMARY KEY("UnitID" AUTOINCREMENT),
    FOREIGN KEY("ItemTypeID") REFERENCES "ItemTypes"("ItemTypeID") ON DELETE CASCADE
);