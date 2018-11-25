package com.ghy.liumall

import graphql.schema.DataFetchingEnvironment
import groovy.transform.CompileStatic
import grails.compiler.GrailsCompileStatic
import org.grails.gorm.graphql.fetcher.impl.EntityDataFetcher

@CompileStatic
class ProductsByUserDataFetcher extends EntityDataFetcher<List<Product>> {

    ProductsByUserDataFetcher() {
        super(Product.gormPersistentEntity)
    }

    @GrailsCompileStatic
    @Override
    protected List executeQuery(DataFetchingEnvironment environment, Map queryArgs) {
        User buyer = User.load((Serializable) environment.getArgument('user'))
        def products = Cart.where { user == buyer }.property('product')
        Product.where {
            id in products
        }.list(queryArgs)
    }
}
