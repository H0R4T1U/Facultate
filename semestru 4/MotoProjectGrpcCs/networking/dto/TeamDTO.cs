namespace moto.networking.dto;

[Serializable]
public class TeamDTO
{
    public String Name { get; set; }
    public String Id { get; set; }

    public TeamDTO(String name) {
        this.Name = name;
    }
    


}