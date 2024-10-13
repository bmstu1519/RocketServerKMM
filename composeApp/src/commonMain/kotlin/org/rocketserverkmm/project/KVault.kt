package org.rocketserverkmm.project

import com.liftric.kvault.KVault

interface KVaultProvider {
    val kVault: KVault
}

expect fun getKVaultInstance() : KVaultProvider

const val NameSessionSettings = "session-settings"
const val KEY_TOKEN = "TOKEN"
