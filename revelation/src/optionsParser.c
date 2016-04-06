#include "optionsParser.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>


int isInFileExisting(char * filename){
    if(access(filename, 0) == 0){
        return 1;
    }
    else{
        fprintf(stderr, "[ERREUR ARGUMENT:] Le fichier pour l'option -in n'existe pas ou est incorrect.\n");
        return 0;
    }
}

int isCorrectBitsUsedOption(int bitsUsed){
    if(bitsUsed <= 8 && bitsUsed > 0)
        return 0;
    else {
        fprintf(stderr, "[ERREUR ARGUMENT:] Le nombre de bits voulu est incorrecte. Il doit être compris entre 1 et 8.\n");
        return 1;
    }
}

int isCorrectFinOption(char * Fin){
    if(strcmp(Fin, "PNG") == 0 || strcmp(Fin, "BMP") == 0 || strcmp(Fin, "PPM") == 0 || strcmp(Fin, "PGM") == 0)
        return 0;
    else {
        fprintf(stderr, "[ERREUR ARGUMENT:] L'options -Fin n'accepte uniquement les valeurs suivantes : \"PNG\", \"BMP\", \"PPM\", \"PGM\".\n");
        return 1;
    }
}

int isCorrectChannel(char * channel){
    if(strcmp(channel, "Red") == 0 || strcmp(channel, "Green") == 0 || strcmp(channel, "Blue") == 0 || strcmp(channel, "Alpha") == 0 || strcmp(channel, "Gray") == 0)
        return 0;
    else {
        fprintf(stderr, "[ERREUR ARGUMENT:] Un channel demandé n'existe pas. Le programme accepte uniquement les valeurs suivantes : \"Red\", \"Green\", \"Blue\", \"Alpha\" et \"Gray\".\n");
        return 1;
    }
}

/**
 * @brief Parseur d'options entré en ligne de commande
 * @details Cette fonction analyse la ligne de commande entré par l'utilisateur et associe à chaque options la valeur
 * entrée.
 *
 * @param argv Ligne de commande de l'utilisateur
 * @return Struct OPTIONLIST, contenant toutes les informations de la ligne de commande
 */
struct OptionList parseOption(char* argv[])
{
    struct OptionList optionList;
    optionList.hasInOption = 0;
    optionList.hasOutOption = 0;
    optionList.finOption = "default";
    optionList.channels = malloc(4);
    optionList.channels[0] = 'R';
    optionList.channels[1] = 'G';
    optionList.channels[2] = 'B';
    optionList.channels[3] = 'A';
    optionList.pattern_used = "direct";
    optionList.magic_number = "HELP";
    optionList.magicNumberSize = 4;
    optionList.numberBitsUsed = 1;
    optionList.hasCompressOption = 0;
    optionList.compressLevel = 0;
    optionList.hasShowOption = 0;
    optionList.erreur = 0;
    argv++;

    //int hasChannelOption = 0;

    while(*argv){
        if(argv[0][0] == '-'){
            if(strcmp("-Fin", *argv) == 0){
                ++argv;
                optionList.finOption = *argv;
                if(isCorrectFinOption(optionList.finOption) == 1)
                    optionList.erreur = 1;
            }
            else if(strcmp("-in", *argv) == 0){
                ++argv;
                optionList.hasInOption = 1;
                optionList.inputFile = *argv;
                if(isInFileExisting(optionList.inputFile) == 0)
                    optionList.erreur = 1;
            }
            else if(strcmp("-out", *argv) == 0){
                ++argv;
                optionList.outputFile = *argv;
                optionList.hasOutOption = 1;
            }
            else if(strcmp("-b", *argv) == 0){
                ++argv;
                optionList.numberBitsUsed = atoi(*argv);
            }
            else if(strcmp("-c", *argv) == 0){
                ++argv;
                char channels[20];
                strcpy (channels, *argv);
                char s[2] = ",";
                char * token;
                int cpt = 0;
                token = strtok(channels, s);
                while(token != NULL){
                    if(isCorrectChannel(token) == 1){
                        optionList.erreur = 1;
                        break;
                    }

                    if(strcmp(token, "Red") == 0)
                        optionList.channels[cpt++] = 'R';
                    else if(strcmp(token, "Green") == 0)
                        optionList.channels[cpt++] = 'G';
                    else if(strcmp(token, "Blue") == 0)
                        optionList.channels[cpt++] = 'B';
                    else if(strcmp(token, "Alpha") == 0)
                        optionList.channels[cpt++] = 'A';
                    else if(strcmp(token, "Gray") == 0)
                        optionList.channels[cpt++] = 'Y';

                    token = strtok(NULL, s);
                }
            }
            else if(strcmp("-p", *argv) == 0){
                ++argv;
                optionList.pattern_used = *argv;
            }
            else if(strcmp("-magic", *argv) == 0){
                ++argv;
                optionList.magic_number = malloc(10);
                optionList.magicNumberSize = 0;
                char * hex;
                unsigned decimalValue;
                while(*argv && argv[0][0] != '-'){
                    if(optionList.magicNumberSize%10 == 0)
                        optionList.magic_number = realloc(optionList.magic_number, optionList.magicNumberSize+10);
                    hex = *argv;
                    decimalValue = strtol(hex, NULL, 16);
                    optionList.magic_number[optionList.magicNumberSize++] = decimalValue;
                    ++argv;
                }
                optionList.magic_number[optionList.magicNumberSize] = '\0';
            }
            else if(strcmp("-compress", *argv) == 0){
                optionList.hasCompressOption = 1;
                ++argv;
                if(! *argv){
                    break;
                }
                else if(argv[0][0] != '-'){
                    if(strcmp("more", *argv) == 0){
                        optionList.compressLevel = 1;
                    }
                    else if(strcmp("even_more", *argv) == 0){
                        optionList.compressLevel = 2;
                    }
                    else if(strcmp("recursive", *argv) == 0){
                        optionList.compressLevel = 3;
                    }
                    else if(strcmp("bg", *argv) == 0){
                        optionList.compressLevel = 4;
                    }
                    else{
                        fprintf(stderr, "The asked compress level don't exist.\n");
                        optionList.erreur = 1;
                    }
                }
            }
            else if(strcmp("-show", *argv) == 0){
                optionList.hasShowOption = 1;
                ++argv;
            }
            else
                ++argv;
        }
        else
            argv++;
    }

    if(optionList.hasInOption == 0){
        fprintf(stderr, "[ERREUR ARGUMENT:] L'option -in n'a pas ete specifie. Elle est obligatoire !\n");
        optionList.erreur = 1;
    }
    if(isCorrectBitsUsedOption(optionList.numberBitsUsed) == 1)
        optionList.erreur = 1;

    if(strcmp(optionList.pattern_used, "direct") != 0 && strcmp(optionList.pattern_used, "reverse") != 0
       && strcmp(optionList.pattern_used, "external_spiral") != 0 && strcmp(optionList.pattern_used, "internal_spiral") != 0){
        fprintf(stderr, "[ERREUR ARGUMENT:] Pattern incorrecte.\n");
        optionList.erreur = 1;
    }

    if(optionList.hasShowOption == 1 && optionList.hasCompressOption == 0){
        fprintf(stderr, "[ERREUR ARGUMENT:] L'option -show est précisé sans que l'option -compress soit présente.\n");
        optionList.erreur = 1;
    }


    return optionList;
};
