package controllo.libreria;

import app.pannelli.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHubLibreria implements HubLibreria {

    private List<Observer> osservatori = new ArrayList<>();

    @Override
    public void aggiungiObserver(Observer o) {
        if(!osservatori.contains(o))
            osservatori.add(o);
    }

    @Override
    public void rimuoviObserver(Observer o) {
        if(osservatori.contains(o))
            osservatori.remove(o);
    }

    @Override
    public void notificaObserver() {
        for(Observer o : osservatori)
            o.update();
    }

}
