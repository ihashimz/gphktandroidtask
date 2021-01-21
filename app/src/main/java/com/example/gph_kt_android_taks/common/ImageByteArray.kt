package com.example.gph_kt_android_taks.common

import java.io.File
import java.io.FileInputStream
import java.io.IOException

object ImageByteArray {
    @Throws(IOException::class)
    fun fullyReadFileToBytes(f: File ? ): ByteArray? {
        if (f != null) {

            val size = f!!.length().toInt()
            val bytes = ByteArray(size)
            val tmpBuff = ByteArray(size)
            val fis = FileInputStream(f)
            try {
                var read: Int = fis.read(bytes, 0, size)
                if (read < size) {
                    var remain = size - read
                    while (remain > 0) {
                        read = fis.read(tmpBuff, 0, remain)
                        System.arraycopy(tmpBuff, 0, bytes, size - remain, read)
                        remain -= read
                    }
                }
            } catch (e: IOException) {
                throw e
            } finally {
                fis.close()
            }
            return bytes
        } else return null
    }

}