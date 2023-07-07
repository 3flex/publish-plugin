/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.gradlenexus.publishplugin

import io.github.gradlenexus.publishplugin.internal.InvalidatingStagingRepositoryDescriptorRegistry
import io.github.gradlenexus.publishplugin.internal.StagingRepositoryTransitioner
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.options.Option
import javax.inject.Inject

abstract class ReleaseNexusStagingRepository @Inject constructor(
    objects: ObjectFactory,
    repository: NexusRepository,
    registry: Provider<InvalidatingStagingRepositoryDescriptorRegistry>
) : AbstractTransitionNexusStagingRepositoryTask(objects, repository, registry) {

    @Option(option = "staging-repository-id", description = "staging repository id to release")
    fun setStagingRepositoryId(stagingRepositoryId: String) {
        this.stagingRepositoryId.set(stagingRepositoryId)
    }

    override fun transitionStagingRepo(repositoryTransitioner: StagingRepositoryTransitioner) {
        logger.info("Releasing staging repository with id '{}'", stagingRepositoryId.get())
        repositoryTransitioner.effectivelyRelease(stagingRepositoryId.get(), repositoryDescription.get())
        logger.info("Repository with id '{}' effectively released", stagingRepositoryId.get())
    }
}
