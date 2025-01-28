package ubb.scs.map.faptebune.Controllers;


import ubb.scs.map.faptebune.Domain.Persoana;
import ubb.scs.map.faptebune.Service.EntityService;
import ubb.scs.map.faptebune.Service.NevoiService;
import ubb.scs.map.faptebune.Service.PersoanaService;

import ubb.scs.map.faptebune.Service.ScreenService;

public class ControllerSuperclass implements Controller{
    private EntityService<Long,Persoana> persoanaService;
    private ScreenService screenService;
    private NevoiService nevoiService;
    public ControllerSuperclass() {
    }

    public void init() {

    }

    public EntityService<Long, Persoana> getPersoanaService() {
        return persoanaService;
    }

    @Override
    public void setPerosanaService(PersoanaService service) {
        persoanaService = service;
    }

    @Override
    public void setScreenService(ScreenService sceneService) {
        this.screenService = sceneService;
    }


    public ScreenService getScreenService() {
        return screenService;
    }

    @Override
    public void setNevoiService(NevoiService service) {
        nevoiService = service;
    }

    public NevoiService getNevoiService() {
        return nevoiService;
    }


}

