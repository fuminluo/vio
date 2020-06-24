package priv.rabbit.vio.factory;

import java.util.List;

public class BarSimple {

    //legend中data的数据
    private List<String> legendData;
    private List<String> xAxisData;
    private List<String> yAxisData;
    private List<List<Object>> seriesData;

    public List<String> getLegendData() {
        return legendData;
    }

    public void setLegendData(List<String> legendData) {
        this.legendData = legendData;
    }

    public List<String> getxAxisData() {
        return xAxisData;
    }

    public void setxAxisData(List<String> xAxisData) {
        this.xAxisData = xAxisData;
    }

    public List<String> getyAxisData() {
        return yAxisData;
    }

    public void setyAxisData(List<String> yAxisData) {
        this.yAxisData = yAxisData;
    }

    public List<List<Object>> getSeriesData() {
        return seriesData;
    }

    public void setSeriesData(List<List<Object>> seriesData) {
        this.seriesData = seriesData;
    }
}