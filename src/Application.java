import service.TransferService;
import utils.FileUtils;

public class Application {
    public static void main(String[] args) {

        String userDataFilePath = "D:\\study\\kakaobank\\study_project\\user.txt";
        String templateFilePath = "D:\\study\\kakaobank\\study_project\\template2.txt";

        TransferService service = new TransferService(userDataFilePath, templateFilePath);
        service.transfer2();
    }
}
