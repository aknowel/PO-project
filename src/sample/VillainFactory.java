package sample;

public interface VillainFactory {
    Villain produce (int i, Double mode);

    class Round1VillainFactory implements VillainFactory {
        @Override
        public Villain produce(int i, Double mode) {
            return switch (i) {
                case 0, 1, 2 -> new Skull(mode);
                case 3, 4, 5 -> new Spider(mode);
                default -> new Predator(mode);
            };
        }
    }

    static VillainFactory getVillainFactory(int k)
    {
        switch (k)
        {
            default ->{
                return new Round1VillainFactory();
            }
        }
    }
}
