package org.rocketserverkmm.project.di

import androidx.compose.runtime.compositionLocalOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.mp.KoinPlatform.getKoin

class ScopeManager {
    private val activeScopes = mutableSetOf<ScopeFlow>()

    fun getOrCreateScope(flow: ScopeFlow): Scope {
        return try {
            getScope(flow)
        } catch (e: Exception) {
            println("getOrCreateScope:$e")
            createScope(flow)
            getScope(flow)
        }
    }

    fun createScope(flow: ScopeFlow) {
        when (flow) {
            ScopeFlow.LAUNCH_FLOW -> {
                if (!isFlowActive(flow)) {
                    getKoin().getOrCreateScope(
                        scopeId = flow.getScopeId(),
                        qualifier = named(flow.getScopeName())
                    )
                    activeScopes.add(flow)
                }
            }

            ScopeFlow.FIRST_LOAD_INITIAL_DATA -> {
                if (!isFlowActive(flow)) {
                    getKoin().getOrCreateScope(
                        scopeId = flow.getScopeId(),
                        qualifier = named(flow.getScopeName())
                    )
                    activeScopes.add(flow)
                }
            }
        }
    }

    fun closeScope(flow: ScopeFlow) {
        when (flow) {
            ScopeFlow.LAUNCH_FLOW -> {
                if (isFlowActive(flow)) {
                    getKoin().getScope(flow.getScopeId()).close()
                    activeScopes.remove(flow)
                }
            }

            ScopeFlow.FIRST_LOAD_INITIAL_DATA -> {
                if (isFlowActive(flow)) {
                    getKoin().getScope(flow.getScopeId()).close()
                    activeScopes.remove(flow)
                }
            }
        }
    }

    fun getScope(flow: ScopeFlow): Scope {
        return getKoin().getScope(flow.getScopeId())
    }

    fun isFlowActive(flow: ScopeFlow): Boolean {
        return activeScopes.contains(flow)
    }

    fun closeAllScopes() {
        activeScopes.toList().forEach { flow ->
            closeScope(flow)
        }
    }
}

val LocalScopeManager = compositionLocalOf<ScopeManager> {
    error("ScopeManager not provided")
}

enum class ScopeFlow {
    LAUNCH_FLOW,
    FIRST_LOAD_INITIAL_DATA
}

fun ScopeFlow.getScopeId(): String = when (this) {
    ScopeFlow.LAUNCH_FLOW -> "LaunchListScope"
    ScopeFlow.FIRST_LOAD_INITIAL_DATA -> "FirstLoadDataScope"
}

fun ScopeFlow.getScopeName(): String = when (this) {
    ScopeFlow.LAUNCH_FLOW -> "LaunchListScope"
    ScopeFlow.FIRST_LOAD_INITIAL_DATA -> "FirstLoadDataScope"
}