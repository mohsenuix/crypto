package crypto

import java.math.BigInteger
object Secp256k1 {
    val P = BigInteger("115792089237316195423570985008687907853269984665640564039457584007908834671663")
    val g = Point(
        BigInteger("55066263022277343669578718895168534326250603453777594175500187360389116729240"),
        BigInteger("32670510020758816978083085130507043184471273380659243275938904335757337482424")
    )
    val order = BigInteger("115792089237316195423570985008687907852837564279074904382605163141518161494337")
    private val half = BigInteger("2").inverse()
    private fun double(p: Point): Point {
        if (p == Point.O)
            return p
        val lambda = BigInteger("3") * p.x * p.x * p.y.inverse() * half
        var x = (lambda * lambda - BigInteger("2") * p.x) % P
        var y = (lambda * (p.x - x) - p.y ) % P
        if(x < BigInteger.ZERO)
            x += P
        if(y < BigInteger.ZERO)
            y += P
        return Point(x, y)
    }

    fun add(p1: Point, p2: Point): Point {
        if (p1 == Point.O)
            return p2
        if (p2 == Point.O)
            return p1
        if (p1 == p2)
            return double(p1)
        val deltaX = (p2.x - p1.x)
        val deltaY = (p2.y - p1.y)
        val lambda = deltaY * deltaX.inverse()
        var x = (lambda * lambda - p1.x - p2.x) % P
        var y = ((lambda * (p1.x - x)) - p1.y) % P
        if(x < BigInteger.ZERO)
            x += P
        if(y < BigInteger.ZERO)
            y += P
        return Point(x, y)
    }


    fun multiply(n: BigInteger, p: Point): Point {
        var temp = Point.O
        val bin = n.toString(2)
        for (element in bin) {
            temp = double(temp)
            if (element == '1')
                temp = add(temp, p)
        }
        return temp
    }

    fun multiply(n: Int, p: Point): Point {
        return multiply(BigInteger(n.toString()), p)
    }

}





