CREATE TABLE `pierreantoine`.`temperatures` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date_import` DATETIME NULL,
  `date_temperature` DATETIME NULL,
  `val_temperature` DOUBLE NULL,
  `nb_points` INT NULL,
  PRIMARY KEY (`id`));
