package com.example.venadostest

class Games {
    var local : Boolean = false
    var opponent : String = ""
    var opponent_image : String = ""
    var datetime : String = ""
    var league : String = ""
    var image : String = ""
    var home_score : Int = 0
    var away_score : Int = 0

    constructor(){

    }

    constructor(l: Boolean, o: String, oImg: String, date: String, le: String, img: String, hScore: Int, aScore: Int)
    {
        this.local = l
        this.opponent = o
        this.opponent_image = oImg
        this.datetime = date.substring(0,10)
        this.league = le
        this.image = img
        this.home_score = hScore
        this.away_score = aScore
    }

    fun add(l: Boolean, o: String, oImg: String, date: String, le: String, img: String, hScore: Int, aScore: Int)
    {
        this.local = l
        this.opponent = o
        this.opponent_image = oImg
        this.datetime = date
        this.league = le
        this.image = img
        this.home_score = hScore
        this.away_score = aScore
    }
}