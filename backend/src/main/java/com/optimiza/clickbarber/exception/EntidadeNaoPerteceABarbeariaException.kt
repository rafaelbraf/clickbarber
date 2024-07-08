package com.optimiza.clickbarber.exception

class EntidadeNaoPerteceABarbeariaException(
    resourceName: String,
    fieldName: String,
    fieldValue: String
) : RuntimeException("$resourceName com $fieldName = $fieldValue não pertence à barbearia.")