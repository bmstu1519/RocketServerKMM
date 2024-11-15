package org.rocketserverkmm.project.repositories

import com.liftric.kvault.KVault
import org.rocketserverkmm.project.domain.repositories.KeyVaultRepository

class KeyVaultRepositoryImpl(private val kVault: KVault) : KeyVaultRepository {
    override fun saveToken(key: String, value: String): Boolean = kVault.set(key, value)
    override fun getToken(forKey: String): String? = kVault.string(forKey)
    override fun deleteToken(forKey: String): Boolean = kVault.deleteObject(forKey)
}