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
    class Round2VillainFactory implements VillainFactory {
        @Override
        public Villain produce(int i, Double mode) {
            return switch (i) {
                case 0, 1, 2 -> new Spider(mode);
                case 3, 4, 5 -> new Bat(mode);
                default -> new Vampire(mode);
            };
        }
    }
    class Round3VillainFactory implements VillainFactory {
        @Override
        public Villain produce(int i, Double mode) {
            return switch (i) {
                case 0, 1, 2 -> new Ogre(mode);
                case 3, 4, 5 -> new Orc(mode);
                default -> new Mummy(mode);
            };
        }
    }
    class Round4VillainFactory implements VillainFactory {
        @Override
        public Villain produce(int i, Double mode) {
            return switch (i) {
                case 0, 1, 2 -> new Bat(mode);
                case 3, 4, 5 -> new Predator(mode);
                default -> new Wizard(mode);
            };
        }
    }
    static VillainFactory getVillainFactory(int k)
    {
        switch (k)
        {
            case 1 ->{
                return new Round1VillainFactory();
            }
            case 2 ->{
                return new Round2VillainFactory();
            }
            case 3 ->{
                return new Round3VillainFactory();
            }
            default  ->{
                return new Round4VillainFactory();
            }
        }
    }
}
