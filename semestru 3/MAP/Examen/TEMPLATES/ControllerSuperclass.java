package ubb.scs.map.avioane.Controllers;


import ubb.scs.map.avioane.Service.ScreenService;
import ubb.scs.map.avioane.Service.Service;

public class ControllerSuperclass {
    private Service service;
    private ScreenService screenService;
    private int tableId;

    public int getTableId() {
        return tableId;
    }

    public void setTable( int id) {
        this.tableId = id;
    }

    public ControllerSuperclass() {
    }

    public void init() {

    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public ScreenService getScreenService() {
        return screenService;
    }

    public void setScreenService(ScreenService screenService) {
        this.screenService = screenService;
    }
}

