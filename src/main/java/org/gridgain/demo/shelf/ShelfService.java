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

package org.gridgain.demo.shelf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

/**
 * Service to work with shelf.
 */
@Component
public class ShelfService {
    /** Shelf itself. */
    private static final Map<UUID, ShelfItem> SHELF;

    static {
        SHELF = Collections.unmodifiableMap(
                Stream.of(
                        new ShelfItem(UUID.fromString("6539a8a4-d75e-4ee1-ac61-e5359b332001"), "food", "Cat food"),
                        new ShelfItem(UUID.fromString("6539a8a4-d75e-4ee1-ac61-e5359b332002"), "food", "Dog food"),
                        new ShelfItem(UUID.fromString("6539a8a4-d75e-4ee1-ac61-e5359b332003"), "accessories", "Cat collar"),
                        new ShelfItem(UUID.fromString("6539a8a4-d75e-4ee1-ac61-e5359b332004"), "accessories", "Dog collar")
                ).collect(Collectors.toMap(ShelfItem::getId, Function.identity()))
        );
    }

    /**
     * Gets item by it's id.
     *
     * @param id Id.
     * @return Shelf item.
     */
    public ShelfItem itemById(UUID id) {
        return SHELF.get(id);
    }

    /**
     * Gets all item of this shelf.
     *
     * @return All items.
     */
    public List<ShelfItem> getAll() {
        return new ArrayList<>(SHELF.values());
    }
}
