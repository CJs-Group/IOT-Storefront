DROP TABLE IF EXISTS "Baskets";

CREATE TABLE "Baskets" (
    "BasketID" INTEGER PRIMARY KEY AUTOINCREMENT,
    "UserID" INTEGER NOT NULL,
    FOREIGN KEY ("UserID") REFERENCES "Users"("UserID")
);