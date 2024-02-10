SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

USE `mydatabase`;

INSERT INTO `member` (`id`, `password`, `phone_number`) VALUES
(2,	'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',	'01012345679'),
(3,	'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',	'01012345670');

INSERT INTO `product` (`id`, `member_phone_number`, `category`, `sale_price`, `cost`, `name`, `description`, `barcode`, `size`, `expired_date`) VALUES
(7,	'01012345670',	'asdf',	1234,	50,	'슈크림',	'test',	1234,	'SMALL',	'2023-02-12'),
(8,	'01012345670',	'asdf',	1234,	50,	'슈크림 라때',	'test',	1234,	'SMALL',	'2023-02-12'),
(9,	'01012345670',	'asdf',	1234,	50,	'슈크림 라때 콤보',	'test',	1234,	'SMALL',	'2023-02-12'),
(10,	'01012345670',	'asdf',	1234,	50,	'슈크림 라때 수퍼 콤보',	'test',	1234,	'SMALL',	'2023-02-12'),
(11,	'01012345670',	'asdf',	1234,	50,	'슈',	'test',	1234,	'SMALL',	'2023-02-12'),
(12,	'01012345670',	'asdf',	1234,	50,	'크',	'test',	1234,	'SMALL',	'2023-02-12'),
(13,	'01012345670',	'asdf',	1234,	50,	'림',	'test',	1234,	'SMALL',	'2023-02-12'),
(14,	'01012345670',	'asdf',	1234,	50,	'라',	'test',	1234,	'SMALL',	'2023-02-12'),
(15,	'01012345670',	'asdf',	1234,	50,	'때',	'test',	1234,	'SMALL',	'2023-02-12'),
(16,	'01012345670',	'asdf',	1234,	50,	'ㅋㅋㅋ',	'test',	1234,	'SMALL',	'2023-02-12'),
(17,	'01012345670',	'asdf',	1234,	50,	'ㅂㅈㄷ',	'test',	1234,	'SMALL',	'2023-02-12'),
(18,	'01012345670',	'asdf',	1234,	50,	'ㅁㄴㅇㄹ',	'test',	1234,	'SMALL',	'2023-02-12'),
(19,	'01012345670',	'asdf',	1234,	500,	'test',	'test',	12341234,	'SMALL',	'2023-02-12');

INSERT INTO `product_first_initial_name` (`id`, `product_id`, `first_initial_name`) VALUES
(2,	7,	'ㅅㅋㄹ'),
(3,	8,	'ㅅㅋㄹ ㄹㄸ'),
(4,	9,	'ㅅㅋㄹ ㄹㄸ ㅋㅂ'),
(5,	10,	'ㅅㅋㄹ ㄹㄸ ㅅㅍ ㅋㅂ'),
(6,	11,	'ㅅ'),
(7,	12,	'ㅋ'),
(8,	13,	'ㄹ'),
(9,	14,	'ㄹ'),
(10,	15,	'ㄸ'),
(11,	16,	NULL),
(12,	17,	NULL),
(13,	18,	NULL),
(14,	19,	NULL);