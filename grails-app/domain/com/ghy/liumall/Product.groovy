package com.ghy.liumall

class Product {

    String name
    Float price
    String description
    Boolean status
    User seller
    User buyer

    static hasMany = [
            imgs: Img,
            tags: Tag
    ]

    static constraints = {
    }

    static mapping = {
        id generator: 'identity'
        imgs cascade: 'all-delete-orphan'
    }

    static graphql = true
}
