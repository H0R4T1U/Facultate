using lab13_MAP.domain;
using lab13_MAP.source.domain;

namespace lab13_MAP.source.repository;

public class TeamsRepository : AbstractRepository<int, Team>
{
    protected override Team CreateEntity(string line)
    {
        var attrs = line.Split(",");
        return new Team(int.Parse(attrs[0]), attrs[1]);
    }

    protected override string GetFilePath()
    {
        return Directory.GetCurrentDirectory() + "/resources/Teams.csv";
    }
}