#include <assert.h>
#include "tools.h"

void toolTest(){
    verifyMagicNumberTest();
    binaryToByteTabTest();
    binaryToByteTabTest2();
    addBinaryBufferInTabTest();
    printf("%s\n", "Test tools.c reussis.");
}

void verifyMagicNumberTest(){
    struct HiddenFileByte tabByte;
    tabByte.byte = "testmagicNumberHELP";
    tabByte.indexNextByte = 19;

    struct Condition condition;
    condition.magic_Number = "HELP";
    condition.magicNumberSize = 4;
    int result;

    result = verifyMagicNumber(&tabByte, &condition);
    assert(result == 1);

    tabByte.byte = "testmagicNumberHELD";
    tabByte.indexNextByte = 19;

    result = verifyMagicNumber(&tabByte, &condition);
    assert(result == 0);
}

void binaryToByteTabTest(){
    struct BufferBinary bufferBinary;
    bufferBinary.binary[0] = '0';
    bufferBinary.binary[1] = '1';
    bufferBinary.binary[2] = '1';
    bufferBinary.binary[3] = '0';
    bufferBinary.binary[4] = '1';
    bufferBinary.binary[5] = '1';
    bufferBinary.binary[6] = '0';
    bufferBinary.binary[7] = '1';// caractère 'm'

    struct HiddenFileByte tabByte;
    tabByte.byte = malloc(4);
    tabByte.byte[0] = 't';
    tabByte.byte[1] = 'e';
    tabByte.byte[2] = 's';
    tabByte.byte[3] = 't';
    tabByte.BLOC = 10;
    tabByte.tabSize = 4;
    tabByte.indexNextByte = 4;

    struct Condition condition;
    condition.magic_Number = "HELP";
    condition.magicNumberSize = 4;

    int result;
    result = binaryToByteTab(&tabByte, &bufferBinary, &condition);

    assert(result == 1);
    assert(tabByte.byte[tabByte.indexNextByte-1] == 'm');
}

void binaryToByteTabTest2(){
    struct BufferBinary bufferBinary;
    bufferBinary.binary[0] = '0';
    bufferBinary.binary[1] = '1';
    bufferBinary.binary[2] = '0';
    bufferBinary.binary[3] = '1';
    bufferBinary.binary[4] = '0';
    bufferBinary.binary[5] = '0';
    bufferBinary.binary[6] = '0';
    bufferBinary.binary[7] = '0';// caractère 'P'

    struct HiddenFileByte tabByte;
    tabByte.byte = malloc(5);
    tabByte.byte[0] = 'm';
    tabByte.byte[1] = 'H';
    tabByte.byte[2] = 'E';
    tabByte.byte[3] = 'L';
    tabByte.BLOC = 10;
    tabByte.tabSize = 4;
    tabByte.indexNextByte = 4;

    struct Condition condition;
    condition.magic_Number = "HELP";
    condition.magicNumberSize = 4;

    int result;
    result = binaryToByteTab(&tabByte, &bufferBinary, &condition);

    assert(result == 0);
    assert(tabByte.byte[tabByte.indexNextByte-1] == 'P');
}

void addBinaryBufferInTabTest(){
    int result;
    unsigned int n = 53;
    struct BufferBinary bufferBinary;
    bufferBinary.indexNextBit = 0;

    struct HiddenFileByte tabByte;
    tabByte.byte = malloc(5);
    tabByte.byte[0] = 'm';
    tabByte.byte[1] = 'H';
    tabByte.byte[2] = 'E';
    tabByte.byte[3] = 'L';
    tabByte.BLOC = 10;
    tabByte.tabSize = 4;
    tabByte.indexNextByte = 4;

    struct Condition condition;
    condition.magic_Number = "HELP";
    condition.magicNumberSize = 4;
    condition.nbBits = 4;

    result = addBinaryBufferInTab(n, &bufferBinary, &tabByte, &condition);
    assert(result == 1);

    n = 48;
    result = addBinaryBufferInTab(n, &bufferBinary, &tabByte, &condition);
    assert(result == 0);//Magic Number trouvé
}
