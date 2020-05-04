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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.gridgain.demo.shelf.ShelfItem;
import org.gridgain.demo.shelf.ShelfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Cart controller.
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {
    /** Logger instance. */
    private static final Logger LOG = LoggerFactory.getLogger(CartController.class);

    /** Demo cart id. */
    private static final UUID DEMO_CART_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    /** Shelf service. */
    private final ShelfService shelfSvc;

    /** Cart dao. */
    private final CartDao cartDao;

    /**
     * @param shelfSvc Shelf service.
     * @param cartDao Cart dao.
     */
    public CartController(
        ShelfService shelfSvc,
        CartDao cartDao
    ) {
        this.shelfSvc = shelfSvc;
        this.cartDao = cartDao;
    }

    /**
     * Gets cart items.
     *
     * @return All {@link CartItem items} of the demo cart.
     */
    @GetMapping("/items")
    public List<CartItem> getCartItems() {
        LOG.info("Processing 'getCartItems()'");

        try {
            List<CartItem> res = cartDao.getItemsByCartId(DEMO_CART_ID);

            LOG.info("Done");

            return res;
        }
        catch (Exception e) {
            LOG.info("Failed: ", e);

            throw e;
        }
    }

    /**
     * Add new items to the demo cart.
     *
     * @param req Request to add new item.
     * @return All {@link CartItem items} of the demo cart.
     */
    @PostMapping("/add")
    public List<CartItem> addItem(@RequestBody AddItemRequest req) {
        LOG.info("Processing 'addItem()' with params: " + req);

        try {
            UUID shelfItemId = req.getShelfItemId();

            ShelfItem shelfItem = shelfSvc.itemById(shelfItemId);

            if (shelfItem == null)
                throw new IllegalArgumentException("There is no shelf item with id=" + shelfItemId);

            Optional<CartItem> oldItem = cartDao.getItemsByCartId(DEMO_CART_ID).stream()
                .filter(item -> item.getProductId().equals(shelfItemId))
                .findFirst();

            int newCnt = oldItem.map(item -> item.getCount() + 1).orElse(1);

            cartDao.addItem(
                createCartItem(shelfItem, newCnt, System.currentTimeMillis())
            );

            List<CartItem> res = cartDao.getItemsByCartId(DEMO_CART_ID);

            LOG.info("Done");

            return res;
        }
        catch (Exception e) {
            LOG.info("Failed: ", e);

            throw e;
        }

    }

    /**
     * @param shelfItem Shelf item.
     * @param cnt Count.
     * @param updateTime Update time.
     */
    private static CartItem createCartItem(ShelfItem shelfItem, int cnt, long updateTime) {
        return new CartItem(
            DEMO_CART_ID, shelfItem.getId(), shelfItem.getCategory(), shelfItem.getName(), cnt, updateTime
        );
    }

    /**
     * Request to add new item to the cart.
     */
    public static class AddItemRequest {
        /** Id of the item from the shelf. */
        private UUID shelfItemId;

        /**
         * Id of the item from the shelf.
         *
         * @return Id of the item from the shelf.
         */
        public UUID getShelfItemId() {
            return shelfItemId;
        }

        /** {@inheritDoc} */
        @Override public String toString() {
            return "AddItemRequest{" +
                "shelfItemId=" + shelfItemId +
                '}';
        }
    }
}
