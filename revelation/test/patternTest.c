#include "tools.h"
#include "pattern.h"
#include "image.h"
#include <assert.h>

void patternTest(){
    //directPatternTest(); VOIR COMMENTAIRE DANS LA FONCTION
    reversePatternTest();
    external_spiralPatternTest();
    internal_spiralPatternTest();
    printf("Tous les tests de pattern ont reussis.");

}

void directPatternTest(){
    char * filename = "direct.png"; //PRENDRE UN FICHIER CORRECT
    struct Img image;
    assert(loadImage(filename, "default", &image, "RGB")==1);
    struct HiddenFileByte tabByte;
    tabByte.BLOC = 200;
    tabByte.byte = malloc(tabByte.BLOC);
    tabByte.tabSize = tabByte.BLOC;
    struct Condition condition;
    condition.nbBits = 1;
    condition.magic_Number = "yolo";
    condition.magicNumberSize = 4;
    condition.pattern="direct";
    condition.channels = "RGB";
    assert(extractByteHiddenFileImage(&image, &tabByte, &condition)==1);
}

void reversePatternTest(){
    char * filename = "reverse.png";
    struct Img image;
    assert(loadImage(filename, "default", &image, "RGB")==1);
    struct HiddenFileByte tabByte;
    tabByte.BLOC = 200;
    tabByte.byte = malloc(tabByte.BLOC);
    tabByte.tabSize = tabByte.BLOC;
    struct Condition condition;
    condition.nbBits = 1;
    condition.magic_Number = "yolo";
    condition.magicNumberSize = 4;
    condition.pattern="reverse";
    condition.channels = "RGB";
    assert(extractByteHiddenFileImage(&image, &tabByte, &condition)==1);
}

void external_spiralPatternTest(){
    char * filename = "ES.png";
    struct Img image;
    assert(loadImage(filename, "default", &image, "R")==1);
    struct HiddenFileByte tabByte;
    tabByte.BLOC = 200;
    tabByte.byte = malloc(tabByte.BLOC);
    tabByte.tabSize = tabByte.BLOC;
    struct Condition condition;
    condition.nbBits = 1;
    condition.magic_Number = "HELP";
    condition.magicNumberSize = 4;
    condition.pattern="external_spiral";
    condition.channels = "RGB";
    assert(external_spiral(&image, &tabByte, &condition) == 1);
}

