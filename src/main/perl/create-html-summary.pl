#!/usr/bin/env perl
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

use strict;
use warnings;
use lib('src/main/perl');
use Utils qw( fread fwrite );

sub inject {
  my ($html, $snippet, $pre) = @_;
  $html =~ s/\{${snippet}\}/${pre}/g;
  return $html;
}

my $html = fread('src/main/html/summary.html');

my $javas = '';
foreach my $f (glob('src/main/java/org/eolang/benchmark/*.java')) {
  my $java = fread($f);
  $java =~ s/^\s|\s$//g; # leading and tailing spaces
  $java =~ s/\/\/.*\n//g; # one-line comments
  $java =~ s/\n\n/\n/g; # empty lines
  $java =~ s/\/\*(.|\n)*\*\///gm; # block comments
  $javas = $javas . "\n" . $java;
}
$javas =~ s/^\s|\s$//g;
$html = inject($html, 'java', $javas);

my $eos = '';
foreach my $f (glob('after/generated-sources/eo/org/eolang/benchmark/*.eo')) {
  my $eo = fread($f);
  $eo =~ s/^\s|\s$//g; # leading and tailing spaces
  $eo =~ s/\n\n/\n/g; # empty lines
  $eos = $eos . "\n" . $eo;
}
$eos =~ s/^\s|\s$//g;
$html = inject($html, 'after-jeo-disassemble', $eos);

fwrite('target/summary.html', $html);
