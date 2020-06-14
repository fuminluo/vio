package priv.rabbit.vio.design.factory;

import java.util.List;

public class BasicLineEcharts extends AbstractLineEcharts {

    public BasicLineEcharts(String[] xData, List<String[]> yData) {
        super();
        super.xAxis.data = xData;
        super.xAxis.type = "category";
        super.yAxis.type = "value";
    }
}
