package net.codinux.kotlin.util

expect class URL(url: String) {

    constructor(baseUrl: String, relativeUrl: String)

    val scheme: String

    val host: String?

    val port: Int?

}