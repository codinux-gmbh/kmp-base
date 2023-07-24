package net.codinux.kotlin.util

expect class URL(url: String) {

    val scheme: String

    val host: String?

    val port: Int?

}