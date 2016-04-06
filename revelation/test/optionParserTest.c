#include "optionsParser.h"
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <string.h>

void parseOptionTest(){

    noErrorTest();
    defaultValueTest();
    inputFileErrorTest();
    finOptionError();
    bitsUsedError();
    channelsError();
    patternError();
    //Faire des assert pour chaques options

    printf("%s\n", "Tous les tests Options Parser reussi");
}

void noErrorTest(){
    struct OptionList optionList;

    char * var[18];

    var[0] = "stegano.exe";
    var[1] = "-in";
    var[2] = "ES.png";
    var[3] = "-out";
    var[4] = "demo.txt";
    var[5] = "-c";
    var[6] = "Red";
    var[7] = "-p";
    var[8] = "external_spiral";
    var[9] = "-b";
    var[10] = "1";
    var[11] = "-Fin";
    var[12] = "PNG";
    var[13] = "-magic";
    var[14] = "48 45 4C 50";
    var[15] = "-c";
    var[16] = "Red,Green,Blue";
    var[17] = '\0';

    optionList = parseOption(var);

    assert(strcmp(optionList.inputFile, "ES.png") == 0);
    assert(strcmp(optionList.outputFile, "demo.txt") == 0);
    assert(optionList.channels[0] == 'R');
    assert(strcmp(optionList.pattern_used, "external_spiral") == 0);
    assert(optionList.numberBitsUsed == 1);
    assert(strcmp(optionList.finOption, "PNG")==0);
}

void defaultValueTest(){

    struct OptionList optionList;
    char * var[4];

    var[0] = "stegano.exe";
    var[1] = "-in";
    var[2] = "ES.png";
    var[3] = '\0';

    optionList = parseOption(var);

    assert(strcmp(optionList.inputFile, "ES.png") == 0);
    assert(optionList.channels[0] == 'R');
    assert(optionList.channels[1] == 'G');
    assert(optionList.channels[2] == 'B');
    assert(strcmp(optionList.pattern_used, "direct") == 0);
    assert(optionList.numberBitsUsed == 1);
    assert(strcmp(optionList.magic_number, "HELP") == 0);
}

void inputFileErrorTest(){
    struct OptionList optionList;

    char * var[4];

    var[0] = "stegano.exe"; //N'importe
    var[1] = "-in";
    var[2] = "E.png"; //Fichier inexistant dans le repertoire
    var[3] = '\0';

    optionList = parseOption(var);

    assert(optionList.erreur == 1);
}

void finOptionError(){

    struct OptionList optionList;

    char * var[6];

    var[0] = "stegano.exe"; //N'importe
    var[1] = "-in";
    var[2] = "ES.png"; //Fichier inexistant dans le repertoire
    var[3] = "-Fin";
    var[4] = "BMB";
    var[5] = '\0';

    optionList = parseOption(var);

    assert(strcmp(optionList.inputFile, "ES.png") == 0);
    assert(optionList.erreur == 1);
}

void bitsUsedError(){
    struct OptionList optionList;

    char * var[6];

    var[0] = "stegano.exe"; //N'importe
    var[1] = "-in";
    var[2] = "ES.png"; //Fichier inexistant dans le repertoire
    var[3] = "-b";
    var[4] = "9";
    var[5] = '\0';

    optionList = parseOption(var);

    assert(strcmp(optionList.inputFile, "ES.png") == 0);
    assert(optionList.erreur == 1);
}

void channelsError(){
    struct OptionList optionList;

    char * var[6];

    var[0] = "stegano.exe"; //N'importe
    var[1] = "-in";
    var[2] = "ES.png"; //Fichier inexistant dans le repertoire
    var[3] = "-c";
    var[4] = "Reda";
    var[5] = '\0';

    optionList = parseOption(var);
    assert(strcmp(optionList.inputFile, "ES.png") == 0);
    assert(optionList.erreur == 1);
}

void patternError(){
    struct OptionList optionList;

    char * var[6];

    var[0] = "stegano.exe"; //N'importe
    var[1] = "-in";
    var[2] = "ES.png"; //Fichier inexistant dans le repertoire
    var[3] = "-p";
    var[4] = "ababa";
    var[5] = '\0';

    optionList = parseOption(var);
    assert(optionList.erreur == 1);
}

