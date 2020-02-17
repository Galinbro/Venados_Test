package com.example.venadostest

class Player {
    var name: String = ""
    var first_surname: String = ""
    var second_surname: String = ""
    var birthday: String = ""
    var birth_place: String = ""
    var weight: Float? = 0f
    var height: Float? = 0f
    var position: String? = ""
    var role: String? = ""
    var role_short: String? = ""
    var number: Int? = -1
    var position_short: String? = ""
    var last_team: String? = ""
    var image: String = ""

   constructor(){

   }

    constructor(name: String, first_surname: String, second_surname: String, birthday: String, birth_place: String, weight: Float, height: Float, position: String, number: Int, position_short: String, last_team: String, image: String){
        this.name = name
        this.first_surname = first_surname
        this.second_surname = second_surname
        this.birthday = birthday
        this.birth_place = birth_place
        this.weight = weight
        this.height = height
        this.position = position
        this.number = number
        this.position_short = position_short
        this.last_team = last_team
        this.image = image
    }

    constructor(n: String, fn: String, sn: String, b: String, bp: String, w: Float?, h: Float?, r: String, rs: String,img: String){
        this.name = n
        this.first_surname = fn
        this.second_surname = sn
        this.birthday = b
        this.birth_place = bp
        this.weight = w
        this.height = h
        this.role = r
        this.role_short = rs
        this.image = img
    }


    fun add(n: String, fn: String, sn: String, b: String, bp: String, w: Float, h: Float, p: String, num: Int, ps: String, lt: String, img: String){
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