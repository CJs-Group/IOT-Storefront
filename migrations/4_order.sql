CREATE TABLE Orders(
    orderID INT NOT NULL,
    userID INT NOT NULL,
    itemID INT NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,
    PRIMARY KEY (orderID, itemID),
    FOREIGN KEY (orderID) REFERENCES orders(orderID),
    FOREIGN KEY (itemID) REFERENCES items(itemID)
);