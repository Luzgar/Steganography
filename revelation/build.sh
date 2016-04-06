#!/bin/bash

#On supprime, si il existe déjà, le .exe précédent

rm *.exe 

#On se place dans le dossier src/
cd src

#On lance le MakeFile
make

#On déplace l'executable et renomme l'exe

mv reveal.exe ..
mv Reveal.exe ..

cd ..

mv Reveal.exe reveal.exe