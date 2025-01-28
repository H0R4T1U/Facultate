using lab13_MAP.domain;
using lab13_MAP.source.domain;

namespace lab13_MAP.source.repository;

public class ActivePlayersRepository : AbstractRepository<int, ActivePlayer>
{
    protected override ActivePlayer CreateEntity(string line)
    {
        var attrs = line.Split(',');
        return new ActivePlayer(
            int.Parse(attrs[0]), 
            attrs[1], 
            int.Parse(attrs[2]),
            int.Parse(attrs[3]), 
            Enum.Parse<PlayerType>(attrs[4])
            );
    }

    protected override string GetFilePath()
    {
        return Directory.GetCurrentDirectory() + "/resources/ActivePlayers.csv";
    }
}