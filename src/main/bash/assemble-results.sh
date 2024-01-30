#!/bin/bash

# The MIT License (MIT)
#
# Copyright (c) 2023-2024 Objectionary.com
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:

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

echo "This is the summary of the tests performed with the TOTAL set to ${TOTAL}, at $(date +'%Y-%m-%d %H:%M'), on $(uname), with $(nproc) CPUs:"
echo ""
echo "| | Before | After | Diff |"
echo "| --- | --: | --: | --: |"

before=$(cat before.jit-time)
after=$(cat after.jit-time)
echo "| Time, msec (with JIT, ×${MULTIPLIER} cycles) | ${before} | ${after} | $(echo 100 \* '(' "${after}" - "${before}" ')' / "${before}" | bc)% |"

before=$(cat before.time)
after=$(cat after.time)
echo "| Time, msec (no JIT) | ${before} | ${after} | $(echo 100 \* '(' "${after}" - "${before}" ')' / "${before}" | bc)% |"

echo "| Total \`.class\` files | $(ls before/classes/org/eolang/benchmark/* | wc -l | xargs) | $(ls after/classes/org/eolang/benchmark/* | wc -l | xargs) | |"
echo "| Bytes in all \`.class\` files | $(du -bs before/classes/org/eolang/benchmark/ | cut -f1) | $(du -bs after/classes/org/eolang/benchmark/ | cut -f1) | |"
echo "| JAR file size, bytes | $(du -bs before.jar | cut -f1) | $(du -bs after.jar | cut -f1) | |"
echo ""
echo "This table is updated on every successful run of the [make](https://github.com/objectionary/benchmark/actions/workflows/make.yml) job of GitHub Actions."
echo "The following JDK is used:"
echo ""
echo "\`\`\`"
java --version
echo "\`\`\`"
