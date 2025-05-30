DROP TABLE IF EXISTS "CardDetails";

CREATE TABLE "CardDetails" (
    "CardID" INTEGER PRIMARY KEY AUTOINCREMENT,
    "UserID" INTEGER NOT NULL,
    "CardNo" TEXT NOT NULL,
    "CCV" TEXT NOT NULL,
    "CardExpr" TEXT NOT NULL,
    "CardHolderName" TEXT NOT NULL,
    FOREIGN KEY ("UserID") REFERENCES "Users"("UserID")
);