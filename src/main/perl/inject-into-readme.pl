#!/usr/bin/env perl

use strict;
use warnings;
use lib('src/main/perl');
use Utils qw( fread fwrite );

my $file = "README.md";

my $readme = fread($file);
my $sep = "<!-- benchmark -->";
my @p = split(/\Q$sep\E/, $readme);

my $table = fread('results.md');
$p[1] = "\n" . $table . "\n";

fwrite($file, join($sep, @p));
