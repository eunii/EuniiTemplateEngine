package service;

import utils.DataToObjectUtils;
import utils.FileUtils;
import utils.TemplateUtils;
import utils.TransferUtils;

import java.util.List;
import java.util.Map;

public class TransferService {

    DataToObjectUtils dataToObjectUtils;
    TemplateUtils templateUtils;
    FileUtils fileUtils = new FileUtils();
    TransferUtils transferUtils;
    String templateFilePath;
    public TransferService(String dataFilePath, String templateFilePath) {
        this.dataToObjectUtils = new DataToObjectUtils(fileUtils.readFileToString(dataFilePath));
        this.templateUtils = new TemplateUtils(fileUtils.readFileToString(templateFilePath));
        this.transferUtils = new TransferUtils(fileUtils.readFileByLine(templateFilePath), dataToObjectUtils);
        this.templateFilePath = templateFilePath;
    }

    public void transfer() {

        //파일에서 템플릿을 한줄 씩 읽어온다

        //예약어가 있는지 확인
            //예약어 내용학인
            // 변수 명이면. 라인 치환
            // for문이면 미니 String<>에 포문 안의 내용 넣기
            // for문 끝나면 미니 팀플릿안의 내용들 다시 돌리기. (String List)
        //탬플릿에서 변수들 리스트 찾아온다
        List<String> variables = templateUtils.getVariable();

        //data에서 변수들 리스트랑 데이터 결과감ㅅ 매칭되는 mpa리스트 뽑아온다.
        Map<String, String> keyValueMap = dataToObjectUtils.getVariableToValueMap(variables);

        //탬플릿에서 map값 받아와서 replace해준다;
        String resultTemplate = templateUtils.templateToResult(keyValueMap);

        fileUtils.writeFileBy(resultTemplate);
    }
    public void transfer2() {
        //탬플릿에서 변수들 리스트 찾아온다
        List<String> strings = transferUtils.start(fileUtils.readFileByLine(templateFilePath), dataToObjectUtils.getDataObject());
        for (int i = 0; i < strings.size(); i++) {
            System.out.println(strings.get(i));
        }
    }


}
