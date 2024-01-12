#!/usr/bin/env perl

package Utils;

use strict;
use warnings;
use Exporter;

our @ISA= qw( Exporter );
our @EXPORT = qw( fread fwrite );

# Read file content.
sub fread {
  my ($path) = @_;
  open(my $h, '<', $path) or die('Cannot open file: ' . $path);
  my $content; { local $/; $content = <$h>; }
  return $content;
}

# Save content to file.
sub fwrite {
  my ($path, $content) = @_;
  open(my $f, '>', $path) or error('Cannot open file for writing: ' . $path);
  print $f $content;
  close($f);
}
