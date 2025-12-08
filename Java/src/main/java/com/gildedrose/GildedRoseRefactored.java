package com.gildedrose;

/**
 * Sistema de atualização de qualidade do inventário Gilded Rose
 * Refatorado usando Strategy Pattern e princípios SOLID
 */
class GildedRoseRefactored {
    Item[] items;

    public GildedRoseRefactored(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            ItemUpdater updater = ItemUpdaterFactory.getUpdater(item);
            updater.update(item);
        }
    }
}

/**
 * Interface Strategy - Define o contrato para atualização de itens
 */
interface ItemUpdater {
    void update(Item item);
}

/**
 * Factory Pattern - Cria o updater apropriado baseado no tipo de item
 */
class ItemUpdaterFactory {
    public static ItemUpdater getUpdater(Item item) {
        if (item.name.equals("Aged Brie")) {
            return new AgedBrieUpdater();
        } else if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            return new BackstagePassUpdater();
        } else if (item.name.equals("Sulfuras, Hand of Ragnaros")) {
            return new SulfurasUpdater();
        } else if (item.name.startsWith("Conjured")) {
            return new ConjuredItemUpdater();
        } else {
            return new NormalItemUpdater();
        }
    }
}

/**
 * Classe base abstrata com lógica comum
 */
abstract class BaseItemUpdater implements ItemUpdater {
    protected static final int MAX_QUALITY = 50;
    protected static final int MIN_QUALITY = 0;

    protected void decreaseSellIn(Item item) {
        item.sellIn--;
    }

    protected void increaseQuality(Item item) {
        if (item.quality < MAX_QUALITY) {
            item.quality++;
        }
    }

    protected void decreaseQuality(Item item) {
        if (item.quality > MIN_QUALITY) {
            item.quality--;
        }
    }

    protected void setQualityToZero(Item item) {
        item.quality = 0;
    }

    protected boolean hasExpired(Item item) {
        return item.sellIn < 0;
    }
}

/**
 * Updater para itens normais
 * Regra: Quality degrada 1/dia antes de expirar, 2/dia depois
 */
class NormalItemUpdater extends BaseItemUpdater {
    @Override
    public void update(Item item) {
        decreaseQuality(item);
        decreaseSellIn(item);
        
        if (hasExpired(item)) {
            decreaseQuality(item);
        }
    }
}

/**
 * Updater para Aged Brie
 * Regra: Quality melhora 1/dia antes de expirar, 2/dia depois
 */
class AgedBrieUpdater extends BaseItemUpdater {
    @Override
    public void update(Item item) {
        increaseQuality(item);
        decreaseSellIn(item);
        
        if (hasExpired(item)) {
            increaseQuality(item);
        }
    }
}

/**
 * Updater para Sulfuras (item lendário)
 * Regra: Nunca muda quality ou sellIn
 */
class SulfurasUpdater extends BaseItemUpdater {
    @Override
    public void update(Item item) {
        // Sulfuras é um item lendário, nunca muda
        // Quality sempre 80, sellIn não decresce
    }
}

/**
 * Updater para Backstage Passes
 * Regra complexa:
 * - +1 quality quando sellIn > 10
 * - +2 quality quando 5 < sellIn <= 10
 * - +3 quality quando 0 < sellIn <= 5
 * - 0 quality quando sellIn < 0
 */
class BackstagePassUpdater extends BaseItemUpdater {
    @Override
    public void update(Item item) {
        increaseQuality(item);
        
        if (item.sellIn <= 10) {
            increaseQuality(item);
        }
        
        if (item.sellIn <= 5) {
            increaseQuality(item);
        }
        
        decreaseSellIn(item);
        
        if (hasExpired(item)) {
            setQualityToZero(item);
        }
    }
}

/**
 * Updater para itens Conjured (mágicos)
 * Regra: Quality degrada 2x mais rápido que itens normais
 */
class ConjuredItemUpdater extends BaseItemUpdater {
    @Override
    public void update(Item item) {
        decreaseQuality(item);
        decreaseQuality(item);
        decreaseSellIn(item);
        
        if (hasExpired(item)) {
            decreaseQuality(item);
            decreaseQuality(item);
        }
    }
}