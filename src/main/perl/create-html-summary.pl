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

sub join_eo {
  my ($dir) = @_;
  my $eos = '';
  foreach my $f (glob($dir . '/*.eo')) {
    my $eo = fread($f);
    $eo =~ s/^\s|\s$//g; # leading and tailing spaces
    $eo =~ s/\n\n/\n/g; # empty lines
    $eos = $eos . "\n\n" . $eo;
  }
  $eos =~ s/^\s|\s$//g;
  return $eos;
}

sub join_phi {
  my ($dir) = @_;
  my $phis = '';
  foreach my $f (glob($dir . '/*.phi')) {
    my $phi = fread($f);
    $phi =~ s/^\s|\s$//g; # leading and tailing spaces
    $phis = $phis . "\n\n" . $phi;
  }
  $phis =~ s/^\s|\s$//g;
  return $phis;
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

$html = inject($html, 'after-jeo-disassemble', join_eo('after/generated-sources/eo/org/eolang/benchmark'));
$html = inject($html, 'after-opeo-decompile', join_eo('after/generated-sources/opeo-eo/org/eolang/benchmark'));
$html = inject($html, 'after-phi', join_phi('after/generated-sources/phi/org/eolang/benchmark'));

fwrite('target/summary.html', $html);
