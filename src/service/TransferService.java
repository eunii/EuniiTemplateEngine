package service;

import org.json.simple.parser.ParseException;
import utils.DataObject;
import utils.DataParser;
import utils.FileUtils;
import utils.TransferUtils;

import java.util.List;

public class TransferService {

    DataParser dataReader = new DataParser();

    public void transferTemplateResult(String jasonString, List<String> templateList) {
        try {
            DataObject dataObject = dataReader.getDataObject(jasonString);
            TransferUtils transferUtils = new TransferUtils(dataObject, templateList);
            List<String> result = transferUtils.transfer();
            FileUtils.writeFileBy(result);
            System.out.println("현재 경로에 결과 파일(out.txt)을 저장했습니다.");
        } catch (ParseException e) {
            System.out.println("데이터 파일이 jason 형태의 파일이 아닙니다.");
        }
    }

}
