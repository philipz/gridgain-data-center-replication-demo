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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.configuration.ClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Cart dao. Used to update and retrieve cart items from storage.
 */
@Component
public class CartDao {
    /** Logger instance. */
    private static final Logger LOG = LoggerFactory.getLogger(CartController.class);

    /** Cart cache. */
    private final ClientCache<CartItemKey, CartItem> cartCache;

    /**
     * @param gridAddr Grid address.
     */
    public CartDao(
        @Value("${ignite.address}") String gridAddr
    ) {
        LOG.info("Going to connect to the ignite: " + gridAddr);

        cartCache = Ignition.startClient(
            new ClientConfiguration().setAddresses(gridAddr)
        ).cache("cart");
    }

    /**
     * Add new item.
     *
     * @param item Item that should be added.
     */
    public void addItem(CartItem item) {
        List<List<?>> res = cartCache.query(new SqlFieldsQuery(
            "UPDATE cart SET count = ?, updateTime = ? WHERE cartId = ? AND productId = ?"
        ).setArgs(item.getCount(), item.getUpdateTime(), item.getCartId(), item.getProductId())).getAll();

        if (isNullOrEmpty(res) || isNullOrEmpty(res.get(0)) || ((Long)res.get(0).get(0)) == 0)
            cartCache.query(new SqlFieldsQuery(
                "INSERT INTO cart (cartId, productId, category, name, count, updateTime, createTime)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)"
            ).setArgs(item.getCartId(), item.getProductId(), item.getCategory(), item.getName(),
                item.getCount(), item.getUpdateTime(), System.currentTimeMillis())).getAll();
    }

    /**
     * Gets all items of specified cart id.
     *
     * @param cartId Cart id.
     */
    @SuppressWarnings("UnusedAssignment")
    public List<CartItem> getItemsByCartId(UUID cartId) {
        List<List<?>> res = cartCache.query(new SqlFieldsQuery(
            "SELECT cartId, productId, category, name, count, updateTime" +
                " FROM cart WHERE cartId = ? ORDER BY createTime"
        ).setArgs(cartId)).getAll();

        if (res == null || res.isEmpty())
            return Collections.emptyList();

        List<CartItem> items = new ArrayList<>(res.size());
        for(List<?> row : res) {
            if (row == null || row.size() != 6)
                throw new RuntimeException("Got unexpected row [expected not null row with exactly 6 columns]: " + row);

            int ind = 0;
            items.add(new CartItem(
                (UUID)row.get(ind++), // cartId
                (UUID)row.get(ind++), // productId
                (String) row.get(ind++), // category
                (String) row.get(ind++), // name
                (Integer)row.get(ind++), // count
                (Long)row.get(ind++) // updateTime
            ));
        }

        return items;
    }

    /**
     * @param list List.
     */
    private static boolean isNullOrEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }
}
