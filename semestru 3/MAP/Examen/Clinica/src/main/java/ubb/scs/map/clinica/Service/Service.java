package ubb.scs.map.clinica.Service;

import ubb.scs.map.clinica.Domain.Consultatie;
import ubb.scs.map.clinica.Domain.Medic;
import ubb.scs.map.clinica.Domain.Sectie;
import ubb.scs.map.clinica.Observers.Observable;
import ubb.scs.map.clinica.Observers.Observer;
import ubb.scs.map.clinica.Repository.ConsultatieRepository;
import ubb.scs.map.clinica.Repository.MedicRepository;
import ubb.scs.map.clinica.Repository.SectieRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

public class Service implements Observable {
    private final ConsultatieRepository consultatieRepository;
    private final MedicRepository medicRepository;
    private final  SectieRepository sectieRepository;

    public Service(ConsultatieRepository consultatieRepository, MedicRepository medicRepository, SectieRepository sectieRepository) {
        this.consultatieRepository = consultatieRepository;
        this.medicRepository = medicRepository;
        this.sectieRepository = sectieRepository;
    }

    public List<Medic> getMedics(){
        return StreamSupport.stream(medicRepository.findAll().spliterator(),false)
                .toList();
    }

    public List<Sectie> getSectii(){
        return StreamSupport.stream(sectieRepository.findAll().spliterator(),false)
                .toList();
    }
    List<Observer> observers = new ArrayList<>();
    @Override
    public void addObserver(Observer e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
    public List<Consultatie> getConsultatiiDoctor(Long medicId){
        return StreamSupport.stream(consultatieRepository.findAll().spliterator(),false)
                .filter(consultatie -> consultatie.getIdMedic().equals(medicId))
                .sorted(Comparator.comparing(Consultatie::getData))
                .sorted(Comparator.comparing(Consultatie::getOra))
                .toList();
    }
    public void createConsultatie(Consultatie consultatie) {
        consultatieRepository.save(consultatie);
        notifyObservers();
    }
}
