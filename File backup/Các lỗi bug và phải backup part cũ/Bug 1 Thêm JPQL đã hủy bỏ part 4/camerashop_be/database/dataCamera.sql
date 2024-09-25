INSERT INTO `category` (`category_name`) VALUES
('Camera'),
('Lens'),
('Accessories');
INSERT INTO `products` (`avg_rating`, `description`, `discount_percent`, `list_price`, `product_name`, `quantity`, `sell_price`, `sold_quantity`) VALUES
(4.5, 'High-quality DSLR camera', 0, 1500, 'DSLR Camera X', 10, 1500, 50),
(4.2, 'Telephoto lens for professional photographers', 10, 800, 'Telephoto Lens Y', 5, 720, 20),
(4.8, 'Camera bag with waterproof material', 20, 50, 'Camera Bag Z', 20, 40, 100);
INSERT INTO `product_category` (`id_product`, `id_category`) VALUES
(1, 1),
(2, 2),
(3, 3);
INSERT INTO `user` (`activation_code`, `avatar`, `date_of_birth`, `delivery_address`, `email`, `enabled`, `first_name`, `gender`, `last_name`, `password`, `phone_number`, `username`) VALUES
(NULL, NULL, '1990-05-15', '123 Main St, Cityville', 'user1@example.com', 1, 'John', 'M', 'Doe', 'hashed_password', '123456789', 'john_doe'),
(NULL, NULL, '1985-09-20', '456 Oak St, Townville', 'user2@example.com', 1, 'Jane', 'F', 'Smith', 'hashed_password', '987654321', 'jane_smith');
INSERT INTO `cart_item` (`quantity`, `id_product`, `id_user`) VALUES
(2, 1, 1),
(1, 3, 2);
INSERT INTO `delivery` (`description`, `fee_delivery`, `name_delivery`) VALUES
('Standard delivery within 5-7 business days', 10, 'Standard'),
('Express delivery within 2-3 business days', 20, 'Express');
INSERT INTO `payment` (`description`, `fee_payment`, `name_payment`) VALUES
('PayPal payment method', 0, 'PayPal'),
('Credit card payment method', 0, 'Credit Card');
INSERT INTO `orders` (`date_created`, `delivery_address`, `fee_delivery`, `fee_payment`, `full_name`, `note`, `phone_number`, `status`, `total_price`, `total_price_product`, `id_delivery`, `id_payment`, `id_user`) VALUES
('2024-04-20', '789 Elm St, Villagetown', 10, 0, 'Alice Wonderland', 'Please deliver after 6 PM', '9876543210', 'Processing', 1550, 1500, 1, 1, 1),
('2024-04-21', '567 Pine St, Hamletville', 20, 0, 'Bob Builder', NULL, '1234567890', 'Shipped', 760, 720, 2, 2, 2);
INSERT INTO `order_detail` (`is_review`, `price`, `quantity`, `id_order`, `id_product`) VALUES
(NULL, 1500, 1, 1, 1),
(NULL, 720, 1, 2, 2);
INSERT INTO `review` (`content`, `rating_point`, `timestamp`, `id_order_detail`, `id_product`, `id_user`) VALUES
('Great camera! Very satisfied with the purchase.', 5, '2024-04-22 10:30:00', 1, 1, 1),
('Lens quality is excellent, highly recommend it.', 4.5, '2024-04-23 09:45:00', 2, 2, 2);
INSERT INTO `role` (`name_role`) VALUES
('ROLE_USER'),
('ROLE_ADMIN');
INSERT INTO `user_role` (`id_user`, `id_role`) VALUES
(1, 1),
(2, 1);
INSERT INTO `favorite_product` (`id_product`, `id_user`) VALUES
(1, 1),
(3, 2);
INSERT INTO `feedback` (`comment`, `date_created`, `is_readed`, `title`, `id_user`) VALUES
('Excellent service, thank you!', '2024-04-20', 1, 'Great Service', 1),
('Product arrived on time, very satisfied.', '2024-04-21', 1, 'Satisfied with Purchase', 2);
