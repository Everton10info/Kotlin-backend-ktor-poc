package com.exemplo // Ajustei o nome do pacote que estava duplicado

import com.exemplo.db.DatabaseFactory
import com.exemplo.routes.usuarioRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

// Com EngineMain, o main fica simples assim:
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    // 1. Configura JSON
    install(ContentNegotiation) {
        json()
    }

    // 2. Inicia Banco
    DatabaseFactory.init()

    // 3. Registra Rotas
    routing {
        usuarioRoutes()
    }
}