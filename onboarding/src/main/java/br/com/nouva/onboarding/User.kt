package br.com.nouva.onboarding

class User {
    var name: String? = null
    var email: String? = null


    constructor(){}

    constructor(name: String?, email: String?){

        this.name  = name
        this.email = email

    }
}