#!/usr/bin/env perl

# SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
# SPDX-License-Identifier: MIT

package Utils;

use strict;
use warnings;
use Exporter;
use File::Basename;
use File::Path qw( make_path );

our @ISA = qw( Exporter );
our @EXPORT = qw( fread fwrite );

# Read file content.
sub fread {
  my ($path) = @_;
  open(my $h, '<', $path) or die("Cannot open file: '$path': $!");
  my $content;
  {
    local $/;
    $content = <$h>;
  }
  close($h);
  return $content;
}

# Save content to file.
sub fwrite {
  my ($path, $content) = @_;
  make_path(dirname($path));
  open(my $f, '>', $path) or die("Cannot open file for writing: '$path': $!");
  print $f $content;
  close($f);
  print("File saved to $path\n");
}
