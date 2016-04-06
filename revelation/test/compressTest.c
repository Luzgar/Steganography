#include "tools.h"
#include "huffmanDecompression.h"
#include "binaryTree.h"
#include <stdio.h>
#include <assert.h>

int compressTest(){
    getCodeTest();
    defaultdecompressTest();
    isSameBinaryTest();
    fillDictionnaryTest();
    getPositionOfCodeInDictionnaryTest();
    getBinaryOfFileTest();
    defaultDecompressionTest();
    encodingTreeDecompressionTest();
    fillTreeTest();
    treeTest();
    printf("Decompression test ok !\n");
}

void getCodeTest(){

    int * test = malloc(4 * sizeof(int));
    test[0] = 197;
    test[1] = 12;
    test[2] = 48;
    test[3] = 64;

    int nbByteUsed = 4;
    int nbBitsCode = 27;
    char * result = getCode(test, nbBitsCode, nbByteUsed);
    printf("Result : %s\n", result);
}

void isSameBinaryTest(){
    char * binary1 = "01";
    char * binary2 = "010";
    printf("%d\n", isSameBinary(binary1, 2, binary2, 3));

    char * binary3 = "01";
    printf("%d\n", isSameBinary(binary2, 3, binary1, 2));

    printf("%d\n", isSameBinary(binary3, 2, binary1, 2));
}


void getBinaryOfFileTest(){
    struct HiddenFileByte tabByte;
    tabByte.byte = malloc(4);

    tabByte.byte[0] = 4;
    tabByte.byte[1] = 100;
    tabByte.byte[2] = 2;
    tabByte.byte[3] = 18;
    tabByte.indexNextByte = 4;

    char * binary = getBinaryOfFile(&tabByte, 32, 0);
    char * expectedResult = "00000100011001000000001000010010";
    assert(strcmp(expectedResult, binary) == 0);
}


void getPositionOfCodeInDictionnaryTest(){
    struct HiddenFileByte tabByte;
    tabByte.byte = malloc(21);
    tabByte.byte[0] = 4;

    tabByte.byte[1] = 100;
    tabByte.byte[2] = 2;
    tabByte.byte[3] = 0;

    tabByte.byte[4] = 101;
    tabByte.byte[5] = 2;
    tabByte.byte[6] = 64;

    tabByte.byte[7] = 99;
    tabByte.byte[8] = 3;
    tabByte.byte[9] = 128;

    tabByte.byte[10] = 98;
    tabByte.byte[11] = 3;
    tabByte.byte[12] = 160;

    tabByte.byte[13] = 97;
    tabByte.byte[14] = 2;
    tabByte.byte[15] = 192;

    tabByte.byte[16] = 5;

    tabByte.byte[17] = 250;
    tabByte.byte[18] = 48;
    tabByte.byte[19] = 219;
    tabByte.byte[20] = 136;

    struct Dictionnary * dictionnary = fillDictionnary(&tabByte);
    int position;
    char * buffer = "00";
    position = getPositionOfCodeInDictionnary(buffer, dictionnary, 2);
    assert(position == 0);

    char * buffer2 = "01";
    position = getPositionOfCodeInDictionnary(buffer2, dictionnary, 2);
    assert(position == 1);

    char * buffer3 = "100";
    position = getPositionOfCodeInDictionnary(buffer3, dictionnary, 3);
    assert(position == 2);

    char * buffer4 = "101";
    position = getPositionOfCodeInDictionnary(buffer4, dictionnary, 3);
    assert(position == 3);

    char * buffer5 = "11";
    position = getPositionOfCodeInDictionnary(buffer5, dictionnary, 2);
    assert(position == 4);
}

void fillDictionnaryTest(){
    struct HiddenFileByte tabByte;
    tabByte.byte = malloc(21);
    tabByte.byte[0] = 4;

    tabByte.byte[1] = 100;
    tabByte.byte[2] = 2;
    tabByte.byte[3] = 0;

    tabByte.byte[4] = 101;
    tabByte.byte[5] = 2;
    tabByte.byte[6] = 64;

    tabByte.byte[7] = 99;
    tabByte.byte[8] = 3;
    tabByte.byte[9] = 128;

    tabByte.byte[10] = 98;
    tabByte.byte[11] = 3;
    tabByte.byte[12] = 160;

    tabByte.byte[13] = 97;
    tabByte.byte[14] = 2;
    tabByte.byte[15] = 192;

    tabByte.byte[16] = 5;

    tabByte.byte[17] = 250;
    tabByte.byte[18] = 48;
    tabByte.byte[19] = 219;
    tabByte.byte[20] = 136;

    struct Dictionnary * dictionnary = fillDictionnary(&tabByte);

    assert('d' == dictionnary->letter[0]);
    assert('e' == dictionnary->letter[1]);
    assert('c' == dictionnary->letter[2]);
    assert('b' == dictionnary->letter[3]);
    assert('a' == dictionnary->letter[4]);

    assert(2 == dictionnary->sizeOfCode[0]);
    assert(2 == dictionnary->sizeOfCode[1]);
    assert(3 == dictionnary->sizeOfCode[2]);
    assert(3 == dictionnary->sizeOfCode[3]);
    assert(2 == dictionnary->sizeOfCode[4]);

    assert(strcmp("00", dictionnary->letterCode[0])==0);
    assert(strcmp("01", dictionnary->letterCode[1])==0);
    assert(strcmp("100", dictionnary->letterCode[2])==0);
    assert(strcmp("101", dictionnary->letterCode[3])==0);
    assert(strcmp("11", dictionnary->letterCode[4])==0);

    assert(4 == dictionnary->nbOfSymbol);
}

void treeTest(){
    //node root = malloc(sizeof(node));
    //root->is_leaf = 0;
    //node tmp = root;
    //char * binary = "10011000";
    //char * shape = construct_tree(binary, root);
    //display_tree(root);
}

void encodingTreeDecompressionTest(){
    struct HiddenFileByte tabByte;
    tabByte.byte = malloc(21);
    tabByte.byte[0] = 4;

    tabByte.byte[1] = 100;

    tabByte.byte[2] = 101;

    tabByte.byte[3] = 99;

    tabByte.byte[4] = 98;

    tabByte.byte[5] = 97;

    tabByte.byte[6] = 152;

    tabByte.byte[7] = 5;

    tabByte.byte[8] = 250;
    tabByte.byte[9] = 48;
    tabByte.byte[10] = 219;
    tabByte.byte[11] = 136;

    tabByte.indexNextByte = 13;
    struct HiddenFileByte finaltabByte = decompress(&tabByte, 1, 1);
    assert(finaltabByte.valid == 1);
}

void defaultdecompressTest(){
    struct HiddenFileByte tabByte;
    tabByte.byte = malloc(21);
    tabByte.byte[0] = 4;

    tabByte.byte[1] = 100;
    tabByte.byte[2] = 2;
    tabByte.byte[3] = 0;

    tabByte.byte[4] = 101;
    tabByte.byte[5] = 2;
    tabByte.byte[6] = 64;

    tabByte.byte[7] = 99;
    tabByte.byte[8] = 3;
    tabByte.byte[9] = 128;

    tabByte.byte[10] = 98;
    tabByte.byte[11] = 3;
    tabByte.byte[12] = 160;

    tabByte.byte[13] = 97;
    tabByte.byte[14] = 2;
    tabByte.byte[15] = 192;

    tabByte.byte[16] = 5;

    tabByte.byte[17] = 250;
    tabByte.byte[18] = 48;
    tabByte.byte[19] = 219;
    tabByte.byte[20] = 136;

    tabByte.indexNextByte = 21;
    struct HiddenFileByte finaltabByte = decompress(&tabByte, 1, 0);
    assert(finaltabByte.valid == 1);
}

void fillTreeTest(){

    node root = malloc(sizeof(struct node));
    root->is_leaf = 0;
    root->left = NULL;
    root->right = NULL;
    char * binary = "10011000";
    char * shape = construct_tree(binary, root);
    display_tree(root);
}
