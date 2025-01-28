package ubb.scs.map.avioane.Service;

import ubb.scs.map.avioane.Domain.MenuItem;
import ubb.scs.map.avioane.Domain.Order;
import ubb.scs.map.avioane.Domain.OrderItem;
import ubb.scs.map.avioane.Domain.Tables;
import ubb.scs.map.avioane.Observers.Observable;
import ubb.scs.map.avioane.Observers.Observer;
import ubb.scs.map.avioane.Repository.MenuItemRepository;
import ubb.scs.map.avioane.Repository.OrderItemsRepository;
import ubb.scs.map.avioane.Repository.OrderRepository;
import ubb.scs.map.avioane.Repository.TableRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service implements Observable {
    private final MenuItemRepository menuItemRepository;
    private final TableRepository tableRepository;
    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private List<Observer> observers = new ArrayList<>();
    public Service(MenuItemRepository menuItemRepository, TableRepository tableRepository, OrderRepository orderRepository, OrderItemsRepository orderItemsRepository) {
        this.menuItemRepository = menuItemRepository;
        this.tableRepository = tableRepository;
        this.orderRepository = orderRepository;
        this.orderItemsRepository = orderItemsRepository;
    }

    public Iterable<Tables> getAllTables(){
        return tableRepository.findAll();
    }
    public Iterable<MenuItem> getMenu(){
        return menuItemRepository.findAll();
    }
    public Map<String, List<MenuItem>> getMenuByCategory() {
        return StreamSupport.stream(menuItemRepository.findAll().spliterator(), false)
                .collect(Collectors.groupingBy(MenuItem::getCategory));
    }

    public void saveOrderItem(OrderItem orderItem) {
        orderItemsRepository.save(orderItem);
        notifyObservers();
    }
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }
    public Optional<Order> getTableOrder(int tableId) {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .filter(order -> order.getTable() == tableId)
                .findFirst();
    }
    public Iterable<Order> getOrders() {
        return orderRepository.findAll();
    }
    public List<String> getOrderItemsNames(int orderId) {
        List<Integer> items = StreamSupport.stream(orderItemsRepository.findAll().spliterator(), false)
                .filter(orderItem -> orderId == orderItem.getOrderId())
                .map(OrderItem::getMenuItem)
                .toList();
// get all order items for order
        return StreamSupport.stream(menuItemRepository.findAll().spliterator(), false)
                .filter(menuItem -> items.contains(menuItem.getId()))
                .map(MenuItem::getItem)
                .toList();
    }
    @Override
    public void addObserver(Observer e) {
        this.observers.add(e);
    }

    @Override
    public void removeObserver(Observer e) {
        this.observers.remove(e);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
