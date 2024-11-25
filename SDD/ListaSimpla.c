#define _CRT_SECURE_NO_WARNINGS

#include <stdio.h>
#include <malloc.h>


typedef struct {
	char* nume;
	int an;
	float medie;
}student;

typedef struct {
	student info;
	struct nodLS* next;

}nodLS;


nodLS* adaugareNodLS(nodLS* cap, student s) {

	nodLS* nou = (nodLS*)malloc(sizeof(nodLS));
	nou->info.nume = (char*)malloc((strlen(s.nume) + 1) * sizeof(char));
	strcpy(nou->info.nume, s.nume);
	nou->info.an = s.an;
	nou->info.medie = s.medie;

	nou->next = NULL;

	if (cap == NULL) {
		cap = nou;
	}
	else {
		nodLS* temp = cap;

		while (temp->next != NULL) {
			temp = temp->next;
		}
		temp->next = nou;
	}

	return cap;

}

nodLS* stergereConditie(nodLS* cap, float medie) {
	nodLS* primul = cap;
	nodLS* doi = cap->next;

	if (cap == NULL) {
		return NULL;
	}

	//if (cap->info.medie < medie) { //partea in care sa stearga capul NU merge
	//	free(cap->info.nume);
	//	free(cap);
	//	cap = doi;
	//	primul = cap;
	//	doi = doi->next;
	//}

	while (doi != NULL) {	

		if (doi->info.medie < medie) {
			primul->next = doi->next;
			free(doi->info.nume);
			free(doi);
			doi = primul->next;
		}
		else {
			primul = doi;
			doi = doi->next;
		}

	}

	return cap;
}

void afisare(nodLS* cap) {
	nodLS* temp = cap;

	while (temp->next != NULL) {
		printf("Nume: %s | An: %d | Medie: %5.2f \n", temp->info.nume, temp->info.an, temp->info.medie);
		temp = temp->next;
	}
	printf("Nume: %s | An: %d | Medie: %5.2f \n", temp->info.nume, temp->info.an, temp->info.medie);
	printf("\n\n");
}

void countStudenti(nodLS* cap) {
	nodLS* temp = cap;
	int count = 0;

	while (temp->next != NULL) {
		count++;
		temp = temp->next;
	}
	count++;
	printf("Numarul studentilor adaugati este de %d\n", count);
}

int countStudentiRet(nodLS* cap) {
	nodLS* temp = cap;
	int count = 0;

	while (temp->next != NULL) {
		count++;
		temp = temp->next;
	}
	count++;
	return count;
}

void countMedie(nodLS* cap, float medie) {
	nodLS* temp = cap;
	int count = 0;

	while (temp !=NULL) {
		if (temp->info.medie > medie) {
			count++;
		}
		temp = temp->next;
	}

	printf("Numarul studentilor cu media mai mare de %5.2f este de %d\n", medie, count);

}

student* toVector(nodLS* cap) {
	int c = 0;
	int nrStud = countStudentiRet(cap);
	student* vect = (student*)malloc(sizeof(student) * nrStud);

	nodLS* temp = cap;

	while(temp!=NULL){
		vect[c].an = temp->info.an;
		vect[c].medie = temp->info.medie;
		vect[c].nume = (char*)malloc((strlen(temp->info.nume) + 1) * sizeof(char));
		strcpy(vect[c].nume, temp->info.nume);
		c++;
		temp = temp->next;
	}

	return vect;

}

int main() {
	student s;
	nodLS* cap = NULL;
	char buffer[20];
	int count=0;

	FILE* f = fopen("studenti.txt", "r");

	while (!feof(f)) {
		fscanf(f, "%s", buffer);
		s.nume = (char*)malloc((strlen(buffer) + 1) * sizeof(char));
		strcpy(s.nume, buffer);

		fscanf(f, "%d", &s.an);
		fscanf(f, "%f", &s.medie);
		cap = adaugareNodLS(cap, s);

		free(s.nume);
	}
	fclose(f);

	printf("==== LISTA ====\n");
	afisare(cap);
	//countStudenti(cap);
	//countMedie(cap, 8.00);

	//stergereConditie(cap, 8.40);
	//afisare(cap);

	printf("==== VECTOR ====\n");
	int nrStud = countStudentiRet(cap);

	student* vect = toVector(cap);

	for (int i = 0; i < nrStud; i++) {
		printf("Nume: %s | An: %d | Medie: %5.2f \n", vect[i].nume, vect[i].an, vect[i].medie);
	}


	return 0;
}
