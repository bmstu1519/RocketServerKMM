package org.rocketserverkmm.project.data.repositories

import com.liftric.kvault.KVault
import org.rocketserverkmm.project.domain.repositories.KeyVaultRepository

class KeyVaultRepositoryImpl(private val kVault: KVault) : KeyVaultRepository {
    override fun saveToken(key: String, value: String): Boolean = kVault.set(key, value)
    override fun getToken(forKey: String): String? = kVault.string(forKey)
    override fun deleteToken(forKey: String): Boolean = kVault.deleteObject(forKey)
    override fun saveBoolean(forKey: String, value: Boolean): Boolean = kVault.set(forKey, value)
    override fun getBoolean(forKey: String): Boolean? = kVault.bool(forKey)
}