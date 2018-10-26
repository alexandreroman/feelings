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

import fr.alexandreroman.demos.feelings.services.Feeling
import fr.alexandreroman.demos.feelings.services.FeelingService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

/**
 * [FeelingService] implementation using Azure Text Analytics API.
 */
class AzureFeelingService(private val endpoint: String, private val key: String) : FeelingService {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun getFeeling(text: String): Feeling {
        // Build a common web client for our requests.
        val webClient = WebClient.builder()
                .baseUrl(endpoint)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Ocp-Apim-Subscription-Key", key)
                .build()

        // We first need to guess the language for the input text:
        // the sentiment score will be better if the language is known.

        logger.debug("Detecting language from: {}", text)
        val langDocs = Documents(listOf(Document(1, text)))
        val langResp = webClient.post()
                .uri("/languages").body(BodyInserters.fromObject(langDocs))
                .exchange().block()?.bodyToMono(Documents::class.java)?.block()
                ?: throw RuntimeException("Failed to detect language")

        val lang = langResp.documents[0].detectedLanguages!![0].iso6391Name
        logger.info("Detecting sentiment with language {}: {}", lang, text)

        val sentimentDocs = Documents(listOf(Document(1, text, language = lang)))
        val sentimentResp = webClient.post()
                .uri("/sentiment").body(BodyInserters.fromObject(sentimentDocs))
                .exchange().block()?.bodyToMono(Documents::class.java)?.block()
                ?: throw RuntimeException("Failed to detect sentiment score")

        val sentimentScore = sentimentResp.documents[0].score!!
        val feeling = sentimentScore.toFeeling()
        logger.info("Detected sentiment score: {} -> {} -> {}", text, sentimentScore, feeling)

        return feeling
    }

    /**
     * Convert a sentiment score to a [Feeling] instance.
     */
    private fun Float.toFeeling(): Feeling {
        if (this < 0.3) {
            return Feeling.SAD
        }
        if (this < 0.6) {
            return Feeling.ANNOYED
        }
        return Feeling.HAPPY
    }

    data class DetectedLanguage(val name: String, val iso6391Name: String, val score: Int)

    data class Documents(val documents: List<Document>)

    data class Document(val id: Int,
                        val text: String? = null,
                        val score: Float? = null,
                        val language: String? = null,
                        val detectedLanguages: List<DetectedLanguage>? = null)
}
