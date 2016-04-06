#include "pattern.h"
#include "image.h"
#include "tools.h"

/**
 * @brief Recuperer les informations d'un pixels pour une image en couleur
 * @details Cette fonction permet de recuperer l'informations d'un pixel (grâce à ces coordonnées) sur un channel précis
 *
 * @param IMG Image sur lequel on recupère les informations
 * @param BUFFERBINARY Tableau de binaire
 * @param HIDDENFILEBYTE Tableau de byte, correspondant au fichier caché dans l'image
 * @param coordX Coordonnée sur l'axe des X du pixel
 * @param coordY Coordonnée sur l'axe des Y du pixel
 * @param Condition Condition de révélation du message (nombre de bits, magic_number,...)
 * @param channel Channel sur lequel lire l'information
 * @return Return 1 si le magic_number a été trouvé, 0 sinon
 */
int colorImageCase(struct Img * image, struct BufferBinary * bufferBinary, struct HiddenFileByte * tabByte, int coordX, int coordY, struct Condition* condition, char channel){
    RGBQUAD color;
    BYTE red, green, blue, alpha;
    if(FreeImage_GetPixelColor(image->bitmapImage, coordX, coordY, &color)){
        if(channel == 'R'){
            red = color.rgbRed;
            if(addBinaryBufferInTab(red, bufferBinary, tabByte, condition) == 0){
                return 1;
            }
        }
        else if(channel == 'G'){
            green = color.rgbGreen;
            if(addBinaryBufferInTab(green, bufferBinary, tabByte, condition) == 0){
                return 1;
            }
        }
        else if(channel == 'B'){
            blue = color.rgbBlue;
            if(addBinaryBufferInTab(blue, bufferBinary, tabByte, condition) == 0){
                return 1;
            }
        }
        else if(channel == 'A'){
            alpha = color.rgbReserved;
            if(addBinaryBufferInTab(alpha, bufferBinary, tabByte, condition) == 0){
                return 1;
            }
        }
    }
    return 0;
}

/**
 * @brief Recuperer l'information d'un pixel sur une image en niveau de gris
 *
 * @param IMG Image sur lequel on recupère les informations
 * @param BUFFERBINARY Tableau de binaire
 * @param HIDDENFILEBYTE Tableau de byte, correspondant au fichier caché dans l'image
 * @param coordX Coordonnée sur l'axe des X du pixel
 * @param coordY Coordonnée sur l'axe des Y du pixel
 * @param Condition Condition de révélation du message (nombre de bits, magic_number,...)
 * @return Return 1 si le magic_number a été trouvé, 0 sinon
 */
int grayImageCase(struct Img * image, struct BufferBinary * bufferBinary, struct HiddenFileByte * tabByte, int coordX, int coordY, struct Condition* condition){
    BYTE gray;
    if(FreeImage_GetPixelIndex(image->bitmapImage, coordX, coordY, &gray)){
        if(addBinaryBufferInTab(gray, bufferBinary, tabByte, condition) == 0){
            return 1;
        }
    }
    return 0;
}

/**
 * @brief Pattern de revelation direct
 *
 * @param IMG Image sur laquelle on recupère l'information
 * @param HIDDENFILEBYTE Tableau de byte du message caché
 * @param Condition Condition de révélation
 * @return Return 1 si le magic_number a été trouvé, 0 sinon (erreur)
 */
int direct(struct Img * image, struct HiddenFileByte * tabByte, struct Condition * condition){
    RGBQUAD color;
    BYTE blue, red, green, alpha;
    struct BufferBinary bufferBinary;
    bufferBinary.indexNextBit = 0;
    tabByte->indexNextByte = 0;
    int stopFlag = 0;
    int i, j, k;
    char channel;
    for(i = 0; i < strlen(condition->channels); ++i){
        channel = condition->channels[i];
        for(j =image->height-1; j >= 0; --j){
            for(k=0; k < image->width; ++k){
                if(image->isColor == 1)
                    stopFlag = colorImageCase(image, &bufferBinary, tabByte, k, j, condition, channel);
                else
                    stopFlag = grayImageCase(image, &bufferBinary, tabByte, k, j, condition);
                if(stopFlag == 1) break;
            }
            if(stopFlag == 1) break;
        }
        if(stopFlag == 1) break;
    }
    if(stopFlag == 1)
        return 1;
    return 0;
}

/**
 * @brief Pattern de revelation reverse (inverse du direct)
 *
 * @param IMG Image sur laquelle on recupère l'information
 * @param HIDDENFILEBYTE Tableau de byte du message caché
 * @param Condition Condition de révélation
 * @return Return 1 si le magic_number a été trouvé, 0 sinon (erreur)
 */
int reverse(struct Img * image, struct HiddenFileByte * tabByte, struct Condition * condition){
    RGBQUAD color;
    BYTE blue, red, green, alpha;
    struct BufferBinary bufferBinary;
    bufferBinary.indexNextBit = 0;
    tabByte->indexNextByte = 0;
    int stopFlag = 0;
    int i, j, k;
    char channel;
    for(i = 0; i < strlen(condition->channels); ++i){
        channel = condition->channels[i];
        for(j = 0; j < image->height; ++j){
            for(k=image->width-1; k >= 0; --k){

                if(image->isColor == 1)
                    stopFlag = colorImageCase(image, &bufferBinary, tabByte, k, j, condition, channel);
                else
                    stopFlag = grayImageCase(image, &bufferBinary, tabByte, k, j, condition);

                if(stopFlag == 1) break;
            }
            if(stopFlag == 1) break;
        }
        if(stopFlag == 1) break;
    }
    if(stopFlag == 1)
        return 1;
    return 0;
}

/**
 * @brief Pattern de revaltion external_spiral. On part en haut à gauche de l'image puis on tourne en spiral dans le sens des aiguilles d'une montre
 *
 * @param IMG Image sur laquelle on recupère l'information
 * @param HIDDENFILEBYTE Tableau de byte du message caché
 * @param Condition Condition de révélation
 * @return Return 1 si le magic_number a été trouvé, 0 sinon (erreur)
 */
int external_spiral(struct Img * image, struct HiddenFileByte * tabByte, struct Condition * condition){
    RGBQUAD color;
    BYTE blue, red, green, alpha;
    struct BufferBinary bufferBinary;
    bufferBinary.indexNextBit = 0;
    tabByte->indexNextByte = 0;
    int stopFlag = 0;
    int i,j,k;
    char channel;
    for(k = 0; k < strlen(condition->channels); ++k){
        int top = image->height-1;
        int down = 0;
        int left = 0;
        int right = image->width - 1;
        channel = condition->channels[k];
        while(1){
            for(j = left; j <= right; ++j){
                if(image->isColor == 1)
                    stopFlag = colorImageCase(image, &bufferBinary, tabByte, j, top, condition, channel);
                else
                    stopFlag = grayImageCase(image, &bufferBinary, tabByte, j, top, condition);
                if(stopFlag == 1) break;
            }

            if((top < down || left > right) || stopFlag == 1)
                break;

            top--;

            for(i = top; i >= down; --i){
                if(image->isColor == 1)
                    stopFlag = colorImageCase(image, &bufferBinary, tabByte, right, i, condition, channel);
                else
                    stopFlag = grayImageCase(image, &bufferBinary, tabByte, right, i, condition);
                if(stopFlag == 1) break;
            }

            if((top < down || left > right) || stopFlag == 1)
                break;

            right--;

            for(j = right; j >= left; --j){
                if(image->isColor)
                    stopFlag = colorImageCase(image, &bufferBinary, tabByte, j, down, condition, channel);
                else
                    stopFlag = grayImageCase(image, &bufferBinary, tabByte, j, down, condition);
                if(stopFlag == 1) break;
            }

            if((top < down || left > right) || stopFlag == 1)
                break;

            down++;

            for(i = down; i <= top; ++i){

                if(image->isColor)
                    stopFlag = colorImageCase(image, &bufferBinary, tabByte, left, i, condition, channel);
                else
                    stopFlag = grayImageCase(image, &bufferBinary, tabByte, left, i, condition);

                if(stopFlag == 1) break;
            }

            if((top < down || left > right) || stopFlag == 1)
                break;
            left++;
        }
        if(stopFlag == 1) break;
    }
    if(stopFlag == 1)
        return 1;
    return 0;
}

/**
 * @brief Pattern de revelation internal_spiral. Pour cette spiral, on commence du centre de l'image
 *
 * @param IMG Image sur laquelle on recupère l'information
 * @param HIDDENFILEBYTE Tableau de byte du message caché
 * @param Condition Condition de révélation
 * @return Return 1 si le magic_number a été trouvé, 0 sinon (erreur)
 */
int internal_spiral(struct Img * image, struct HiddenFileByte * tabByte, struct Condition * condition){
    RGBQUAD color;
    BYTE blue, red, green, alpha;
    struct BufferBinary bufferBinary;
    bufferBinary.indexNextBit = 0;
    tabByte->indexNextByte = 0;
    int stopFlag = 0;
    int i,j,k;
    char channel;
    for(k = 0; k < strlen(condition->channels); ++k){
        int top = image->height/2;
        int down = image->height/2-1;
        int left = image->width/2;
        int right = image->width/2+1;
        channel = condition->channels[k];
        while(1){
            for(j = down; j <= top; ++j){
                if(image->isColor == 1)
                    stopFlag = colorImageCase(image, &bufferBinary, tabByte, left, j, condition, channel);
                else
                    stopFlag = grayImageCase(image, &bufferBinary, tabByte, left, j, condition);
                if(stopFlag == 1) break;
            }

            left--;
            if(left < 0 || stopFlag == 1)
                break;

            for(i = right; i >= left; --i){
                if(image->isColor == 1)
                    stopFlag = colorImageCase(image, &bufferBinary, tabByte, right, i, condition, channel);
                else
                    stopFlag = grayImageCase(image, &bufferBinary, tabByte, right, i, condition);
                if(stopFlag == 1) break;
            }

            if((top < down || left > right) || stopFlag == 1)
                break;

            down--;
            if(down < 0 || stopFlag == 1)
                break;

            for(j = top; j >= down; --j){
                if(image->isColor)
                    stopFlag = colorImageCase(image, &bufferBinary, tabByte, right, j, condition, channel);
                else
                    stopFlag = grayImageCase(image, &bufferBinary, tabByte, right, j, condition);
                if(stopFlag == 1) break;
            }



            right++;
            if(right >= image->width || stopFlag == 1)
                break;

            for(i = right; i <= left; ++i){

                if(image->isColor)
                    stopFlag = colorImageCase(image, &bufferBinary, tabByte, i, down, condition, channel);
                else
                    stopFlag = grayImageCase(image, &bufferBinary, tabByte, i, down, condition);

                if(stopFlag == 1) break;
            }
            top++;
            if(top >= image->height || stopFlag == 1)
                break;
        }
        if(stopFlag == 1) break;
    }

    if(stopFlag == 1)
        return 1;
    return 0;
}

