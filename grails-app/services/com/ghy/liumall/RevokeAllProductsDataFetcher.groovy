package com.ghy.liumall

import grails.compiler.GrailsCompileStatic
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment

@GrailsCompileStatic
class RevokeAllProductsDataFetcher implements DataFetcher {

    @Override
    Object get(DataFetchingEnvironment environment) {
        User buyer = User.load((Serializable)environment.getArgument('user'))
        int count = Cart.where {
            user == buyer
        }.deleteAll().intValue()

        [success: count > 0]
    }
}