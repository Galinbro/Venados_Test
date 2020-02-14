package com.example.venadostest

class Player {
    var name: String = "Esteban"
    var first_surname: String = "Torres"
    var second_surname: String = "Rivera"
    var birthday: String = "1999-06-27T00:00:00+00:00"
    var birth_place: String = "Ciudad Madero, Tamaulipas"
    var weight: Float = 65f
    var height: Float = 1.82f
    var position: String = "Delantero"
    var number: Int = 15
    var position_short: String = "DEL"
    var last_team: String = "Venados FC"
    var image: String = "https://venados.dacodes.mx/img/usr/6c0f2264ef654a3ca139c982a842144f.jpg"

    constructor(){

    }

    constructor(n: String, fn: String, sn: String, b: String, bp: String, w: Float, h: Float, p: String, num: Int, ps: String, lt: String, img: String){
        this.name = n
        this.first_surname = fn
        this.second_surname = sn
        this.birthday = b
        this.birth_place = bp
        this.weight = w
        this.height = h
        this.position = p
        this.number = num
        this.position_short = ps
        this.last_team = lt
        this.image = img
    }

}