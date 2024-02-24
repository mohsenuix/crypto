package crypto
import crypto.Secp256k1.g
import java.math.BigInteger

class Main{
    companion object{
        @JvmStatic
        fun main(args:Array<String>){
            val privateKey = 3325
            var publicKey = privateKey * g
            println(publicKey)
            println(AddressUtil.findAddress(publicKey))
            val privateKey2 = BigInteger("1215615654546546465465465465434")
            publicKey = privateKey2 * g
            println(publicKey)
            println(AddressUtil.findAddress(publicKey))

        }
    }
}








