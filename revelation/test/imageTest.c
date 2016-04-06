#include "image.h"
#include <assert.h>

void testImage(){
	testLoadImageNoError();
	testLoadImageFakeImage();
	testConflictFormatChannel();
	printf("%s\n", "Tous les test de image.c reussi.");
}

void testLoadImageNoError(){
    struct Img image, image2, image3, image4;
    int result, result2, result3, result4;

    result = loadImage("ES.png", "PNG", &image, "RGB");
    assert(1 == result);
    assert(image.isColor == 1);
    result2 = loadImage("lena.bmp", "BMP", &image2, "RGB");
    assert(1 == result2);
    assert(image2.isColor == 1);
    result3 = loadImage("lena.ppm", "PPM", &image3, "RGB");
    assert(1 == result3);
    assert(image3.isColor == 1);
    result4 = loadImage("lena.pgm", "PGM", &image4, "Y");
    assert(1 == result4);
    assert(image4.isColor == 0);
}

void testLoadImageFakeImage(){
	struct Img image;
    int result;

    result = loadImage("fake.png", "PNG", &image, "RGB");
    assert(0 == result);
}

void testConflictFormatChannel(){
	struct Img image, image2, image3, image4;
    int result, result2, result3, result4;

    result = loadImage("ES.png", "PNG", &image, "RYB");
    assert(0 == result);
    result2 = loadImage("lena.bmp", "BMP", &image2, "RBY");
    assert(0 == result2);
    result3 = loadImage("lena.ppm", "PPM", &image3, "YRB");
    assert(0 == result3);
    result4 = loadImage("lena.pgm", "PGM", &image4, "BY");
    assert(0 == result4);
}
