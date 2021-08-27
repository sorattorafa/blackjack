BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `used_cards` (
	`carta_id`	INTEGER NOT NULL,
	`baralho_id`	INTEGER NOT NULL,
	FOREIGN KEY(`baralho_id`) REFERENCES `baralho`(`id`),
	FOREIGN KEY(`carta_id`) REFERENCES `carta`(`id`)
);
CREATE TABLE IF NOT EXISTS `unused_cards` (
	`carta_id`	INTEGER NOT NULL,
	`baralho_id`	INTEGER NOT NULL,
	FOREIGN KEY(`baralho_id`) REFERENCES `baralho`(`id`),
	FOREIGN KEY(`carta_id`) REFERENCES `carta`
);
CREATE TABLE IF NOT EXISTS `mesa` (
	`lista_jogadores_id`	INTEGER NOT NULL,
	`baralho_id`	INTEGER NOT NULL,
	FOREIGN KEY(`baralho_id`) REFERENCES `baralho`(`id`)
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
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`lista_jogadores_id`	INTEGER NOT NULL
);
COMMIT;
