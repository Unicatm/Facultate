#define _CRT_SECURE_NO_WARNINGS

#include <stdio.h>
#include <malloc.h>


typedef struct {
	int cod;
	char* nume;
	float nota;
	int anStudiu;
}student;

typedef struct {
	student info;
	struct LS* next;
}LS;

typedef struct {
	struct LS** vect;
	int nrElem;
}hashT;



int functieHash(int cheie, hashT table) {
	return cheie % table.nrElem;
}

int inserare(hashT tabela, student s) {
	int pozitie;

	if (tabela.vect != NULL) {
		pozitie = functieHash(s.cod, tabela);

		LS* nou = (LS*)malloc(sizeof(LS));
		nou->info.cod = s.cod;
		nou->info.anStudiu = s.anStudiu;
		nou->info.nota = s.nota;
		nou->info.nume = (char*)malloc((strlen(s.nume) + 1) * sizeof(char));
		strcpy(nou->info.nume, s.nume);

		nou->next = NULL;

		if (tabela.vect[pozitie] == NULL) { //daca pozitia respectiva e libera, inserez acolo
			tabela.vect[pozitie] = nou;
		}
		else { //daca nu, voi insera in lista simpla, pe urmatoarea pozitie libera
			LS* temp = tabela.vect[pozitie];
			while (temp->next) {
				temp = temp->next;
			}
			temp->next = nou;
		}
	}

	return pozitie; //returnez pozitie pt ca ma va ajuta in alte cazuri sa aflu pe ce pozitie s-a introdus un student
}

void traversare(hashT tabela) {
	int pozitie;
	
	if (tabela.vect != NULL) {
		for (int i = 0; i < tabela.nrElem; i++) {
			if (tabela.vect[i] != NULL) {

				printf("=== Pozitia %d === \n", i);

				LS* temp = tabela.vect[i];	
				while (temp) {
					printf("%d | %s | %5.2f | %d\n",temp->info.cod, temp->info.nume, temp->info.nota, temp->info.anStudiu);
					temp = temp->next;
				}

			}
		}

	}
}

int main() {
	hashT tabela;
	tabela.nrElem = 4;
	tabela.vect = (LS**)malloc(tabela.nrElem * sizeof(LS*));
	for (int i = 0; i < tabela.nrElem; i++)
		tabela.vect[i] = NULL;

	int nrProd;
	char buffer[20];
	student p;

	FILE* f = fopen("studenti.txt", "r");
	fscanf(f, "%d", &nrProd);
	for (int i = 0; i < nrProd; i++)
	{
		fscanf(f, "%d", &p.cod);
		fscanf(f, "%s", buffer);
		p.nume = (char*)malloc((strlen(buffer) + 1) *
			sizeof(char));
		strcpy(p.nume, buffer);
		fscanf(f, "%f", &p.nota);
		fscanf(f, "%d", &p.anStudiu);

		inserare(tabela, p);

		free(p.nume);
	}
	fclose(f);

	traversare(tabela);

	return 0;
}
