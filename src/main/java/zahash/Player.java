package zahash;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player {
    public final UUID id = UUID.randomUUID();
    public final List<Card> cards = new ArrayList<>();
}
