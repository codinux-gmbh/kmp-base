package net.codinux.kotlin.platform

import net.codinux.kotlin.Platform

internal actual object PlatformEnvironment {

    actual val isRunningTests: Boolean by lazy {
        // for jsNode only kotlinTest is defined
        js("globalThis.__karma__ !== 'undefined' || globalThis.mocha !== 'undefined' || globalThis.Mocha !== 'undefined' || globalThis.kotlinTest !== 'undefined'")
    }


    actual fun getEnvironmentVariables(): Map<String, String> =
        if (Platform.isRunningInBrowser) {
            // browsers are sandboxed, they have no access to system's environment variables
            emptyMap()
        } else {
            val isProcessEnvDefined = js("""typeof process !== 'undefined' && process.env !== 'undefined'""")

            if (isProcessEnvDefined != null) {
                // don't know how to map dynamic returned from js("""typeof process !== 'undefined' && process.env""") to a Map<String, String>, so returning 'serialized' Map from JS directly
                // js() does not support ES6, so we cannot use arrow functions (like '.map(([key, value]) => ...')
                val envVarString: String = js("""Object.entries(process.env).map(function(entry) { return entry[0] + "=" + entry[1] }).join('\n')""")
                val environmentVariables = envVarString.split('\n').map { it.split('=').let { it.first() to it[1] }}.toMap()

                environmentVariables
            } else {
                emptyMap()
            }
        }

}