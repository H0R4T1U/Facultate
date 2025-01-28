#include <iostream>
#include <fstream>
#include <string>
#include <map>

using namespace std;

ifstream fin("3.txt");

void CodareHuffman(){
	// Structura pentru perechi frecvență-caracter
	struct FreqPair{
		int freq = 0;
		char chr = 0;
		bool operator<(const FreqPair& oth) const{
			return freq < oth.freq || (freq == oth.freq && chr < oth.chr);
		}
	};

	string text;
	getline(fin, text);

	// Dacă textul este gol, se încheie funcția
	if (text == "") return;

	// Array-ul de frecvențe pentru toate caracterele posibile
	int freq[256]{};
	int letters = 0;

	// Calculează frecvența fiecărui caracter în text și numărul total de caractere distincte
	for (char chr : text){
		freq[int(chr)]++; // Incrementare frecvență pentru caracterul curent
		freq[int(chr)] == 1 ? letters++ : 0; // Dacă este prima apariție a caracterului, incrementează numărul de caractere distincte
	}

	// Multimap pentru a ține perechile (Frecvență, Șir de caractere) în ordine
	multimap<FreqPair, string> Q;

	cout << letters << "\n";
	// Afisează fiecare caracter distinct și frecvența sa
	for (int i = 0; i < 256; ++i){
		if (freq[i]){
			cout << char(i) << " " << freq[i] << "\n";
			// Inserează în multimap o pereche (Frecvență, Șir de caracter) pentru fiecare caracter distinct
			Q.insert({ { freq[i], char(i) }, string(1, i) });
		}
	}

	string code[256];
	// Construiește arborele Huffman
	while (--letters){
		// Extrage cel mai mic element din multimap
		auto x = *Q.begin();
		// Actualizează codurile caracterelor în funcție de arborele Huffman
		for (char chr : x.second){
			code[int(chr)] = "0" + code[int(chr)];
		}
		Q.erase(Q.begin());

		// Extrage al doilea cel mai mic element din multimap
		auto y = *Q.begin();
		// Actualizează codurile caracterelor în funcție de arborele Huffman
		for (char chr : y.second){
			code[int(chr)] = "1" + code[int(chr)];
		}
		Q.erase(Q.begin());

		// Inserează un nou nod în arborele Huffman cu frecvența dată de suma frecvențelor celor două noduri extrase anterior
		Q.insert({ { x.first.freq + y.first.freq, min(x.first.chr, y.first.chr) }, x.second + y.second });
	}

	// Afisează codurile Huffman pentru textul dat
	for (char chr : text){
		cout << code[int(chr)];
	}
}

int main(){
	CodareHuffman();
	return 0;
}
