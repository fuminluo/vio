package priv.rabbit.vio.dto.excel;

import lombok.Data;

import java.util.List;

@Data
public class ExcelStyleDTO {

    private String style;

    private String name;

    private String code;

    private List<String> valueSet;

    public ExcelStyleDTO(String style, String name, String code, List<String> valueSet) {
        this.style = style;
        this.name = name;
        this.code = code;
        this.valueSet = valueSet;
    }
}
