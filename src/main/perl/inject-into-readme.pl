#!/usr/bin/env perl
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

use strict;
use warnings;
use lib('src/main/perl');
use Utils qw( fread fwrite );

my $readme_file = "README.md";
my $results_file = "results.md";
my $separator = "<!-- benchmark -->";

my $readme = fread($readme_file);
my @parts = split(/\Q$separator\E/, $readme);

die "README.md must contain exactly 2 benchmark separators" unless @parts == 3;

my $table = fread($results_file);
$parts[1] = "\n" . $table . "\n";

fwrite($readme_file, join($separator, @parts));
