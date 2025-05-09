using Grpc.Core;
using Services;

namespace MotoClientGTK;
using Grpc.Net.Client;
using Google.Protobuf.WellKnownTypes;

public class GrpcServiceClient
{
    private readonly MotoService.MotoServiceClient _client;
    private readonly GrpcChannel _channel;

    public GrpcServiceClient(string ip, int port)
    {
        var address = $"http://{ip}:{port}";
        _channel = GrpcChannel.ForAddress(address);
        _client = new MotoService.MotoServiceClient(_channel);
    }
    
    public LoginResponse Login(string username, string password)
    {
        User user = new User
        {
            Username = username,
            Password = password
        };

        var response = _client.Login(new LoginRequest
        {
            User = user
        });
        return response;
        
    }
    public LoginResponse Logout(string username, string password)
    {
        User user = new User
        {
            Username = username,
            Password = password
        };
        var response =  _client.Logout(new LoginRequest
        {
            User = user
        });
        return response;
    }
    public async Task<moto.model.Race[]> GetAllRacesAsync()
    {
        var call = _client.GetAllRaces(new Services.Empty());
        var races = new List<moto.model.Race>();
        await foreach (var protoRace in call.ResponseStream.ReadAllAsync())
        {
            races.Add(ProtoMapper.MapRace(protoRace));
        }
        return races.ToArray();
    }
    public async Task<moto.model.Team> GetTeamByName(string teamName)
    {
        var response = await _client.GetTeamByNameAsync(new TeamRequest
        {
            Name = teamName
        });
        return ProtoMapper.MapTeam(response);
    }
    public async Task<moto.model.Player[]> GetRacePlayersOfTeam(int raceId, int teamId)
    {
        var response = await _client.GetRacePlayersOfTeamAsync(new RacePlayersRequest()
        {
            RaceId = raceId,
            TeamId = teamId
        });
        return response.Players.Select(ProtoMapper.MapPlayer).ToArray();
    }
    public async Task<Services.Empty> AddPlayerAsync(moto.model.Player player, int raceId)
    {
        var protoPlayer = new Services.Player
        {
            Name = player.Name,
            Code = player.Code,
            Team = player.Team
        };
        var response = await _client.AddPlayerAsync(new AddPlayerRequest
        {
            Player = protoPlayer,
            RaceId = raceId
        });
        return response;
    }
    public async Task SubscribeToPlayerUpdates(Action<moto.model.Player> onPlayerAdded, CancellationToken token,string username,string password)
    {
        var call = _client.SubscribeToPlayerUpdates(new User{Username = username,Password = password});
        try
        {
            while (await call.ResponseStream.MoveNext(token))
            {
                var protoPlayer = call.ResponseStream.Current;
                var player = ProtoMapper.MapPlayer(protoPlayer); // Your mapping function
                onPlayerAdded(player);
            }
        }
        catch (RpcException ex) when (ex.StatusCode == StatusCode.Cancelled)
        {
        }
    }
    // public async Task<AddResponse> AddAsync(string id, string name)
    // {
    //     var response = await _client.AddAsync(new AddRequest
    //     {
    //         Object = new MyDomainObject
    //         {
    //             Id = id,
    //             Name = name
    //         }
    //     });
    //     return response;
    // }
    //
    // public async Task SubscribeToAdditionsAsync(CancellationToken cancellationToken)
    // {
    //     using var call = _client.SubscribeToAdditions(new Empty());
    //
    //     await foreach (var update in call.ResponseStream.ReadAllAsync(cancellationToken))
    //     {
    //         Console.WriteLine($"[Update] ID: {update.Id}, Name: {update.Name}");
    //     }
    // }

    public void Dispose()
    {
        _channel.Dispose();
    }
}
