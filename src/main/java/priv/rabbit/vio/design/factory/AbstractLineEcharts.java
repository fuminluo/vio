package priv.rabbit.vio.design.factory;

import java.util.List;

public abstract class AbstractLineEcharts extends AbstractEcharts{

    protected XAxis xAxis;

    protected YAxis yAxis;

    protected List<Serie> series;
}
class XAxis {
    protected String type;
    protected String[] data;


}

class YAxis {
    protected String type;
}


class Serie {
    protected String type;
    protected String[] data;
}
