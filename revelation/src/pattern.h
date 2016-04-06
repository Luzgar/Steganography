#ifndef PATTERN_H_INCLUDED
#define PATTERN_H_INCLUDED

#include "image.h"

int colorImageCase(struct Img * image, struct BufferBinary * bufferBinary, struct HiddenFileByte * tabByte, int coordX, int coordY, struct Condition* condition, char channel);

int grayImageCase(struct Img * image, struct BufferBinary * bufferBinary, struct HiddenFileByte * tabByte, int coordX, int coordY, struct Condition* condition);

int direct(struct Img * image, struct HiddenFileByte * tabByte, struct Condition * condition);

int reverse(struct Img * image, struct HiddenFileByte * tabByte, struct Condition * condition);

int external_spiral(struct Img * image, struct HiddenFileByte * tabByte, struct Condition * condition);

int internal_spiral(struct Img * image, struct HiddenFileByte * tabByte, struct Condition * condition);
#endif // PATTERN_H_INCLUDED
