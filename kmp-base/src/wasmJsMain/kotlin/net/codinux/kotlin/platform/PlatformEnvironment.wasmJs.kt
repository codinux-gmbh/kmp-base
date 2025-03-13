package net.codinux.kotlin.platform

private fun isRunningInTests(): Boolean = js("""typeof globalThis !== 'undefined' && (globalThis.__karma__ != null || 
    globalThis.Mocha !== 'undefined' || globalThis.mocha !== 'undefined' || globalThis.kotlinTest !== 'undefined')""")

private fun isJsCodeMinified(): Boolean = js("Function.prototype.toString.call(function () {}).length < 100")


internal actual object PlatformEnvironment {

    actual val isRunningTests by lazy { isRunningInTests() }

    actual val isRunningInDebugMode = false

    /**
     * Tries to determine if the JavaScript code is minified.
     *
     * Not foolproof, makes educated guesses based on code minification.
     */
    val isMinified: Boolean by lazy { isJsCodeMinified() }


    // browsers are sandboxed, they have no access to system's environment variables
    actual fun getEnvironmentVariables(): Map<String, String> = emptyMap()

}