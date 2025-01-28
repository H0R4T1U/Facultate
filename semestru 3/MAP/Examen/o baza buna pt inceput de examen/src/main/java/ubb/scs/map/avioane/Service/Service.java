package ubb.scs.map.avioane.Service;

import ubb.scs.map.avioane.Domain.MenuItem;
import ubb.scs.map.avioane.Domain.Table;
import ubb.scs.map.avioane.Repository.MenuItemRepository;
import ubb.scs.map.avioane.Repository.TableRepository;

public class Service {
    private final MenuItemRepository menuItemRepository;
    private final TableRepository tableRepository;

    public Service(MenuItemRepository menuItemRepository, TableRepository tableRepository) {
        this.menuItemRepository = menuItemRepository;
        this.tableRepository = tableRepository;
    }

    public Iterable<Table> getAllTables(){
        return tableRepository.findAll();
    }
    public Iterable<MenuItem> getMenu(){
        return menuItemRepository.findAll();
    }

}
