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

package fr.alexandreroman.demos.feelings.controllers

import fr.alexandreroman.demos.feelings.services.Feeling
import fr.alexandreroman.demos.feelings.services.FeelingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller providing a function to convert a text to a "feeling".
 */
@RestController
class FeelingController(private val feelingService: FeelingService) {
    data class FeelingResponse(val feeling: Feeling)

    @GetMapping("/feeling")
    @ResponseBody
    fun getFeeling(@RequestParam("text", required = false) text: String?): FeelingResponse {
        if (text.isNullOrEmpty()) {
            return FeelingResponse(Feeling.ANNOYED)
        }
        return FeelingResponse(feelingService.getFeeling(text!!))
    }
}
