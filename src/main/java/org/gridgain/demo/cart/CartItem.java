/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gridgain.demo.cart;

import java.util.UUID;

/**
 * Item of the shopping cart.
 */
public class CartItem {
    /** Id of the cart this item belongs to. */
    private final UUID cartId;

    /** Id of the product from the shelf this item represents. */
    private final UUID productId;

    /** Category of the product. */
    private final String category;

    /** Name of the product. */
    private final String name;

    /** Count of pieces of this product. */
    private final int cnt;

    /** Timestamp when this product was updated for the last time. */
    private final long updateTime;

    /**
     * @param cartId Cart id.
     * @param productId Product id.
     * @param category Category.
     * @param name Name.
     * @param cnt Count.
     * @param updateTime Update time.
     */
    public CartItem(UUID cartId, UUID productId, String category, String name, int cnt, long updateTime) {
        this.cartId = cartId;
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.cnt = cnt;
        this.updateTime = updateTime;
    }

    /**
     * @return Id of the cart this item belongs to.
     */
    public UUID getCartId() {
        return cartId;
    }

    /**
     * @return Id of the product from the shelf this item represents.
     */
    public UUID getProductId() {
        return productId;
    }

    /**
     * @return Name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Category of the product.
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return Count of pieces of this product.
     */
    public int getCount() {
        return cnt;
    }

    /**
     * @return Timestamp when this product was updated for the last time.
     */
    public long getUpdateTime() {
        return updateTime;
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return "CartItem{" +
            "cartId=" + cartId +
            ", productId=" + productId +
            ", category='" + category + '\'' +
            ", name='" + name + '\'' +
            ", count=" + cnt +
            ", updateTime=" + updateTime +
            '}';
    }
}
