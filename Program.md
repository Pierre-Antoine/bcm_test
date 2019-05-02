## SQL Query

The program was croned to run every hour.

The query to get the data is the following:
```SQL
SELECT date_format(t.date_import,'%Y-%m-%d %H') AS date_heure, t.region, t.val_temperature, t3.val_temperature AS temperature_minus3 
FROM temperatures t 
LEFT JOIN (
SELECT region, val_temperature, 
		date_format(date_add(date_import, interval 3 HOUR),'%Y-%m-%d %H') AS date_heure 
		FROM temperatures ) AS t3  
ON (t3.region = t.region AND date_format(t.date_import,'%Y-%m-%d %H') = t3.date_heure ) 
ORDER BY t.region, date_format(t.date_import,'%Y-%m-%d %H') DESC; 
```

As MySQL does not support windows functions, we have to use a self join to get the results.
