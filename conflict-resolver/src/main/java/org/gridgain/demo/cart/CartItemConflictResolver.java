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

import org.gridgain.grid.cache.conflict.CacheConflictContext;
import org.gridgain.grid.cache.conflict.CacheConflictResolver;

/**
 * Conflict resolver that prefer entry which were updated later.
 */
public class CartItemConflictResolver implements CacheConflictResolver<Object, Object> {
    /** {@inheritDoc} */
    @SuppressWarnings("ConstantConditions")
    @Override public void resolve(CacheConflictContext ctx) {
        final String updateTimeFieldName = "updateTime";

        if (!ctx.newEntry().isValueBinary() || !ctx.oldEntry().isValueBinary()
            || !ctx.newEntry().valueBinary().hasField(updateTimeFieldName)
            || !ctx.oldEntry().valueBinary().hasField(updateTimeFieldName)
        ) {
            ctx.useNew();

            return;
        }

        long newEntryUpdateTime = ctx.newEntry().valueBinary().field(updateTimeFieldName);
        long oldEntryUpdateTime = ctx.oldEntry().valueBinary().field(updateTimeFieldName);

        if (newEntryUpdateTime > oldEntryUpdateTime)
            ctx.useNew();
        else
            ctx.useOld();
    }
}
