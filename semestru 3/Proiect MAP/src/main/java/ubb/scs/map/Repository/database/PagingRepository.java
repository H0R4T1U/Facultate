package ubb.scs.map.Repository.database;

import ubb.scs.map.Domain.Entity;
import ubb.scs.map.Repository.Repository;
import ubb.scs.map.Utils.Paging.Page;
import ubb.scs.map.Utils.Paging.Pageable;

public interface PagingRepository<ID, E extends Entity<ID>> extends Repository<ID, E> {
    Page<E> findAllOnPage(Pageable pageable);

}