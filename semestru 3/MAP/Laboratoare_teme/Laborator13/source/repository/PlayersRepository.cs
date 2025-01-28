using lab13_MAP.domain;
using lab13_MAP.source.domain;

namespace lab13_MAP.source.repository;

public class PlayersRepository : AbstractRepository<int, Player>
{
    protected override Player CreateEntity(string line)
    {
        var attrs = line.Split(',');
        return new Player(int.Parse(attrs[0]), attrs[1], attrs[2], int.Parse(attrs[3]));
    }

    protected override string GetFilePath()
    {
        return Directory.GetCurrentDirectory() + "/resources/Players.csv";
    }
}