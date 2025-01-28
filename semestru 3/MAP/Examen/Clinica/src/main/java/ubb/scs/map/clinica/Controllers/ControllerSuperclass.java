package ubb.scs.map.clinica.Controllers;

import ubb.scs.map.clinica.Service.ScreenService;
import ubb.scs.map.clinica.Service.Service;

public class ControllerSuperclass {
    private Service service;
    private ScreenService screenService;
    private Long doctorId;
    private Long sectieId;


    public ControllerSuperclass() {
    }

    public void init() {

    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getSectieId() {
        return sectieId;
    }

    public void setSectieId(Long sectieId) {
        this.sectieId = sectieId;
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

