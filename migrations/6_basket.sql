CREATE TABLE Baskets (
    userID INT NOT NULL,
    itemID INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1 CHECK (quantity > 0),
    PRIMARY KEY (userID,itemID)
);