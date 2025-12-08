package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Gilded Rose - Testes Completos de Qualidade")
class GildedRoseTest {

    // ==================== TESTES DE ITENS NORMAIS ====================

    @Test
    @DisplayName("Item normal deve diminuir quality em 1 antes da data de venda")
    void normalItem_ShouldDecreaseQualityByOne_BeforeSellDate() {
        Item[] items = new Item[] { new Item("Normal Item", 5, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(9, items[0].quality,
                "Quality deve diminuir em 1 para item normal antes da data de venda");
        assertEquals(4, items[0].sellIn,
                "SellIn deve diminuir em 1");
    }

    @Test
    @DisplayName("Item normal deve diminuir quality em 2 após a data de venda")
    void normalItem_ShouldDecreaseQualityByTwo_AfterSellDate() {
        Item[] items = new Item[] { new Item("Normal Item", 0, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(8, items[0].quality,
                "Quality deve diminuir em 2 para item normal após a data de venda");
    }

    @Test
    @DisplayName("Quality de item normal nunca deve ficar negativa")
    void normalItem_QualityShouldNeverBeNegative() {
        Item[] items = new Item[] { new Item("Normal Item", 5, 0) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(0, items[0].quality,
                "Quality nunca pode ser negativa");
    }

    @Test
    @DisplayName("Item normal com quality 1 após venda deve ficar com quality 0")
    void normalItem_WithQualityOne_AfterSellDate_ShouldBecomeZero() {
        Item[] items = new Item[] { new Item("Normal Item", 0, 1) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(0, items[0].quality,
                "Quality deve ser 0 (não pode ficar negativa mesmo degradando 2)");
    }

    // ==================== TESTES DE AGED BRIE ====================

    @Test
    @DisplayName("Aged Brie deve aumentar quality com o tempo")
    void agedBrie_ShouldIncreaseQuality_OverTime() {
        Item[] items = new Item[] { new Item("Aged Brie", 5, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(11, items[0].quality,
                "Aged Brie deve aumentar quality em 1 antes da data de venda");
    }

    @Test
    @DisplayName("Aged Brie deve aumentar quality em 2 após a data de venda")
    void agedBrie_ShouldIncreaseQualityByTwo_AfterSellDate() {
        Item[] items = new Item[] { new Item("Aged Brie", 0, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(12, items[0].quality,
                "Aged Brie deve aumentar quality em 2 após a data de venda");
    }

    @Test
    @DisplayName("Quality de Aged Brie nunca deve passar de 50")
    void agedBrie_QualityShouldNeverExceed50() {
        Item[] items = new Item[] { new Item("Aged Brie", 5, 50) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(50, items[0].quality,
                "Quality máxima deve ser 50");
    }

    @Test
    @DisplayName("Aged Brie com quality 49 deve chegar a 50, não ultrapassar")
    void agedBrie_WithQuality49_AfterSellDate_ShouldBeCapped50() {
        Item[] items = new Item[] { new Item("Aged Brie", 0, 49) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(50, items[0].quality,
                "Quality deve ser limitada a 50 mesmo com aumento de 2");
    }

    // ==================== TESTES DE SULFURAS ====================

    @Test
    @DisplayName("Sulfuras nunca deve alterar quality ou sellIn")
    void sulfuras_ShouldNeverChange_QualityOrSellIn() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(80, items[0].quality,
                "Sulfuras sempre mantém quality 80");
        assertEquals(0, items[0].sellIn,
                "Sulfuras nunca altera sellIn");
    }

    @Test
    @DisplayName("Sulfuras com sellIn negativo também não deve mudar")
    void sulfuras_WithNegativeSellIn_ShouldNeverChange() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", -1, 80) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(80, items[0].quality);
        assertEquals(-1, items[0].sellIn);
    }

    // ==================== TESTES DE BACKSTAGE PASSES ====================

    @Test
    @DisplayName("Backstage pass deve aumentar quality em 1 quando faltam mais de 10 dias")
    void backstagePass_ShouldIncreaseByOne_WhenMoreThan10Days() {
        Item[] items = new Item[] {
                new Item("Backstage passes to a TAFKAL80ETC concert", 11, 20)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(21, items[0].quality,
                "Quality aumenta em 1 quando sellIn > 10");
    }

    @Test
    @DisplayName("Backstage pass deve aumentar quality em 2 quando faltam 10 dias ou menos")
    void backstagePass_ShouldIncreaseByTwo_When10DaysOrLess() {
        Item[] items = new Item[] {
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(22, items[0].quality,
                "Quality aumenta em 2 quando sellIn <= 10");
    }

    @Test
    @DisplayName("Backstage pass deve aumentar quality em 3 quando faltam 5 dias ou menos")
    void backstagePass_ShouldIncreaseByThree_When5DaysOrLess() {
        Item[] items = new Item[] {
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(23, items[0].quality,
                "Quality aumenta em 3 quando sellIn <= 5");
    }

    @Test
    @DisplayName("Backstage pass deve ter quality 0 após o concerto")
    void backstagePass_ShouldDropToZero_AfterConcert() {
        Item[] items = new Item[] {
                new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(0, items[0].quality,
                "Quality cai para 0 após o concerto (sellIn < 0)");
    }

    @Test
    @DisplayName("Backstage pass nunca deve exceder quality 50")
    void backstagePass_QualityShouldNeverExceed50() {
        Item[] items = new Item[] {
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(50, items[0].quality,
                "Quality máxima deve ser 50 mesmo com aumento de 3");
    }

    @Test
    @DisplayName("Backstage pass com quality 48 e 5 dias deve chegar a 50")
    void backstagePass_WithQuality48_5Days_ShouldBeCapped50() {
        Item[] items = new Item[] {
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 48)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(50, items[0].quality,
                "Quality aumenta de 48 para 50 (limite de +3, mas cap em 50)");
    }

    // ==================== TESTES DE MÚLTIPLOS ITENS ====================

    @Test
    @DisplayName("Deve processar múltiplos itens corretamente na mesma atualização")
    void multipleItems_ShouldProcessIndependently() {
        Item[] items = new Item[] {
                new Item("Normal Item", 5, 10),
                new Item("Aged Brie", 5, 10),
                new Item("Sulfuras, Hand of Ragnaros", 5, 80),
                new Item("Backstage passes to a TAFKAL80ETC concert", 11, 20)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(9, items[0].quality, "Normal item degradou");
        assertEquals(11, items[1].quality, "Aged Brie melhorou");
        assertEquals(80, items[2].quality, "Sulfuras manteve");
        assertEquals(21, items[3].quality, "Backstage pass aumentou");
    }

    // ==================== TESTES DE CASOS EXTREMOS ====================

    @Test
    @DisplayName("Item com sellIn muito negativo deve degradar corretamente")
    void normalItem_WithVeryNegativeSellIn_ShouldDegradeCorrectly() {
        Item[] items = new Item[] { new Item("Normal Item", -10, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(8, items[0].quality,
                "Deve degradar em 2 independente de quão negativo seja sellIn");
    }

    @Test
    @DisplayName("Aged Brie com sellIn muito negativo deve aumentar em 2")
    void agedBrie_WithVeryNegativeSellIn_ShouldIncreaseByTwo() {
        Item[] items = new Item[] { new Item("Aged Brie", -10, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(12, items[0].quality);
    }

    @Test
    @DisplayName("Backstage pass com sellIn muito negativo deve ter quality 0")
    void backstagePass_WithVeryNegativeSellIn_ShouldBeZero() {
        Item[] items = new Item[] {
                new Item("Backstage passes to a TAFKAL80ETC concert", -5, 30)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(0, items[0].quality);
    }

    // ==================== TESTE DE INTEGRAÇÃO ====================

    @Test
    @DisplayName("Simulação de 10 dias de atualizações")
    void integration_Simulate10DaysOfUpdates() {
        Item[] items = new Item[] {
                new Item("+5 Dexterity Vest", 10, 20),
                new Item("Aged Brie", 2, 0)
        };
        GildedRose app = new GildedRose(items);

        // Simula 10 dias
        for (int day = 0; day < 10; day++) {
            app.updateQuality();
        }

        // Vest: sellIn inicial 10
        // Dias 0-9: sellIn 10→9→8→7→6→5→4→3→2→1→0
        // Quality: dia 0-9 degrada 1/dia = 20-10 = 10
        // Após sellIn chegar a 0, no último dia ainda não dobrou
        assertEquals(10, items[0].quality,
                "Vest deve ter quality 10 após 10 dias (ainda não expirou)");

        // Brie: melhora com tempo
        assertTrue(items[1].quality > 0,
                "Aged Brie deve ter melhorado");
    }

    // ==================== TESTES DE VALIDAÇÃO DE LIMITES ====================

    @Test
    @DisplayName("Quality inicial em 50 não deve ultrapassar para itens que melhoram")
    void itemThatImproves_StartingAt50_ShouldStayAt50() {
        Item[] items = new Item[] { new Item("Aged Brie", 10, 50) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(50, items[0].quality);
    }

    @Test
    @DisplayName("Quality inicial em 0 não deve ficar negativa para itens que degradam")
    void itemThatDegrades_StartingAt0_ShouldStayAt0() {
        Item[] items = new Item[] { new Item("Normal Item", 10, 0) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(0, items[0].quality);
    }

@Test
@DisplayName("Teste ruim de propósito - cheiro de código")
void badSmellyTest() {
    Item[] items = new Item[] {
        new Item("Normal Item", 5, 10),
        new Item("Aged Brie", 0, 10)
    };
    GildedRose app = new GildedRose(items);

    // lógica duplicada do normalItem_ShouldDecreaseQualityByOne_BeforeSellDate
    app.updateQuality();

    // assert confuso: mistura itens diferentes no mesmo assert
    assertTrue(items[0].quality < 10 && items[1].quality > 10,
        "Normal Item deve diminuir, Aged Brie deve aumentar");

    // variável inútil
    int temp = items[0].sellIn;
    temp = temp + 0; // operação sem efeito

    // assert redundante
    assertEquals(4, items[0].sellIn);
    assertEquals(4, items[0].sellIn); // repetido de propósito
}

}