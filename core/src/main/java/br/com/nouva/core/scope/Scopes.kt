package br.com.nouva.core.scope

import br.com.nouva.core.complements.Response
import br.com.nouva.core.complements.User


val eval = {
    User().run {
        name = "Ricardo"
        return@run display(name!!)
    }
}

val response = {
    Response().run {
        received = arrayListOf()
        return@run retrieve(received!!)
    }
}
