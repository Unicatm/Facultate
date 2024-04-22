#define _CRT_SECURE_NO_WARNINGS

#include <stdio.h>
#include <malloc.h>

typedef struct {
	char* den;
	float supr;
	unsigned char nrAng;
	char* adresa;
}farmacie;

typedef struct{
	farmacie info;
	struct nodLD* next, * prev;
}nodLD;

void adaugareNod(nodLD** cap, nodLD** coada, farmacie f) {
	nodLD* nou = (nodLD*)malloc(sizeof(nodLD));
	nou->info.den = (char*)malloc((strlen(f.den) + 1) * sizeof(char));
	strcpy(nou->info.den, f.den);

	nou->info.supr = f.supr;
	nou->info.nrAng=f.nrAng;

	nou->info.adresa = (char*)malloc((strlen(f.adresa) + 1) * sizeof(char));
	strcpy(nou->info.adresa,f.adresa);


	if (*cap == NULL) {
		*cap = nou;
		*coada = nou;
		nou->next = nou;
		nou->prev = nou;
	}
	else {
		nodLD* temp = *cap;

		while (temp->next != *cap) {
			temp = temp->next;
		}

		temp->next = nou;
		nou->prev = temp;

		*coada = nou;
		(*coada)->next = *cap;
		(*cap)->prev = *coada;
	}
}

void afisareAsc(nodLD* cap) {
	nodLD* temp = cap;
	printf("=== LISTA ASC ===\n");
	while (temp->next != cap) {
		printf("Denumire: %s | Supraf: %5.2f m2 | Nr Ang: %d | Adresa: %s\n", temp->info.den, temp->info.supr, temp->info.nrAng, temp->info.adresa);
		temp = temp->next;
	}
	printf("Denumire: %s | Supraf: %5.2f m2 | Nr Ang: %d | Adresa: %s\n\n", temp->info.den, temp->info.supr, temp->info.nrAng, temp->info.adresa);
}


void countFarmaciiBySuprafata(nodLD* cap, float prag) {
	nodLD* temp = cap;
	int count = 0;

	do {
		if (temp->info.supr < prag) {
			count++;
		}
		temp = temp->next;
	}
	while (temp != cap);


	printf("Nuamrul de farmacii cu suprafata mai mica de %5.2f m2 este de %d.\n", prag, count);
}

void stergereNod(nodLD** cap, nodLD** coada, unsigned char prag) {
	nodLD* temp = *cap;

	while(temp!=*cap) {
		nodLD* urm = temp->next;
		nodLD* spate = temp->prev;

		if (temp->info.nrAng > prag) {
			free(temp->info.den);
			free(temp->info.adresa);
			free(temp);

			if (temp == *cap) {
				*cap = (*cap)->next;
				(*cap)->prev = *coada;
			}
			else if (temp == *coada) {
				*coada = (*coada)->prev;
				(*coada)->next = *cap;
			}
			else {
				spate->next = urm;
				urm->prev = spate;
			}

			temp = urm;

		}
		else {
			temp = temp->next;
		}

	}
}

farmacie* toVector(nodLD* cap, nodLD* coada) {
	int nr = counter(cap);
	farmacie* vect = (farmacie*)malloc(sizeof(farmacie) * nr);

	nodLD* temp = cap;
	for (int i = 0; i < nr; i++) {
		vect[i].den = (char*)malloc((strlen(temp->info.den) + 1) * sizeof(char));
		strcpy(vect[i].den, temp->info.den);

		vect[i].supr = temp->info.supr;
		vect[i].nrAng = temp->info.nrAng;
		vect[i].adresa = (char*)malloc((strlen(temp->info.adresa) + 1) * sizeof(char));
		strcpy(vect[i].adresa, temp->info.adresa);

		temp = temp->next;
	}

	return vect;
}

int counter(nodLD* cap) {
	nodLD* temp = cap;
	int count = 0;
	do {
		count++;
		temp = temp->next;
	}
	while (temp != NULL);

	return count;
}


int main() {
	nodLD* cap = NULL, * coada = NULL;
	farmacie f;

	char buffer[30];

	FILE* g = fopen("farmacii.txt", "r");

	while (!feof(g)) {
		fscanf(g, "%s", buffer);
		f.den = (char*)malloc((strlen(buffer) + 1) * sizeof(char));
		strcpy(f.den, buffer);

		fscanf(g, "%f", &f.supr);
		fscanf(g, "%d", &f.nrAng);

		fscanf(g, "%s", buffer);
		f.adresa = (char*)malloc((strlen(buffer) + 1) * sizeof(char));
		strcpy(f.adresa, buffer);

		adaugareNod(&cap, &coada, f);
	}
	fclose(g);

	afisareAsc(cap);
	countFarmaciiBySuprafata(cap, 15.90);
	int nr = counter(cap);

	printf("Sters!");
	stergereNod(&cap, &coada, 3);
	afisareAsc(cap);

	farmacie* vect = toVector(cap, coada);

	for (int i = 0; i < nr; i++) {
		printf("Denumire: %s | Supraf: %5.2f m2 | Nr Ang: %d | Adresa: %s\n", vect[i].den, vect[i].supr, vect[i].nrAng, vect[i].adresa);

	}

	return 0;

}
