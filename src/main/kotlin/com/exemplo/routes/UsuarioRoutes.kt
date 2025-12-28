package com.exemplo.routes

import com.exemplo.db.TabelaUsuarios
import com.exemplo.model.Usuario
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction



fun Route.usuarioRoutes() {
    get("/usuarios") {
        val lista = transaction {
            TabelaUsuarios.selectAll().map { row ->
                Usuario(
                    id = row[TabelaUsuarios.id],
                    nome = row[TabelaUsuarios.nome],
                    email = row[TabelaUsuarios.email]
                )
            }
        }
        call.respond(lista)
    }
}