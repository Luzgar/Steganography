#ifndef IMAGE_H_INCLUDED
#define IMAGE_H_INCLUDED

#include "FreeImage.h"
#include "pattern.h"
#include "tools.h"

struct Img {
    FIBITMAP* bitmapImage;
    int height;
    int width;
    int isColor;
};

struct Condition{
    int nbBits;
    char * channels;
    char * magic_Number;
    int magicNumberSize;
    char * pattern;
};

int loadImage(char * filename, char * finOption, struct Img* image, char * channels);

int extractByteHiddenFileImage(struct Img* image, struct HiddenFileByte* tabByte, struct Condition* condition);

#endif // IMAGE_H_INCLUDED
