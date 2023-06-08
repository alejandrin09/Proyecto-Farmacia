-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema farmacia
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `farmacia` ;

-- -----------------------------------------------------
-- Schema farmacia
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `farmacia` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `farmacia` ;

-- -----------------------------------------------------
-- Table `farmacia`.`administrador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `farmacia`.`administrador` (
  `admin_id` INT NOT NULL AUTO_INCREMENT,
  `correo` VARCHAR(100) NOT NULL,
  `contraseña` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`admin_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `farmacia`.`ciudad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `farmacia`.`ciudad` (
  `ciudad_id` INT NOT NULL AUTO_INCREMENT,
  `Nombre_ciudad` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`ciudad_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `farmacia`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `farmacia`.`cliente` (
  `correo` VARCHAR(100) NOT NULL,
  `nombre` VARCHAR(50) NOT NULL,
  `contraseña` VARCHAR(50) NOT NULL,
  `identificacion` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`correo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `farmacia`.`sede`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `farmacia`.`sede` (
  `sede_id` INT NOT NULL AUTO_INCREMENT,
  `nombre_sede` VARCHAR(50) NOT NULL,
  `ciudad_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`sede_id`),
  INDEX `ciudad_id` (`ciudad_id` ASC) VISIBLE,
  CONSTRAINT `sede_ibfk_1`
    FOREIGN KEY (`ciudad_id`)
    REFERENCES `farmacia`.`ciudad` (`ciudad_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `farmacia`.`empleado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `farmacia`.`empleado` (
  `empleado_id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  `puesto` VARCHAR(50) NOT NULL,
  `correo` VARCHAR(100) NOT NULL,
  `contraseña` VARCHAR(50) NOT NULL,
  `sede_id` INT NULL DEFAULT NULL,
  `RFC` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`empleado_id`),
  INDEX `sede_id` (`sede_id` ASC) VISIBLE,
  CONSTRAINT `empleado_ibfk_1`
    FOREIGN KEY (`sede_id`)
    REFERENCES `farmacia`.`sede` (`sede_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 242425
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `farmacia`.`proveedor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `farmacia`.`proveedor` (
  `proveedor_id` INT NOT NULL AUTO_INCREMENT,
  `nombreProveedor` VARCHAR(50) NOT NULL,
  `direccion` VARCHAR(100) NULL DEFAULT NULL,
  `telefono` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`proveedor_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `farmacia`.`producto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `farmacia`.`producto` (
  `producto_id` INT NOT NULL AUTO_INCREMENT,
  `nombre_producto` VARCHAR(50) NOT NULL,
  `fecha_caducidad` DATE NOT NULL,
  `presentacion` VARCHAR(50) NOT NULL,
  `tamano` VARCHAR(50) NOT NULL,
  `tipo` VARCHAR(50) NOT NULL,
  `disponible` TINYINT(1) NULL DEFAULT '1',
  `proveedor_id` INT NULL DEFAULT NULL,
  `precio` INT NULL DEFAULT NULL,
  PRIMARY KEY (`producto_id`),
  INDEX `proveedor_id` (`proveedor_id` ASC) VISIBLE,
  CONSTRAINT `producto_ibfk_1`
    FOREIGN KEY (`proveedor_id`)
    REFERENCES `farmacia`.`proveedor` (`proveedor_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `farmacia`.`promocion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `farmacia`.`promocion` (
  `promocion_id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  `descripcion` VARCHAR(255) NULL DEFAULT NULL,
  `fecha_inicio` DATE NOT NULL,
  `fecha_fin` DATE NOT NULL,
  `sede_id` INT NULL DEFAULT NULL,
  `TerminosYConciciones` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`promocion_id`),
  INDEX `fk_promociones_sede` (`sede_id` ASC) VISIBLE,
  CONSTRAINT `fk_promociones_sede`
    FOREIGN KEY (`sede_id`)
    REFERENCES `farmacia`.`sede` (`sede_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `farmacia`.`sedeproducto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `farmacia`.`sedeproducto` (
  `sedeproducto_id` INT NOT NULL AUTO_INCREMENT,
  `sede_id` INT NULL DEFAULT NULL,
  `producto_id` INT NULL DEFAULT NULL,
  `existencia` INT NULL DEFAULT NULL,
  PRIMARY KEY (`sedeproducto_id`),
  INDEX `sedeproducto_ibfk_1` (`sede_id` ASC) VISIBLE,
  INDEX `sedeproducto_ibfk_2` (`producto_id` ASC) VISIBLE,
  CONSTRAINT `sedeproducto_ibfk_1`
    FOREIGN KEY (`sede_id`)
    REFERENCES `farmacia`.`sede` (`sede_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `sedeproducto_ibfk_2`
    FOREIGN KEY (`producto_id`)
    REFERENCES `farmacia`.`producto` (`producto_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `farmacia`.`venta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `farmacia`.`venta` (
  `venta_id` INT NOT NULL AUTO_INCREMENT,
  `producto_id` INT NULL DEFAULT NULL,
  `empleado_id` INT NULL DEFAULT NULL,
  `cliente_correo` VARCHAR(100) NULL DEFAULT NULL,
  `fecha_venta` DATETIME NOT NULL,
  PRIMARY KEY (`venta_id`),
  INDEX `producto_id` (`producto_id` ASC) VISIBLE,
  INDEX `empleado_id` (`empleado_id` ASC) VISIBLE,
  INDEX `cliente_correo` (`cliente_correo` ASC) VISIBLE,
  CONSTRAINT `venta_ibfk_1`
    FOREIGN KEY (`producto_id`)
    REFERENCES `farmacia`.`producto` (`producto_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `venta_ibfk_2`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `farmacia`.`empleado` (`empleado_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `venta_ibfk_3`
    FOREIGN KEY (`cliente_correo`)
    REFERENCES `farmacia`.`cliente` (`correo`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `farmacia` ;

-- -----------------------------------------------------
-- procedure ValidarUsuario
-- -----------------------------------------------------

DELIMITER $$
USE `farmacia`$$
CREATE DEFINER=`AleArePal`@`%` PROCEDURE `ValidarUsuario`(
    IN p_correo VARCHAR(100),
    IN p_contrasena VARCHAR(100),
    OUT p_tipo_usuario INT
)
BEGIN
    SET p_tipo_usuario = 
    CASE
        WHEN EXISTS (SELECT correo, contraseña FROM administrador WHERE correo = p_correo 
        AND contraseña = p_contrasena) THEN 1
        WHEN EXISTS (SELECT correo, contraseña FROM empleado WHERE correo = p_correo 
        AND contraseña = p_contrasena) THEN 2
        WHEN EXISTS (SELECT correo, contraseña FROM cliente WHERE correo = p_correo 
        AND contraseña = p_contrasena) THEN 3
        ELSE 0
    END;
END$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
