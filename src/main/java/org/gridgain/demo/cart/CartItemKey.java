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
 * Key of the shopping cart item.
 */
public class CartItemKey {
    /** Id of the cart this item belongs to. */
    private final UUID cartId;

    /** Id of the product from the shelf this item represents. */
    private final UUID itemId;

    /**
     * @param cartId Cart id.
     * @param itemId Item id.
     */
    public CartItemKey(UUID cartId, UUID itemId) {
        this.cartId = cartId;
        this.itemId = itemId;
    }

    /**
     * @return Id of the product from the shelf this item represents.
     */
    public UUID getItemId() {
        return itemId;
    }

    /**
     * @return Id of the cart this item belongs to.
     */
    public UUID getCartId() {
        return cartId;
    }

    @Override public String toString() {
        return "CartItemKey{" +
            "cartId=" + cartId +
            ", itemId=" + itemId +
            '}';
    }
}
