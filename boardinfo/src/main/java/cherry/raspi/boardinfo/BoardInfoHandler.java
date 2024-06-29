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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import com.pi4j.context.Context;

import cherry.raspi.BaseHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor()
@Component()
public class BoardInfoHandler extends BaseHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Context pi4j;

    @Override
    public void run(ApplicationArguments args) {

        var boardinfo = pi4j.boardInfo();
        logger.info("BoardInfo");
        logger.info("  BoardModel: {}", boardinfo.getBoardModel().getLabel());
        logger.info("  OperatingSystem: {}", boardinfo.getOperatingSystem());
        logger.info("  JavaInfo: {}", boardinfo.getJavaInfo());

        setExitCode(0);
    }

}
