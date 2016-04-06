#include "tools.h"
#include "huffmanDecompression.h"
#include "binaryTree.h"
#include <stdio.h>
#include <assert.h>

int binaryTreeTest(){
	//construct_tree_test(); OK!
	fillLetterCodeTest();
	printf("Binary Tree Test OK!\n");
}

void construct_tree_test(){
	char * shape = "10011000";
	node root = malloc(sizeof(struct node));
    root->is_leaf = 0;
    root->left = NULL;
    root->right = NULL;
    construct_tree(shape, root);
    assert(root->is_leaf == 0);
    assert(root->left->is_leaf == 0);
    assert(root->left->left->is_leaf == 1);
    assert(root->left->right->is_leaf == 1);
    assert(root->right->is_leaf == 0);
    assert(root->right->left->is_leaf == 0);
    assert(root->right->left->left->is_leaf == 1);
    assert(root->right->left->right->is_leaf == 1);
    assert(root->right->right->is_leaf == 1);
}

void fillLetterCodeTest(){
	struct Dictionnary * dictionnary = malloc(sizeof(struct Dictionnary));
	dictionnary->nbOfSymbol = 4;
	dictionnary->letter = malloc(5);
	dictionnary->nbOfSymbol = malloc(5);
	dictionnary->letter[0] = 'd';
	dictionnary->letter[1] = 'e';
	dictionnary->letter[2] = 'c';
	dictionnary->letter[3] = 'b';
	dictionnary->letter[4] = 'a';

	char * shape = "10011000";
	node root = malloc(sizeof(struct node));
    root->is_leaf = 0;
    root->left = NULL;
    root->right = NULL;
    construct_tree(shape, root);

    fillLetterCode(root, dictionnary);

    assert(strcmp(dictionnary->letterCode[0], "00") == 0);
    assert(dictionnary->sizeOfCode[0] == 2);
    assert(strcmp(dictionnary->letterCode[1], "01") == 0);
    assert(dictionnary->sizeOfCode[1] == 2);
    assert(strcmp(dictionnary->letterCode[2], "100") == 0);
    assert(dictionnary->sizeOfCode[2] == 3);
    assert(strcmp(dictionnary->letterCode[3], "101") == 0);
    assert(dictionnary->sizeOfCode[3] == 3);
    assert(strcmp(dictionnary->letterCode[4], "11") == 0);
    assert(dictionnary->sizeOfCode[4] == 2);
}