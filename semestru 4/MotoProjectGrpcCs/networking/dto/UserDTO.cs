namespace moto.networking.dto;

[Serializable]
public class UserDTO
{
    public string Username { get; set; }
    public string Passwd { get; set; }
    
    public UserDTO(){}
    
    public UserDTO(string id) : this(id,"")
    {
    }
    public UserDTO(string id, string passwd)
    {
        Username = id;
        Passwd = passwd;
    }
}