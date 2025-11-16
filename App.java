import model.SlangDictionary;
import view.MainMenuView;
import controller.MainMenuController;

public class App {
    public static void main(String[] args) throws Exception {
        SlangDictionary model = new SlangDictionary();
        MainMenuView view = new MainMenuView();
        new MainMenuController(model, view);
    }
}
