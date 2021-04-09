package priv.rabbit.vio.utils;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BasePoiExcel {

    public static final String[] columnNames = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH",
            "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ",
            "AB", "BB", "BC", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BK", "BL", "BM", "BN", "BO", "BP", "BQ", "BR",
            "BS", "BT", "BU", "BV", "BW", "BX", "BY", "BZ"};

    public abstract void doProcess() throws IOException;

    protected Map<String, CellStyle> initCellStyles(Workbook wb) {

        Map<String, CellStyle> styles = new HashMap<>();
        // 默认样式
        {
            // 表头样式
            CellStyle headerCellStyle = wb.createCellStyle();
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
            headerCellStyle.setBorderTop(BorderStyle.THIN);
            headerCellStyle.setBorderRight(BorderStyle.THIN);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);

            // 表头字体样式
            Font headerFontStyle = wb.createFont();
            headerFontStyle.setFontHeightInPoints((short) 10);
            headerCellStyle.setFont(headerFontStyle);
            styles.put("DEFAULT", headerCellStyle);
        }
        // 表头样式
        {
            // 表头样式
            CellStyle headerCellStyle = wb.createCellStyle();
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
            headerCellStyle.setBorderTop(BorderStyle.THIN);
            headerCellStyle.setBorderRight(BorderStyle.THIN);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);

            // 表头字体样式
            Font headerFontStyle = wb.createFont();
            headerFontStyle.setFontName("微软雅黑");
            headerFontStyle.setFontHeightInPoints((short) 12);
            headerFontStyle.setBold(true);
            headerCellStyle.setFont(headerFontStyle);
            styles.put("HEADDER", headerCellStyle);
        }

        // 不可编辑样式
        {
            // 表头样式
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);

            if (wb instanceof HSSFWorkbook) {
                HSSFWorkbook workbook = (HSSFWorkbook) wb;
                HSSFPalette customPalette = workbook.getCustomPalette();
                Short index = (short) (HSSFColor.HSSFColorPredefined.AUTOMATIC.getIndex() - 1);
                customPalette.setColorAtIndex(index, (byte) 247, (byte) 245, (byte) 245);
                cellStyle.setFillForegroundColor(index);// 设置背景色
            } else {
                cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());// 设置背景色
            }
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // 设置加粗

            // 表头字体样式
            Font fontStyle = wb.createFont();
            // fontStyle.setFontName("微软雅黑");
            fontStyle.setFontHeightInPoints((short) 10);
            fontStyle.setBold(false);
            cellStyle.setFont(fontStyle);
            styles.put("UNEDITABLE", cellStyle);
        }

        // 不可编辑样式
        {
            // 表头样式
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);

            // 表头字体样式
            Font fontStyle = wb.createFont();
            // fontStyle.setFontName("微软雅黑");
            fontStyle.setFontHeightInPoints((short) 10);
            fontStyle.setBold(false);
            cellStyle.setFont(fontStyle);
            styles.put("EDITABLE", cellStyle);
        }

        // 必填字段
        {
            // 表头样式
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            // cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//
            // 设置背景色
            // cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // 设置加粗

            // 表头字体样式
            Font fontStyle = wb.createFont();
            // fontStyle.setFontName("微软雅黑");
            fontStyle.setFontHeightInPoints((short) 10);
            fontStyle.setBold(false);
            fontStyle.setColor(Font.COLOR_RED);
            cellStyle.setFont(fontStyle);
            styles.put("REQUIRED", cellStyle);
        }

        // 文本
        {
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);

            DataFormat format = wb.createDataFormat();
            cellStyle.setDataFormat(format.getFormat("@"));
            styles.put("TEXT", cellStyle);
        }

        // 数字
        {
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);

            DataFormat format = wb.createDataFormat();
            cellStyle.setDataFormat(format.getFormat("0"));
            styles.put("NUMBER", cellStyle);
        }

        // 数字
        {
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);

            DataFormat format = wb.createDataFormat();
            cellStyle.setDataFormat(format.getFormat("0.00"));
            styles.put("DOUBLE", cellStyle);
        }

        // 货币
        {
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);

            DataFormat format = wb.createDataFormat();
            cellStyle.setDataFormat(format.getFormat("0.00"));
            styles.put("CURRENCY", cellStyle);
        }

        {
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);

            DataFormat format = wb.createDataFormat();
            cellStyle.setDataFormat(format.getFormat("yyyy-mm-dd"));
            styles.put("DATE", cellStyle);
        }

        {
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);

            DataFormat format = wb.createDataFormat();
            cellStyle.setDataFormat(format.getFormat("yyyy-mm-dd hh:mm:ss"));
            styles.put("DATETIME", cellStyle);
        }
        return styles;
    }


    /**
     * 生成值集页签数据
     *
     * @param wb
     * @param sheet
     * @param keys
     * @param keyValues
     */
    protected void generateKeyValueSheet(Workbook wb, Sheet sheet, List<String> keys,
                                         Map<String, List<String>> keyValues) {
        // 生成值集页签
        // 表格名称样式
        CellStyle kvHeaderCellStyle = wb.createCellStyle();
        kvHeaderCellStyle.setAlignment(HorizontalAlignment.CENTER); // 水平居中
        kvHeaderCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
        kvHeaderCellStyle.setBorderTop(BorderStyle.THIN);
        kvHeaderCellStyle.setBorderRight(BorderStyle.THIN);
        kvHeaderCellStyle.setBorderBottom(BorderStyle.THIN);
        kvHeaderCellStyle.setBorderLeft(BorderStyle.THIN);
        kvHeaderCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());// 设置背景色
        kvHeaderCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // 设置加粗

        Font kvTitleFontStyle = wb.createFont();
        kvTitleFontStyle.setFontName("微软雅黑");
        // kvTitleFontStyle.setFontHeightInPoints((short) 10);
        kvTitleFontStyle.setBold(true);
        kvHeaderCellStyle.setFont(kvTitleFontStyle);

        CellStyle kvDataCellStyle = wb.createCellStyle();
        kvDataCellStyle.setAlignment(HorizontalAlignment.LEFT); // 水平居中
        kvDataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
        kvDataCellStyle.setBorderTop(BorderStyle.THIN);
        kvDataCellStyle.setBorderRight(BorderStyle.THIN);
        kvDataCellStyle.setBorderBottom(BorderStyle.THIN);
        kvDataCellStyle.setBorderLeft(BorderStyle.THIN);

        Font kvDataFontStyle = wb.createFont();
        kvDataFontStyle.setFontName("微软雅黑");
        // kvTitleFontStyle.setFontHeightInPoints((short) 10);
        kvDataFontStyle.setBold(false);
        kvDataCellStyle.setFont(kvDataFontStyle);

        Map<Integer, Row> rowMap = new HashMap<>();
        // 标题行
        Row headerRow = sheet.createRow(0);// 第一个sheet的第一行为标题
        for (int i = 0; i < keys.size(); i++) {

            sheet.setColumnWidth(i, 10000); // 设置每列的列宽

            // 设置值集名称
            String label = keys.get(i);
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellStyle(kvHeaderCellStyle);
            headerCell.setCellValue(label);

            // 输出值
            List<String> values = keyValues.get(label);

            if (values != null) {
                for (int j = 0; j < values.size(); j++) {
                    String value = values.get(j);
                    Integer dataRowNo = j + 1;
                    Row row = rowMap.get(dataRowNo);
                    if (row == null) {
                        row = sheet.createRow(dataRowNo);
                        rowMap.put(dataRowNo, row);
                    }
                    Cell cell = row.createCell(i);
                    cell.setCellStyle(kvDataCellStyle);
                    cell.setCellValue(value);
                }
            }
        }

        // 自适应列宽
        for (int i = 0; i < keys.size(); i++) {
            // sheet.autoSizeColumn(i);
        }
    }

    /**
     * 设置数据有效性
     *
     * @param strFormula 公式
     * @param firstRow   起始行
     * @param endRow     终止行
     * @param firstCol   起始列
     * @param endCol     终止列
     * @return
     */
    protected  HSSFDataValidation SetDataValidation(String strFormula, int firstRow, int endRow, int firstCol,
                                                        int endCol) {

        // 设置数据有效性加载在哪个单元格上。四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(strFormula);
        HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);

        dataValidation.createErrorBox("提示", "该值不在有效范围内");
        dataValidation.createPromptBox("", null);

        return dataValidation;
    }
}
