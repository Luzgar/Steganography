#include "tools.h"
#include "mathlib.h"

/**
 * @brief Transforme un decimal en binaire et l'ajoute au Binary Buffer
 * @details Cette fonction prend en paramètre un nombre decimal (valeur RGBA ou Gray d'un pixel), extrait le
 * binaire du fichier caché (en fonction du nombre de bits sur lequel celui ci est codé) et l'ajoute au tableau
 * de bits(struct bufferBinary)
 *
 * @param int Valeur RGBA / Gray du pixel
 * @param BUFFERBINARY Tableau de bits
 * @param HIDDENFILEBYTE Tableau de byte du fichier caché
 * @param Condition Conditions de revelation (pattern, channels,...)
 * @return Retourne 0 si on vient de finir de lire le magic_number, 0 sinon.
 */
int addBinaryBufferInTab(unsigned int n, struct BufferBinary * bufferBinary, struct HiddenFileByte * tabByte, struct Condition* condition){
    int c,k;
    for(c=7; c >=0; c--){
        k=n >> c;
        if(c < condition->nbBits){
            if(k & 1){
                bufferBinary->binary[bufferBinary->indexNextBit++] = '1';
            }
            else{
                bufferBinary->binary[bufferBinary->indexNextBit++] = '0';
            }
            if(bufferBinary->indexNextBit == 8){
                if(binaryToByteTab(tabByte, bufferBinary, condition) == 0){
                    tabByte->indexNextByte -= condition->magicNumberSize;
                    return 0;
                }
                bufferBinary->indexNextBit = 0;
            }
        }
    }
    return 1;
}

/**
 * @brief Tableau binaire vers tableau de byte
 * @details Cette fonction est appelé lorsque le tableau de bits et plein. On calcule ainsi la valeur en decimal
 * du nombre binaire et on l'ajoute au tableau de byte du fichier caché.
 *
 * @param HIDDENFILEBYTE Tableau de byte du fichier caché
 * @param BUFFERBINARY Tableau de bits
 * @param Condition Condition de revelation (on s'en sert ici surtout pour le magic number)
 * @return Retourne 1 si le magic number a été decouvert, 0 sinon.
 */
int binaryToByteTab(struct HiddenFileByte * tabByte, struct BufferBinary * bufferBinary, struct Condition * condition){
    int i;
    Byte byte = 0;
    for(i=0; i < 8 ; i+=1){
        if(bufferBinary->binary[i] == '1')
            byte += power(2, 7-i);
    }
    if(tabByte->indexNextByte >= tabByte->tabSize){
        tabByte->byte = realloc(tabByte->byte, tabByte->BLOC+tabByte->tabSize);
        tabByte->tabSize += tabByte->BLOC;
    }
    tabByte->byte[tabByte->indexNextByte++] = byte;

    if(verifyMagicNumber(tabByte, condition) == 0)
        return 1;
    else{
        return 0;
    }
}

/**
 * @brief Verifie si les derniers byte sont le magic number
 * @details Cette fonction verifie les "magicNumberSize" dernier byte du tableau de byte.
 *
 * @param HIDDENFILEBYTE Tableau de byte
 * @param Condition Condition de revelation (ici utilisé pour les informations sur le magic number)
 *
 * @return Retourne 1 si le magic_number est trouvé, 0 sinon
 */
int verifyMagicNumber(struct HiddenFileByte * tabByte, struct Condition * condition){
    if(tabByte->indexNextByte < condition->magicNumberSize)
        return 0;
    int i = 0;
    for(; i < condition->magicNumberSize; ++i){
        if(tabByte->byte[tabByte->indexNextByte - condition->magicNumberSize + i] != condition->magic_Number[i])
            return 0;
    }
    return 1;
}
