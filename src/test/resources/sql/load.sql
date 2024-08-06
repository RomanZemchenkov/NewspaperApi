INSERT INTO category(title)
VALUES ('Base_category_title');

INSERT INTO news(title, text, date_of_publication, category_id)
VALUES ('News_title','News_text',current_timestamp,(SELECT cat.id FROM category AS cat WHERE cat.title = 'Base_category_title'));