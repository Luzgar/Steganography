#!/bin/bash

cd dissimulation
./build.sh
cd ..

cd revelation
./build.sh
cd ..

#L1
cd dissimulation
./dissimulate -in large.jpg -msg "aaa" -compress
mv result.png ../revelation

cd ../revelation
./reveal -in result.png -compress -out L1.txt

#L2
cd ../dissimulation
./dissimulate -in large.jpg -msg "ababababa" -compress
mv result.png ../revelation

cd ../revelation
./reveal -in result.png -compress -out L2.txt

#L5
cd ../dissimulation
./dissimulate -in large.jpg -msg agrange.txt -compress
mv result.png ../revelation

cd ../revelation
./reveal -in result.png -compress -out L5.txt

#L6
cd ../dissimulation
./dissimulate -in large.jpg -msg agrange.txt -compress more
mv result.png ../revelation

cd ../revelation
./reveal -in result.png -compress more -out L6.txt