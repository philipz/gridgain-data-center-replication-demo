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

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Shelf controller
 */
@RestController
@RequestMapping("/api/shelf")
public class ShelfController {
    /** Logger instance. */
    private static final Logger LOG = LoggerFactory.getLogger(ShelfController.class);

    /** Shelf service. */
    private final ShelfService shelfSvc;

    /**
     * @param shelfSvc Shelf service.
     */
    public ShelfController(ShelfService shelfSvc) {
        this.shelfSvc = shelfSvc;
    }

    /**
     * Gets all items of the shelf.
     */
    @GetMapping("/all")
    public List<ShelfItem> getAll() {
        LOG.info("Processing 'getAll()'");

        try {
            List<ShelfItem> res = shelfSvc.getAll();

            LOG.info("Done");

            return res;
        }
        catch (Exception e) {
            LOG.info("Failed: ", e);

            throw e;
        }
    }
}
