package lv.wings.model.interfaces;

import java.util.List;

public interface HasImages<I extends Imageable> extends Translatable {
    List<I> getImages();
}
