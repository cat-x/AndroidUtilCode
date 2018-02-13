package com.blankj.utilcode.util

import android.util.Base64
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.security.DigestInputStream
import java.security.InvalidKeyException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/02
 * desc  : 加密解密相关的工具类
</pre> *
 */
class EncryptUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        ///////////////////////////////////////////////////////////////////////////
        // 哈希加密相关
        ///////////////////////////////////////////////////////////////////////////

        /**
         * MD2 加密
         *
         * @param data 明文字符串
         * @return 16 进制密文
         */
        fun encryptMD2ToString(data: String): String? {
            return encryptMD2ToString(data.toByteArray())
        }

        /**
         * MD2 加密
         *
         * @param data 明文字节数组
         * @return 16 进制密文
         */
        fun encryptMD2ToString(data: ByteArray): String? {
            return bytes2HexString(encryptMD2(data))
        }

        /**
         * MD2 加密
         *
         * @param data 明文字节数组
         * @return 密文字节数组
         */
        fun encryptMD2(data: ByteArray): ByteArray? {
            return hashTemplate(data, "MD2")
        }

        /**
         * MD5 加密
         *
         * @param data 明文字符串
         * @return 16 进制密文
         */
        fun encryptMD5ToString(data: String): String? {
            return encryptMD5ToString(data.toByteArray())
        }

        /**
         * MD5 加密
         *
         * @param data 明文字符串
         * @param salt 盐
         * @return 16 进制加盐密文
         */
        fun encryptMD5ToString(data: String, salt: String): String? {
            return bytes2HexString(encryptMD5((data + salt).toByteArray()))
        }

        /**
         * MD5 加密
         *
         * @param data 明文字节数组
         * @return 16 进制密文
         */
        fun encryptMD5ToString(data: ByteArray): String? {
            return bytes2HexString(encryptMD5(data))
        }

        /**
         * MD5 加密
         *
         * @param data 明文字节数组
         * @param salt 盐字节数组
         * @return 16 进制加盐密文
         */
        fun encryptMD5ToString(data: ByteArray?, salt: ByteArray?): String? {
            if (data == null || salt == null) return null
            val dataSalt = ByteArray(data.size + salt.size)
            System.arraycopy(data, 0, dataSalt, 0, data.size)
            System.arraycopy(salt, 0, dataSalt, data.size, salt.size)
            return bytes2HexString(encryptMD5(dataSalt))
        }

        /**
         * MD5 加密
         *
         * @param data 明文字节数组
         * @return 密文字节数组
         */
        fun encryptMD5(data: ByteArray): ByteArray? {
            return hashTemplate(data, "MD5")
        }

        /**
         * MD5 加密文件
         *
         * @param filePath 文件路径
         * @return 文件的 16 进制密文
         */
        fun encryptMD5File2String(filePath: String): String? {
            val file = if (isSpace(filePath)) null else File(filePath)
            return encryptMD5File2String(file)
        }

        /**
         * MD5 加密文件
         *
         * @param filePath 文件路径
         * @return 文件的 MD5 校验码
         */
        fun encryptMD5File(filePath: String): ByteArray? {
            val file = if (isSpace(filePath)) null else File(filePath)
            return encryptMD5File(file)
        }

        /**
         * MD5 加密文件
         *
         * @param file 文件
         * @return 文件的 16 进制密文
         */
        fun encryptMD5File2String(file: File?): String? {
            return bytes2HexString(encryptMD5File(file))
        }

        /**
         * MD5 加密文件
         *
         * @param file 文件
         * @return 文件的 MD5 校验码
         */
        fun encryptMD5File(file: File?): ByteArray? {
            if (file == null) return null
            var fis: FileInputStream? = null
            val digestInputStream: DigestInputStream
            try {
                fis = FileInputStream(file)
                var md = MessageDigest.getInstance("MD5")
                digestInputStream = DigestInputStream(fis, md)
                val buffer = ByteArray(256 * 1024)
                while (true) {
                    if (digestInputStream.read(buffer) <= 0) break
                }
                md = digestInputStream.messageDigest
                return md.digest()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
                return null
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            } finally {
                CloseUtils.closeIO(fis)
            }
        }

        /**
         * SHA1 加密
         *
         * @param data 明文字符串
         * @return 16 进制密文
         */
        fun encryptSHA1ToString(data: String): String? {
            return encryptSHA1ToString(data.toByteArray())
        }

        /**
         * SHA1 加密
         *
         * @param data 明文字节数组
         * @return 16 进制密文
         */
        fun encryptSHA1ToString(data: ByteArray): String? {
            return bytes2HexString(encryptSHA1(data))
        }

        /**
         * SHA1 加密
         *
         * @param data 明文字节数组
         * @return 密文字节数组
         */
        fun encryptSHA1(data: ByteArray): ByteArray? {
            return hashTemplate(data, "SHA1")
        }

        /**
         * SHA224 加密
         *
         * @param data 明文字符串
         * @return 16 进制密文
         */
        fun encryptSHA224ToString(data: String): String? {
            return encryptSHA224ToString(data.toByteArray())
        }

        /**
         * SHA224 加密
         *
         * @param data 明文字节数组
         * @return 16 进制密文
         */
        fun encryptSHA224ToString(data: ByteArray): String? {
            return bytes2HexString(encryptSHA224(data))
        }

        /**
         * SHA224 加密
         *
         * @param data 明文字节数组
         * @return 密文字节数组
         */
        fun encryptSHA224(data: ByteArray): ByteArray? {
            return hashTemplate(data, "SHA224")
        }

        /**
         * SHA256 加密
         *
         * @param data 明文字符串
         * @return 16 进制密文
         */
        fun encryptSHA256ToString(data: String): String? {
            return encryptSHA256ToString(data.toByteArray())
        }

        /**
         * SHA256 加密
         *
         * @param data 明文字节数组
         * @return 16 进制密文
         */
        fun encryptSHA256ToString(data: ByteArray): String? {
            return bytes2HexString(encryptSHA256(data))
        }

        /**
         * SHA256 加密
         *
         * @param data 明文字节数组
         * @return 密文字节数组
         */
        fun encryptSHA256(data: ByteArray): ByteArray? {
            return hashTemplate(data, "SHA256")
        }

        /**
         * SHA384 加密
         *
         * @param data 明文字符串
         * @return 16 进制密文
         */
        fun encryptSHA384ToString(data: String): String? {
            return encryptSHA384ToString(data.toByteArray())
        }

        /**
         * SHA384 加密
         *
         * @param data 明文字节数组
         * @return 16 进制密文
         */
        fun encryptSHA384ToString(data: ByteArray): String? {
            return bytes2HexString(encryptSHA384(data))
        }

        /**
         * SHA384 加密
         *
         * @param data 明文字节数组
         * @return 密文字节数组
         */
        fun encryptSHA384(data: ByteArray): ByteArray? {
            return hashTemplate(data, "SHA384")
        }

        /**
         * SHA512 加密
         *
         * @param data 明文字符串
         * @return 16 进制密文
         */
        fun encryptSHA512ToString(data: String): String? {
            return encryptSHA512ToString(data.toByteArray())
        }

        /**
         * SHA512 加密
         *
         * @param data 明文字节数组
         * @return 16 进制密文
         */
        fun encryptSHA512ToString(data: ByteArray): String? {
            return bytes2HexString(encryptSHA512(data))
        }

        /**
         * SHA512 加密
         *
         * @param data 明文字节数组
         * @return 密文字节数组
         */
        fun encryptSHA512(data: ByteArray): ByteArray? {
            return hashTemplate(data, "SHA512")
        }

        /**
         * hash 加密模板
         *
         * @param data      数据
         * @param algorithm 加密算法
         * @return 密文字节数组
         */
        private fun hashTemplate(data: ByteArray?, algorithm: String): ByteArray? {
            if (data == null || data.size <= 0) return null
            try {
                val md = MessageDigest.getInstance(algorithm)
                md.update(data)
                return md.digest()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
                return null
            }

        }

        /**
         * HmacMD5 加密
         *
         * @param data 明文字符串
         * @param key  秘钥
         * @return 16 进制密文
         */
        fun encryptHmacMD5ToString(data: String, key: String): String? {
            return encryptHmacMD5ToString(data.toByteArray(), key.toByteArray())
        }

        /**
         * HmacMD5 加密
         *
         * @param data 明文字节数组
         * @param key  秘钥
         * @return 16 进制密文
         */
        fun encryptHmacMD5ToString(data: ByteArray, key: ByteArray): String? {
            return bytes2HexString(encryptHmacMD5(data, key))
        }

        /**
         * HmacMD5 加密
         *
         * @param data 明文字节数组
         * @param key  秘钥
         * @return 密文字节数组
         */
        fun encryptHmacMD5(data: ByteArray, key: ByteArray): ByteArray? {
            return hmacTemplate(data, key, "HmacMD5")
        }

        /**
         * HmacSHA1 加密
         *
         * @param data 明文字符串
         * @param key  秘钥
         * @return 16 进制密文
         */
        fun encryptHmacSHA1ToString(data: String, key: String): String? {
            return encryptHmacSHA1ToString(data.toByteArray(), key.toByteArray())
        }

        /**
         * HmacSHA1 加密
         *
         * @param data 明文字节数组
         * @param key  秘钥
         * @return 16 进制密文
         */
        fun encryptHmacSHA1ToString(data: ByteArray, key: ByteArray): String? {
            return bytes2HexString(encryptHmacSHA1(data, key))
        }

        /**
         * HmacSHA1 加密
         *
         * @param data 明文字节数组
         * @param key  秘钥
         * @return 密文字节数组
         */
        fun encryptHmacSHA1(data: ByteArray, key: ByteArray): ByteArray? {
            return hmacTemplate(data, key, "HmacSHA1")
        }

        /**
         * HmacSHA224 加密
         *
         * @param data 明文字符串
         * @param key  秘钥
         * @return 16 进制密文
         */
        fun encryptHmacSHA224ToString(data: String, key: String): String? {
            return encryptHmacSHA224ToString(data.toByteArray(), key.toByteArray())
        }

        /**
         * HmacSHA224 加密
         *
         * @param data 明文字节数组
         * @param key  秘钥
         * @return 16 进制密文
         */
        fun encryptHmacSHA224ToString(data: ByteArray, key: ByteArray): String? {
            return bytes2HexString(encryptHmacSHA224(data, key))
        }

        /**
         * HmacSHA224 加密
         *
         * @param data 明文字节数组
         * @param key  秘钥
         * @return 密文字节数组
         */
        fun encryptHmacSHA224(data: ByteArray, key: ByteArray): ByteArray? {
            return hmacTemplate(data, key, "HmacSHA224")
        }

        /**
         * HmacSHA256 加密
         *
         * @param data 明文字符串
         * @param key  秘钥
         * @return 16 进制密文
         */
        fun encryptHmacSHA256ToString(data: String, key: String): String? {
            return encryptHmacSHA256ToString(data.toByteArray(), key.toByteArray())
        }

        /**
         * HmacSHA256 加密
         *
         * @param data 明文字节数组
         * @param key  秘钥
         * @return 16 进制密文
         */
        fun encryptHmacSHA256ToString(data: ByteArray, key: ByteArray): String? {
            return bytes2HexString(encryptHmacSHA256(data, key))
        }

        /**
         * HmacSHA256 加密
         *
         * @param data 明文字节数组
         * @param key  秘钥
         * @return 密文字节数组
         */
        fun encryptHmacSHA256(data: ByteArray, key: ByteArray): ByteArray? {
            return hmacTemplate(data, key, "HmacSHA256")
        }

        /**
         * HmacSHA384 加密
         *
         * @param data 明文字符串
         * @param key  秘钥
         * @return 16 进制密文
         */
        fun encryptHmacSHA384ToString(data: String, key: String): String? {
            return encryptHmacSHA384ToString(data.toByteArray(), key.toByteArray())
        }

        /**
         * HmacSHA384 加密
         *
         * @param data 明文字节数组
         * @param key  秘钥
         * @return 16 进制密文
         */
        fun encryptHmacSHA384ToString(data: ByteArray, key: ByteArray): String? {
            return bytes2HexString(encryptHmacSHA384(data, key))
        }

        /**
         * HmacSHA384 加密
         *
         * @param data 明文字节数组
         * @param key  秘钥
         * @return 密文字节数组
         */
        fun encryptHmacSHA384(data: ByteArray, key: ByteArray): ByteArray? {
            return hmacTemplate(data, key, "HmacSHA384")
        }

        /**
         * HmacSHA512 加密
         *
         * @param data 明文字符串
         * @param key  秘钥
         * @return 16 进制密文
         */
        fun encryptHmacSHA512ToString(data: String, key: String): String? {
            return encryptHmacSHA512ToString(data.toByteArray(), key.toByteArray())
        }

        /**
         * HmacSHA512 加密
         *
         * @param data 明文字节数组
         * @param key  秘钥
         * @return 16 进制密文
         */
        fun encryptHmacSHA512ToString(data: ByteArray, key: ByteArray): String? {
            return bytes2HexString(encryptHmacSHA512(data, key))
        }

        /**
         * HmacSHA512 加密
         *
         * @param data 明文字节数组
         * @param key  秘钥
         * @return 密文字节数组
         */
        fun encryptHmacSHA512(data: ByteArray, key: ByteArray): ByteArray? {
            return hmacTemplate(data, key, "HmacSHA512")
        }

        /**
         * Hmac 加密模板
         *
         * @param data      数据
         * @param key       秘钥
         * @param algorithm 加密算法
         * @return 密文字节数组
         */
        private fun hmacTemplate(data: ByteArray?,
                                 key: ByteArray?,
                                 algorithm: String): ByteArray? {
            if (data == null || data.size == 0 || key == null || key.size == 0) return null
            try {
                val secretKey = SecretKeySpec(key, algorithm)
                val mac = Mac.getInstance(algorithm)
                mac.init(secretKey)
                return mac.doFinal(data)
            } catch (e: InvalidKeyException) {
                e.printStackTrace()
                return null
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
                return null
            }

        }

        ///////////////////////////////////////////////////////////////////////////
        // DES 加密相关
        ///////////////////////////////////////////////////////////////////////////

        /**
         * DES 转变
         *
         * 法算法名称/加密模式/填充方式
         *
         * 加密模式有：电子密码本模式 ECB、加密块链模式 CBC、加密反馈模式 CFB、输出反馈模式 OFB
         *
         * 填充方式有：NoPadding、ZerosPadding、PKCS5Padding
         */
        private val DES_Algorithm = "DES"

        /**
         * DES 加密后转为 Base64 编码
         *
         * @param data           明文
         * @param key            8 字节秘钥
         * @param transformation 转变
         * @param iv             初始化向量
         * @return Base64 密文
         */
        fun encryptDES2Base64(data: ByteArray,
                              key: ByteArray,
                              transformation: String,
                              iv: ByteArray): ByteArray {
            return base64Encode(encryptDES(data, key, transformation, iv))
        }

        /**
         * DES 加密后转为 16 进制
         *
         * @param data           明文
         * @param key            8 字节秘钥
         * @param transformation 转变
         * @param iv             初始化向量
         * @return 16 进制密文
         */
        fun encryptDES2HexString(data: ByteArray,
                                 key: ByteArray,
                                 transformation: String,
                                 iv: ByteArray): String? {
            return bytes2HexString(encryptDES(data, key, transformation, iv))
        }

        /**
         * DES 加密
         *
         * @param data           明文
         * @param key            8 字节秘钥
         * @param transformation 转变
         * @param iv             初始化向量
         * @return 密文
         */
        fun encryptDES(data: ByteArray,
                       key: ByteArray,
                       transformation: String,
                       iv: ByteArray): ByteArray? {
            return desTemplate(data, key, DES_Algorithm, transformation, iv, true)
        }

        /**
         * DES 解密 Base64 编码密文
         *
         * @param data           Base64 编码密文
         * @param key            8 字节秘钥
         * @param transformation 转变
         * @param iv             初始化向量
         * @return 明文
         */
        fun decryptBase64DES(data: ByteArray,
                             key: ByteArray,
                             transformation: String,
                             iv: ByteArray): ByteArray? {
            return decryptDES(base64Decode(data), key, transformation, iv)
        }

        /**
         * DES 解密 16 进制密文
         *
         * @param data           16 进制密文
         * @param key            8 字节秘钥
         * @param transformation 转变
         * @param iv             初始化向量
         * @return 明文
         */
        fun decryptHexStringDES(data: String,
                                key: ByteArray,
                                transformation: String,
                                iv: ByteArray): ByteArray? {
            return decryptDES(hexString2Bytes(data), key, transformation, iv)
        }

        /**
         * DES 解密
         *
         * @param data           密文
         * @param key            8 字节秘钥
         * @param transformation 转变
         * @param iv             初始化向量
         * @return 明文
         */
        fun decryptDES(data: ByteArray?,
                       key: ByteArray,
                       transformation: String,
                       iv: ByteArray): ByteArray? {
            return desTemplate(data, key, DES_Algorithm, transformation, iv, false)
        }

        ///////////////////////////////////////////////////////////////////////////
        // 3DES 加密相关
        ///////////////////////////////////////////////////////////////////////////

        /**
         * 3DES 转变
         *
         * 法算法名称/加密模式/填充方式
         *
         * 加密模式有：电子密码本模式 ECB、加密块链模式 CBC、加密反馈模式 CFB、输出反馈模式 OFB
         *
         * 填充方式有：NoPadding、ZerosPadding、PKCS5Padding
         */
        private val TripleDES_Algorithm = "DESede"


        /**
         * 3DES 加密后转为 Base64 编码
         *
         * @param data           明文
         * @param key            24 字节秘钥
         * @param transformation 转变
         * @param iv             初始化向量
         * @return Base64 密文
         */
        fun encrypt3DES2Base64(data: ByteArray,
                               key: ByteArray,
                               transformation: String,
                               iv: ByteArray): ByteArray {
            return base64Encode(encrypt3DES(data, key, transformation, iv))
        }

        /**
         * 3DES 加密后转为 16 进制
         *
         * @param data           明文
         * @param key            24 字节秘钥
         * @param transformation 转变
         * @param iv             初始化向量
         * @return 16 进制密文
         */
        fun encrypt3DES2HexString(data: ByteArray,
                                  key: ByteArray,
                                  transformation: String,
                                  iv: ByteArray): String? {
            return bytes2HexString(encrypt3DES(data, key, transformation, iv))
        }

        /**
         * 3DES 加密
         *
         * @param data           明文
         * @param key            24 字节密钥
         * @param transformation 转变
         * @param iv             初始化向量
         * @return 密文
         */
        fun encrypt3DES(data: ByteArray,
                        key: ByteArray,
                        transformation: String,
                        iv: ByteArray): ByteArray? {
            return desTemplate(data, key, TripleDES_Algorithm, transformation, iv, true)
        }

        /**
         * 3DES 解密 Base64 编码密文
         *
         * @param data           Base64 编码密文
         * @param key            24 字节秘钥
         * @param transformation 转变
         * @param iv             初始化向量
         * @return 明文
         */
        fun decryptBase64_3DES(data: ByteArray,
                               key: ByteArray,
                               transformation: String,
                               iv: ByteArray): ByteArray? {
            return decrypt3DES(base64Decode(data), key, transformation, iv)
        }

        /**
         * 3DES 解密 16 进制密文
         *
         * @param data           16 进制密文
         * @param key            24 字节秘钥
         * @param transformation 转变
         * @param iv             初始化向量
         * @return 明文
         */
        fun decryptHexString3DES(data: String,
                                 key: ByteArray,
                                 transformation: String,
                                 iv: ByteArray): ByteArray? {
            return decrypt3DES(hexString2Bytes(data), key, transformation, iv)
        }

        /**
         * 3DES 解密
         *
         * @param data           密文
         * @param key            24 字节密钥
         * @param transformation 转变
         * @param iv             初始化向量
         * @return 明文
         */
        fun decrypt3DES(data: ByteArray?,
                        key: ByteArray,
                        transformation: String,
                        iv: ByteArray): ByteArray? {
            return desTemplate(data, key, TripleDES_Algorithm, transformation, iv, false)
        }

        ///////////////////////////////////////////////////////////////////////////
        // AES 加密相关
        ///////////////////////////////////////////////////////////////////////////

        /**
         * AES 转变
         *
         * 法算法名称/加密模式/填充方式
         *
         * 加密模式有：电子密码本模式 ECB、加密块链模式 CBC、加密反馈模式 CFB、输出反馈模式 OFB
         *
         * 填充方式有：NoPadding、ZerosPadding、PKCS5Padding
         */
        private val AES_Algorithm = "AES"


        /**
         * AES 加密后转为 Base64 编码
         *
         * @param data           明文
         * @param key            16、24、32 字节秘钥
         * @param transformation 转变
         * @param iv             初始化向量
         * @return Base64 密文
         */
        fun encryptAES2Base64(data: ByteArray,
                              key: ByteArray,
                              transformation: String,
                              iv: ByteArray): ByteArray {
            return base64Encode(encryptAES(data, key, transformation, iv))
        }

        /**
         * AES 加密后转为 16 进制
         *
         * @param data           明文
         * @param key            16、24、32 字节秘钥
         * @param transformation 转变
         * @param iv             初始化向量
         * @return 16 进制密文
         */
        fun encryptAES2HexString(data: ByteArray,
                                 key: ByteArray,
                                 transformation: String,
                                 iv: ByteArray): String? {
            return bytes2HexString(encryptAES(data, key, transformation, iv))
        }

        /**
         * AES 加密
         *
         * @param data           明文
         * @param key            16、24、32 字节秘钥
         * @param transformation 转变
         * @param iv             初始化向量
         * @return 密文
         */
        fun encryptAES(data: ByteArray,
                       key: ByteArray,
                       transformation: String,
                       iv: ByteArray): ByteArray? {
            return desTemplate(data, key, AES_Algorithm, transformation, iv, true)
        }

        /**
         * AES 解密 Base64 编码密文
         *
         * @param data           Base64 编码密文
         * @param key            16、24、32 字节秘钥
         * @param transformation 转变
         * @param iv             初始化向量
         * @return 明文
         */
        fun decryptBase64AES(data: ByteArray,
                             key: ByteArray,
                             transformation: String,
                             iv: ByteArray): ByteArray? {
            return decryptAES(base64Decode(data), key, transformation, iv)
        }

        /**
         * AES 解密 16 进制密文
         *
         * @param data           16 进制密文
         * @param key            16、24、32 字节秘钥
         * @param transformation 转变
         * @param iv             初始化向量
         * @return 明文
         */
        fun decryptHexStringAES(data: String,
                                key: ByteArray,
                                transformation: String,
                                iv: ByteArray): ByteArray? {
            return decryptAES(hexString2Bytes(data), key, transformation, iv)
        }

        /**
         * AES 解密
         *
         * @param data           密文
         * @param key            16、24、32 字节秘钥
         * @param transformation 转变
         * @param iv             初始化向量
         * @return 明文
         */
        fun decryptAES(data: ByteArray?,
                       key: ByteArray,
                       transformation: String,
                       iv: ByteArray): ByteArray? {
            return desTemplate(data, key, AES_Algorithm, transformation, iv, false)
        }

        /**
         * DES 加密模板
         *
         * @param data           数据
         * @param key            秘钥
         * @param algorithm      加密算法
         * @param transformation 转变
         * @param isEncrypt      `true`: 加密 `false`: 解密
         * @return 密文或者明文，适用于 DES，3DES，AES
         */
        private fun desTemplate(data: ByteArray?,
                                key: ByteArray?,
                                algorithm: String,
                                transformation: String,
                                iv: ByteArray?,
                                isEncrypt: Boolean): ByteArray? {
            if (data == null || data.size == 0 || key == null || key.size == 0) return null
            try {
                val keySpec = SecretKeySpec(key, algorithm)
                val cipher = Cipher.getInstance(transformation)
                if (iv == null || iv.size == 0) {
                    cipher.init(if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, keySpec)
                } else {
                    val params = IvParameterSpec(iv)
                    cipher.init(if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, keySpec, params)
                }
                return cipher.doFinal(data)
            } catch (e: Throwable) {
                e.printStackTrace()
                return null
            }

        }

        private val hexDigits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

        private fun bytes2HexString(bytes: ByteArray?): String? {
            if (bytes == null) return null
            val len = bytes.size
            if (len <= 0) return null
            val ret = CharArray(len shl 1)
            var i = 0
            var j = 0
            while (i < len) {
                ret[j++] = hexDigits[bytes[i].toInt().ushr(4) and 0x0f]
                ret[j++] = hexDigits[bytes[i].toInt() and 0x0f]
                i++
            }
            return String(ret)
        }

        private fun hexString2Bytes(hexString: String): ByteArray? {
            var hexString = hexString
            if (isSpace(hexString)) return null
            var len = hexString.length
            if (len % 2 != 0) {
                hexString = "0" + hexString
                len = len + 1
            }
            val hexBytes = hexString.toUpperCase().toCharArray()
            val ret = ByteArray(len shr 1)
            var i = 0
            while (i < len) {
                ret[i shr 1] = (hex2Dec(hexBytes[i]) shl 4 or hex2Dec(hexBytes[i + 1])).toByte()
                i += 2
            }
            return ret
        }

        private fun hex2Dec(hexChar: Char): Int {
            return if (hexChar >= '0' && hexChar <= '9') {
                hexChar - '0'
            } else if (hexChar >= 'A' && hexChar <= 'F') {
                hexChar - 'A' + 10
            } else {
                throw IllegalArgumentException()
            }
        }

        private fun base64Encode(input: ByteArray?): ByteArray {
            return Base64.encode(input, Base64.NO_WRAP)
        }

        private fun base64Decode(input: ByteArray): ByteArray {
            return Base64.decode(input, Base64.NO_WRAP)
        }

        private fun isSpace(s: String?): Boolean {
            if (s == null) return true
            var i = 0
            val len = s.length
            while (i < len) {
                if (!Character.isWhitespace(s[i])) {
                    return false
                }
                ++i
            }
            return true
        }
    }
}
