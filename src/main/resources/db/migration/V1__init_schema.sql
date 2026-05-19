CREATE TABLE categories (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE products (
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(200) NOT NULL,
    brand           VARCHAR(100),
    category_id     BIGINT NOT NULL REFERENCES categories(id),
    price_inr       DECIMAL(10, 2) NOT NULL,
    protein_g       DECIMAL(6, 2) DEFAULT 0,
    carbs_g         DECIMAL(6, 2) DEFAULT 0,
    fat_g           DECIMAL(6, 2) DEFAULT 0,
    calories        INT DEFAULT 0,
    fiber_g         DECIMAL(6, 2) DEFAULT 0,
    is_vegetarian   BOOLEAN DEFAULT TRUE,
    meal_tags       VARCHAR(255),
    description     TEXT,
    stock_quantity  INT DEFAULT 0,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_products_protein ON products(protein_g DESC);
CREATE INDEX idx_products_meal_tags ON products USING gin (to_tsvector('english', COALESCE(meal_tags, '')));
