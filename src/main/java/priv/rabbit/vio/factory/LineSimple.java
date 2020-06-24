package priv.rabbit.vio.factory;

import java.util.List;

public class LineSimple {

    private List<String> xAxisData;
    private List<String> yAxisData;
    private List<String> seriesData;

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

    public List<String> getSeriesData() {
        return seriesData;
    }

    public void setSeriesData(List<String> seriesData) {
        this.seriesData = seriesData;
    }
}
