package com.example.kcptest

@Suppress("UNUSED_EXPRESSION")
fun kmpTestPromise(block: (resolve: (Any?) -> Unit, reject: (Throwable) -> Unit) -> Any?): Any? {
    block({}, { null })
    return null
}