package ubb.scs.map.faptebune.Controllers;

import ubb.scs.map.faptebune.Domain.Persoana;
import ubb.scs.map.faptebune.Service.EntityService;
import ubb.scs.map.faptebune.Service.NevoiService;
import ubb.scs.map.faptebune.Service.PersoanaService;
import ubb.scs.map.faptebune.Service.ScreenService;

public interface Controller {
    void init();
    void setPerosanaService(PersoanaService servicex);

    void setScreenService(ScreenService sceneService);
    void setNevoiService(NevoiService nevoiService);
}
