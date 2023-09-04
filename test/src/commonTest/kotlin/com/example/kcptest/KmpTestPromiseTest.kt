package com.example.kcptest

import kotlin.test.Test


class KmpTestPromiseTestCommon {

    @Test
    fun should_test_async(): Any? {
        println("start")
        return kmpTestPromise { resolve, reject ->
            println("==================================================")
            println("side-effect")
            println("==================================================")
            resolve(0)
        }
    }

}