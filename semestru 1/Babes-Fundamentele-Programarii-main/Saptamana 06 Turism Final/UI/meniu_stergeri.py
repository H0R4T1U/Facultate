from Service.crud import stergere_pachete_destinatie, stergere_pachete_data, stergere_pachete_pret
from Utility.utility import citire_nr, cls


def print_menu_stergeri():
    print("Meniu stergeri")
    print("1. Stergere pachete prin locatie")
    print("2. Stergere pachete cu mai putin de n zile")
    print("3. Stergere pachete cu pretul mai scump de x")
    print("q. Back")


def UI_stergere_pachet_locatie(pachete, undo_list):
    locatie = input("Locatie:").lower()
    stergere_pachete_destinatie(pachete, locatie, undo_list)


def UI_stergere_pachete_data(pachete, undo_list):
    zile = citire_nr("Zile:", int, "Ziua trebuie sa fie un nr")
    stergere_pachete_data(pachete, zile, undo_list)


def UI_stergere_pachete_pret(pachete, undo_list):
    pret = citire_nr("Pret:", int, "Pretul trebuie sa fie un numar intreg")
    stergere_pachete_pret(pachete, pret, undo_list)


def stergere_pachet_menu(pachete, undo_list):
    print_menu_stergeri()
    while True:
        cmd = input(":").lower()
        match cmd:
            case '1':
                cls()
                UI_stergere_pachet_locatie(pachete, undo_list)
                print_menu_stergeri()
            case '2':
                cls()
                UI_stergere_pachete_data(pachete, undo_list)
                print_menu_stergeri()
            case '3':
                cls()
                UI_stergere_pachete_pret(pachete, undo_list)
                print_menu_stergeri()
            case 'q':
                break