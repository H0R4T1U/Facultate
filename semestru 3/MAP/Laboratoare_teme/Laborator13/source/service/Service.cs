using lab13_MAP.domain;
using lab13_MAP.source.domain;
using lab13_MAP.source.dto;
using lab13_MAP.source.repository;

namespace lab13_MAP.source.service;

public class Service(
    IRepository<int, ActivePlayer> activePlayersRepository,
    IRepository<int, Match> matchesRepository,
    IRepository<int, Player> playersRepository,
    IRepository<int, Team> teamsRepository)
{
    
    private delegate void PopulationAction();
    
    public string GetPlayersForTeam(string? teamName)
    {
        var team = FindTeamByName(teamName);
        var players = 
            from player in playersRepository.FindAll()
            where player.TeamId.Equals(team.Id)
            select player;
        return string.Join("\n", players.Select(MapToDto));
    }

    public string GetActivePlayersForMatch(string teamName, string firstTeamName, string secondTeamName)
    {
        var team = FindTeamByName(teamName);
        var match = FindMatch(firstTeamName, secondTeamName);
        return GetActivePlayersForMatch(team, match);
    }

    private string GetActivePlayersForMatch(Team team, Match match)
    {
        var result = 
            from activePlayer in activePlayersRepository.FindAll()
            where activePlayer.MatchId.Equals(match.Id) 
            join player in playersRepository.FindAll()
                on activePlayer.Id equals player.Id
            where player.TeamId.Equals(team.Id)
            select activePlayer;
        return string.Join("\n", result.Select(MapToDto));
    }


    public string GetMatchesForPeriod(DateTime startDate, DateTime endDate)
    {
        var matches =
            from match in matchesRepository.FindAll()
            where match.MatchDate >= startDate && match.MatchDate <= endDate
            select match;
        return string.Join("\n", matches.Select(MapToDto));
    }

    private string GetMatchScore(Match m)
    {
        var firstTeamScore = (from activePlayer in activePlayersRepository.FindAll()
            join player in playersRepository.FindAll()
                on activePlayer.Id equals player.Id
            where player.TeamId == m.FirstTeamId
            select activePlayer.Points).Sum();

        var secondTeamScore = (from activePlayer in activePlayersRepository.FindAll()
            join player in playersRepository.FindAll()
                on activePlayer.Id equals player.Id
            where player.TeamId == m.SecondTeamId
            select activePlayer.Points).Sum();

        return $"Score is: {firstTeamScore}:{secondTeamScore}";
    }
    
    public string GetMatchScore(string? firstTeam, string? secondTeam)
    {
        return GetMatchScore(FindMatch(firstTeam, secondTeam)) + " (" + firstTeam + ":" + secondTeam + ")";
    }
    
    private Team? FindTeamByName(string? name)
    {
        return teamsRepository.FindAll().FirstOrDefault(t => t.Name.Equals(name));
    }

    private Match? FindMatch(string? firstTeam, string? secondTeam)
    {
        var firstTeamId = FindTeamByName(firstTeam).Id;
        var secondTeamId = FindTeamByName(secondTeam).Id;
        return 
            (from match in matchesRepository.FindAll()
             where match.FirstTeamId.Equals(firstTeamId) &&
                  match.SecondTeamId.Equals(secondTeamId)
             select match).FirstOrDefault();
    }

    private string MapToDto(object obj)
    {
        return obj switch
        {
            Match match => new MatchDto(
                teamsRepository.FindOne(match.FirstTeamId).Name,
                teamsRepository.FindOne(match.SecondTeamId).Name,
                match.MatchDate).ToString(),

            ActivePlayer activePlayer => new ActivePlayerDto(
                activePlayer.Name,
                MapToDto(matchesRepository.FindOne(activePlayer.MatchId)),
                activePlayer.Points,
                activePlayer.Type.ToString()).ToString(),

            Player player => new PlayerDto(
                player.Name,
                player.School,
                teamsRepository.FindOne(player.TeamId).Name).ToString(),

            _ => string.Empty
        };
    }
    
    public void Populate()
    {
        PopulateEntities(PopulateTeams);
        PopulateEntities(PopulatePlayers);
        PopulateEntities(PopulateMatches);
        PopulateEntities(PopulateActivePlayers);
    }

    
    private static void PopulateEntities(PopulationAction populationAction)
    {
        populationAction();
    }

    
    private void PopulateTeams()
    {
        teamsRepository.Save(new Team(1, "Houston Rockets"));
        teamsRepository.Save(new Team(2, "Los Angeles Lakers"));
        teamsRepository.Save(new Team(3, "LA Clippers"));
    }
    
    private void PopulatePlayers()
    {
        playersRepository.Save(new Player(1, "abc", "Scoala Gimnaziala \"Horea\"", 1));
        playersRepository.Save(new Player(2, "def", "Scoala Gimnaziala \"Horea\"", 1));
        playersRepository.Save(new Player(3, "ghi", "Scoala Gimnaziala \"Octavian Goga\"", 2));
        playersRepository.Save(new Player(4, "jkl", "Scoala Gimnaziala \"Octavian Goga\"", 2));
        playersRepository.Save(new Player(5, "mno", "Scoala Gimnaziala \"Octavian Goga\"", 2));
    }
    
    private void PopulateMatches()
    {
        matchesRepository.Save(new Match(1, 1, 2, DateTime.Parse("2024-05-10")));
        matchesRepository.Save(new Match(2, 1, 3, DateTime.Parse("2024-12-20")));
        matchesRepository.Save(new Match(3, 2, 3, DateTime.Parse("2024-10-10")));
    }
    
    private void PopulateActivePlayers()
    {
        activePlayersRepository.Save(new ActivePlayer(1, "abcd", 1, 10, PlayerType.Active));
        activePlayersRepository.Save(new ActivePlayer(2, "defg", 1, 0, PlayerType.Benched));
        activePlayersRepository.Save(new ActivePlayer(3, "ghij", 1, 15, PlayerType.Active));
        activePlayersRepository.Save(new ActivePlayer(4, "klmn", 1, 25, PlayerType.Active));
        activePlayersRepository.Save(new ActivePlayer(5, "ghij", 1, 15, PlayerType.Active));
    }
}