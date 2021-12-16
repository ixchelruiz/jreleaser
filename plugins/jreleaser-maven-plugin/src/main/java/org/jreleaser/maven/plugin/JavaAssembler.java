/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2020-2021 The JReleaser authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jreleaser.maven.plugin;

import java.util.List;

/**
 * @author Andres Almiray
 * @since 0.8.0
 */
public interface JavaAssembler extends Assembler {
    String getExecutable();

    void setExecutable(String executable);

    String getTemplateDirectory();

    void setTemplateDirectory(String templateDirectory);

    Java getJava();

    void setJava(Java java);

    Artifact getMainJar();

    void setMainJar(Artifact mainJar);

    List<Glob> getJars();

    void setJars(List<Glob> jars);

    List<Glob> getFiles();

    void setFiles(List<Glob> files);
}
