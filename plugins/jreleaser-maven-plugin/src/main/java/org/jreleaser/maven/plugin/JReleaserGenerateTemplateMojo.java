/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2020-2021 Andres Almiray.
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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.jreleaser.maven.plugin.internal.JReleaserLoggerAdapter;
import org.jreleaser.model.Distribution;
import org.jreleaser.templates.TemplateGenerationException;
import org.jreleaser.templates.TemplateGenerator;
import org.jreleaser.util.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Andres Almiray
 * @since 0.1.0
 */
@Mojo(name = "generate-template")
public class JReleaserGenerateTemplateMojo extends AbstractMojo {
    /**
     * The project whose model will be checked.
     */
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    protected MavenProject project;

    /**
     * Skip execution.
     */
    @Parameter(property = "jreleaser.template.skip")
    private boolean skip;

    /**
     * The name of the distribution
     */
    @Parameter(property = "jreleaser.template.distributionName", required = true)
    private String distributionName;

    /**
     * The type of the distribution
     */
    @Parameter(property = "jreleaser.template.distributionType", defaultValue = "BINARY")
    private Distribution.DistributionType distributionType = Distribution.DistributionType.BINARY;

    /**
     * The name of the distribution
     */
    @Parameter(property = "jreleaser.template.toolName", required = true)
    private String toolName;

    /**
     * Overwrite existing files.
     */
    @Parameter(property = "jreleaser.template.overwrite")
    private boolean overwrite;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Banner.display(project, getLog());
        if (skip) return;

        try {
            Path outputDirectory = Paths.get(project.getBasedir().getAbsolutePath())
                .resolve("src")
                .resolve("distributions");

            boolean result = TemplateGenerator.builder()
                .logger(getLogger())
                .distributionName(distributionName)
                .distributionType(distributionType)
                .toolName(toolName)
                .outputDirectory(outputDirectory)
                .overwrite(overwrite)
                .build()
                .generate();

            if (result) {
                getLog().info("Template generated at " +
                    outputDirectory.resolve(distributionName).resolve(toolName));
            }
        } catch (TemplateGenerationException e) {
            throw new MojoExecutionException("Unexpected error", e);
        }
    }

    private Logger getLogger() {
        return new JReleaserLoggerAdapter(getLog());
    }
}