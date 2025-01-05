package net.codinux.kotlin.platform

private fun isRunningInTests(): Boolean = js("""typeof globalThis !== 'undefined' && (globalThis.__karma__ != null || 
    globalThis.Mocha !== 'undefined' || globalThis.mocha !== 'undefined' || globalThis.kotlinTest !== 'undefined')""")

internal actual object PlatformEnvironment {

    actual val isRunningTests by lazy { isRunningInTests() }


    // browsers are sandboxed, they have no access to system's environment variables
    actual fun getEnvironmentVariables(): Map<String, String> = emptyMap()

}