INSERT INTO categories (name, description) VALUES
    ('Dairy', 'Milk, yogurt, cheese'),
    ('Eggs & Protein', 'Eggs, paneer, tofu'),
    ('Grains', 'Oats, bread, rice'),
    ('Snacks', 'Nuts, bars, chips'),
    ('Beverages', 'Juices, shakes, milk drinks'),
    ('Fresh Produce', 'Fruits and vegetables');

INSERT INTO products (name, brand, category_id, price_inr, protein_g, carbs_g, fat_g, calories, fiber_g, is_vegetarian, meal_tags, description, stock_quantity) VALUES
    ('Greek Yogurt', 'Epigamia', 1, 65.00, 10.0, 4.0, 0.5, 100, 0, TRUE, 'breakfast,snack,high-protein', 'Thick strained yogurt, high protein', 120),
    ('Whole Milk 1L', 'Amul', 1, 58.00, 3.2, 4.8, 3.2, 62, 0, TRUE, 'breakfast,beverage', 'Full-fat fresh milk', 200),
    ('Cottage Cheese 200g', 'Britannia', 2, 95.00, 14.0, 3.0, 4.5, 120, 0, TRUE, 'breakfast,lunch,high-protein', 'Low-fat paneer cubes', 80),
    ('Brown Eggs (6 pack)', 'Licious', 2, 72.00, 6.0, 0.6, 5.0, 78, 0, FALSE, 'breakfast,high-protein', 'Farm fresh brown eggs', 150),
    ('Steel Cut Oats 500g', 'Quaker', 3, 189.00, 5.0, 27.0, 3.0, 150, 4.0, TRUE, 'breakfast,high-fiber', 'Slow-release energy oats', 90),
    ('Multigrain Bread', 'Harvest Gold', 3, 55.00, 4.0, 22.0, 2.0, 130, 3.0, TRUE, 'breakfast,sandwich', '7-grain sliced loaf', 110),
    ('Roasted Almonds 200g', 'Happilo', 4, 299.00, 6.0, 6.0, 14.0, 170, 3.5, TRUE, 'snack,high-protein', 'Lightly salted almonds', 75),
    ('Protein Bar Chocolate', 'Yoga Bar', 4, 55.00, 10.0, 18.0, 8.0, 200, 2.0, TRUE, 'snack,post-workout,high-protein', '20g protein energy bar', 200),
    ('Banana Shake Mix', 'Slurrp Farm', 5, 149.00, 8.0, 24.0, 2.0, 180, 1.0, TRUE, 'breakfast,shake,quick', 'Just add milk shake powder', 60),
    ('Cold Coffee 200ml', 'Nescafe', 5, 40.00, 2.0, 12.0, 3.0, 90, 0, TRUE, 'beverage,quick', 'Ready-to-drink iced coffee', 180),
    ('Spinach 250g', 'Fresho', 6, 35.00, 2.9, 3.6, 0.4, 23, 2.2, TRUE, 'lunch,dinner,low-calorie', 'Fresh leafy spinach', 95),
    ('Chicken Breast 500g', 'Licious', 2, 249.00, 31.0, 0, 3.6, 165, 0, FALSE, 'lunch,dinner,high-protein', 'Skinless boneless breast', 45),
    ('Tofu Firm 200g', 'Urban Platter', 2, 89.00, 16.0, 2.0, 8.0, 144, 1.0, TRUE, 'lunch,dinner,high-protein,vegan', 'Plant-based protein block', 70),
    ('Peanut Butter Creamy', 'Pintola', 4, 199.00, 8.0, 6.0, 16.0, 190, 2.0, TRUE, 'breakfast,snack,high-protein', 'All-natural peanut butter', 85),
    ('Protein Oats Maple', 'MuscleBlaze', 3, 349.00, 22.0, 30.0, 6.0, 320, 5.0, TRUE, 'breakfast,post-workout,high-protein', 'Fortified high-protein oatmeal', 50);
