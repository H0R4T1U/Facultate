using lab13_MAP.source.domain;

namespace lab13_MAP.source.repository;

public class MatchesRepository : AbstractRepository<int, Match>
{
    protected override Match CreateEntity(string line)
    {
        var attrs = line.Split(',');
        return new Match(int.Parse(attrs[0]), int.Parse(attrs[1]), int.Parse(attrs[2]), DateTime.Parse(attrs[3]));
    }

    protected override string GetFilePath()
    {
        return Directory.GetCurrentDirectory() + "/resources/Matches.csv";
    }
}