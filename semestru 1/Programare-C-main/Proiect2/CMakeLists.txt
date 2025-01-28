cmake_minimum_required(VERSION 3.27)
project(Proiect C)

set(CMAKE_C_STANDARD 11)

add_executable(Proiect2 main.c
        Repository/fileRepository.c
        Repository/fileRepository.h
        Repository/transaction.c
        Repository/transaction.h
        Services/crud.c
        Services/crud.h
        Services/Reports.c
        Services/Reports.h
        Tests/testService.c
        Tests/testService.h
        Ui/mainMenu.c
        Ui/mainMenu.h
        Utility/Utility.c
        Utility/Utility.h
        Validator/validator.c
        Validator/validator.h
        Views/transactionsView.c
        Views/transactionsView.h
        Repository/fileRepository.c
        Utility/dynamic_allocation.c
        Utility/dynamic_allocation.h
        Utility/encryption.c
        Utility/encryption.h
        Repository/accounts.c
        Repository/accounts.h
        Login/Login.c
        Login/Login.h)
