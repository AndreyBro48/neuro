package com.bukalapak.neuro

import android.net.Uri
import com.bukalapak.result.Response
import java.util.concurrent.ConcurrentSkipListMap
import java.util.concurrent.ConcurrentSkipListSet

typealias SignalAction<T> = (Signal) -> Response<T>
typealias AxonPreprocessor<T> = (AxonProcessor<T>, SignalAction<T>, Signal) -> Response<T>
typealias AxonProcessor<T> = (SignalAction<T>, Signal) -> Response<T>
typealias RouteDecision<T> = Triple<Nucleus.Chosen, AxonBranch<T>?, Uri>
typealias AxonTerminal<T> = ConcurrentSkipListMap<Int, ConcurrentSkipListSet<AxonBranch<T>>>

private const val COMMON_PATTERN = """[^/]+"""

val ANONYMOUS_REGEX = """<>""".toRegex()
val UNPATTERNED_REGEX = """<\w+>""".toRegex()
val PATTERNED_REGEX = """<\w+:[^>]+>""".toRegex()

val ANY_VARIABLE = """<[^>]*>""".toRegex()
val LITERAL_STRING_REGEX = """([^>]+)(?=<|${'$'})""".toRegex()
val VARIABLE_ABLE_REGEX = """<(\w+)(:([^>]+))?>""".toRegex()

internal fun String.toPattern(): String {
    return this.replace(LITERAL_STRING_REGEX) { """\Q${it.value}\E""" }
            .replace(ANONYMOUS_REGEX, COMMON_PATTERN) // unspecified regex
            .replace(UNPATTERNED_REGEX, "($COMMON_PATTERN)") // variable with no specific regex
            .replace(VARIABLE_ABLE_REGEX, "($3)") // variable with specific regex
}