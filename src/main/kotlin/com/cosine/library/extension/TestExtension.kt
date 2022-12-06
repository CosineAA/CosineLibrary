package com.cosine.library.extension

fun main() {

    val map = mapOf( "물고기" to 2.0 , "동물" to 2.0 , "인간" to 6.0 )

    //

    var fish = 0
    var human = 0
    var animal = 0
    for(i in 0 until 1000) {
        when(map.percent()) {
            "물고기"-> fish++
            "인간"-> human++
            "동물"->animal++
        }
    }

    println("물고기 : $fish")
    println("인간 : $human")
    println("동물 : $animal")
}