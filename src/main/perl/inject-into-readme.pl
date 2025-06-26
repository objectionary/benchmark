#!/usr/bin/env perl
# SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
# SPDX-License-Identifier: MIT

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
