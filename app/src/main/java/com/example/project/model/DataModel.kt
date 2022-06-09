package com.example.project.model

data class DataModel(val coord:CoordModel, val weather:List<WeatherModel>, val base:String, val main:MainModel,
                     val visibility:Int, val wind:WindModel, val clouds:CloudModel, val dt:Long, val sys:SysModel,
                     val timezone:Long, val id:Long, val name:String, val cod:Int) {
}

