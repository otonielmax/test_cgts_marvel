package com.example.testcgts.utils

import android.os.Build
import com.example.testcgts.utils.Constants.API_PRIVATE_KEY
import com.example.testcgts.utils.Constants.API_PUBLIC_KEY
import java.math.BigInteger
import java.security.MessageDigest
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

object Common {

    fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    fun generateTimestamp() : String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        } else {
            return "omarquez"
        }
    }

    fun generateHashAPI(ts: String) : String {
        return md5(ts + API_PRIVATE_KEY + API_PUBLIC_KEY)
    }

    fun getCurrentDateInt(): Int {
        return (Date().time / 1000).toInt()
    }
}