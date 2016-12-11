UPDATE bidder SET deleted=true;
INSERT INTO bidder (name, endpoint) VALUES
    ('bidder1', 'http://{{host}}'),
    ('bidder2', 'http://{{host}}');
