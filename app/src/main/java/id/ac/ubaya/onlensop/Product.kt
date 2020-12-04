package id.ac.ubaya.onlensop

import java.io.Serializable

data class Product(
    var id: Int,
    var name: String,
    var desc: String,
    var price: Int,
    var image: String,
    var stock: Int,
    var category: Int
)