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

package fr.alexandreroman.demos.feelings.local

import fr.alexandreroman.demos.feelings.services.Feeling
import fr.alexandreroman.demos.feelings.services.FeelingService
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.*

/**
 * Local [FeelingService] implementation, mainly used in development mode
 * when no Azure Text Analytics endpoint is available.
 */
@Profile("!cloud")
@Service
class LocalFeelingService : FeelingService {
    private val random = Random()

    override fun getFeeling(text: String): Feeling {
        val index = random.nextInt(Feeling.values().size)
        return Feeling.values()[index]
    }
}
