package com.optimiza.clickbarber.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class PropertiesConfig {

    @Value("\${frontend.admin.url}")
    lateinit var FRONTEND_ADMIN_URL: String

    @Value("\${frontend.cliente.url}")
    lateinit var FRONTEND_CLIENTE_URL: String

}