package priv.rabbit.vio.factory;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LineSimpleImpl extends AbstractLineSimple {

    /*@Autowired
    private LeadergroupTotalAnalysisMapper leadergroupTotalAnalysisMapper;*/


    @Override
    protected void setParameters(Map<String, Object> parameters) {
        super.parameters = parameters;
    }

    @Override
    protected List<ExtMapData<String, String>> ListXYAxis() {
        //return leadergroupTotalAnalysisMapper.getLeadergroupExcellentRates(parameters);
        return new ArrayList<>();
    }

}
