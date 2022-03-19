import service.TransferService;
import utils.FileUtils;

public class Application {
    public static void main(String[] args) {

        String userDataFilePath = "D:\\study\\kakaobank\\study_project\\user.txt";
        String templateFilePath = args[1];

        TransferService service = new TransferService(userDataFilePath, templateFilePath);
        service.transfer();
    }
}
