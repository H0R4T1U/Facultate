using lab13_MAP.source.service;

namespace lab13_MAP.source.ui;

public class Ui
{
    private readonly Service _service;

    public Ui(Service service)
    {
        _service = service;
        // _service.Populate();
    }

    private static void ShowMenuOptions()
    {
        Console.WriteLine("1. Print all players for a given team.");
        Console.WriteLine("2. Print all active players for a given team and match.");
        Console.WriteLine("3. Print all matches for given period of time.");
        Console.WriteLine("4. Print score for given match.");
        Console.WriteLine("0. Exit.");
    }

    private void PrintPlayersForTeam()
    {
        Console.WriteLine("Enter team name: ");
        Console.WriteLine(_service.GetPlayersForTeam(Console.ReadLine()));
    }

    private void PrintMatches()
    {
        Console.WriteLine("Enter first date as dd/mm/yyyy: ");
        var d1 = DateTime.Parse(Console.ReadLine());
        Console.WriteLine("Enter second date as dd/mm/yyyy: ");
        var d2 = DateTime.Parse(Console.ReadLine());
        Console.WriteLine(_service.GetMatchesForPeriod(d1, d2));
    }

    private void PrintMatchScore()
    {
        Console.WriteLine("Enter first team name: ");
        var firstTeam = Console.ReadLine();
        Console.WriteLine("Enter second team name: ");
        var secondTeam = Console.ReadLine();
        Console.WriteLine("Match score is: ");
        Console.WriteLine(_service.GetMatchScore(firstTeam, secondTeam));
    }

    private void PrintActivePlayers()
    {
        Console.WriteLine("Enter team name: ");
        var team = Console.ReadLine();
        Console.WriteLine("Enter first team name: ");
        var firstTeam = Console.ReadLine();
        Console.WriteLine("Enter second team name: ");
        var secondTeam = Console.ReadLine();
        Console.WriteLine(_service.GetActivePlayersForMatch(team, firstTeam, secondTeam));
    }

    public void Run()
    {
        while (true)
        {
            ShowMenuOptions();
            Console.WriteLine("Enter a number: ");
            var input = Console.ReadLine();
            switch (input)
            {
                case "0":
                    return;
                
                case "1":
                    PrintPlayersForTeam();
                    break;
                
                case "2":
                    PrintActivePlayers();
                    break;
                
                case "3":
                    PrintMatches();
                    break;
                
                case "4":
                    PrintMatchScore();
                    break;
                
                default:
                    Console.WriteLine("Invalid input.");
                    break;
            }
        }
    }
}