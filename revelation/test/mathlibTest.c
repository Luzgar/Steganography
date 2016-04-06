#include "mathlib.h"
#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

void powerTest(){
    int nb = 2;
    int pow = 4;
    int result;

    result = power(2, 4);

    assert(result == 16);

    printf("%s\n", "Test MathLib.c passé");
}