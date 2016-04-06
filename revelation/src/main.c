#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "FreeImage.h"
#include "mathlib.h"
#include "optionsParser.h"
#include "tools.h"
#include "image.h"
#include "output.h"
#include "pattern.h"
#include "huffmanDecompression.h"

int main(int argc, char *argv[])
{
    struct OptionList optionList;
    /*On remplit la structure OPTIONLIST avec les arguments passé en ligne de commande par l'utilisateur*/
    optionList = parseOption(argv);
    if(optionList.erreur == 0){
        struct Img img;
        if(loadImage(optionList.inputFile, optionList.finOption, &img, optionList.channels) == 1){

            struct HiddenFileByte tabByte;
            tabByte.BLOC = 200;
            tabByte.byte = malloc(tabByte.BLOC);
            tabByte.tabSize = tabByte.BLOC;

            struct Condition condition;
            condition.magic_Number      = optionList.magic_number;
            condition.magicNumberSize   = optionList.magicNumberSize;
            condition.nbBits            = optionList.numberBitsUsed;
            condition.pattern           = optionList.pattern_used;
            condition.channels          = optionList.channels;

            if(extractByteHiddenFileImage(&img, &tabByte, &condition) == 1){
                if(optionList.hasCompressOption == 1){
                    struct HiddenFileByte finalByteTab = decompress(&tabByte, optionList.hasShowOption, optionList.compressLevel);
                    if(finalByteTab.valid == 1){
                        if(optionList.hasOutOption == 1)
                            createHideFile(optionList.outputFile, &finalByteTab);
                        else
                            printOnStandardOutput(&finalByteTab);
                        printf("Processus termine sans erreurs !\n");
                    }
                }
                else {
                    if(optionList.hasOutOption == 1)
                        createHideFile(optionList.outputFile, &tabByte);
                    else
                        printOnStandardOutput(&tabByte);

                    printf("Processus termine sans erreurs !\n");
                }
            }
            else {
                fprintf(stderr,"Le magic number n'a pas été trouvé dans l'image. Verifiez les valeurs des options !\n");
                return 1;
            }
        }
        else{
            fprintf(stderr,"Erreur lors du chargement de l'image ! Verifiez le nom de l'image, ou mettez une option -Fin compatible avec l'image.\n");
            return 1;
        }
    }
    else {
        fprintf(stderr,"Arret premature du programme ! Une erreur a été detectée dans les options !\n");
        return 1;

    }
    return 0;
}


