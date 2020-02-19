### What schema do we use?

We will use the following document for the queries 1, 3 and 4:
```json
{
	_id				:	Number,
	orderKey   : Number,
	orderDate  : Date,
	mktSegment : String,
	shipPriority : Number,
	lineItems  : [{
      discount      : Number,
      extendedPrice : Number,
      shipDate      : Date,
      returnFlag    : String,
      quantity      : Number,  
      lineStatus    : Number,
      tax           : Number
	}],
	regionName : String,
	nationName : String
}
```
We have chosen this structure since the three queries are very similar. In the queries 3 and 4 makes sense that the "internal" documents are LineItems as we can desist from seeking LineItems in Orders that do not meet the mktSegment or orderDate requirements. If we did the other way and a LineItem document was containing information of Orders and mktSegment, it would check each LineItem to meet the requirements, and that is not efficient.
The query 1 has to check all LineItems, we do not care that it is inside another document. We put it in the same document as the query 3 and 4 to avoid replication.  

the following one for the query 2:
```json
{
	_id				:	Number,
	suppKey    : Number,
	name       : String,
	address    : String,
	phone      : String,
	acctbal    : String,
	comment    : String,
	nationName : String,
	regionName : String,
	parts      : [{
	     partKey     : String,
       mfgr        : String,
       type        : String,
       size        : Number,
       supplyCost  : Number
	}]
}
```

As the query 2 has nothing in common with the fields that appear in other queries, we have decided to do so in a separate document.
As the two queries (external and internal) use the regionName, we thought it was appropriate that the document had in another document with information on parts because otherwise, was less efficient. Thus, the "outside" document discards documents that do not contain the desired regionName and does not check unnecessary parts.
If we did it the other way around, and was parts containing a document with information of the supplier and the region, it would check too many documents (to check the regionName) and would not be efficient.
