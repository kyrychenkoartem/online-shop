INSERT INTO users (username, email, password, birth_date, first_name, last_name, role, gender, created_at, created_by,
                   updated_at, updated_by)
VALUES ('admin', 'admin@gmail.com', 'testPassword', '1999-01-08', 'admin', 'admin', 'ADMIN', 'MALE', now(),
        'admin@gmail.com', null, null),
       ('test1', 'test1@gmail.com', 'testPassword', '2000-01-08', 'test1', 'test1', 'USER', 'MALE', now(),
        'admin@gmail.com', null, null),
       ('test2', 'test2@gmail.com', 'testPassword', '1995-01-08', 'test2', 'test2', 'USER', 'FEMALE', now(),
        'admin@gmail.com', null, null);

INSERT INTO promo_code (key, value)
VALUES ('default', 0),
       ('key2', 15),
       ('key3', 20),
       ('key4', 25),
       ('key5', 30),
       ('key6', 40),
       ('key7', 50),
       ('key8', 60);

INSERT INTO product (name, description, price, quantities, category, material)
VALUES ('Tome Hoops', 'A tubular, chubby hoop with rounded ends and a custom JB ball backing.', 100.00, 10, 'EARRINGS',
        'SILVER'),
       ('Tome Hoops', 'A tubular, chubby hoop with rounded ends and a custom JB ball backing.', 150.00, 15, 'EARRINGS',
        'GOLD'),
       ('Tome Hoops', 'A tubular, chubby hoop with rounded ends and a custom JB ball backing.', 200.00, 10, 'EARRINGS',
        'TWO_TONE'),
       ('Tome Hoops', 'A tubular, chubby hoop with rounded ends and a custom JB ball backing.', 175.00, 20, 'EARRINGS',
        'PEARL'),
       ('Tome Hoops', 'A tubular, chubby hoop with rounded ends and a custom JB ball backing.', 75.00, 5, 'EARRINGS',
        'HAND_PAINTED'),
       ('Icon Hoops',
        'Consider these your wear-with-everything piece; a classic hoop shape that never goes out of style.', 125.00,
        15, 'EARRINGS', 'SILVER'),
       ('Icon Hoops',
        'Consider these your wear-with-everything piece; a classic hoop shape that never goes out of style.', 175.00,
        13, 'EARRINGS', 'GOLD'),
       ('Icon Hoops',
        'Consider these your wear-with-everything piece; a classic hoop shape that never goes out of style.', 250.00,
        20, 'EARRINGS', 'TWO_TONE'),
       ('Icon Hoops',
        'Consider these your wear-with-everything piece; a classic hoop shape that never goes out of style.', 200.00,
        17, 'EARRINGS', 'PEARL'),
       ('Icon Hoops',
        'Consider these your wear-with-everything piece; a classic hoop shape that never goes out of style.', 100.00,
        18, 'EARRINGS', 'HAND_PAINTED'),
       ('Rafael earrings',
        'These innovative earrings explore the concept of opening the classic link shape, and wrapping the earlobe in a statement-making way.',
        200.00, 20, 'EARRINGS', 'SILVER'),
       ('Rafael earrings',
        'These innovative earrings explore the concept of opening the classic link shape, and wrapping the earlobe in a statement-making way.',
        300.00, 30, 'EARRINGS', 'GOLD'),
       ('Rafael earrings',
        'These innovative earrings explore the concept of opening the classic link shape, and wrapping the earlobe in a statement-making way.',
        400.00, 20, 'EARRINGS', 'TWO_TONE'),
       ('Rafael earrings',
        'These innovative earrings explore the concept of opening the classic link shape, and wrapping the earlobe in a statement-making way.',
        350.00, 25, 'EARRINGS', 'PEARL'),
       ('Rafael earrings',
        'These innovative earrings explore the concept of opening the classic link shape, and wrapping the earlobe in a statement-making way.',
        150.00, 30, 'EARRINGS', 'HAND_PAINTED'),
       ('Hidden Heart Jackets',
        'This grooved half circle grazes the back of earlobe leading to a sculpted heart shape that attaches to a heart stud on the front of the ear.',
        300.00, 15, 'EARRINGS', 'SILVER'),
       ('Hidden Heart Jackets',
        'This grooved half circle grazes the back of earlobe leading to a sculpted heart shape that attaches to a heart stud on the front of the ear.',
        400.00, 20, 'EARRINGS', 'GOLD'),
       ('Hidden Heart Jackets',
        'This grooved half circle grazes the back of earlobe leading to a sculpted heart shape that attaches to a heart stud on the front of the ear.',
        500.00, 20, 'EARRINGS', 'TWO_TONE'),
       ('Hidden Heart Jackets',
        'This grooved half circle grazes the back of earlobe leading to a sculpted heart shape that attaches to a heart stud on the front of the ear.',
        450.00, 15, 'EARRINGS', 'PEARL'),
       ('Hidden Heart Jackets',
        'This grooved half circle grazes the back of earlobe leading to a sculpted heart shape that attaches to a heart stud on the front of the ear.',
        200.00, 25, 'EARRINGS', 'HAND_PAINTED'),
       ('Izzabela Ring', 'A voluminous ring in a modern, tubular shape with a contoured element along the top.', 100.00,
        10, 'RINGS', 'SILVER'),
       ('Izzabela Ring', 'A voluminous ring in a modern, tubular shape with a contoured element along the top.', 200.00,
        15, 'RINGS', 'GOLD'),
       ('Izzabela Ring', 'A voluminous ring in a modern, tubular shape with a contoured element along the top.', 150.00,
        20, 'RINGS', 'TWO_TONE'),
       ('Izzabela Ring', 'A voluminous ring in a modern, tubular shape with a contoured element along the top.', 300.00,
        15, 'RINGS', 'PEARL'),
       ('Izzabela Ring', 'A voluminous ring in a modern, tubular shape with a contoured element along the top.', 150.00,
        30, 'RINGS', 'HAND_PAINTED'),
       ('Arlo Ring',
        'Boasting smooth edges and luxurious shine, this ring defies gravity with a bouyant arch shape, as if it was filled with air.',
        100.00, 20, 'RINGS', 'SILVER'),
       ('Arlo Ring',
        'Boasting smooth edges and luxurious shine, this ring defies gravity with a bouyant arch shape, as if it was filled with air.',
        200.00, 22, 'RINGS', 'GOLD'),
       ('Arlo Ring',
        'Boasting smooth edges and luxurious shine, this ring defies gravity with a bouyant arch shape, as if it was filled with air.',
        300.00, 23, 'RINGS', 'TWO_TONE'),
       ('Arlo Ring',
        'Boasting smooth edges and luxurious shine, this ring defies gravity with a bouyant arch shape, as if it was filled with air.',
        300.00, 24, 'RINGS', 'PEARL'),
       ('Arlo Ring',
        'Boasting smooth edges and luxurious shine, this ring defies gravity with a bouyant arch shape, as if it was filled with air.',
        200.00, 25, 'RINGS', 'HAND_PAINTED'),
       ('Viviana Ring',
        'Bold and comfortable, this ring boasts a voluminous quality, as if it was gently inflated with air, while curvy grooves catch the light in a unique way.',
        50.00, 20, 'RINGS', 'SILVER'),
       ('Viviana Ring',
        'Bold and comfortable, this ring boasts a voluminous quality, as if it was gently inflated with air, while curvy grooves catch the light in a unique way.',
        60.00, 20, 'RINGS', 'GOLD'),
       ('Viviana Ring',
        'Bold and comfortable, this ring boasts a voluminous quality, as if it was gently inflated with air, while curvy grooves catch the light in a unique way.',
        80.00, 25, 'RINGS', 'TWO_TONE'),
       ('Viviana Ring',
        'Bold and comfortable, this ring boasts a voluminous quality, as if it was gently inflated with air, while curvy grooves catch the light in a unique way.',
        99.00, 25, 'RINGS', 'PEARL'),
       ('Viviana Ring',
        'Bold and comfortable, this ring boasts a voluminous quality, as if it was gently inflated with air, while curvy grooves catch the light in a unique way.',
        45.00, 35, 'RINGS', 'HAND_PAINTED'),
       ('Studio Ring', 'A sculptural ring with a cool, modern feel. Designed to be worn on your pinky.', 80.00, 30,
        'RINGS', 'SILVER'),
       ('Studio Ring', 'A sculptural ring with a cool, modern feel. Designed to be worn on your pinky.', 90.00, 20,
        'RINGS', 'GOLD'),
       ('Studio Ring', 'A sculptural ring with a cool, modern feel. Designed to be worn on your pinky.', 100.00, 25,
        'RINGS', 'TWO_TONE'),
       ('Studio Ring', 'A sculptural ring with a cool, modern feel. Designed to be worn on your pinky.', 200.00, 20,
        'RINGS', 'PEARL'),
       ('Studio Ring', 'A sculptural ring with a cool, modern feel. Designed to be worn on your pinky.', 75.00, 35,
        'RINGS', 'HAND_PAINTED'),
       ('Sofia Choker',
        'A gorgeously fluid choker that boasts a ribbed texture through a unique caterpillar construction. A removable short chain extender allows you to find your preferred fit.',
        80.00, 10, 'NECKLACES', 'SILVER'),
       ('Sofia Choker',
        'A gorgeously fluid choker that boasts a ribbed texture through a unique caterpillar construction. A removable short chain extender allows you to find your preferred fit.',
        80.00, 15, 'NECKLACES', 'GOLD'),
       ('Sofia Choker',
        'A gorgeously fluid choker that boasts a ribbed texture through a unique caterpillar construction. A removable short chain extender allows you to find your preferred fit.',
        80.00, 20, 'NECKLACES', 'TWO_TONE'),
       ('Sofia Choker',
        'A gorgeously fluid choker that boasts a ribbed texture through a unique caterpillar construction. A removable short chain extender allows you to find your preferred fit.',
        80.00, 30, 'NECKLACES', 'PEARL'),
       ('Sofia Choker',
        'A gorgeously fluid choker that boasts a ribbed texture through a unique caterpillar construction. A removable short chain extender allows you to find your preferred fit.',
        80.00, 25, 'NECKLACES', 'HAND_PAINTED'),
       ('Bo Choker',
        'A statement-making piece that will turn heads. This bold and chunky curb chain choker boasts glossy oversized links, a luxurious foldover clasp and and is punctuated with an elongated plate.',
        80.00, 20, 'NECKLACES', 'SILVER'),
       ('Bo Choker',
        'A statement-making piece that will turn heads. This bold and chunky curb chain choker boasts glossy oversized links, a luxurious foldover clasp and and is punctuated with an elongated plate.',
        80.00, 21, 'NECKLACES', 'GOLD'),
       ('Bo Choker',
        'A statement-making piece that will turn heads. This bold and chunky curb chain choker boasts glossy oversized links, a luxurious foldover clasp and and is punctuated with an elongated plate.',
        80.00, 22, 'NECKLACES', 'TWO_TONE'),
       ('Bo Choker',
        'A statement-making piece that will turn heads. This bold and chunky curb chain choker boasts glossy oversized links, a luxurious foldover clasp and and is punctuated with an elongated plate.',
        80.00, 33, 'NECKLACES', 'PEARL'),
       ('Bo Choker',
        'A statement-making piece that will turn heads. This bold and chunky curb chain choker boasts glossy oversized links, a luxurious foldover clasp and and is punctuated with an elongated plate.',
        80.00, 31, 'NECKLACES', 'HAND_PAINTED'),
       ('Tome Pendant',
        'A voluminous, tubular pendant with rounded ends and light-catching curves glides from a minimalist curb chain.',
        80.00, 20, 'NECKLACES', 'SILVER'),
       ('Tome Pendant',
        'A voluminous, tubular pendant with rounded ends and light-catching curves glides from a minimalist curb chain.',
        80.00, 20, 'NECKLACES', 'GOLD'),
       ('Tome Pendant',
        'A voluminous, tubular pendant with rounded ends and light-catching curves glides from a minimalist curb chain.',
        80.00, 25, 'NECKLACES', 'TWO_TONE'),
       ('Tome Pendant',
        'A voluminous, tubular pendant with rounded ends and light-catching curves glides from a minimalist curb chain.',
        80.00, 31, 'NECKLACES', 'PEARL'),
       ('Tome Pendant',
        'A voluminous, tubular pendant with rounded ends and light-catching curves glides from a minimalist curb chain.',
        80.00, 22, 'NECKLACES', 'HAND_PAINTED'),
       ('Aria Chain',
        'A lean herringbone chain is a jewelry collection essential. This slinky and slim chain captures the light beautifully on its own, thanks to its refined lines, and layers like a dream with wider, longer chains.',
        80.00, 15, 'NECKLACES', 'SILVER'),
       ('Aria Chain',
        'A lean herringbone chain is a jewelry collection essential. This slinky and slim chain captures the light beautifully on its own, thanks to its refined lines, and layers like a dream with wider, longer chains.',
        80.00, 22, 'NECKLACES', 'GOLD'),
       ('Aria Chain',
        'A lean herringbone chain is a jewelry collection essential. This slinky and slim chain captures the light beautifully on its own, thanks to its refined lines, and layers like a dream with wider, longer chains.',
        80.00, 33, 'NECKLACES', 'TWO_TONE'),
       ('Aria Chain',
        'A lean herringbone chain is a jewelry collection essential. This slinky and slim chain captures the light beautifully on its own, thanks to its refined lines, and layers like a dream with wider, longer chains.',
        80.00, 32, 'NECKLACES', 'PEARL'),
       ('Aria Chain',
        'A lean herringbone chain is a jewelry collection essential. This slinky and slim chain captures the light beautifully on its own, thanks to its refined lines, and layers like a dream with wider, longer chains.',
        80.00, 35, 'NECKLACES', 'HAND_PAINTED'),
       ('Gia Bangle',
        'A bold, tubular bangle to wear on its own or finish your arm stack. This custom bangle opens and closes with a hinge and tab lock closure.',
        100.00, 15, 'BRACELETS', 'SILVER'),
       ('Gia Bangle',
        'A bold, tubular bangle to wear on its own or finish your arm stack. This custom bangle opens and closes with a hinge and tab lock closure.',
        150.00, 20, 'BRACELETS', 'GOLD'),
       ('Gia Bangle',
        'A bold, tubular bangle to wear on its own or finish your arm stack. This custom bangle opens and closes with a hinge and tab lock closure.',
        99.00, 25, 'BRACELETS', 'TWO_TONE'),
       ('Gia Bangle',
        'A bold, tubular bangle to wear on its own or finish your arm stack. This custom bangle opens and closes with a hinge and tab lock closure.',
        131.00, 20, 'BRACELETS', 'PEARL'),
       ('Gia Bangle',
        'A bold, tubular bangle to wear on its own or finish your arm stack. This custom bangle opens and closes with a hinge and tab lock closure.',
        154.00, 30, 'BRACELETS', 'HAND_PAINTED'),
       ('Brady Bracelet',
        'A modern chain bracelet boasting textural interest with two variations of flat links. Adjust the bracelet to show either link on the outside of your wrist.',
        88.00, 15, 'BRACELETS', 'SILVER'),
       ('Brady Bracelet',
        'A modern chain bracelet boasting textural interest with two variations of flat links. Adjust the bracelet to show either link on the outside of your wrist.',
        100.00, 16, 'BRACELETS', 'GOLD'),
       ('Brady Bracelet',
        'A modern chain bracelet boasting textural interest with two variations of flat links. Adjust the bracelet to show either link on the outside of your wrist.',
        77.00, 17, 'BRACELETS', 'TWO_TONE'),
       ('Brady Bracelet',
        'A modern chain bracelet boasting textural interest with two variations of flat links. Adjust the bracelet to show either link on the outside of your wrist.',
        98.00, 18, 'BRACELETS', 'PEARL'),
       ('Brady Bracelet',
        'A modern chain bracelet boasting textural interest with two variations of flat links. Adjust the bracelet to show either link on the outside of your wrist.',
        76.00, 19, 'BRACELETS', 'HAND_PAINTED'),
       ('Sofia Bracelet',
        'A gorgeously fluid bracelet that boasts a ribbed texture through a unique caterpillar construction.', 150.00,
        20, 'BRACELETS', 'SILVER'),
       ('Sofia Bracelet',
        'A gorgeously fluid bracelet that boasts a ribbed texture through a unique caterpillar construction.', 200.00,
        22, 'BRACELETS', 'GOLD'),
       ('Sofia Bracelet',
        'A gorgeously fluid bracelet that boasts a ribbed texture through a unique caterpillar construction.', 178.00,
        23, 'BRACELETS', 'TWO_TONE'),
       ('Sofia Bracelet',
        'A gorgeously fluid bracelet that boasts a ribbed texture through a unique caterpillar construction.', 79.00,
        24, 'BRACELETS', 'PEARL'),
       ('Sofia Bracelet',
        'A gorgeously fluid bracelet that boasts a ribbed texture through a unique caterpillar construction.', 98.00,
        25, 'BRACELETS', 'HAND_PAINTED'),
       ('Stevie Bangle',
        'Simple yet striking. This best selling, vintage-inspired bangle brings a finishing touch to your wrist',
        100.00, 23, 'BRACELETS', 'SILVER'),
       ('Stevie Bangle',
        'Simple yet striking. This best selling, vintage-inspired bangle brings a finishing touch to your wrist',
        120.00, 54, 'BRACELETS', 'GOLD'),
       ('Stevie Bangle',
        'Simple yet striking. This best selling, vintage-inspired bangle brings a finishing touch to your wrist', 99.00,
        24, 'BRACELETS', 'TWO_TONE'),
       ('Stevie Bangle',
        'Simple yet striking. This best selling, vintage-inspired bangle brings a finishing touch to your wrist', 88.00,
        22, 'BRACELETS', 'PEARL'),
       ('Stevie Bangle',
        'Simple yet striking. This best selling, vintage-inspired bangle brings a finishing touch to your wrist', 77.00,
        53, 'BRACELETS', 'HAND_PAINTED');

INSERT INTO bank_card (users_id, card_number, expiry_date, bank, cvv, card_type)
VALUES (1, '1234567890234567', '10/27', 'Bank1', '123', 'DEBIT'),
       (2, '3456789034567890', '11/27', 'Bank2', '456', 'CREDIT'),
       (3, '9876543219876543', '12/27', 'Bank3', '789', 'GIFT');





