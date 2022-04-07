package br.com.nouva.core.complements

class User {
    var name: String? = null
    fun display(res: String): String = res
}

class Response {
    var received: List<String>? = null
    fun retrieve(res: List<String>): List<String> = res
}