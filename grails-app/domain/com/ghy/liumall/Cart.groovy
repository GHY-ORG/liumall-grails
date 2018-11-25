package com.ghy.liumall

import grails.gorm.DetachedCriteria
import org.codehaus.groovy.util.HashCodeHelper
import org.grails.gorm.graphql.entity.dsl.GraphQLMapping

class Cart implements Serializable{

    private static final long serialVersionUID = 1

    User user
    Product product

    @Override
    boolean equals(other) {
        if (other instanceof Cart) {
            other.userId == user?.id && other.productId == product?.id
        }
    }

    @Override
    int hashCode() {
        int hashCode = HashCodeHelper.initHash()
        if (user) {
            hashCode = HashCodeHelper.updateHash(hashCode, user.id)
        }
        if (product) {
            hashCode = HashCodeHelper.updateHash(hashCode, product.id)
        }
        hashCode
    }

    static boolean exists(long userId, long productId) {
        criteriaFor(userId, productId).count()
    }

    private static DetachedCriteria criteriaFor(long userId, long productId) {
        Cart.where {
            user == User.load(userId) &&
                    product == Product.load(productId)
        }
    }



    static constraints = {
        product validator: { Product p, Cart cart ->
            if (cart.user?.id) {
                Cart.withNewSession {
                    if (Cart.exists(cart.user.id, p.id)) {
                        return ['cart.exists']
                    }
                }
            }
        }
    }

    static mapping = {
        id composite: ['user', 'product']
        version false
    }

    /**
     * The use of lazy here is required because the
     * data fetcher provided needs access to the persistent
     * entity API which will not be available when the
     * class is initialized
     */
    static graphql = GraphQLMapping.lazy {
        operations.update.enabled false

        query('productsByUser', [Product]) {
            argument('user', Long)
            dataFetcher(new ProductsByUserDataFetcher())
        }

        mutation('revokeAllProducts', 'RevokeSuccess') {
            argument('user', Long)
            returns {
                field('success', Boolean)
            }
            dataFetcher(new RevokeAllProductsDataFetcher())
        }


    }
}
