#!/usr/bin/perl

@ls = qx( ls *psd );

foreach $line (@ls) {
	chomp($line);
	$old = $line;
	
	$line =~ s/(.)\.psd/$1\.png/;

	qx( cp $old $line );
}

