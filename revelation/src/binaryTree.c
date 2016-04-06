#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "huffmanDecompression.h"
#include "binaryTree.h"


/**
 * @brief Function who, from a shape, construct a binary tree. This function is recursive
 * 
 * @param shape Shape of the binary tree
 * @param n Current node
 * 
 * @return Return the shape
 */
char * construct_tree(char * shape, node n) {
    if (n->is_leaf == 1) return shape;
    if (n->left == NULL) {
        n->left = malloc(sizeof(struct node));
        n->left->left = NULL;
        n->left->right = NULL;
        n->left->value = '0';
        if (shape[0] == '1') n->left->is_leaf = 0;
        else if(shape[0] == '0') n->left->is_leaf = 1;
        ++shape;
        shape = construct_tree(shape, n->left);
    }
    if (n->right == NULL) {
        n->right = malloc(sizeof(struct node));
        n->right->left = NULL;
        n->right->right = NULL;
        n->right->value = '1';
        if (shape[0] == '1') n->right->is_leaf = 0;
        else if(shape[0] == '0') n->right->is_leaf = 1;
        ++shape;
        shape = construct_tree(shape, n->right);
    }

    return shape;
}

/**
 * @brief Function who display the tree (only used for DEBUG)
 * 
 * @param n Node to display
 */
void display_tree(node n) {
    printf("%d\n", n->is_leaf);
    if(n->is_leaf == 0){
        if (n->left != NULL) {
            display_tree(n->left);
        }
        if (n->right != NULL) {
            display_tree(n->right);
        }
    }
    else
        printf("\n");
}

/**
 * @brief Once the tree is construct, this function fill the dictionnary with the good letter code
 * 
 * @param n Root node of the binary tree
 * @param Dictionnary Dictionnary to fill
 */
void fillLetterCode(node n, struct Dictionnary * dictionnary) 
{
    char path[100000];
    struct codeList * codelist = malloc(sizeof(struct codeList));
    codelist->tabSize = 100;
    codelist->codeTab = malloc(100000);
    codelist->sizeOfCode = malloc(dictionnary->nbOfSymbol+1);
    codelist->indexOfNextCode = 0;
    getLeafPathRecurs(n, path, 0, codelist);
    int i;
    dictionnary->letterCode = malloc(100000);
    int max = 0;
    for(i=0; i <= dictionnary->nbOfSymbol; ++i){
        dictionnary->letterCode[i] = codelist->codeTab[i];
        max = maximum(max, codelist->sizeOfCode[i]);
        dictionnary->sizeOfCode[i] = codelist->sizeOfCode[i];
    }
    dictionnary->maxSize = max;
}

/**
 * @brief Function to get all Root-to-Leaf path (to find the code of each symbols)
 * 
 * @param n Current node
 * @param path Path from the current node to the root node
 * @param pathLen Path length
 * @param codeList Struct codeList to fill with all Root-to-Leaf path
 */
void getLeafPathRecurs(node n, char path[], int pathLen, struct codeList * codelist) 
{
  if (n==NULL) return;
 
  path[pathLen] = n->value;
  pathLen++;
 
  if (n->left==NULL && n->right==NULL) {
    char * buffer;
    buffer = pathToCode(path, pathLen);
    codelist->codeTab[codelist->indexOfNextCode] = buffer;
    codelist->sizeOfCode[codelist->indexOfNextCode] = pathLen-1;
    //printf("%s\n", buffer);
    codelist->indexOfNextCode++;
  }
  else{
    getLeafPathRecurs(n->left, path, pathLen, codelist);
    getLeafPathRecurs(n->right, path, pathLen, codelist);
  }
}

/**
 * @brief Function who transform a path to a correct symbol code
 * 
 * @param path Path to interprate
 * @param len Length of the path
 * 
 * @return Return, in a char *, the associate code
 */
char * pathToCode(char path[], int len)
{
    char * code = malloc(len+1);
    int i;
    for(i=0; i < len-1; ++i){
        code[i] = path[i+1];
    }
    code[i] = '\0';
    return code;
} 