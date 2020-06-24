package priv.rabbit.vio.factory;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BarSimpleImpl extends AbstractBarSimple {

    /*@Autowired
    private LeadergroupTotalAnalysisMapper leadergroupTotalAnalysisMapper;*/

    @Override
    protected void setParameters(Map<String, Object> parameters) {
        super.parameters = parameters;
    }

    @Override
    protected List<String> getLegendList() {
        List<String> legendList = new ArrayList<String>();
        legendList.add("分数");
        return legendList;
    }

    @Override
    protected List<ExtMapData<String, String>> ListXYAxis() {
        // return leadergroupTotalAnalysisMapper.getLeadergroupQuotaDistribute(parameters);
        return new ArrayList<>();
    }


}

