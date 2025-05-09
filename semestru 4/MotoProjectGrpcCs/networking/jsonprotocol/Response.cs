using System;
using moto.networking.dto;

namespace moto.networking.jsonprotocol;

public class Response
{
    public ResponseType Type { get; set; }
    public string ErrorMessage { get; set; }
    public UserDTO User { get; set; }
    public PlayerDTO[] Players { get; set; }
    public TeamDTO Team { get; set; }
    public RaceDTO[] Races { get; set; }
    public PlayerDTO NewPlayer { get; set; }

    public Response()
    {
    }


    public override string ToString()
    {
        string playersString = Players != null ? string.Join(", ", Array.ConvertAll(Players, p => p.ToString())) : "null";
        string racesString = Races != null ? string.Join(", ", Array.ConvertAll(Races, r => r.ToString())) : "null";

        return $"Response{{type={Type}, errorMessage='{ErrorMessage}', user={User}, players={playersString}, team={Team}, races={racesString}, newPlayer={NewPlayer}}}";
    }
}