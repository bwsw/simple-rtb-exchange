To start end-to-end test execute the following command:

**make run rtb_host=\<rtb_host\> bidder_host=\<bidder_host\>**

where 
\<rtb_host\> url for rtb-exchange application,
\<bidder_host\> url for fake bidder,

e.g.

_make run rtb_host=localhost:8081 bidder_host=localhost:8000_

test_cases.txt contains pairs: **\<sql\>,\<collection\>**
