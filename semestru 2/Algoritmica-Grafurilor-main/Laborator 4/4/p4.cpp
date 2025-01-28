#include <iostream>
#include <fstream>
#include <map>
#include <string>

using namespace std;

ifstream fin("4.txt");
ofstream fout("4out.txt");

void DecodareHuffman(){
	struct FreqPair{
		int freq = 0; // Frecvența caracterului
		char chr = 0; // Caracterul
		bool operator<(const FreqPair& oth) const{
			return freq < oth.freq || (freq == oth.freq && chr < oth.chr);
		}
	};

	// Multimap pentru a ține perechile (Frecvență, Șir de caractere)
	multimap <FreqPair, string> Q;

	int letters, freq[256]{}; // Numărul total de caractere distincte și un array pentru frecvențele caracterelor
	string line;

	// Citeste numărul de caractere distincte din primul rând al fișierului
	getline(fin, line);
	letters = stoi(line);

	// Citeste fiecare caracter și frecvența sa din fișier
	for (int i = 0; i < letters; ++i){
		getline(fin, line);
		char chr = line[0];
		freq[int(chr)] = stoi(line.substr(2)); // Extrage frecvența caracterului din linia curentă și o converteste la int
		// Inserează în multimap o pereche (Frecvență, Șir de caracter) pentru fiecare caracter distinct
		Q.insert({ {freq[int(chr)], chr }, string(1, chr) });
	}

	string encoded;
	getline(fin, encoded); // Citeste șirul codificat Huffman

	string text;
	map<string, char> codes; // Map pentru a ține codurile Huffman pentru fiecare caracter

	string code[256]{}; // Array pentru a stoca codurile Huffman
	while (--letters){
		auto x = *Q.begin();
		for (auto chr : x.second){
			code[int(chr)] = "0" + code[int(chr)];
		}
		Q.erase(Q.begin());

		auto y = *Q.begin();
		for (auto chr : y.second){
			code[int(chr)] = "1" + code[int(chr)];
		}
		Q.erase(Q.begin());

		Q.insert({ { x.first.freq + y.first.freq, min(x.first.chr, y.first.chr) }, x.second + y.second });
	}

	// Construiește map-ul cu codurile Huffman
	for (int i = 0; i < 256; ++i){
		if (code[i] != "\0"){
			codes.insert({ code[i], i });
		}
	}

	int last = 0, len = 0;
	string prev, chr;

	// Parcurge șirul codificat pentru a-l decoda
	while (codes.size() > 1){
		string until = encoded.substr(last, len);
		len++;
		auto srch = codes.find(until);
		if (srch != codes.end()){
			prev = until;
			chr = srch->second;
		}
		else if (srch == codes.end() && prev != "\0"){
			text.append(chr);
			freq[int(chr[0])]--;

			if (freq[int(chr[0])] == 0){
				codes.erase(prev);
			}

			last += prev.size();
			len = 0;
			prev = "\0";
			chr = "\0";
		}
	}

	// Adaugă ultimul caracter decodat la text
	if (codes.size() == 1){
		text.append(string(1, codes.begin()->second));
	}

	// Scrie textul decodat în fișierul de ieșire și afișează-l
	fout << text;
	cout << text;
}


int main(){
	// Apelul funcției pentru decodarea Huffman
	DecodareHuffman();
	return 0;
}
