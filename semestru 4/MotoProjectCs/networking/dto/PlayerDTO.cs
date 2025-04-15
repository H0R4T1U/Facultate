namespace moto.networking.dto;
[Serializable]
public class PlayerDTO
{
    public String Name { get; set; }
    public String Code { get; set; }
    public String Team { get; set; }

    public PlayerDTO(string name, string code, string team)
    {
        this.Name = name;
        this.Code = code;
        this.Team = team;
    }
}