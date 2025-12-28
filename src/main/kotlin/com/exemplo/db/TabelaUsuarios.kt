package com.exemplo.db

import org.jetbrains.exposed.sql.Table

object TabelaUsuarios : Table("usuarios") {
    val id = integer("id").autoIncrement()
    val nome = varchar("nome", 128)
    val email = varchar("email", 128)

    override val primaryKey = PrimaryKey(id)
}