import service.TransferService;

public class Application {
    public static void main(String[] args) {

        String userDataFilePath = "D:\\study\\kakaobank\\study_project\\user.txt";
        String templateFilePath = "D:\\study\\kakaobank\\study_project\\template3.txt";

        TransferService service = new TransferService(userDataFilePath, templateFilePath);
        service.transfer2();
    }
}
