BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `used_cards` (
	`carta_id`	INTEGER NOT NULL,
	`baralho_id`	INTEGER NOT NULL,
	`jogador_id`	INTEGER NOT NULL,
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	FOREIGN KEY(`jogador_id`) REFERENCES `jogador`(`id`),
	FOREIGN KEY(`carta_id`) REFERENCES `carta`(`id`),
	FOREIGN KEY(`baralho_id`) REFERENCES `baralho`(`id`)
);
CREATE TABLE IF NOT EXISTS `unused_cards` (
	`carta_id`	INTEGER NOT NULL,
	`baralho_id`	INTEGER NOT NULL,
	FOREIGN KEY(`carta_id`) REFERENCES `carta`,
	FOREIGN KEY(`baralho_id`) REFERENCES `baralho`(`id`)
);
CREATE TABLE IF NOT EXISTS `players_hand` (
	`mao_jogador_id`	INTEGER NOT NULL,
	`jogador_id`	INTEGER NOT NULL,
	FOREIGN KEY(`jogador_id`) REFERENCES `jogador`(`id`),
	FOREIGN KEY(`mao_jogador_id`) REFERENCES `mao_jogador`(`id`)
);
CREATE TABLE IF NOT EXISTS `mesa` (
	`lista_jogadores_id`	INTEGER NOT NULL,
	`baralho_id`	INTEGER NOT NULL,
	`lista_mao_jogadores_id`	INTEGER NOT NULL,
	FOREIGN KEY(`baralho_id`) REFERENCES `baralho`(`id`),
	FOREIGN KEY(`lista_mao_jogadores_id`) REFERENCES `mao_jogador`(`id`)
);
CREATE TABLE IF NOT EXISTS `mao_jogador` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`jogador_id`	INTEGER,
	`used_card_id`	INTEGER NOT NULL,
	FOREIGN KEY(`used_card_id`) REFERENCES `used_cards`(`id`)
);
CREATE TABLE IF NOT EXISTS `lista_jogador` (
	`id`	INTEGER NOT NULL,
	`jogador_id`	INTEGER NOT NULL,
	FOREIGN KEY(`jogador_id`) REFERENCES `jogador`(`id`)
);
CREATE TABLE IF NOT EXISTS `jogador` (
	`nickname`	TEXT NOT NULL,
	`password`	TEXT NOT NULL,
	`cash`	INTEGER NOT NULL DEFAULT 0,
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT
);
CREATE TABLE IF NOT EXISTS `carta` (
	`name`	TEXT NOT NULL,
	`symbol`	TEXT NOT NULL,
	`value`	INTEGER NOT NULL,
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT
);
CREATE TABLE IF NOT EXISTS `baralho` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT
);
COMMIT;
