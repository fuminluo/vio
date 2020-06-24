package priv.rabbit.vio.factory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLineSimple extends BsaeEchartsBean<LineSimple> {
    /**
     * 获取data数据集
     * name :x轴值集合
     * value :对应的y轴值集合
     * ExtMapData<name:value> 的List集合形式
     *
     * @return
     */
    protected abstract List<ExtMapData<String, String>> ListXYAxis();

    public LineSimple initEchartsData() {
        LineSimple lineSimple = new LineSimple();
        List<String> xAxisDataList = new ArrayList<String>();
        List<String> seriesDataList = new ArrayList<String>();
        List<ExtMapData<String, String>> listData = ListXYAxis();

        for (ExtMapData<String, String> extMapData : listData) {
            xAxisDataList.add(String.valueOf(extMapData.getName()));
            seriesDataList.add(String.valueOf(extMapData.getValue()));
        }

        lineSimple.setxAxisData(xAxisDataList);
        lineSimple.setSeriesData(seriesDataList);
        return lineSimple;
    }

}
