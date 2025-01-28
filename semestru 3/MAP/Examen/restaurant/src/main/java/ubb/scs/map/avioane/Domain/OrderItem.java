package ubb.scs.map.avioane.Domain;

public class OrderItem {
    private Integer orderId;
    private Integer menuItem;

    public OrderItem(Integer orderId, Integer menuItem) {
        this.orderId = orderId;
        this.menuItem = menuItem;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(Integer menuItem) {
        this.menuItem = menuItem;
    }
}
