sealed class Item{
    abstract val name: String
}
enum class Rarity{
    Common, Rare, Legendary
}
enum class Effect{
    override val name: String,
    val effect: String
}

data class Ingredient(
    override val name: String,
    val name rarity: Rarity
): Item()

data class Equipment(
    override val name: String,
    val durability: Int,
    val power: Int
): Item()

data class Potion(
    override val name: String, 
    val effect: Effect
): Item()

data class Recipe(
    val НеобходимыеИнгредиенты: Map<Ingredient, Int>,
    val result: Item
)

class Inventory{
    val items = mutableListOf<Item>()
    operator fun plusAssign(item:Item) {
        items.add(item)
        println("${item.name} Добавилось в инвентарь =)")
    }
    operator fun minusAssign(item:Item) {
        items.remove(item)
        println("${item.name} Удалено из инвентаря =(")
    }

    fun show() {
        println("Инвентарь:")
        items.forEach {println("- ${it.name}")}
    }
}

class CraftingTable {
    fun craft(ingredients: List<Item>, recipe: Recipe): Item? {
        for (item in ingredients) {
            if (item!is Ingredient) {
                println("Упс, ${item.name} не тот ингредиент")
                return null
            }
        }
        val inputMap = ingredients
        .map {it as Ingredient }
        .groupingBy {it}
        .eachCount()

        if (inputMap == recipe.НеобходимыеИнгредиенты) {
            println("Всё чётко, создан ${recipe.result.name}")
            return recipe.result
        } else {
            println("Не те ингредиенты, попробуй ещё раз")
            return null
        }
    }
}

fun main() {
    val fireRoot = Ingredient("Огненный корень", Rarity.RARE)
    val dragonBlood = Ingredient("Кровь дракона", Rarity.LEGENDARY)
    val ironSword = Equipment("Железный меч", durability = 100, power = 15)
    val healthPotion = Potion("Зелье здоровья", "Восстанавливает 100 HP")
    val potionRecipe = Recipe(
        НеобходимыеИнгредиенты = mapOf(fireRoot to 2, dragonBlood to 1),
        result = healthPotion
    )
    val craftingTable = CraftingTable()
    val inventory = Inventory()

    craftingTable.craft(listOf(fireRoot, sword), potionRecipe)

    val resultPotion = craftingTable.craft(listOf(fireRoot, fireRoot, dragonBlood), potionRecipe)
    if (resultPotion!=null) {
        inventory += resultPotion
    }

    inventory.show()
    inventory -= healthPotion
    inventory.show()
}