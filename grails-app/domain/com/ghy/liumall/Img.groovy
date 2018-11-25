package com.ghy.liumall

class Img {

    String name
    String path

    static belongsTo = [product: Product]

    static constraints = {
    }

    static mapping = {id generator: 'identity'}

    static graphql = true
}
