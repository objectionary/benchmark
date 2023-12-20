#!/usr/bin/env perl

my $readme;
my $file = "README.md";
open(my $r, "<", $file);
{ local $/; $readme = <$r>; }
close($r);
my $sep = "<!-- benchmark -->";
my @p = split(/\Q$sep\E/, $readme);
my $table = "results.md";
open(my $t, "<", $table);
{ local $/; $table = <$t>; }
close($t);
$p[1] = "\n" . $table . "\n";
my $new = join($sep, @p);
open(my $w, ">", $file);
print $w join($sep, @p);
close($w);
