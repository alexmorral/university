# mongopy-cbde

Simple Python program to insert and find data on mongoDB with pymongo

### The following schema and SQL queries have to be replicated using mongoDB

#### TPC-H SCHEMA

![](https://raw.github.com/alexmorral/mongopy-cbde/master/tpchschema.png)

#### QUERIES

**QUERY 1**
```sql
SELECT l_returnflag, l_linestatus, sum(l_quantity) as sum_qty, sum(l_extendedprice) as sum_base_price, sum(l_extendedprice*(1-l_discount)) as sum_disc_price, sum(l_extendedprice*(1-l_discount) * (1+l_tax)) as sum_charge, avg(l_quantity) as avg_qty, avg(l_extendedprice) as avg_price, avg(l_discount) as avg_disc, count( * ) as count_order
FROM lineitem WHERE l_shipdate <= '[date]'  
GROUP BY l_returnflag, l_linestatus  
ORDER BY l_returnflag, l_linestatus;
```

**QUERY 2**
```sql
SELECT s_acctbal, s_name, n_name, p_partkey, p_mfgr, s_address, s_phone, s_comment
FROM part, supplier, partsupp, nation, region  
WHERE p_partkey = ps_partkey AND s_suppkey = ps_suppkey AND p_size = [SIZE] AND p_type like '%[TYPE]' AND s_nationkey = n_nationkey AND n_regionkey = r_regionkey AND r_name = '[REGION]' AND ps_supplycost = (SELECT min(ps_supplycost) FROM partsupp, supplier, nation, region WHERE
p_partkey = ps_partkey AND s_suppkey = ps_suppkey AND s_nationkey = n_nationkey AND
n_regionkey = r_regionkey AND r_name = '[REGION]')  
ORDER BY s_acctbal desc, n_name, s_name, p_partkey;
```

**QUERY 3**
```sql
SELECT l_orderkey, sum(l_extendedprice*(1-l_discount)) as revenue, o_orderdate, o_shippriority  
FROM customer, orders, lineitem  
WHERE c_mktsegment = '[SEGMENT]' AND c_custkey = o_custkey AND l_orderkey = o_orderkey AND o_orderdate < '[DATE1]' AND l_shipdate > '[DATE2]'
GROUP BY l_orderkey, o_orderdate, o_shippriority  
ORDER BY revenue desc, o_orderdate;  
```

**QUERY 4**
```sql
SELECT n_name, sum(l_extendedprice * (1-l_discount)) as revenue  
FROM customer, orders, lineitem, supplier, nation, region  
WHERE c_custkey = o_custkey AND l_orderkey = o_orderkey AND l_suppkey = s_suppkey AND c_nationkey = s_nationkey AND s_nationkey = n_nationkey AND n_regionkey = r_regionkey AND r_name = '[REGION]' AND o_orderdate >= date '[DATE]' AND o_orderdate < date '[DATE]' + interval '1' year  
GROUP BY n_name  
ORDER BY revenue desc;
```
