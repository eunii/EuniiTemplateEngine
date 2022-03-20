package service;

import utils.DataReader;
import utils.TemplateReader;
import utils.TransferUtils;

import java.util.List;

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
        List<String> result = transferUtils.transfer();
        transferUtils.writeFile(result);
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
    }


}
