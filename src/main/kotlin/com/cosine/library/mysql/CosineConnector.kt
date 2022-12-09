package com.cosine.library.mysql

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection

abstract class CosineConnector(
    database: String,
    user: String,
    password: String,
    host: String,
    port: String,
    maxPoolSize: Int,
    minPoolSize: Int,
) {

    private var ds: HikariDataSource = HikariDataSource(
        HikariConfig().apply {
            this.jdbcUrl = "jdbc:mysql://$host:$port/$database"
            this.username = user
            this.password = password
            this.maximumPoolSize = maxPoolSize
            this.minimumIdle = minPoolSize
        }
    )

    open fun getConnection(): Connection = ds.connection
    open fun closeConnection() = ds.close()
}