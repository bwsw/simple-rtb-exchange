DELETE FROM bidder;
INSERT INTO bidder (name, endpoint) VALUES
    ('bidder1', '{{host}}timeout=50'),
    ('bidder2', '{{host}}?timeout=200');
