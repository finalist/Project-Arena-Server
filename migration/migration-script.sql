drop table LOCATION_positionable;
drop table _geodb;
alter table POSITIONable rename to contentElement;
alter table location add column quest_id int8;
alter table location add column name varchar(255);
update public.location as l set quest_id = (select quest_id from (select c.location_id as location_id,c.quest_id as quest_id from contentelement as c group by c.location_id,c.quest_id) as g where g.location_id=l.id);
delete from public.location where location.quest_id is null;
alter table contentelement add column isblended boolean;
alter table contentelement add column scale float;
alter table contentelement add column x float;
alter table contentelement add column y float;
alter table contentelement add column z float;

