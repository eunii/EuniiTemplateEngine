package service;

import utils.DataUtils;
import utils.FileUtils;
import utils.TemplateUtils;

import java.util.List;
import java.util.Map;

public class TransferService {

    DataUtils dataUtils;
    TemplateUtils templateUtils;
    FileUtils fileUtils = new FileUtils();

    public TransferService(String dataFilePath, String templateFilePath) {
        this.dataUtils = new DataUtils(fileUtils.readFileToString(dataFilePath));
        this.templateUtils = new TemplateUtils(fileUtils.readFileToString(templateFilePath));
    }

    public void transfer() {
        //탬플릿에서 변수들 리스트 찾아온다
        List<String> variables = templateUtils.getVariable();

        //data에서 변수들 리스트랑 데이터 결과감ㅅ 매칭되는 mpa리스트 뽑아온다.
        Map<String, String> keyValueMap = dataUtils.getVariableToValueMap(variables);

        //탬플릿에서 map값 받아와서 replace해준다;
        String resultTemplate = templateUtils.templateToResult(keyValueMap);

        fileUtils.writeFileBy(resultTemplate);
    }


}
