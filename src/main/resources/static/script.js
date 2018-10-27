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

function doAnalyze() {
    var resultDiv = document.getElementById("feeling");
    resultDiv.innerText = "🤔";

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var json = JSON.parse(this.responseText);
            if (json.feeling == "HAPPY") {
                resultDiv.innerText = "😁";
            } else if (json.feeling == "ANNOYED") {
                resultDiv.innerText = "😕";
            } else if (json.feeling == "SAD") {
                resultDiv.innerText = "😢";
            } else {
                resultDiv.innerText = "";
            }
        }
    };

    var nameInput = document.getElementById("text");
    xhttp.open("GET", "feeling?text=" + nameInput.value, true);
    xhttp.send();
}

window.onload = function () {
    var resultDiv = document.getElementById("feeling");
    var textDiv = document.getElementById("text");
    textDiv.onkeypress = function (e) {
        if (!e) e = window.event;
        var keyCode = e.keyCode || e.which;
        if (keyCode == '13') {
            e.preventDefault();
            doAnalyze();
        } else {
            resultDiv.innerText = "";
        }
    };
    document.getElementById("analyze").onclick = doAnalyze;
};
