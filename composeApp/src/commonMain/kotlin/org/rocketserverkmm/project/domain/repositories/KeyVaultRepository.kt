package org.rocketserverkmm.project.domain.repositories

interface KeyVaultRepository {
    fun saveToken(key: String, value: String): Boolean
    fun getToken(forKey: String): String?
    fun deleteToken(forKey: String): Boolean
}