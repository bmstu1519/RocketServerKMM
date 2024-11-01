package org.rocketserverkmm.project.data.local

import com.liftric.kvault.KVault
import org.rocketserverkmm.project.getKVaultInstance

object KVaultClientProviderSingleton {
    private val instance: KVault = getKVaultInstance().kVault

    fun getInstance() = instance
}
