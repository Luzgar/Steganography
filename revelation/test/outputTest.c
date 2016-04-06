#include "output.h"
#include "tools.h"
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <string.h>

void outputTest(){
    createHideFileTest();
    printf("Tous les tests de Output sont passes correctement.");
}

void createHideFileTest(){
    struct HiddenFileByte hiddenFile;
    hiddenFile.byte = "abcd";
    hiddenFile.indexNextByte = 4;

    char * filename = "test.txt";
    createHideFile(filename, &hiddenFile);
    assert(access(filename, 0) == 0);
}
