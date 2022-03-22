import service.ScannerService;
import service.TransferService;

public class Application {
    public static void main(String[] args) {
        TransferService service = new TransferService();
        service.transferTemplateResult(ScannerService.getDataFile(), ScannerService.getTemplateFile());
    }
}
