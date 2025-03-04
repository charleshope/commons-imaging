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

package org.apache.commons.imaging.formats.png;

import org.apache.commons.imaging.internal.Debug;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PngWriteForceTrueColorText extends PngBaseTest {

    @Test
    public void test() throws Exception {
        final PngImageParser pngImageParser = new PngImageParser();
        final List<File> images = getPngImages();
        for (final File imageFile : images) {

            try {
                if (isInvalidPNGTestFile(imageFile)) {
                    continue;
                }

                Debug.debug("imageFile", imageFile);
                // Debug.debug();

                // params.put(ImagingConstants.PARAM_KEY_VERBOSE,
                // Boolean.TRUE);

                final BufferedImage image = pngImageParser.getBufferedImage(imageFile, new PngImagingParameters());
                assertNotNull(image);

                final File outFile = Files.createTempFile(imageFile.getName() + ".", ".png").toFile();
                // Debug.debug("outFile", outFile);

                final PngImagingParameters params = new PngImagingParameters();
                params.setForceTrueColor(Boolean.TRUE);
                try (FileOutputStream fos = new FileOutputStream(outFile)) {
                    pngImageParser.writeImage(image, fos, params);
                }

                final BufferedImage image2 = pngImageParser.getBufferedImage(outFile, new PngImagingParameters());
                assertNotNull(image2);
            } catch (final Exception e) {
                Debug.debug("imageFile", imageFile);
                throw e;
            }
        }
        Debug.debug("complete.");
    }

}
