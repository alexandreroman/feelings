/*
 * Copyright (c) 2018 Pivotal Software, Inc.
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

package fr.alexandreroman.demos.feelings.azure

import org.slf4j.LoggerFactory
import org.springframework.cloud.service.AbstractServiceConnectorCreator
import org.springframework.cloud.service.ServiceConnectorConfig

/**
 * Create an [AzureFeelingService] instance connected to an Azure Text Analytics endpoint.
 */
class AzureFeelingServiceConnector : AbstractServiceConnectorCreator<AzureFeelingService, AzureTextAnalyticsServiceInfo>() {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun create(serviceInfo: AzureTextAnalyticsServiceInfo?, serviceConnectorConfig: ServiceConnectorConfig?): AzureFeelingService {
        logger.info("Creating Azure feeling service from Text Analytics service: " +
                "endpoint={}, key={}", serviceInfo!!.endpoint, serviceInfo.key)
        return AzureFeelingService(
                serviceInfo.endpoint,
                serviceInfo.key)
    }
}
