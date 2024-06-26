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

package cherry.raspi.boardinfo;

import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

import com.pi4j.context.Context;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor()
@Component()
public class BoardInfoHandler implements ApplicationRunner, ExitCodeGenerator {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final AtomicReference<Integer> exitCode = new AtomicReference<>();

    private final Context context;

    @Override
    public void run(ApplicationArguments args) {

        var boardinfo = context.boardInfo();
        logger.info("BoardInfo");
        logger.info("  BoardModel: {}", boardinfo.getBoardModel().getLabel());
        logger.info("  OperatingSystem: {}", boardinfo.getOperatingSystem());
        logger.info("  JavaInfo: {}", boardinfo.getJavaInfo());

        setExitCode(0);
    }

    private synchronized void setExitCode(int code) {
        exitCode.set(code);
        notifyAll();
    }

    @Override
    public synchronized int getExitCode() {
        while (true) {
            if (exitCode.get() != null) {
                return exitCode.get();
            }
            try {
                wait();
            } catch (InterruptedException ex) {
                // NOTHING TO DO
            }
        }
    }
}
