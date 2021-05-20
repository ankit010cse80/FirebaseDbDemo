package com.example.userdailydata

class DateModel() {

    lateinit var date:String
    lateinit var steps:String

    constructor(date: String, steps: String) : this() {
        this.date=date
        this.steps=steps
    }
}