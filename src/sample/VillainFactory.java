package sample;

public interface VillainFactory {
    Villain produce (int i, Double mode);
    Boss produceBoss(Double mode);

    class Round1VillainFactory implements VillainFactory {
        @Override
        public Villain produce(int i, Double mode) {
            return switch (i) {
                case 0, 1, 2 -> new Skull(mode);
                case 3, 4, 5 -> new Spider(mode);
                default -> new Predator(mode);
            };
        }
        @Override
        public Boss produceBoss( Double mode)
        {
            Boss boss=new PredatorBoss(mode);
            Game.game.shootingVillains.add(boss);
            return boss;
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
        @Override
        public Boss produceBoss( Double mode)
        {
            return new SpiderBoss(mode);
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
        @Override
        public Boss produceBoss( Double mode)
        {
            return new VampireBoss(mode);
        }
    }
    class Round4VillainFactory implements VillainFactory {
        @Override
        public Villain produce(int i, Double mode) {
            return switch (i) {
                case 0, 1, 2 -> new Zombie(mode);
                case 3, 4, 5 -> new Predator(mode);
                default -> new Wizard(mode);
            };
        }
        @Override
        public Boss produceBoss( Double mode)
        {
            Boss boss=new Loki(mode);
            Game.game.shootingVillains.add(boss);
            return boss;
        }
    }
    class AllVillainFactory implements  VillainFactory {
        @Override
        public Villain produce(int i, Double mode) {
            return switch (i){
                case 1 -> new Bat(mode);
                case 2 -> new Skull(mode);
                case 3 -> new Predator(mode);
                case 4 -> new Spider (mode);
                case 5 -> new Mummy(mode);
                case 6 -> new Ogre(mode);
                case 7 -> new Orc(mode);
                case 8 -> new Vampire(mode);
                case 9 -> new Wizard(mode);
                case 10 -> new Zombie(mode);
                default -> throw new IllegalStateException("Unexpected value: " + i);
            };
        }
        @Override
        public Boss produceBoss(Double mode)
        {
            return null;
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
            case 4  ->{
                return new Round4VillainFactory();
            }
            default -> {
                return new AllVillainFactory();
            }
        }
    }
}
