# --- !Ups

create or replace view main_most_commentated as 
 select article.id,
       article.title,
       article.intro,
       count(*) AS count
 from article,comment
 where article.id = comment.article_id
 group by article.id
 order by count(*) desc
 limit 10;

create or replace view main_most_viewed as 
 select article.id,
       article.title,
	   article.intro,
       article.body,
       article.summary,
	   article.date,
	   article.image,
       article.views
 from article
 order by article.views desc
 limit 10;

create or replace view main_new as 
 select article.id,
       article.title,
	   article.intro,
       article.body,
       article.summary,
	   article.date,
	   article.image,
       article.views
 from article
 order by article.date desc
 limit 10;

# --- !Downs

drop view if exists main_most_commentated;

drop view if exists main_most_viewed;

drop view if exists main_new;