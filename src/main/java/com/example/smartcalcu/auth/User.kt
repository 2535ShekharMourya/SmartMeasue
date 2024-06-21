package com.example.smartcalcu.auth

class User {
    var name:String?=null
    var email:String?=null
    var uid:String?=null
   /* var height:String?=null
    var Weight:String?=null
    var dow:String?=null*/
    constructor(){}
    constructor(name:String?,email:String?, uid:String?){
        this.email=email
        this.name=name
        this.uid=uid
        /*this.Weight=Weight
        this.height=height
        this.dow=dow*/
    }
}