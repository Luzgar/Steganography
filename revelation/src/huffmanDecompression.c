#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "tools.h"
#include "huffmanDecompression.h"
#include "binaryTree.h"
#include "mathlib.h"

int cpt;/*Global counter to browse the compress byte table*/

/**
 * @brief Function who return the code of a character in the dictionnary
 *
 * @param decimalCode Byte table of the code of a character present in the dictionnary
 * @param nbBitsCode On how many bit are the character's code
 * @param nbByte On how many bytes are the character's code
 * @return Return the code in a char *
 */
char * getCode(int * decimalCode, int nbBitsCode, int nbByte){

    char * binaryOfCode = malloc(nbBitsCode);
    int i = 0;
    int countBitsUsed = 0;
    int decimal;
    for(; i < nbByte; ++i){
        int c,k,j;
        decimal = decimalCode[i];
        for(c=7; c >=0; c--){
            k= decimal >> c;
            if(countBitsUsed < nbBitsCode){
                if(k & 1){
                    binaryOfCode[countBitsUsed++] = '1';
                }
                else{
                    binaryOfCode[countBitsUsed++] = '0';
                }
            }
        }
    }
    binaryOfCode[nbBitsCode] = '\0';
    return binaryOfCode;
}

/**
 * @brief Function who simply reset a
 * @details [long description]
 *
 * @param buffer [description]
 */
void resetBuffer(char * buffer){
    int i;
    for(i = 0; i < 8; ++i){
        buffer[i] = '0';
    }
}

/**
 * @brief Function who compare two binary table
 * @details [long description]
 *
 * @param firstBinary char * of the first binary
 * @param sizeOfFirst Size of the first binary
 * @param secondBinary char * of the second binary
 * @param sizeOfSecond Size of the second binary
 * @return Return 1 if they are equals, 0 otherwise
 */
int isSameBinary(char * firstBinary, int sizeOfFirst, char * secondBinary, int sizeOfSecond){
    if(sizeOfFirst != sizeOfSecond)
        return 0;
    int i;
    for(i = 0; i < sizeOfFirst; ++i){
        if(firstBinary[i] != secondBinary[i])
            return 0;
    }
    return 1;
}

/**
 * @brief Function to get the position of a code in the dictionnary letterCode table
 *
 * @param buffer Buffer of a binary to search in the dictionnary
 * @param Dictionnary Dictionnary used for the compression
 * @param sizeBuffer Size of the buffer
 * @return Return the position in the dictionnary letterCode table, -1 otherwise
 */
int getPositionOfCodeInDictionnary(char * buffer, struct Dictionnary * dictionnary, int sizeBuffer){
    int counter = 0;
    //printf("%d\n",dictionnary->nbOfSymbol);
    for(; counter <= dictionnary->nbOfSymbol; counter++){
        int sizeOfCode = dictionnary->sizeOfCode[counter];
        if(isSameBinary(buffer, sizeBuffer, dictionnary->letterCode[counter], sizeOfCode) == 1){
            return counter;
        }
    }
    return -1;
}

/**
 * @brief Function who fill the dictionnary struct
 *
 * @param HiddenFileByte Struct representing the byte table of the hidden message
 * @return Return a Dictionnary struct
 */
struct Dictionnary * fillDictionnary(struct HiddenFileByte * tabByte){

    struct Dictionnary * dictionnary = malloc(sizeof(struct Dictionnary));
    int nbSymbol = tabByte->byte[cpt++];

    int counterSizeLetterCode = nbSymbol+1;

    dictionnary->letter = malloc(nbSymbol+1);
    dictionnary->sizeOfCode = malloc(nbSymbol+1);
    dictionnary->letterCode = malloc(nbSymbol+1);
    //dictionnary.letterCodeDecimal = malloc((nbSymbol+1)*sizeof(int));
    dictionnary->nbOfSymbol = nbSymbol;
    dictionnary->maxSize = 0;

    int nbBitsCode;
    int * code;
    int i = 0;
    int j = 0;
    for(; i <= nbSymbol; ++i){
        dictionnary->letter[i] = tabByte->byte[cpt++];
        //printf("Letter : %c\n", dictionnary->letter[i]);
        nbBitsCode = tabByte->byte[cpt++];
        dictionnary->maxSize = maximum(nbBitsCode, dictionnary->maxSize);
        dictionnary->sizeOfCode[i] = nbBitsCode;
        counterSizeLetterCode+= nbBitsCode;
        dictionnary->letterCode = realloc(dictionnary->letterCode, counterSizeLetterCode);
        int add1 = 0;
        if(nbBitsCode%8 == 0)
            add1 = 0;
        else
            add1 = 1;
        int nbByte = (nbBitsCode/8)+add1;
        free(code);
        code = malloc(nbByte*sizeof(int));
        j=0;
        //printf("Code : ");
        for(; j < nbByte; ++j){
            code[j] = tabByte->byte[cpt++];
            //printf("%d ", code[j]);
        }
        //printf("\n\n");
        dictionnary->letterCode[i] = getCode(code, nbBitsCode, nbByte);
    }

    int lastPadding = tabByte->byte[cpt++];
    dictionnary->lastPadding = lastPadding;

    return dictionnary;
}

/**
 * @brief Function who get the shape of the encoded tree
 * 
 * @param Dictionnary Dictionnary, use to know how many symbols are compressed
 * @param HiddenFileByte Byte of the compressed file
 * 
 * @return Reture the shape of the tree
 */
char * getTreeShape(struct Dictionnary * dictionnary, struct HiddenFileByte * tabByte){
    int countLeaf = 0;
    int BLOC = 10;
    char * treeShape = malloc(BLOC);
    int treeShapeSize = 0;
    treeShape[0] = '1';
    ++treeShapeSize;
    int i;
    int byte;
    char * buffer = malloc(8);

    while (countLeaf <= dictionnary->nbOfSymbol-1){
        buffer = realloc(buffer, 8);
        byte = tabByte->byte[cpt++];
        buffer = decimalToBinary(byte, 8);
        for(i=0; i < 8; ++i){
            if(buffer[i] == '0')
                ++countLeaf;
            if(countLeaf > dictionnary->nbOfSymbol-1){
                break;
            }
            treeShape = realloc(treeShape, treeShapeSize+BLOC);
            treeShape[treeShapeSize++] = buffer[i];     
        }
    }
    treeShape[treeShapeSize++]='0';
    treeShape[treeShapeSize++]='0';
    treeShape[treeShapeSize] = '\0';
    treeShape = realloc(treeShape, treeShapeSize);
    return treeShape;
}


/**
 * @brief Fill the beginning of the dictionnary (the symbols compressed) in case of a encoded tree 
 * 
 * @param HiddenFileByte Byte tab of the hidden file
 * @return return the dictionnary filled
 */
struct Dictionnary * fillDictionnaryWithEncodingTree(struct HiddenFileByte * tabByte){
    
    struct Dictionnary * dictionnary = malloc(sizeof(struct Dictionnary));
    int nbSymbol = tabByte->byte[cpt++];
    int counterSizeLetterCode = nbSymbol+1;

    dictionnary->letter = malloc(nbSymbol+1);
    dictionnary->sizeOfCode = malloc(nbSymbol+1);
    dictionnary->nbOfSymbol = nbSymbol;
    dictionnary->maxSize = 0;
    int i = 0;
    for(; i <= nbSymbol; ++i){
        dictionnary->letter[i] = tabByte->byte[cpt++];
    }
    return dictionnary;
}


/**
 * @brief Function who transform the byte table to a char * representing the binary
 * @details [long description]
 *
 * @param HiddenFileByte Byte table of the hidden message
 * @param sizeOfBinaryTab Size of the binary in return
 *
 * @return Return the binary in a char *
 */
char * getBinaryOfFile(struct HiddenFileByte * tabByte, int sizeOfBinaryTab, int positionToStart){
    char * binaryOfFile = malloc(sizeOfBinaryTab+1);

    int valueOfByte;
    int cptBinary = 0;
    int i,c,k;
    for(i = 0; positionToStart < tabByte->indexNextByte; ++i){
        valueOfByte = tabByte->byte[positionToStart++];
        for(c=7; c >=0; c--){
            k=valueOfByte >> c;
            if(k & 1){
                binaryOfFile[cptBinary++] = '1';
            }
            else{
                binaryOfFile[cptBinary++] = '0';
            }
        }
    }
    binaryOfFile[cptBinary] = '\0';
    return binaryOfFile;
}

/**
 * @brief Function who decompress only the message (not the dictionnary)
 *
 * @param HiddenFileByte Final Byte table, filled by decompressing the compressed message
 * @param Dictionnary Dictionnary used for the compression
 * @param sizeOfBinaryTab Size of the binary table
 * @param binaryOfFile Binary of the hidden message
 * @return Return 0 if an erro occured, 1 otherwise
 */
int decompressMessage(struct HiddenFileByte * finalByteTab, struct Dictionnary * dictionnary, int sizeOfBinaryTab, char * binaryOfFile){
    char * buffer = malloc(dictionnary->maxSize);
    resetBuffer(buffer);
    int cptBuffer = 0;
    int position=-1;
    int i;
    int size;
    if(dictionnary->lastPadding == 0){
        size = sizeOfBinaryTab;
    }
    else {
        size = sizeOfBinaryTab-(8-dictionnary->lastPadding);
    }
    for(i = 0; i < size; ++i){
        if(cptBuffer > dictionnary->maxSize){
            fprintf(stderr, "Error ! A symbol has not been recognized in the compressed message !\n");
            return 0;
        }
        buffer[cptBuffer++] = binaryOfFile[i];
        position = getPositionOfCodeInDictionnary(buffer, dictionnary, cptBuffer);
        if(position != -1){
            cptBuffer = 0;
            resetBuffer(buffer);
            if(finalByteTab->tabSize <= finalByteTab->indexNextByte){
                finalByteTab->byte = realloc(finalByteTab->byte, finalByteTab->tabSize + finalByteTab->BLOC);
                finalByteTab->tabSize += finalByteTab->BLOC;
            }
            finalByteTab->byte[finalByteTab->indexNextByte++] = dictionnary->letter[position];
        }
    }
    //finalByteTab->byte[finalByteTab->indexNextByte] = '\0';
    return 1;
}

/**
 * @brief Function called in case of a default decompression (-compress option with no arguments)
 * 
 * @param HiddenFileByte Byte tab of the compressed file
 * @param HiddenFileByte Byte tab of the decompressed file
 * @param hasShowOption Paramaters to know if we print the dictionnary of not
 * @return Return 0 if an error occured during the decompression, 1 otherwise
 */
int defaultDecompression(struct HiddenFileByte * tabByte, struct HiddenFileByte * finalByteTab, int hasShowOption){

    cpt = 0;
    struct Dictionnary * dictionnary;
    dictionnary = fillDictionnary(tabByte);
    int sizeOfBinaryTab = tabByte->indexNextByte*8 - (cpt)*8;
    char * binaryOfFile = getBinaryOfFile(tabByte, sizeOfBinaryTab, cpt);

    if(decompressMessage(finalByteTab, dictionnary, sizeOfBinaryTab, binaryOfFile) == 1){
        if(hasShowOption == 1){
            int i;
            for(i=0; i <= dictionnary->nbOfSymbol; ++i){
                printf("0x%X: %s\n", dictionnary->letter[i], dictionnary->letterCode[i]);
            }
            printf(binaryOfFile);
            printf("\n");
        }
        finalByteTab->valid = 1;
        return 1;
    }
    finalByteTab->valid = 0;
    return 0;
}

/**
 * @brief Function called when the tree is encoded
 * 
 * @param HiddenFileByte Byte tab of the compressed hidden file
 * @param HiddenFileByte Byte tab of the decompressed hidden file
 * @param hasShowOption Parameters to know if we print the dictionnary or not
 * @return Return 0 if an error occured during the decompression, 1 otherwise
 */
int huffmanTreeEncodingDecompression(struct HiddenFileByte * tabByte, struct HiddenFileByte * finalByteTab, int hasShowOption){
    struct Dictionnary * dictionnary = fillDictionnaryWithEncodingTree(tabByte);
    if(dictionnary->nbOfSymbol == 0){
        dictionnary->sizeOfCode[0] = 1;
        dictionnary->letterCode = malloc(2);
        dictionnary->letterCode[0] = "0";
        char * treeShape = getTreeShape(dictionnary, tabByte);
        cpt++;
        dictionnary->maxSize = 1;
    }
    else {
        int i = 0;
        char * treeShape = getTreeShape(dictionnary, tabByte);
        
        node root = malloc(sizeof(struct node));
        root->is_leaf = 0;
        root->left = NULL;
        root->right = NULL;

        ++treeShape;
        char * shape = construct_tree(treeShape, root);
        fillLetterCode(root, dictionnary);

    }
    dictionnary->lastPadding = tabByte->byte[cpt++];
    int sizeOfBinaryTab = tabByte->indexNextByte*8 - (cpt)*8;
    char * binaryOfFile = getBinaryOfFile(tabByte, sizeOfBinaryTab, cpt);
    
    if(decompressMessage(finalByteTab, dictionnary, sizeOfBinaryTab, binaryOfFile) == 1){
        if(hasShowOption == 1){
            int i;
            for(i=0; i <= dictionnary->nbOfSymbol; ++i){
                printf("0x%X: %s\n", dictionnary->letter[i], dictionnary->letterCode[i]);
            }
            printf(binaryOfFile);
            printf("\n");
        }
        finalByteTab->valid = 1;
        return 1;
    }
    finalByteTab->valid = 0;
    return 0;
}

int huffmanTreeSymbolsOptimization(struct HiddenFileByte * tabByte, struct HiddenFileByte * finalByteTab, int hasShowOption){

    return 0;
}


/**
 * @brief Main function of the decompression. It decompress the dictionnary and the message by calling all needed functions
 *
 * @param HiddenFileByte Byte table of the hidden file
 * @param hasShowOption Have the users write the show option or not (for print the dictionnary in stdout)
 *
 * @return Byte Table of the decompressed file
 */
struct HiddenFileByte decompress(struct HiddenFileByte * tabByte, int hasShowOption, int levelOfCompression){

    struct HiddenFileByte finalByteTab;
    finalByteTab.BLOC = 200;
    finalByteTab.byte = malloc(finalByteTab.BLOC);
    finalByteTab.tabSize = finalByteTab.BLOC;
    finalByteTab.indexNextByte = 0;

    if(levelOfCompression == 0){
        if(defaultDecompression(tabByte, &finalByteTab, hasShowOption) == 0){
            fprintf(stderr, "An error occured during the default decompression.\n");
        }
    }
    else if (levelOfCompression == 1){
        if(huffmanTreeEncodingDecompression(tabByte, &finalByteTab, hasShowOption) == 0){
            fprintf(stderr, "An error occured during the \"more\" compression.\n");
        }
    }
    /*else if(levelOfCompression == 2){
        if(huffmanTreeSymbolsOptimization(tabByte, finalByteTab, hasShowOption) == 0){
            fprintf(stderr, "An error occured during the \"more\" compression.\n");
        }
    }*/
    else {
        fprintf(stderr, "The asked level of compression don't exist or are not implemented yet.\n");
    }
    
    return finalByteTab;
}
