package br.com.nouva.core.data

import br.com.nouva.core.Type
import br.com.nouva.core.Sender

data class Messages(
    var message: String? = null,
    var type: Type? = null,
    var sender: Sender? = null
)
