package priv.rabbit.vio.dto.excel;

import priv.rabbit.vio.config.excel.ExcelCell;

/**
 * @Author administered
 * @Description
 * @Date 2018/11/6 22:13
 **/
public class Goods {
    @ExcelCell(col = 0)
    private String name; //商品名
    @ExcelCell(col = 1)
    private String unit; //单位
    @ExcelCell(col = 2)
    private String format; //规格
    @ExcelCell(col = 3)
    private String factory;//生产厂家

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }
}
