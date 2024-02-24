package crypto
import org.spongycastle.crypto.digests.RIPEMD160Digest
import java.security.MessageDigest
object AddressUtil {
    fun findAddress(publicKey :Point): String{
        return findAddress("04${publicKey.x.toString(16)}${publicKey.y.toString(16)}")
    }
    fun findAddress(publicKey: String, compressed: Boolean = false): String {
        val hexStr = if (compressed) {
            val lastTwoHex = publicKey.takeLast(2)
            val lastByte = lastTwoHex.toInt(16).toByte()
            val pref = if (lastByte.toInt() % 2 == 0) {
                "02"
            } else {
                "03"
            }
            pref + publicKey.substring(2, 66)
        } else {
            publicKey
        }
        val hexBytes = hexStr.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
        val keyHash = "00" + hash160(hexBytes).toHexString()

        val sha = MessageDigest.getInstance("SHA-256")
        val checksum = sha.digest(hexStringToByteArray(keyHash))
        sha.update(checksum)
        val checksumHex = sha.digest().toHexString().substring(0, 8)
        return Base58.encode(hexStringToByteArray(keyHash + checksumHex))
    }

    private fun hash160(input: ByteArray): ByteArray {
        val sha = MessageDigest.getInstance("SHA-256")
        val hash1 = sha.digest(input)
        val ripemd160 = RIPEMD160Digest()
        ripemd160.update(hash1, 0, hash1.size)
        val output = ByteArray(ripemd160.digestSize)
        ripemd160.doFinal(output, 0)
        return output
    }

    private fun hexStringToByteArray(hexString: String): ByteArray {
        val len = hexString.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(hexString[i], 16) shl 4) + Character.digit(hexString[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }

    private fun ByteArray.toHexString(): String {
        return joinToString("") { "%02x".format(it) }
    }
}
