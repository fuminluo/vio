package priv.rabbit.vio.service.impl;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import priv.rabbit.vio.dto.excel.ExcelStyleDTO;
import priv.rabbit.vio.utils.BasePoiExcel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PoiExcelService extends BasePoiExcel {

    @Override
    public void doProcess() throws IOException {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();

        HSSFWorkbook wb = new HSSFWorkbook();// 创建工作薄

        // 设置常用的样式
        Map<String, CellStyle> styles = this.initCellStyles(wb);

        // 值集页签数据
        List<String> keys = new ArrayList<>();
        Map<String, List<String>> keyValues = new HashMap<>();
        List<Map<String, Object>> validationCells = new ArrayList<>();
        Map<Sheet, Map<Cell, Object>> allValidCols = new HashMap<>();

        ExcelStyleDTO excelStyleDTO = new ExcelStyleDTO("S", "公司", "company", Arrays.asList("物流公司", "科技公司", "汽车公司"));
        ExcelStyleDTO excelStyleDTO2 = new ExcelStyleDTO("S", "部门", "dept", Arrays.asList("财务部", "市场部", "后勤部"));
        ExcelStyleDTO excelStyleDTO3 = new ExcelStyleDTO("CURRENCY", "工资", "salary", Arrays.asList("5000", "6000", "8000"));

        List<ExcelStyleDTO> excelStyleDTOS = Arrays.asList(excelStyleDTO, excelStyleDTO2, excelStyleDTO3);

        //模板名称
        String titleName = "导入模板";

        if (!CollectionUtils.isEmpty(excelStyleDTOS)) {
            // 以类型名称作为sheet的名称
            HSSFSheet sheet = wb.createSheet(titleName);
            Map<Cell, Object> validCols = new HashMap<>();
            allValidCols.put(sheet, validCols);
            AtomicInteger sheetRowNo = new AtomicInteger(0);
            // 生成模版头部及填表说明信息
            this.generateTableHeader(wb, sheet, sheetRowNo, titleName, 10);
            // 生成标题行
            Row headerRow = sheet.createRow(sheetRowNo.getAndIncrement());
            //遍历列表头
            AtomicInteger colNo = new AtomicInteger(0);
            excelStyleDTOS.forEach(item -> {
                // 生成数据行
                int colIndex = colNo.getAndIncrement();
                Cell cell = headerRow.createCell(colIndex);
                sheet.setColumnWidth(colIndex, 4000);

                cell.setCellStyle(styles.get("DEFAULT"));
                // 根据数据类型设置列类型
                if ("N".equalsIgnoreCase(item.getStyle())) {
                    sheet.setDefaultColumnStyle(colIndex, styles.get("NUMBER"));
                } else if ("S".equalsIgnoreCase(item.getStyle())) {
                    sheet.setDefaultColumnStyle(colIndex, styles.get("TEXT"));
                } else if ("D".equalsIgnoreCase(item.getStyle())) {
                    sheet.setDefaultColumnStyle(colIndex, styles.get("TEXT"));
                } else if ("CURRENCY".equalsIgnoreCase(item.getStyle())) {
                    sheet.setDefaultColumnStyle(colIndex, styles.get("CURRENCY"));
                } else {
                    sheet.setDefaultColumnStyle(colIndex, styles.get("TEXT"));
                }
                //设置单元格内容
                cell.setCellValue(item.getName());

                Map<String, Object> validCellInfo = new HashMap<>();
                validCellInfo.put("cell", cell);
                validCellInfo.put("ruleName", item.getName());
                validationCells.add(validCellInfo);

                if (!CollectionUtils.isEmpty(item.getValueSet())) {
                    keyValues.put(item.getName(), item.getValueSet());
                    keys.add(item.getName());
                }
                validCols.put(cell, item.getName());

            });
        }


        //如果有值集则设置值集页签
        if (!CollectionUtils.isEmpty(keys)) {
            HSSFSheet sheet2 = wb.createSheet("值集");
            this.generateKeyValueSheet(wb, sheet2, keys, keyValues);
            sheet2.protectSheet("hfasp"); // 设置密码锁定页签
            // 数据首行位置
            Integer firstRow = 3;
            // 添加有效性校验
            allValidCols.forEach((sheet, map) -> {
                if (!map.isEmpty()) {
                    map.forEach((cell, ruleName) -> {
                        int idx = keys.indexOf(ruleName);
                        List<String> ruleItems = keyValues.get(ruleName);
                        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(ruleItems)) {
                            int to = ruleItems.size() + 1;
                            String strFormula = "值集!$" + columnNames[idx] + "$2:$" + columnNames[idx] + "$" + to;
                            sheet.addValidationData(SetDataValidation(strFormula, firstRow, 65535, cell.getColumnIndex(),
                                    cell.getColumnIndex()));
                        }
                    });
                }
            });
        }


        try {
            response.reset();
            String fileName = URLEncoder
                    .encode(String.format("%s-%s.xls", titleName + "模板", LocalDate.now().toString()), "UTF-8");
            response.setContentType("application/octet-stream");
            response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            OutputStream out = response.getOutputStream();
            out.flush();
            wb.write(out);
            wb.close();
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成表头
     *
     * @param wb
     * @param sheet
     * @param rowNo
     * @param title
     * @param cols  列数
     */
    private void generateTableHeader(Workbook wb, Sheet sheet, AtomicInteger rowNo, String title, Integer cols) {
        // 表格名称样式
        CellStyle titleCellStyle = wb.createCellStyle();
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER); // 水平居中
        titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
        titleCellStyle.setBorderTop(BorderStyle.THIN);
        titleCellStyle.setBorderRight(BorderStyle.THIN);
        titleCellStyle.setBorderBottom(BorderStyle.THIN);
        titleCellStyle.setBorderLeft(BorderStyle.THIN);

        // 表格名称字体样式
        Font titleFontStyle = wb.createFont();
        titleFontStyle.setFontName("微软雅黑");
        titleFontStyle.setFontHeightInPoints((short) 16);
        titleFontStyle.setBold(true);
        titleCellStyle.setFont(titleFontStyle);

        // 表头样式
        CellStyle descCellStyle = wb.createCellStyle();
        descCellStyle.setAlignment(HorizontalAlignment.LEFT); // 水平居中
        descCellStyle.setVerticalAlignment(VerticalAlignment.TOP); // 垂直靠上
        descCellStyle.setBorderTop(BorderStyle.THIN);
        descCellStyle.setBorderRight(BorderStyle.THIN);
        descCellStyle.setBorderBottom(BorderStyle.THIN);
        descCellStyle.setBorderLeft(BorderStyle.THIN);

        // 表头字体样式(默认)
        Font descFontStyle = wb.createFont();
        descFontStyle.setFontName("微软雅黑");
        descFontStyle.setFontHeightInPoints((short) 12);
        descCellStyle.setFont(descFontStyle);
        descCellStyle.setWrapText(true); //换行属性

        // 表头字体样式（标红）
        Font redFontStyle = wb.createFont();
        redFontStyle.setFontName("微软雅黑");
        redFontStyle.setFontHeightInPoints((short) 12);
        redFontStyle.setColor(Font.COLOR_RED);

        // 表格名称行设置
        Row titleRow = sheet.createRow(rowNo.get());
        sheet.addMergedRegion(new CellRangeAddress(rowNo.get(), rowNo.get(), 0, cols - 1)); // 合并单元格
        {
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(titleCellStyle);
            titleCell.setCellValue(title);
            for (int i = 1; i < cols; i++) {
                Cell cell = titleRow.createCell(i);
                cell.setCellStyle(titleCellStyle);
            }
        }
        rowNo.getAndIncrement();
        // 表格填表说明行设置
        Row descRow = sheet.createRow(rowNo.get());
        // 生成填表说明行
        String tip = "填写说明：\n" +
                "1.标题为红色的信息项必填；";
        descRow.setHeight((short) (125 * 6)); //设置行高
        sheet.addMergedRegion(new CellRangeAddress(rowNo.get(), rowNo.get(), 0, cols - 1));
        {
            Cell descCell = descRow.createCell(0);
            descCell.setCellStyle(descCellStyle);
            //部分文字内容标红
            HSSFRichTextString ts = new HSSFRichTextString(tip);  //单元格内容
            ts.applyFont(descFontStyle);
            int index = tip.indexOf("红色");
            int index2 = tip.indexOf("必填");
            ts.applyFont(index, index + 2, redFontStyle);
            ts.applyFont(index2, index2 + 2, redFontStyle);
            //设置单元格值
            descCell.setCellValue(ts);
            for (int i = 1; i < cols; i++) {
                Cell cell = descRow.createCell(i);
                cell.setCellStyle(descCellStyle);
            }
        }
        rowNo.getAndIncrement();
    }
}
