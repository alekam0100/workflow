SET FOREIGN_KEY_CHECKS=0;
-- Insert ENUMS: --
DELETE FROM `ordermgmt`.`billstatus`;
INSERT INTO `ordermgmt`.`billstatus` VALUES (1,'open'), (2,'closed');

DELETE FROM `ordermgmt`.`orderstatus`;
INSERT INTO `ordermgmt`.`orderstatus` VALUES (1,'created'), (2,'in progress'), (3,'done'), (4,'cancelled');

DELETE FROM `ordermgmt`.`orderitemstatus`;
INSERT INTO `ordermgmt`.`orderitemstatus` VALUES (1,'created'), (2,'in progress'),(3,'ready for delivery'), (4,'done'), (5,'cancelled');

DELETE FROM `ordermgmt`.`foodtype`;
INSERT INTO `ordermgmt`.`foodtype` VALUES (1,0.2,'beverage'), (2,0.1,'dish');

DELETE FROM `ordermgmt`.`tablestatus`;
INSERT INTO `ordermgmt`.`tablestatus` VALUES (1,'free'),(2,'reserved'),(3,'occupied');

DELETE FROM `ordermgmt`.`sizeunit`;
INSERT INTO `ordermgmt`.`sizeunit` VALUES (1,'liter'),(2,'centiliter'),(3,'milliliter'),(4,'gram'),(5,'kilogram');

DELETE FROM `ordermgmt`.`reservationstatus`;
INSERT INTO `ordermgmt`.`reservationstatus` VALUES (1,'valid'),(2,'cancelled');

DELETE FROM `ordermgmt`.`waiterstatus`;
INSERT INTO `ordermgmt`.`waiterstatus` VALUES (1,'none'),(2,'waiter'),(3,'bill');

-- Insert testdata --
DELETE FROM `ordermgmt`.`user`;
INSERT INTO `ordermgmt`.`user` VALUES (NULL,'user','x',NULL);
INSERT INTO `ordermgmt`.`user` VALUES (NULL,'kitchen','pwd',NULL);
INSERT INTO `ordermgmt`.`user` VALUES (NULL,'manager','pwd',NULL);
INSERT INTO `ordermgmt`.`user` VALUES (NULL,'waiter','pwd',NULL);
INSERT INTO `ordermgmt`.`user` VALUES (NULL,'bar','pwd',NULL);


DELETE FROM `ordermgmt`.`customer`;
INSERT INTO `ordermgmt`.`customer` (`first_name`, `last_name`, `birth_date`, `street`, `house_no`, `post_code`, `city`, `country`, `fk_id_user`, `email`) VALUES ('Test', 'Person', '1990-01-01 00:00:00', 'Teststreet', '1', '1010', 'Vienna', 'Austria', '1', 'test@domain.at');

DELETE FROM `ordermgmt`.`food`;
INSERT INTO `ordermgmt`.`food` (`pk_id_food`, `net_price`, `available`, `name`, `description`, `fk_id_foodtype`, `size`, `fk_id_sizeunit`) VALUES (NULL, '10', '1', 'WienerSchnitzel', 'Wiener Schnitzel nit Pommes', '2', NULL, NULL);
INSERT INTO `ordermgmt`.`food` (`pk_id_food`, `net_price`, `available`, `name`, `description`, `fk_id_foodtype`, `size`, `fk_id_sizeunit`) VALUES (NULL, '3', '1', 'Rindsuppe', 'Rindsuppe mit Nudeln', '2', NULL, NULL);
INSERT INTO `ordermgmt`.`food` (`pk_id_food`, `net_price`, `available`, `name`, `description`, `fk_id_foodtype`, `size`, `fk_id_sizeunit`) VALUES (NULL, '5', '1', 'Mojito', 'Mojito', '1', NULL, NULL);

DELETE FROM `ordermgmt`.`table`;
INSERT INTO `ordermgmt`.`table` (`pk_id_table`, `max_person`, `fk_id_tablestatus`, `fk_id_waiterstatus`) VALUES (NULL, '4', '1','1');
INSERT INTO `ordermgmt`.`table` (`pk_id_table`, `max_person`, `fk_id_tablestatus`, `fk_id_waiterstatus`) VALUES (NULL, '2', '1', '1');

SET FOREIGN_KEY_CHECKS=1;
