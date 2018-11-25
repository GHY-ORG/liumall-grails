package com.ghy.liumall

class Tag {

    String name

    static hasMany = [products: Product]

    static constraints = {
    }

    static mapping = {id generator: 'identity'}

    static graphql = true
}
