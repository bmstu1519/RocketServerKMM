package org.rocketserverkmm.project.platform

import android.content.Context
import com.liftric.kvault.KVault

class KVaultInstanceAndroid(context: Context) : KVaultProvider {
    override val kVault: KVault = KVault(context, NameSessionSettings)
}

object KVaultProviderFactory {
    private var instance: KVaultProvider? = null

    internal fun create(context: Context): KVaultProvider {
        return instance ?: synchronized(this) {
            instance ?: KVaultInstanceAndroid(context).also { instance = it }
        }
    }

    internal fun getKVaultInstance(): KVaultProvider? = instance
}

fun initializeKVault(context: Context) {
    KVaultProviderFactory.create(context)
    getKVaultInstance()
}

actual fun getKVaultInstance(): KVaultProvider = KVaultProviderFactory.getKVaultInstance()
    ?: throw IllegalStateException("KVault is not initialized")