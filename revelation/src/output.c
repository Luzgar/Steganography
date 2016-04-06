#include "tools.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/**
 * @brief Ecriture sur la sortie standard
 * @details Si l'utilisateur n'a pas specifié l'option -out, alors cette fonction est appelé, ecrivant le fichier
 * caché sur la sortie standard.
 *
 * @param HIDDENFILEBYTE Tableau de byte à ecrire sur stdout.
 */
void printOnStandardOutput(struct HiddenFileByte * tabByte){
    int i = 0;
    for(; i < tabByte->indexNextByte; ++i){
        printf("%c", tabByte->byte[i]);
    }
    printf("\n");

    free(tabByte->byte);
}

/**
 * @brief Ecriture dans un nouveau fichier
 * @details Si l'utilisateur a specifié l'option -out, alors on ecrit le fichier caché dans un nouveau fichier
 * où l'utilisateur a spécifié le nom
 *
 * @param outputFileName Nom du fichier sur lequel ecrire
 * @param HIDDENFILEBYTE Tableau de byte à ecrire dans le nouveau fichier
 */
void createHideFile(char * outputFileName, struct HiddenFileByte * tabByte){
    FILE * f;
    f = fopen(outputFileName, "w");
    if (f != NULL)
    {
        int i = 0;
        for(;i < tabByte->indexNextByte; ++i)
            fputc(tabByte->byte[i], f);
        fclose(f);
    }

    free(tabByte->byte);
}
