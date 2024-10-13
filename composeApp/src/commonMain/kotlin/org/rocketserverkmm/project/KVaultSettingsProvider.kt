package org.rocketserverkmm.project

import com.liftric.kvault.KVault

interface SettingsProvider {
    fun setToken(key: String, value: String): Boolean
    fun getToken(forKey: String): String?
    fun deleteToken(forKey: String): Boolean
}

class KVaultSettingsProvider(private val kVault: KVault): SettingsProvider {
    override fun setToken(key: String, value: String): Boolean = kVault.set(key, value)
    override fun getToken(forKey: String): String? = kVault.string(forKey)
    override fun deleteToken(forKey: String): Boolean = kVault.deleteObject(forKey)
}

object KVaultSettingsProviderSingleton {
    private val instance: KVaultSettingsProvider = KVaultSettingsProvider(getKVaultInstance().kVault)

    fun getInstance() = instance
}