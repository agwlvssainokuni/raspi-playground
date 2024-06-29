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

package cherry.raspi.dout;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalState;

import cherry.raspi.BaseHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor()
@Component()
public class DoutHandler extends BaseHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Context pi4j;

    @Override
    public void run(ApplicationArguments args) {

        var dout = pi4j.dout().create(18); // BCM 18
        dout.config().shutdownState(DigitalState.LOW);
        dout.addListener(event -> logger.info("{}", event));

        IntStream.range(0, 10).forEach(i -> {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException ex) {
                logger.warn("", ex);
            }
            dout.toggle();
        });

        var future = dout.blinkAsync(250, TimeUnit.MILLISECONDS);
        try {
            Thread.sleep(5_000L);
        } catch (InterruptedException ex) {
            logger.warn("", ex);
        }
        future.cancel(true);

        setExitCode(0);
    }

}
