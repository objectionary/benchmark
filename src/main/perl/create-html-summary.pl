#!/usr/bin/env perl
# SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
# SPDX-License-Identifier: MIT

use strict;
use warnings;
use Env;
use File::Basename;
use lib('src/main/perl');
use Utils qw( fread fwrite );

my $target = 'Big';

sub timed {
  my ($html) = @_;
  my $time = `date +'%Y-%m-%d %H:%M'`;
  chomp $time;
  $html =~ s/\{time\}/$time/g;
  return $html;
}

sub trimmed {
  my ($txt) = @_;
  $txt =~ s/^\s+|\s+$//g;
  return $txt;
}

sub inject {
  my ($html, $snippet, $pre) = @_;
  my $safe = $pre;
  $safe =~ s/</&lt;/g;
  $safe =~ s/>/&gt;/g;
  my $show = fread('src/main/html/show.html');
  $show =~ s/\{snippet\}/$snippet/g;
  $show =~ s/\{pre\}/$safe/g;
  fwrite("html/$snippet.html", timed($show));
  $html =~ s/\{$snippet\}/$safe/g;
  return $html;
}

sub join_classes {
  my ($dir) = @_;
  my @codes;
  my $total = 0;
  foreach my $f (glob("$dir/org/eolang/benchmark/$target.class")) {
    my $base = substr($f, length($dir) + 1);
    my $code = `cd '$dir' && javap -c '$base'`;
    chomp $code;
    $code = trimmed($code);
    push @codes, $code;
    $total++;
  }
  my $result = join("\n\n", @codes);
  $result =~ s/^\s+|\s+$//g;
  print "$total .class files joined from $dir\n";
  return $result;
}

sub join_eo {
  my ($dir) = @_;
  my @eos;
  my $total = 0;
  foreach my $f (glob("$dir/$target.eo")) {
    my $eo = trimmed(fread($f));
    $eo =~ s/\n\n+/\n/g;
    push @eos, $eo;
    $total++;
  }
  my $result = join("\n\n", @eos);
  $result =~ s/^\s+|\s+$//g;
  print "$total .eo files joined from $dir\n";
  return $result;
}

sub join_java {
  my ($dir) = @_;
  my @javas;
  my $total = 0;
  foreach my $f (glob("$dir/$target.java")) {
    my $java = trimmed(fread($f));
    $java =~ s{//.*$}{}gm;
    $java =~ s/\n\n+/\n/g;
    $java =~ s{/\*.*?\*/}{}gs;
    push @javas, trimmed($java);
    $total++;
  }
  my $result = join("\n\n", @javas);
  $result = trimmed($result);
  print "$total .java files joined from $dir\n";
  return $result;
}

sub join_phi {
  my ($dir) = @_;
  my @phis;
  my $total = 0;
  foreach my $f (glob("$dir/$target.phi")) {
    my $phi = trimmed(fread($f));
    push @phis, $phi;
    $total++;
  }
  my $result = join("\n\n", @phis);
  $result =~ s/^\s+|\s+$//g;
  print "$total .phi files joined from $dir\n";
  return $result;
}

my $html = fread('src/main/html/summary.html');

$html = inject($html, 'java', join_java('src/main/java/org/eolang/benchmark'));
$html = inject($html, 'after-javac', join_classes('after/classes-before-hone'));
$html = inject($html, 'after-jeo-disassemble', join_eo('after/generated-sources/jeo-disassemble.eo/org/eolang/benchmark'));
$html = inject($html, 'after-phi', join_phi('after/generated-sources/phi/org/eolang/benchmark'));
$html = inject($html, 'after-phino', join_phi('after/generated-sources/phi-optimized/org/eolang/benchmark'));
$html = inject($html, 'after-unphi', join_eo('after/generated-sources/unphi.eo/org/eolang/benchmark'));
$html = inject($html, 'after-jeo-assemble', join_classes('after/classes'));
$html = inject($html, 'after-jd', join_java('after/generated-sources/after-jd/org/eolang/benchmark'));

my $javac_version = `javac --version`;
chomp $javac_version;
$html = inject($html, 'version-javac', $javac_version);
$html = inject($html, 'eo-version', $ENV{EO_VERSION} || '');
$html = inject($html, 'jeo-version', $ENV{JEO_VERSION} || '');
$html = inject($html, 'phino-version', $ENV{PHINO_VERSION} || '');
$html = inject($html, 'jd-version', $ENV{JD_VERSION} || '');

fwrite('html/summary.html', timed($html));
