package scs.map.Services;

import scs.map.Domain.Network;
import scs.map.Repository.Repository;

    public class NetworkService extends EntityService<Long,Network> {
    public NetworkService(Repository<Long, Network> repository) {
        super(repository);
    }
}
