#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "image.h"
#include "tools.h"
#include "FreeImage.h"

/**
 * @brief Verifie si l'image est une image en couleur (avec RGB) ou non
 *
 * @param format format de l'image
 * @return Retourne 1 si c'est une image couleur, 0 sinon
 */
int isColorImage(FREE_IMAGE_FORMAT format){
    if(format == FIF_PNG || format == FIF_BMP || format == FIF_PPM)
        return 1;
    return 0;
}

/**
 * @brief Fonction qui verifie si il y a une cohérence entre le format de l'image et les channels (ici Y correspond à Gray)
 *
 * @param format Format de l'image
 * @param channels Channel sur lesquels decoder
 *
 * @return Retourne 0 si erreur, 1 sinon
 */
int verifyChannelAndImageFormat(FREE_IMAGE_FORMAT format, char * channels){
    if(format == FIF_PNG || format == FIF_BMP || format == FIF_PPM){
        if(channels[0] == 'Y' || channels[1] == 'Y' || channels[2] == 'Y' || channels[3] == 'Y' )
            return 0;
        return 1;
    }
    else if(format == FIF_PGM) {
        if(channels[0] != 'Y')
            return 0;
        return 1;
    }
}

/**
 * @brief Si l'utilisateur n'a pas spécifier un format à l'aide de l'option -Fin, on choisit ici le bon format(parmi les 4 disponibles) correspondant à l'image
 *
 * @param filename Image traiter
 * @return Retourne NULL si l'image correspond à aucun des 4 formats disponible, sinon retourne le bon format
 */
FREE_IMAGE_FORMAT getGoodFormat(char * filename){
    FREE_IMAGE_FORMAT format[4];
    format[0] = FIF_PNG;
    format[1] = FIF_BMP;
    format[2] = FIF_PPM;
    format[3] = FIF_PGM;
    FIBITMAP* image;
    int i = 0;
    for(;i < 4; ++i){
        image = FreeImage_Load(format[i], filename, 0);
        if(FreeImage_GetWidth(image) != 0 && FreeImage_GetHeight(image) != 0)
            return format[i];
    }
    return NULL;
}
/**
 * @brief Chargement d'une image
 * @details Cette methode a pour but de charger une image grâce à son nom, en utilisant le librairie FreeImage
 * On remplit une struct avec le bitmap de l'image ainsi que sa hauteur et largeur.
 *
 * @param filename Chemin absolu ou relatif de l'image à charger
 * @param IMG [description] Struct dans laquelle remplir les informations concernant l'image
 *
 * @return Si la fonction renvoie 0, alors une erreur s'est produite. Sinon, la fonction renvoie 1
 */
int loadImage(char * filename, char * finOption, struct Img* image, char * channels){
    FREE_IMAGE_FORMAT format;

    if(strcmp(finOption, "PNG") == 0){
        format = FIF_PNG;
    }
    else if(strcmp(finOption, "BMP") == 0){
        format = FIF_BMP;
    }
    else if(strcmp(finOption, "PPM") == 0){
        format = FIF_PPM;
    }
    else if(strcmp(finOption, "PGM") == 0){
        format = FIF_PGM;
    }
    else{
        //format = FreeImage_GetFIFFromFilename(filename);
        format = getGoodFormat(filename);
        if(format == NULL)
            return 0;
    }
    image->bitmapImage = FreeImage_Load(format, filename, 0);
    image->height = FreeImage_GetHeight(image->bitmapImage);
    image->width = FreeImage_GetWidth(image->bitmapImage);
    image->isColor = isColorImage(format);
    if(image->height == 0 || image->width == 0)
        return 0;

    if(verifyChannelAndImageFormat(format, channels) == 0)
        return 0;


    return 1;
}

/**
 * @brief Redirection vers le bon pattern de revelation
 *
 * @param IMG Structure avec les informations de l'image (bitmap, hauteur, largeur)
 * @param HIDDENFILEBYTE Structure représentant le tableau de byte du fichier caché
 * @param Condition Condition de revelation du fichier (nombre de bits sur lequel est codé le fichier, pattern, channels,...)
 */
int extractByteHiddenFileImage(struct Img* image, struct HiddenFileByte* tabByte, struct Condition* condition){

    if(strcmp(condition->pattern, "direct") == 0){
        return direct(image, tabByte, condition);
    }
    else if(strcmp(condition->pattern, "reverse") == 0){
        return reverse(image, tabByte, condition);
    }
    else if(strcmp(condition->pattern, "external_spiral") == 0){
        return external_spiral(image, tabByte, condition);
    }
    else if(strcmp(condition->pattern, "internal_spiral") == 0){
        return internal_spiral(image, tabByte, condition);
    }
}
