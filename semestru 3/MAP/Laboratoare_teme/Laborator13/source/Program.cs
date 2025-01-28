using lab13_MAP.source.repository;
using lab13_MAP.source.service;
using lab13_MAP.source.ui;

namespace lab13_MAP.source;

internal static class Program
{
    public static void Main()
    {
        var activePlayersRepository = new ActivePlayersRepository();
        var matchesRepository = new MatchesRepository();
        var playersRepository = new PlayersRepository();
        var teamsRepository = new TeamsRepository();
        var service = new Service(activePlayersRepository, matchesRepository, playersRepository, teamsRepository);
        var ui = new Ui(service);
        ui.Run();
    }
}