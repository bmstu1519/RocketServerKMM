package org.rocketserverkmm.project.settings.local

import com.liftric.kvault.KVault
import org.rocketserverkmm.project.platform.getKVaultInstance

object KVaultClientProviderSingleton {
    private val instance: KVault = getKVaultInstance().kVault

    fun getInstance() = instance
}
