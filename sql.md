CREATE TABLE `admin` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `full_name` varchar(100) NOT NULL,
  `contact_number` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `address` text NOT NULL,
  `city` varchar(50) NOT NULL,
  `state` varchar(50) NOT NULL,
  `pincode` varchar(6) NOT NULL,
  `referral_code` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `fuel_bookings` (
  `booking_id` bigint NOT NULL AUTO_INCREMENT,
  `customer_id` bigint NOT NULL,
  `pump_id` bigint NOT NULL,
  `pump_name` varchar(255) NOT NULL,
  `fuel_type` varchar(50) NOT NULL,
  `quantity` decimal(6,2) NOT NULL,
  `vehicle_number` varchar(20) NOT NULL,
  `delivery_time_slot` varchar(20) NOT NULL,
  `delivery_address` varchar(500) NOT NULL,
  `payment_method` varchar(20) NOT NULL,
  `base_cost` decimal(10,2) NOT NULL,
  `delivery_charge` decimal(10,2) DEFAULT '0.00',
  `tax` decimal(10,2) DEFAULT '0.00',
  `total_amount` decimal(10,2) NOT NULL,
  `status` varchar(20) DEFAULT 'PENDING',
  `order_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `latitude` decimal(10,6) DEFAULT NULL,
  `longitude` decimal(10,6) DEFAULT NULL,
  `notes` text,
  `riderstatus` varchar(45) DEFAULT NULL,
  `rider_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`booking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `petrol_pump` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `owner_name` varchar(100) NOT NULL,
  `contact_number` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL,
  `address` text NOT NULL,
  `city` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  `pincode` varchar(6) NOT NULL,
  `fuel_types` varchar(255) NOT NULL,
  `latitude` decimal(10,7) NOT NULL,
  `longitude` decimal(10,7) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `riders` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,

  `full_name` VARCHAR(100) NOT NULL,
  `date_of_birth` DATE NOT NULL,
  `gender` VARCHAR(20) NOT NULL,
  `contact_number` VARCHAR(15) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `emergency_contact` VARCHAR(15) NOT NULL,
  `address` TEXT NOT NULL,
  `city` VARCHAR(50) NOT NULL,
  `state` VARCHAR(50) NOT NULL,
  `pincode` VARCHAR(10) NOT NULL,

  `vehicle_type` VARCHAR(50) NOT NULL,
  `vehicle_brand` VARCHAR(50) NOT NULL,
  `vehicle_model` VARCHAR(50) NOT NULL,
  `vehicle_year` INT NOT NULL,
  `vehicle_number` VARCHAR(20) NOT NULL,

  `license_number` VARCHAR(30) NOT NULL,
  `license_expiry` DATE NOT NULL,
  `profile_image` LONGBLOB DEFAULT NULL,

  `service_type` VARCHAR(30) NOT NULL,
  `availability` VARCHAR(30) NOT NULL,
  `areas_covered` TEXT,

  `registration_status` VARCHAR(20) DEFAULT 'PENDING',
  `verified` TINYINT(1) DEFAULT '0',

  `password` VARCHAR(255) DEFAULT NULL,

  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_riders_email` (`email`),
  UNIQUE KEY `uk_riders_vehicle_number` (`vehicle_number`),
  UNIQUE KEY `uk_riders_license_number` (`license_number`)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;




