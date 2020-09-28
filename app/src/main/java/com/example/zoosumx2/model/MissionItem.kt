package com.example.zoosumx2.model

class MissionItem {

    var icons:Int ?= 0
    var title:String ?= null
    var context:String ?= null

    constructor(icons: Int?, title: String?, context: String?) {
        this.icons = icons
        this.title = title
        this.context = context
    }
}