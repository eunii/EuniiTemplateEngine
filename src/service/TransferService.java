package service;

import utils.DataReader;
import utils.FileUtils;
import utils.TemplateReader;
import utils.TransferUtils;

import java.util.List;
import java.util.Map;

public class TransferService {

    DataReader dataReader;
    TemplateReader templateReader;
    TransferUtils transferUtils;

    public TransferService(String dataFilePath, String templateFilePath) {
        this.dataReader = new DataReader(dataFilePath);
        this.templateReader = new TemplateReader(templateFilePath);
        this.transferUtils = new TransferUtils(templateReader, dataReader);
    }

    public void transfer2() {
        //탬플릿에서 변수들 리스트 찾아온다
        List<String> strings = transferUtils.transfer();
        System.out.println("---------------------");
        for (int i = 0; i < strings.size(); i++) {
            System.out.println(strings.get(i));
        }
    }


}
