namespace moto.networking.dto;

[Serializable]
public class RaceDTO
{
    public String NoPlayers { get; set; }
    public String EngineType { get; set; }
    public String Id { get; set; }

    public RaceDTO(string noPlayers, string engineType, string id)
    {
        NoPlayers = noPlayers;
        EngineType = engineType;
        Id = id;
    }
    
}