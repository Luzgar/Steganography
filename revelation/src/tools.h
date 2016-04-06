#ifndef TOOLS_H_INCLUDED
#define TOOLS_H_INCLUDED

#include "mathlib.h"
#include "image.h"

struct BufferBinary {
    char binary[8];
    int indexNextBit;
};

struct HiddenFileByte {
    unsigned char * byte;
    unsigned long indexNextByte;
    int BLOC;
    unsigned long tabSize;
    int valid;
};

typedef unsigned char Byte;
int addBinaryBufferInTab(unsigned int n, struct BufferBinary * bufferBinary, struct HiddenFileByte * tabByte, struct Condition* condition);
int binaryToByteTab(struct HiddenFileByte * tabByte, struct BufferBinary * bufferBinary, struct Condition * condition);
int verifyMagicNumber(struct HiddenFileByte * tabByte, struct Condition * condition);

#endif // TOOLS_H_INCLUDED
