package priv.rabbit.vio.factory;

import java.util.Observer;

/**
 * @Author administered
 * @Description
 * @Date 2019/3/15 23:51
 **/
public interface Observerable {

    public void registerObserver(Observer o);
    public void removeObserver(Observer o);

    void registerObserver(priv.rabbit.vio.factory.Observer o);

    void removeObserver(priv.rabbit.vio.factory.Observer o);

    public void notifyObserver();

}
