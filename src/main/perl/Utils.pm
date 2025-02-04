#!/usr/bin/env perl
# The MIT License (MIT)
#
# Copyright (c) 2023-2025 Objectionary.com
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

package Utils;

use strict;
use warnings;
use Exporter;
use File::Basename;
use File::Path qw( make_path );

our @ISA= qw( Exporter );
our @EXPORT = qw( fread fwrite );

# Read file content.
sub fread {
  my ($path) = @_;
  open(my $h, '<', $path) or die("Cannot open file: '$path'");
  my $content; { local $/; $content = <$h>; }
  return $content;
}

# Save content to file.
sub fwrite {
  my ($path, $content) = @_;
  make_path(dirname($path));
  open(my $f, '>', $path) or die("Cannot open file for writing: '$path'");
  print $f $content;
  close($f);
  print("File saved to $path\n");
}
