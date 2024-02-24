package crypto
import crypto.Secp256k1.g

class Main{
    companion object{
        @JvmStatic
        fun main(args:Array<String>){
            val public = Point("3697c83a1498587c947d1ee7ae357aba00fdc46812e544f4e1513dd0e9f03d5b",
            "893f0679b22105eb092b903f6ba60c4c06cf5343d7d03a82f5229ecc82398e60")
            println(AddressUtil.findAddress(public))

        }
    }
}








