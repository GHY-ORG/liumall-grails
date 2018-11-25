package com.ghy.liumall

class User {

    String open_id
    String session_key
    String union_id
    String school_num
    String phone_num
    String other_contact
    String password
    Boolean is_admin
    Boolean status

    static constraints = {
        school_num nullable: true, blank:true, unique: true
        phone_num nullable: true, blank:true
        other_contact nullable: true, blank:true
    }

    static hasMany = [
            sell: Product,
            buy: Product
    ]

    static mappedBy = [
            sell: 'seller',
            buy: 'buyer'
    ]

    static mapping = {
        table 'ghy_user'
        id generator: 'identity'
        sell cascade: 'all-delete-orphan'
    }

    static graphql = true
}
