delete from die_test.post;
delete from die_test.author;

insert into die_test.author(`id`, `first_name`, `last_name`, `birthday`)values(1, 'Rodrigo', 'Test 1', '1978-01-14');
insert into die_test.author(`id`, `first_name`, `last_name`, `birthday`)values(2, 'Ana', 'Test 2', '2000-07-25');
insert into die_test.author(`id`, `first_name`, `last_name`, `birthday`)values(3, 'Daniel', 'Test 3', '1995-03-11');
insert into die_test.author(`id`, `first_name`, `last_name`, `birthday`)values(4, 'Fernando', 'Test 4', '2021-08-01');
insert into die_test.author(`id`, `first_name`, `last_name`, `birthday`)values(5, 'Tayssa', 'Test 5', '2022-04-18');
insert into die_test.author(`id`, `first_name`, `last_name`, `birthday`)values(6, 'Paula', 'Test 6', '1999-01-08');
insert into die_test.author(`id`, `first_name`, `last_name`, `birthday`)values(7, 'Nica', 'Test 7', '1945-10-11');
insert into die_test.author(`id`, `first_name`, `last_name`, `birthday`)values(8, 'Nicolas', 'Test 8', '2002-10-20');
insert into die_test.author(`id`, `first_name`, `last_name`, `birthday`)values(9, 'Titi', 'Test 9', '2010-09-03');
insert into die_test.author(`id`, `first_name`, `last_name`, `birthday`)values(10, 'Izaura', 'Test 10', '2022-02-17');
insert into die_test.author(`id`, `first_name`, `last_name`, `birthday`)values(11, 'Grazi', 'Test 11', '1998-07-01');
insert into die_test.author(`id`, `first_name`, `last_name`, `birthday`)values(12, 'Ritinha', 'Test 12', '2005-12-19');
insert into die_test.author(`id`, `first_name`, `last_name`, `birthday`)values(13, 'Sofia', 'Test 13', '2006-11-27');
insert into die_test.author(`id`, `first_name`, `last_name`, `birthday`)values(14, 'Euuuuu', 'Test 14', '2018-05-22');

insert into die_test.post(`id`, `title`, `description`, `id_author`, `time_created`, `time_disabled`)values(1, 'Motocas', 'São muito legais de legais de legal e de legalzaozao', 1, now(), date_add(now(),interval 1 day));
insert into die_test.post(`id`, `title`, `description`, `id_author`, `time_created`)values(2, 'Peixe', 'Todos bem lindoes', 1, now());
insert into die_test.post(`id`, `title`, `description`, `id_author`, `time_created`)values(3, 'Carros sao legais ou nao?', 'Carros sao bem legais e vamos que vamos', 1, now());
insert into die_test.post(`id`, `title`, `description`, `id_author`, `time_created`, `time_disabled`)values(4, 'Nuvem', 'Formada pro agua e mais paranaues', 2, now(), date_add(now(),interval 2 day));
insert into die_test.post(`id`, `title`, `description`, `id_author`, `time_created`)values(5, 'Testando', 'Falando sobre testes e mais testes', 3, date_add(now(),interval 2 day));
insert into die_test.post(`id`, `title`, `description`, `id_author`, `time_created`)values(6, 'Teste Author 5', 'vinculado ao author 5', 5, date_add(now(),interval 5 day));
insert into die_test.post(`id`, `title`, `description`, `id_author`, `time_created`)values(7, 'Teste Author 5.1', 'vinculado ao author 5.1', 5, date_add(now(),interval 10 day));
insert into die_test.post(`id`, `title`, `description`, `id_author`, `time_created`)values(8, 'Teste Author 7', 'vinculado ao author 7', 7, now());