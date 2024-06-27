/*
 * Copyright 2024 agwlvssainokuni
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cherry.raspi;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;

@Configuration
public class Pi4JConfiguration {

    private final AtomicReference<Context> pi4j = new AtomicReference<>();

    @Bean(destroyMethod = "shutdown")
    Context pi4j() {
        return pi4j.updateAndGet(p -> Optional.ofNullable(p).orElseGet(() -> {
            return Pi4J.newContextBuilder()
                    .autoDetect()
                    .build();
        }));
    }

}
