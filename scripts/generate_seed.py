items = [
    (1, "Skim Milk 1L", "Amul", 45, 3.4, 5, 0.1, 42, 0, True, "breakfast,beverage,low-fat", "Fat-free milk", 180),
    (1, "Butter 100g", "Amul", 55, 0.5, 0, 11, 81, 0, True, "breakfast,cooking", "Salted table butter", 200),
    (1, "Cheese Slices 10pk", "Britannia", 120, 16, 2, 12, 200, 0, True, "breakfast,sandwich", "Processed cheese", 90),
    (1, "Flavored Yogurt Strawberry", "Mother Dairy", 35, 4, 12, 2, 90, 0, True, "snack,breakfast", "Fruit yogurt cup", 150),
    (1, "Lassi Sweet 200ml", "Amul", 25, 3, 15, 2, 110, 0, True, "beverage,quick", "Traditional sweet lassi", 120),
    (1, "Mozzarella 200g", "Go", 165, 18, 2, 10, 250, 0, True, "lunch,dinner", "Pizza cheese", 70),
    (1, "Cream 200ml", "Amul", 58, 2, 3, 20, 200, 0, True, "cooking,dessert", "Fresh cream", 85),
    (2, "Egg Whites 500ml", "Eggoz", 89, 11, 1, 0, 52, 0, False, "breakfast,high-protein", "Liquid egg whites", 60),
    (2, "Turkey Slices 100g", "Licious", 149, 18, 2, 4, 110, 0, False, "lunch,sandwich,high-protein", "Smoked turkey breast", 40),
    (2, "Salmon Fillet 200g", "Licious", 399, 20, 0, 13, 200, 0, False, "dinner,high-protein", "Atlantic salmon", 35),
    (2, "Tempeh 200g", "Green Chick", 99, 19, 9, 6, 170, 4, True, "lunch,dinner,vegan,high-protein", "Fermented soy block", 55),
    (2, "Soya Chunks 200g", "Nutrela", 55, 52, 33, 0.4, 345, 13, True, "lunch,dinner,high-protein,vegan", "Textured soy protein", 80),
    (2, "Fish Fingers 300g", "ITC", 199, 12, 18, 10, 220, 1, False, "snack,dinner", "Breaded fish fingers", 50),
    (3, "Basmati Rice 1kg", "India Gate", 189, 3, 78, 0.5, 350, 1, True, "lunch,dinner", "Premium aged basmati", 140),
    (3, "Brown Rice 1kg", "Daawat", 129, 3, 76, 2, 360, 3, True, "lunch,dinner,high-fiber", "Whole grain brown rice", 85),
    (3, "Quinoa 500g", "True Elements", 349, 14, 64, 6, 380, 7, True, "lunch,dinner,high-protein", "Organic quinoa", 45),
    (3, "Ragi Flour 500g", "Aashirvaad", 65, 3, 72, 1, 340, 8, True, "breakfast,high-fiber", "Finger millet flour", 70),
    (3, "Poha Thick 500g", "MTR", 45, 2, 80, 1, 350, 2, True, "breakfast,quick", "Flattened rice", 100),
    (3, "Whole Wheat Pasta 500g", "Barilla", 199, 13, 68, 2, 350, 6, True, "lunch,dinner,high-protein", "Durum wheat pasta", 65),
    (3, "Cornflakes 475g", "Kelloggs", 199, 2, 84, 0.5, 380, 3, True, "breakfast,quick", "Original cornflakes", 110),
    (3, "Muesli Fruit Nut", "Bagrrys", 349, 8, 65, 8, 400, 6, True, "breakfast,high-fiber", "Fruit and nut muesli", 75),
    (4, "Trail Mix 200g", "Nutty Gritties", 249, 8, 18, 14, 220, 4, True, "snack,high-protein", "Nuts and seeds mix", 80),
    (4, "Makhana 100g", "Farmley", 149, 9, 76, 0.5, 350, 2, True, "snack,low-calorie", "Fox nuts roasted", 90),
    (4, "Dark Chocolate 70%", "Amul", 99, 2, 22, 12, 180, 3, True, "snack", "Dark chocolate bar", 110),
    (4, "Granola Honey 400g", "Yogabar", 299, 10, 55, 12, 380, 5, True, "breakfast,snack", "Crunchy granola", 75),
    (4, "Rice Cakes Plain", "Kelloggs", 89, 2, 18, 0.5, 90, 1, True, "snack,low-calorie", "Light rice cakes", 95),
    (4, "Popcorn Microwavable", "Act II", 49, 2, 24, 8, 150, 4, True, "snack", "Butter popcorn", 180),
    (4, "Khakhra Masala", "Jain", 65, 3, 12, 2, 80, 2, True, "snack", "Gujarati khakhra", 100),
    (5, "Green Tea 25 bags", "Tetley", 149, 0, 0, 0, 0, 0, True, "beverage,low-calorie", "Antioxidant green tea", 200),
    (5, "Coconut Water 200ml", "Raw Pressery", 45, 0.5, 9, 0, 45, 0, True, "beverage,quick", "Natural coconut water", 130),
    (5, "Protein Shake RTD", "MuscleBlaze", 99, 25, 8, 3, 180, 1, True, "post-workout,beverage,high-protein", "Ready protein drink", 85),
    (5, "Orange Juice 1L", "Tropicana", 110, 1, 26, 0, 110, 0, True, "beverage,breakfast", "No pulp OJ", 70),
    (5, "Masala Chai Premix", "Tata Tea", 75, 1, 14, 2, 80, 0, True, "beverage,quick", "Instant chai mix", 160),
    (5, "Electrolyte Drink", "Gatorade", 50, 0, 14, 0, 60, 0, True, "beverage,post-workout", "Sports drink", 95),
    (5, "Cold Brew Coffee", "Sleepy Owl", 120, 1, 5, 0, 25, 0, True, "beverage,quick", "Black cold brew", 75),
    (6, "Banana 6pc", "Fresho", 49, 1.2, 27, 0.3, 105, 3, True, "breakfast,snack", "Ripe bananas", 200),
    (6, "Apple Royal Gala 4pc", "Fresho", 129, 0.5, 25, 0.3, 95, 4, True, "snack,breakfast", "Imported apples", 90),
    (6, "Broccoli 250g", "Fresho", 79, 2.8, 7, 0.4, 34, 2.5, True, "lunch,dinner,low-calorie", "Fresh broccoli", 60),
    (6, "Sweet Potato 500g", "Fresho", 45, 2, 20, 0.1, 86, 3, True, "lunch,dinner,high-fiber", "Orange sweet potato", 85),
    (6, "Avocado", "Fresho", 199, 2, 9, 15, 160, 7, True, "breakfast,snack,healthy-fats", "Ripe hass avocado", 40),
    (6, "Tomato 500g", "Fresho", 35, 1, 4, 0.2, 18, 1.2, True, "lunch,dinner,cooking", "Fresh tomatoes", 150),
    (6, "Cucumber 500g", "Fresho", 25, 0.7, 4, 0.1, 16, 0.5, True, "salad,snack,low-calorie", "Salad cucumber", 140),
]

for i in range(1, 51):
    items.append((1, f"Curd Cup {i}", "Amul", 28 + i, 3.5, 4, 3, 60, 0, True, "breakfast,snack", f"Fresh curd cup variant {i}", 40 + i))
    items.append((3, f"Atta Pack {i}kg", "Aashirvaad", 45 + i * 5, 3, 72, 1, 340, 10, True, "cooking", f"Whole wheat flour {i}kg", 35 + i))
    items.append((4, f"Biscuit Pack {i}", "Parle-G", 10 + i, 1, 8, 2, 75, 0, True, "snack,quick", f"Tea time biscuits pack {i}", 50 + i))

rows = []
for t in items:
    cat, name, brand, price, p, c, f, cal, fib, veg, tags, desc, stock = t
    veg_sql = "TRUE" if veg else "FALSE"
    rows.append(
        f"    ('{name}', '{brand}', {cat}, {price}.00, {p}, {c}, {f}, {cal}, {fib}, {veg_sql}, '{tags}', '{desc}', {stock})"
    )

sql = (
    "INSERT INTO products (name, brand, category_id, price_inr, protein_g, carbs_g, fat_g, calories, fiber_g, is_vegetarian, meal_tags, description, stock_quantity) VALUES\n"
    + ",\n".join(rows)
    + ";\n"
)

out = r"c:\Users\Hp\Downloads\grocery\src\main\resources\db\migration\V3__seed_more_products.sql"
with open(out, "w", encoding="utf-8") as f:
    f.write(sql)
print(f"Wrote {len(rows)} products to {out}")
