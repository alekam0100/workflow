SET FOREIGN_KEY_CHECKS=0;
-- Insert ENUMS: --
DELETE FROM `ordermgmt`.`billstatus`;
INSERT INTO `ordermgmt`.`billstatus` VALUES (1,'open'), (2,'closed');

DELETE FROM `ordermgmt`.`orderstatus`;
INSERT INTO `ordermgmt`.`orderstatus` VALUES (1,'created'), (2,'in progress'), (3,'done'), (4,'cancelled');

DELETE FROM `ordermgmt`.`orderitemstatus`;
INSERT INTO `ordermgmt`.`orderitemstatus` VALUES (1,'created'), (2,'in progress'),(3,'ready for delivery'), (4,'done'), (5,'cancelled');

DELETE FROM `ordermgmt`.`food_type`;
INSERT INTO `ordermgmt`.`food_type` VALUES (1,0.2,'beverage'), (2,0.1,'dish');

DELETE FROM `ordermgmt`.`table_status`;
INSERT INTO `ordermgmt`.`table_status` VALUES (1,'free'),(2,'reserved'),(3,'occupied');

DELETE FROM `ordermgmt`.`size_unit`;
INSERT INTO `ordermgmt`.`size_unit` VALUES (1,'liter'),(2,'centiliter'),(3,'milliliter'),(4,'gram'),(5,'kilogram');

DELETE FROM `ordermgmt`.`reservation_status`;
INSERT INTO `ordermgmt`.`reservation_status` VALUES (1,'valid'),(2,'cancelled');

DELETE FROM `ordermgmt`.`waiter_status`;
INSERT INTO `ordermgmt`.`waiter_status` VALUES (1,'none'),(2,'waiter'),(3,'bill');

-- Insert testdata --
DELETE FROM `ordermgmt`.`user`;
INSERT INTO `ordermgmt`.`user` VALUES (1,'user','x',NULL);
INSERT INTO `ordermgmt`.`user` VALUES (2,'kitchen','pwd',NULL);
INSERT INTO `ordermgmt`.`user` VALUES (3,'manager','pwd',NULL);
INSERT INTO `ordermgmt`.`user` VALUES (4,'waiter','pwd',NULL);
INSERT INTO `ordermgmt`.`user` VALUES (5,'bar','pwd',NULL);


DELETE FROM `ordermgmt`.`customer`;
INSERT INTO `ordermgmt`.`customer` (`first_name`, `last_name`, `birth_date`, `street`, `house_no`, `post_code`, `city`, `country`, `fk_id_user`, `email`) VALUES ('Test', 'Person', '1990-01-01 00:00:00', 'Teststreet', '1', '1010', 'Vienna', 'Austria', '1', 'test@domain.at');

DELETE FROM `ordermgmt`.`food`;
INSERT INTO `ordermgmt`.`food` (`pk_id_food`, `net_price`, `available`, `name`, `description`, `fk_id_food_type`, `size`, `fk_id_size_unit`) VALUES (NULL, '10', '1', 'WienerSchnitzel', 'Wiener Schnitzel mit Pommes', '2', NULL, NULL);
INSERT INTO `ordermgmt`.`food` (`pk_id_food`, `net_price`, `available`, `name`, `description`, `fk_id_food_type`, `size`, `fk_id_size_unit`) VALUES (NULL, '3', '1', 'Rindsuppe', 'Rindsuppe mit Nudeln', '2', NULL, NULL);
INSERT INTO `ordermgmt`.`food` (`pk_id_food`, `net_price`, `available`, `name`, `description`, `fk_id_food_type`, `size`, `fk_id_size_unit`) VALUES (NULL, '5', '1', 'Mojito', 'Mojito', '1', NULL, NULL);

DELETE FROM `ordermgmt`.`restaurant_table`;
INSERT INTO `ordermgmt`.`restaurant_table` (`pk_id_restaurant_table`, `max_person`, `fk_id_table_status`, `fk_id_waiter_status`) VALUES (NULL, '4', '1','1');
INSERT INTO `ordermgmt`.`restaurant_table` (`pk_id_restaurant_table`, `max_person`, `fk_id_table_status`, `fk_id_waiter_status`) VALUES (NULL, '2', '1', '1');

delete from `ordermgmt`.`reservation`;
INSERT INTO `ordermgmt`.`reservation` (`pk_id_reservation`, `time_from`, `time_to`, `persons`, `reservation_col`, `fk_id_user`, `fk_id_restaurant_table`, `fk_id_reservation_status`)
VALUES (NULL, '2015-05-10 16:00:00', '2015-05-10 18:00:00', 4, "", 1, 1, 1);

SET FOREIGN_KEY_CHECKS=1;
