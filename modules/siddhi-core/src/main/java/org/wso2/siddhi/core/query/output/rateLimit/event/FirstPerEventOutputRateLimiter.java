/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org)
 * All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.siddhi.core.query.output.rateLimit.event;

import org.wso2.siddhi.core.event.ComplexEvent;
import org.wso2.siddhi.core.event.ComplexEventChunk;
import org.wso2.siddhi.core.query.output.rateLimit.OutputRateLimiter;

public class FirstPerEventOutputRateLimiter extends OutputRateLimiter {
    private final Integer value;
    private String id;
    private volatile int counter = 0;

    public FirstPerEventOutputRateLimiter(String id, Integer value) {
        this.value = value;
        this.id = id;
    }

    @Override
    public OutputRateLimiter clone(String key) {
        return new FirstPerEventOutputRateLimiter(id + key, value);
    }

    @Override
    public void process(ComplexEventChunk complexEventChunk) {

    }

    @Override
    public void add(ComplexEvent complexEvent) {
        if (counter == 0) {
            ComplexEventChunk<ComplexEvent> firstPerEventChunk = new ComplexEventChunk<ComplexEvent>();
            firstPerEventChunk.add(complexEvent);
            sendToCallBacks(firstPerEventChunk);
        }
        if (++counter == value) {
            counter = 0;
        }
    }

    @Override
    public void start() {
        //Nothing to start
    }

    @Override
    public void stop() {
        //Nothing to stop
    }

}
