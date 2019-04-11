package com.example.prete.kotlinp

//model class
class Model {
    var Image: String? = null
    var Name : String? = null


    constructor():this("","") {

    }


    constructor(Image: String?, Name: String?) {
        this.Image = Image
        this.Name = Name
    }
}