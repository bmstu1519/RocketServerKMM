package org.rocketserverkmm.project

import com.liftric.kvault.KVault

class KVaultInstanceIOS : KVaultProvider {
    override val kVault: KVault = KVault(NameSessionSettings)
}

actual fun getKVaultInstance() : KVaultProvider = KVaultInstanceIOS()