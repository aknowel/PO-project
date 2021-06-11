package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class SpecialObject extends ImageView {
    SpecialObject(Image img)
    {
        super(img);
    }
}
class PoisonCloud extends SpecialObject {
    PoisonCloud()
    {
        super(new Image("resources/Images/PoisonCloud"));
    }
}

