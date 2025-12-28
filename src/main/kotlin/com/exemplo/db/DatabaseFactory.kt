package com.exemplo.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val dbName = System.getenv("DB_NAME")?:"meubanco"
        // No seu compose você definiu DB_URL, então vamos usá-la.
        // Se não existir (rodando local no IntelliJ), usa o localhost:5434
        val dbUrl = System.getenv("DB_URL") ?: "jdbc:postgresql://localhost:5434/$dbName"

        // No compose você usou POSTGRES_USER/PASS para o banco,
        // mas definiu DB_USER/PASS para o app. Use os nomes do APP:
        val dbUser = System.getenv("DB_USER") ?: "admin"
        val dbPassword = System.getenv("DB_PASSWORD") ?: "admin"

        Database.connect(
            url = dbUrl,
            driver = "org.postgresql.Driver",
            user = dbUser,
            password = dbPassword
        )

        transaction {
            // Adicione esse logger para ver o que acontece no console do Docker!
            addLogger(org.jetbrains.exposed.sql.StdOutSqlLogger)

            SchemaUtils.create(TabelaUsuarios)

            if (TabelaUsuarios.selectAll().count() == 0L) {
                TabelaUsuarios.insert {
                    it[nome] = "Admin Ktor"
                    it[email] = "admin@ktor.io"
                }
                TabelaUsuarios.insert {
                    it[nome] = "USER Ktor"
                    it[email] = "USER@ktor.io"
                }
            }
        }
    }
}