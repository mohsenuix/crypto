package crypto

import crypto.Secp256k1.P
import java.math.BigInteger
class Point(val x:BigInteger, val y:BigInteger){
    constructor(x: String, y:String) : this(BigInteger(x.substring(2),16),
        BigInteger(y.substring(2),16))
    constructor(s:String) : this(BigInteger(s.substring(2,66)), BigInteger(s.substring(67)))

    companion object {
        val O = Point(BigInteger.ZERO, BigInteger.ZERO)
    }

    override fun equals(other: Any?): Boolean {
        val that: Point = other as Point
        if (this.x == that.x && this.y == that.y)
            return true
        return false
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
    override fun toString(): String {
        return "(0x${x.toString(16)}, 0x${y.toString(16)})"
    }

    operator fun unaryMinus() = Secp256k1.multiply(BigInteger("-1"), this)
    operator fun plus(g: Point) = Secp256k1.add(this, g)
    operator fun times(that:BigInteger) = Secp256k1.multiply(that, this)
    operator fun times(that:Int) = Secp256k1.multiply(BigInteger(that.toString()), this)
}
operator fun Int.times(g: Point) = Secp256k1.multiply(this, g)
operator fun BigInteger.times(g: Point) = Secp256k1.multiply(this, g)
fun BigInteger.inverse():BigInteger = this.modPow(BigInteger("-1"), P)

