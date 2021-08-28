BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "partida" (
	"perdedor_id"	INTEGER NOT NULL,
	"ganhador_id"	INTEGER NOT NULL,
	"total_cash"	INTEGER NOT NULL,
	FOREIGN KEY("perdedor_id") REFERENCES "jogador"("id"),
	FOREIGN KEY("ganhador_id") REFERENCES "jogador"("id")
);
CREATE TABLE IF NOT EXISTS "jogador" (
	"id"	INTEGER,
	"nickname"	TEXT NOT NULL UNIQUE,
	"password"	TEXT NOT NULL,
	"cash"	INTEGER DEFAULT 10000,
	PRIMARY KEY("id" AUTOINCREMENT)
);
INSERT INTO "jogador" ("id","nickname","password","cash") VALUES (1,'rafa','123456',10000),
 (2,'2312312','321312',10000),
 (3,'12312','132123',10000);
COMMIT;
