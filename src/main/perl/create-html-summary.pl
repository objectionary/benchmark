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
use Env;
use File::Basename;
use lib('src/main/perl');
use Utils qw( fread fwrite );

sub inject {
  my ($html, $snippet, $pre) = @_;
  my $safe = $pre;
  $safe =~ s/</&lt;/g;
  $safe =~ s/>/&gt;/g;
  $safe =~ s/\$/\\\$/g;
  $html =~ s/\{${snippet}\}/${safe}/g;
  return $html;
}

sub join_classes {
  my ($dir) = @_;
  my $codes = '';
  my $total = 0;
  foreach my $f (glob($dir . '/org/eolang/benchmark/*.class')) {
    my $base = substr($f, length($dir) + 1);
    my $code = `cd $dir ; javap -c '$base'`;
    $code =~ s/^\s|\s$//g; # leading and tailing spaces
    $codes = $codes . "\n\n" . $code;
    $total += 1;
  }
  $codes =~ s/^\s|\s$//g;
  print("$total .class files joined from $dir\n");
  return $codes;
}

sub join_eo {
  my ($dir) = @_;
  my $eos = '';
  my $total = 0;
  foreach my $f (glob($dir . '/*.eo')) {
    my $eo = fread($f);
    $eo =~ s/^\s|\s$//g; # leading and tailing spaces
    $eo =~ s/\n\n/\n/g; # empty lines
    $eos = $eos . "\n\n" . $eo;
    $total += 1;
  }
  $eos =~ s/^\s|\s$//g;
  print("$total .eo files joined from $dir\n");
  return $eos;
}

sub join_java {
  my ($dir) = @_;
  my $javas = '';
  my $total = 0;
  foreach my $f (glob($dir . '/*.java')) {
    my $java = fread($f);
    $java =~ s/^\s|\s$//g; # leading and tailing spaces
    $java =~ s/\/\/.*\n//g; # one-line comments
    $java =~ s/\n\n/\n/g; # empty lines
    $java =~ s/\/\*(.|\n)*\*\///gm; # block comments
    $javas = $javas . "\n" . $java;
    $total += 1;
  }
  $javas =~ s/^\s|\s$//g;
  print("$total .java files joined from $dir\n");
  return $javas;
}

sub join_phi {
  my ($dir) = @_;
  my $phis = '';
  my $total = 0;
  foreach my $f (glob($dir . '/*.phi')) {
    my $phi = fread($f);
    $phi =~ s/^\s|\s$//g; # leading and tailing spaces
    $phis = $phis . "\n\n" . $phi;
    $total += 1;
  }
  $phis =~ s/^\s|\s$//g;
  print("$total .phi files joined from $dir\n");
  return $phis;
}

my $html = fread('src/main/html/summary.html');

$html = inject($html, 'java', join_java('src/main/java/org/eolang/benchmark'));
$html = inject($html, 'after-javac', join_classes('after/classes'));
$html = inject($html, 'after-jeo-disassemble', join_eo('after/generated-sources/jeo-disassemble.eo/org/eolang/benchmark'));
$html = inject($html, 'after-opeo-decompile', join_eo('after/generated-sources/opeo-decompile.eo/org/eolang/benchmark'));
# $html = inject($html, 'after-phi', join_phi('after/generated-sources/phi/org/eolang/benchmark'));

$html = inject($html, 'version-javac', `javac --version`);
$html = inject($html, 'eo-version', $ENV{EO_VERSION});
$html = inject($html, 'jeo-version', $ENV{JEO_VERSION});
$html = inject($html, 'opeo-version', $ENV{OPEO_VERSION});

fwrite('target/summary.html', $html);