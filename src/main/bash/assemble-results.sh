#!/bin/bash

# SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
# SPDX-License-Identifier: MIT

# The above copyright notice and this permission notice shall be included
# in all copies or substantial portions of the Software.

# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.

set -e

echo "This is the summary of the tests performed"
echo "by $(java --version | head -1 | cut -d ' ' -f1,2),"
echo "on $(date +'%Y-%m-%d')"
echo "at $(date +'%H:%M'),"
echo "on $(uname) with $(nproc) CPUs,"
echo "in [this GHA run][benchmark-gha]"
echo "(the numbers are in milliseconds):"
echo ""
echo "| Test method | Before | After | Diff | Gain | Ratio |"
echo "| --- | --: | --: | --: | --: | --: |"

before=$(tail -n +2 before.csv | cut -d ',' -f 1,5 | tr -d '"' | cut -c22-)
after=$(tail -n +2 after.csv | cut -d ',' -f 1,5 | tr -d '"' | cut -c22-)

while IFS= read -r ln; do
    method=$(echo "${ln}" | cut -d ',' -f1)
    ms1=$(echo "${ln}" | cut -d ',' -f2)
    ms2=$(echo "${after}" | grep "${method}," | cut -d ',' -f2)
    printf '| '
    printf "[\`%s\`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/%s.java)" \
        "${method}" \
        "$(echo "${method}" | cut -d '.' -f 1)"
    printf ' | '
    printf "\`%0.2f\`" "${ms1}"
    printf ' | '
    printf "\`%0.2f\`" "${ms2}"
    printf ' | '
    printf "\`%+0.2f\`" "$(perl -E "say ${ms2} - ${ms1}")"
    printf ' | '
    printf "\`%+d%%\`" "$(perl -E "say 100 * ( ${ms1} - ${ms2} ) / ${ms1}")"
    printf ' | '
    printf "\`%0.2fx\`" "$(perl -E "say ${ms1} / ${ms2}")"
    printf " |\n"
done <<< "${before}"
