package net.codinux.kotlin.platform

open class Environment(open val variables: Map<String, String> = PlatformEnvironment.getEnvironmentVariables()) {

    open val variableNames: Set<String>
        get() = variables.keys

    open fun getValue(name: String): String? = variables[name]

}