package scs.map;

import scs.map.Domain.validators.FriendshipValidator;
import scs.map.Domain.validators.UserValidator;
import scs.map.Repository.file.FriendshipFileRepository;
import scs.map.Repository.file.UserFileRepository;
import scs.map.Services.FriendshipService;
import scs.map.Services.UserService;
import scs.map.Ui.Console;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        UserFileRepository userRepository = new UserFileRepository(new UserValidator(),"src/data/users.txt");
        FriendshipFileRepository friendRepository = new FriendshipFileRepository(new FriendshipValidator(),"src/data/friends.txt");
        UserService userService = new UserService(userRepository);
        FriendshipService friendshipService = new FriendshipService(friendRepository,userService);
        Console console = new Console(userService,friendshipService);
        console.MainMenu();

    }
}