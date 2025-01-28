package ubb.scs.map.avioane.Domain;

import java.time.LocalDateTime;
import java.util.Map;

public class Order {
    private Integer id;
    private Integer table;
    private Map<Integer,MenuItem> menuItems;
    private LocalDateTime date;
    private String status;

    public Order(Integer table, Map<Integer, MenuItem> menuItems, LocalDateTime date, String status) {
        this.table = table;
        this.menuItems = menuItems;
        this.date = date;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTable() {
        return table;
    }

    public void setTable(Integer table) {
        this.table = table;
    }

    public Map<Integer, MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(Map<Integer, MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
