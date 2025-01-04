
allprojects {
    group = "net.codinux.codinux"
    version = "0.5.0-SNAPSHOT"

    ext["sourceCodeRepositoryBaseUrl"] = "github.com/codinux-gmbh/kmp-base"

    ext["projectDescription"] = "Common functions and classes for all KMP platforms that are missing in Kotlin Stdlib like concurrent structures, URL, Locale, ..."

    repositories {
        mavenCentral()
    }
}