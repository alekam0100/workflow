-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 07, 2015 at 08:27 PM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `ordermgmt`
--
CREATE DATABASE IF NOT EXISTS `ordermgmt` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `ordermgmt`;

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
CREATE TABLE IF NOT EXISTS `bill` (
  `pk_id_bill` int(11) NOT NULL,
  `issuing_date` datetime DEFAULT NULL,
  `fk_id_billstatus` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `billstatus`
--

DROP TABLE IF EXISTS `billstatus`;
CREATE TABLE IF NOT EXISTS `billstatus` (
  `pk_id_billstatus` int(11) NOT NULL,
  `status` varchar(45) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `billstatus`
--

INSERT INTO `billstatus` (`pk_id_billstatus`, `status`) VALUES
(1, 'open'),
(2, 'closed');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `fk_id_user` int(11) NOT NULL,
  `birth_date` datetime DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `house_no` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `post_code` varchar(45) DEFAULT NULL,
  `street` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`fk_id_user`, `birth_date`, `city`, `country`, `email`, `first_name`, `house_no`, `last_name`, `post_code`, `street`) VALUES
(1, '1990-01-01 00:00:00', 'Vienna', 'Austria', 'test@domain.at', 'Test', '1', 'Person', '1010', 'Teststreet');

-- --------------------------------------------------------

--
-- Table structure for table `food`
--

DROP TABLE IF EXISTS `food`;
CREATE TABLE IF NOT EXISTS `food` (
  `pk_id_food` int(11) NOT NULL,
  `available` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `fk_id_food_type` int(11) DEFAULT NULL,
  `fk_id_size_unit` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `net_price` double DEFAULT NULL,
  `size` double DEFAULT NULL,
  `fk_id_foodtype` int(11) DEFAULT NULL,
  `fk_id_sizeunit` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `food`
--

INSERT INTO `food` (`pk_id_food`, `available`, `description`, `fk_id_food_type`, `fk_id_size_unit`, `name`, `net_price`, `size`, `fk_id_foodtype`, `fk_id_sizeunit`) VALUES
(4, b'1', 'Wiener Schnitzel mit Pommes', 2, NULL, 'WienerSchnitzel', 10, NULL, NULL, NULL),
(5, b'1', 'Rindsuppe mit Nudeln', 2, NULL, 'Rindsuppe', 3, NULL, NULL, NULL),
(6, b'1', 'Mojito', 1, NULL, 'Mojito', 5, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `foodtype`
--

DROP TABLE IF EXISTS `foodtype`;
CREATE TABLE IF NOT EXISTS `foodtype` (
  `pk_id_foodtype` int(11) NOT NULL,
  `tax` double NOT NULL,
  `type` varchar(45) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `foodtype`
--

INSERT INTO `foodtype` (`pk_id_foodtype`, `tax`, `type`) VALUES
(1, 0.2, 'beverage'),
(2, 0.1, 'dish');

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
CREATE TABLE IF NOT EXISTS `order` (
  `pk_id_order` int(11) NOT NULL,
  `comment` longtext,
  `creation_time` datetime DEFAULT NULL,
  `fk_id_bill` int(11) DEFAULT NULL,
  `fk_id_user` int(11) DEFAULT NULL,
  `fk_id_orderstatus` int(11) DEFAULT NULL,
  `fk_id_reservation` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `orderitem`
--

DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE IF NOT EXISTS `orderitem` (
  `pk_id_orderitem` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `comment` longtext,
  `fk_id_food` int(11) DEFAULT NULL,
  `fk_id_order` int(11) DEFAULT NULL,
  `fk_id_orderitemstatus` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `orderitemstatus`
--

DROP TABLE IF EXISTS `orderitemstatus`;
CREATE TABLE IF NOT EXISTS `orderitemstatus` (
  `pk_id_orderitemstatus` int(11) NOT NULL,
  `status` varchar(45) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orderitemstatus`
--

INSERT INTO `orderitemstatus` (`pk_id_orderitemstatus`, `status`) VALUES
(1, 'created'),
(2, 'in progress'),
(3, 'ready for delivery'),
(4, 'done'),
(5, 'cancelled');

-- --------------------------------------------------------

--
-- Table structure for table `orderstatus`
--

DROP TABLE IF EXISTS `orderstatus`;
CREATE TABLE IF NOT EXISTS `orderstatus` (
  `pk_id_orderstatus` int(11) NOT NULL,
  `status` varchar(45) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orderstatus`
--

INSERT INTO `orderstatus` (`pk_id_orderstatus`, `status`) VALUES
(1, 'created'),
(2, 'in progress'),
(3, 'done'),
(4, 'cancelled');

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `pk_id_reservation` int(11) NOT NULL,
  `persons` int(11) DEFAULT NULL,
  `time_from` datetime NOT NULL,
  `time_to` datetime NOT NULL,
  `fk_id_user` int(11) DEFAULT NULL,
  `fk_id_reservation_status` int(11) DEFAULT NULL,
  `fk_id_restaurant_table` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`pk_id_reservation`, `persons`, `time_from`, `time_to`, `fk_id_user`, `fk_id_reservation_status`, `fk_id_restaurant_table`) VALUES
(1, 4, '2015-05-10 16:00:00', '2015-05-10 18:00:00', 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `reservationstatus`
--

DROP TABLE IF EXISTS `reservationstatus`;
CREATE TABLE IF NOT EXISTS `reservationstatus` (
  `pk_id_reservationstatus` int(11) NOT NULL,
  `status` varchar(45) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `reservationstatus`
--

INSERT INTO `reservationstatus` (`pk_id_reservationstatus`, `status`) VALUES
(1, 'valid'),
(2, 'cancelled');

-- --------------------------------------------------------

--
-- Table structure for table `restaurant_table`
--

DROP TABLE IF EXISTS `restaurant_table`;
CREATE TABLE IF NOT EXISTS `restaurant_table` (
  `pk_id_restaurant_table` int(11) NOT NULL,
  `fk_id_table_status` int(11) NOT NULL,
  `fk_id_waiter_status` int(11) NOT NULL,
  `max_person` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `restaurant_table`
--

INSERT INTO `restaurant_table` (`pk_id_restaurant_table`, `fk_id_table_status`, `fk_id_waiter_status`, `max_person`) VALUES
(3, 1, 1, 4),
(4, 1, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `sizeunit`
--

DROP TABLE IF EXISTS `sizeunit`;
CREATE TABLE IF NOT EXISTS `sizeunit` (
  `pk_id_sizeunit` int(11) NOT NULL,
  `unit` varchar(45) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sizeunit`
--

INSERT INTO `sizeunit` (`pk_id_sizeunit`, `unit`) VALUES
(1, 'liter'),
(2, 'centiliter'),
(3, 'milliliter'),
(4, 'gram'),
(5, 'kilogram');

-- --------------------------------------------------------

--
-- Table structure for table `table_status`
--

DROP TABLE IF EXISTS `table_status`;
CREATE TABLE IF NOT EXISTS `table_status` (
  `pk_id_table_status` int(11) NOT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `table_status`
--

INSERT INTO `table_status` (`pk_id_table_status`, `status`) VALUES
(1, 'free'),
(2, 'reserved'),
(3, 'occupied');

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
CREATE TABLE IF NOT EXISTS `test` (
  `pk_id_test` int(11) NOT NULL,
  `text` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `pk_id_user` int(11) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `token` varchar(64) DEFAULT NULL,
  `username` varchar(45) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`pk_id_user`, `password`, `token`, `username`) VALUES
(1, 'x', NULL, 'user'),
(2, 'pwd', NULL, 'kitchen'),
(3, 'pwd', NULL, 'manager'),
(4, 'pwd', NULL, 'waiter'),
(5, 'pwd', NULL, 'bar');

-- --------------------------------------------------------

--
-- Table structure for table `waiterstatus`
--

DROP TABLE IF EXISTS `waiterstatus`;
CREATE TABLE IF NOT EXISTS `waiterstatus` (
  `pk_id_waiter_status` int(11) NOT NULL,
  `status` varchar(45) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `waiterstatus`
--

INSERT INTO `waiterstatus` (`pk_id_waiter_status`, `status`) VALUES
(1, 'none'),
(2, 'waiter'),
(3, 'bill');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`pk_id_bill`), ADD KEY `FK_8nlar88vroyrc9994xh8bxi18` (`fk_id_billstatus`);

--
-- Indexes for table `billstatus`
--
ALTER TABLE `billstatus`
  ADD PRIMARY KEY (`pk_id_billstatus`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`fk_id_user`);

--
-- Indexes for table `food`
--
ALTER TABLE `food`
  ADD PRIMARY KEY (`pk_id_food`), ADD KEY `FK_jn4dx8ddkeij5x6b7fwpcp7yd` (`fk_id_foodtype`), ADD KEY `FK_9pvj7pg91oduvp8e44xefpmte` (`fk_id_sizeunit`);

--
-- Indexes for table `foodtype`
--
ALTER TABLE `foodtype`
  ADD PRIMARY KEY (`pk_id_foodtype`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`pk_id_order`), ADD KEY `FK_ggwx2k8o8oomtc455ff60bt0j` (`fk_id_bill`), ADD KEY `FK_am9e79ih81an646g09vlx6jbd` (`fk_id_user`), ADD KEY `FK_m0ijwn6j1phx9in2a3t48gdyn` (`fk_id_orderstatus`), ADD KEY `FK_o5hyda1rsei1dsrry4i3en80h` (`fk_id_reservation`);

--
-- Indexes for table `orderitem`
--
ALTER TABLE `orderitem`
  ADD PRIMARY KEY (`pk_id_orderitem`), ADD KEY `FK_2qst2029xa42wrodi02m3pne6` (`fk_id_food`), ADD KEY `FK_8y49kauugw27aa3yd9422s15q` (`fk_id_order`), ADD KEY `FK_bexa4w517w2ceq0rdhslpysbi` (`fk_id_orderitemstatus`);

--
-- Indexes for table `orderitemstatus`
--
ALTER TABLE `orderitemstatus`
  ADD PRIMARY KEY (`pk_id_orderitemstatus`);

--
-- Indexes for table `orderstatus`
--
ALTER TABLE `orderstatus`
  ADD PRIMARY KEY (`pk_id_orderstatus`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`pk_id_reservation`), ADD KEY `FK_sc2l69hvj313qoqk203qe94l9` (`fk_id_user`), ADD KEY `FK_oxwsmgu4o6vvrib000c3ou5nn` (`fk_id_reservation_status`), ADD KEY `FK_2a3wbnynamrwcm3ame3ddly7h` (`fk_id_restaurant_table`);

--
-- Indexes for table `reservationstatus`
--
ALTER TABLE `reservationstatus`
  ADD PRIMARY KEY (`pk_id_reservationstatus`);

--
-- Indexes for table `restaurant_table`
--
ALTER TABLE `restaurant_table`
  ADD PRIMARY KEY (`pk_id_restaurant_table`), ADD KEY `FK_qbesga6l6utyliwbqicy7xnhj` (`fk_id_table_status`), ADD KEY `FK_6ey0nqbb82wtnlmboes5iocb5` (`fk_id_waiter_status`);

--
-- Indexes for table `sizeunit`
--
ALTER TABLE `sizeunit`
  ADD PRIMARY KEY (`pk_id_sizeunit`);

--
-- Indexes for table `table_status`
--
ALTER TABLE `table_status`
  ADD PRIMARY KEY (`pk_id_table_status`);

--
-- Indexes for table `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`pk_id_test`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`pk_id_user`);

--
-- Indexes for table `waiterstatus`
--
ALTER TABLE `waiterstatus`
  ADD PRIMARY KEY (`pk_id_waiter_status`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bill`
--
ALTER TABLE `bill`
  MODIFY `pk_id_bill` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `billstatus`
--
ALTER TABLE `billstatus`
  MODIFY `pk_id_billstatus` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `food`
--
ALTER TABLE `food`
  MODIFY `pk_id_food` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `foodtype`
--
ALTER TABLE `foodtype`
  MODIFY `pk_id_foodtype` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `pk_id_order` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `orderitem`
--
ALTER TABLE `orderitem`
  MODIFY `pk_id_orderitem` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `orderitemstatus`
--
ALTER TABLE `orderitemstatus`
  MODIFY `pk_id_orderitemstatus` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `orderstatus`
--
ALTER TABLE `orderstatus`
  MODIFY `pk_id_orderstatus` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `pk_id_reservation` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `reservationstatus`
--
ALTER TABLE `reservationstatus`
  MODIFY `pk_id_reservationstatus` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `restaurant_table`
--
ALTER TABLE `restaurant_table`
  MODIFY `pk_id_restaurant_table` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `sizeunit`
--
ALTER TABLE `sizeunit`
  MODIFY `pk_id_sizeunit` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `table_status`
--
ALTER TABLE `table_status`
  MODIFY `pk_id_table_status` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `test`
--
ALTER TABLE `test`
  MODIFY `pk_id_test` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `pk_id_user` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `waiterstatus`
--
ALTER TABLE `waiterstatus`
  MODIFY `pk_id_waiter_status` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `bill`
--
ALTER TABLE `bill`
ADD CONSTRAINT `FK_8nlar88vroyrc9994xh8bxi18` FOREIGN KEY (`fk_id_billstatus`) REFERENCES `billstatus` (`pk_id_billstatus`);

--
-- Constraints for table `food`
--
ALTER TABLE `food`
ADD CONSTRAINT `FK_9pvj7pg91oduvp8e44xefpmte` FOREIGN KEY (`fk_id_sizeunit`) REFERENCES `sizeunit` (`pk_id_sizeunit`),
ADD CONSTRAINT `FK_jn4dx8ddkeij5x6b7fwpcp7yd` FOREIGN KEY (`fk_id_foodtype`) REFERENCES `foodtype` (`pk_id_foodtype`);

--
-- Constraints for table `order`
--
ALTER TABLE `order`
ADD CONSTRAINT `FK_am9e79ih81an646g09vlx6jbd` FOREIGN KEY (`fk_id_user`) REFERENCES `customer` (`fk_id_user`),
ADD CONSTRAINT `FK_ggwx2k8o8oomtc455ff60bt0j` FOREIGN KEY (`fk_id_bill`) REFERENCES `bill` (`pk_id_bill`),
ADD CONSTRAINT `FK_m0ijwn6j1phx9in2a3t48gdyn` FOREIGN KEY (`fk_id_orderstatus`) REFERENCES `orderstatus` (`pk_id_orderstatus`),
ADD CONSTRAINT `FK_o5hyda1rsei1dsrry4i3en80h` FOREIGN KEY (`fk_id_reservation`) REFERENCES `reservation` (`pk_id_reservation`);

--
-- Constraints for table `orderitem`
--
ALTER TABLE `orderitem`
ADD CONSTRAINT `FK_2qst2029xa42wrodi02m3pne6` FOREIGN KEY (`fk_id_food`) REFERENCES `food` (`pk_id_food`),
ADD CONSTRAINT `FK_8y49kauugw27aa3yd9422s15q` FOREIGN KEY (`fk_id_order`) REFERENCES `order` (`pk_id_order`),
ADD CONSTRAINT `FK_bexa4w517w2ceq0rdhslpysbi` FOREIGN KEY (`fk_id_orderitemstatus`) REFERENCES `orderitemstatus` (`pk_id_orderitemstatus`);

--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
ADD CONSTRAINT `FK_2a3wbnynamrwcm3ame3ddly7h` FOREIGN KEY (`fk_id_restaurant_table`) REFERENCES `restaurant_table` (`pk_id_restaurant_table`),
ADD CONSTRAINT `FK_oxwsmgu4o6vvrib000c3ou5nn` FOREIGN KEY (`fk_id_reservation_status`) REFERENCES `reservationstatus` (`pk_id_reservationstatus`),
ADD CONSTRAINT `FK_sc2l69hvj313qoqk203qe94l9` FOREIGN KEY (`fk_id_user`) REFERENCES `customer` (`fk_id_user`);

--
-- Constraints for table `restaurant_table`
--
ALTER TABLE `restaurant_table`
ADD CONSTRAINT `FK_6ey0nqbb82wtnlmboes5iocb5` FOREIGN KEY (`fk_id_waiter_status`) REFERENCES `waiterstatus` (`pk_id_waiter_status`),
ADD CONSTRAINT `FK_qbesga6l6utyliwbqicy7xnhj` FOREIGN KEY (`fk_id_table_status`) REFERENCES `table_status` (`pk_id_table_status`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
