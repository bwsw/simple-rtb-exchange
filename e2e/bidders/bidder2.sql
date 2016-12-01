DELETE FROM bidder;
INSERT INTO bidder (name, endpoint) VALUES
    ('bidder1', 'http://{{host}}?timeout=50'),
    ('bidder2', 'http://{{host}}?timeout=200');
