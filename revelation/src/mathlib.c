#include "mathlib.h"

/**
 * @brief Calcul d'une puissance
 *
 * @param nombre Nombre sur lequel on souhaite calculer la puissance
 * @param pow Puissance a laquelle on souhaite elevé le nombre
 *
 * @return Resultat du calcul
 */
int power(int nombre, int pow){
    int returnNb = 1;
    int i = 0;
    for(; i < pow;++i){
        returnNb*=nombre;
    }
    return returnNb;
}

int maximum(int nb1, int nb2){
    return nb1 > nb2 ? nb1 : nb2;
}

int binaryToDecimal(char * binary, int size){
    int i = size;
    int result;
    for(; i >=0; --i){
        result += power(binary[i]*2,i);
    }
    return result;
}

char * decimalToBinary(int decimal, int nbBitsToTake){
    char * binary = malloc(nbBitsToTake);
    int c,k,j;
    j=0;
    for(c=7; c >=0; c--){
        k=decimal >> c;
        if(c < nbBitsToTake){
            if(k & 1){
                binary[j++] = '1';
            }
            else{
                binary[j++] = '0';
            }
        }

    }
    //binary[j] = '\0';
    return binary;
}
