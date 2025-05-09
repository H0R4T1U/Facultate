using moto.networking.dto;

namespace moto.networking.jsonprotocol;

public class Request
{
    public RequestType Type { get; set; }
    public UserDTO User { get; set; }
    public PlayerDTO Player { get; set; }
    public string RaceId { get; set; }
    public string TeamName { get; set; }
    public string TeamId { get; set; }

    public override string ToString()
    {
        return $"Request{{type={Type}, user={User}, player={Player}, raceId={RaceId}, teamName='{TeamName}', teamId={TeamId}}}";
    }
}