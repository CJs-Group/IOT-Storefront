DROP TABLE IF EXISTS "Payments";

CREATE TABLE "Payments" (
    "PaymentID" INTEGER PRIMARY KEY AUTOINCREMENT,
    "OrderID" INTEGER NOT NULL,
    "CardID" INTEGER NOT NULL,
    "Amount" INTEGER NOT NULL, -- amount in cents
    "PaymentDate" DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "PaymentStatus" TEXT NOT NULL CHECK ("PaymentStatus" IN ('Pending', 'Completed', 'Failed', 'Cancelled')),
    FOREIGN KEY ("OrderID") REFERENCES "Orders"("OrderID"),
    FOREIGN KEY ("CardID") REFERENCES "CardDetails"("CardID")
);