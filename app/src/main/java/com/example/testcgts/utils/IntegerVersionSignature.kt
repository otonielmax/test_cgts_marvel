package com.example.testcgts.utils

import com.bumptech.glide.load.Key
import java.nio.ByteBuffer
import java.security.MessageDigest

object IntegerVersionSignature : Key {

    private var currentVersion = 0

    fun IntegerVersionSignature(currentVersion: Int) {
        this.currentVersion = currentVersion
    }

    override fun equals(o: Any?): Boolean {
        if (o is IntegerVersionSignature) {
            return currentVersion == o.currentVersion
        }
        return false
    }

    override fun hashCode(): Int {
        return currentVersion
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ByteBuffer.allocate(Integer.SIZE).putInt(currentVersion).array())
    }
}