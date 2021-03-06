set foreign_key_checks = 0;

lock tables city write, kitchen  write, state  write, payment_way  write, team  write, team_permission  write,
            permission  write, product  write, restaurant  write, restaurant_payment_way  write, user  write,
            user_team  write, restaurant_user  write, purchase_order  write, item_order  write,
            photo_product  write, oauth_client_details  write;

delete from city;
delete from kitchen;
delete from state;
delete from payment_way;
delete from team;
delete from team_permission;
delete from permission;
delete from product;
delete from restaurant;
delete from restaurant_payment_way;
delete from user;
delete from user_team;
delete from restaurant_user;
delete from purchase_order;
delete from item_order;
delete from photo_product;
delete from oauth_client_details;


set foreign_key_checks = 1;

alter table city auto_increment = 1;
alter table kitchen auto_increment = 1;
alter table state auto_increment = 1;
alter table payment_way auto_increment = 1;
alter table team auto_increment = 1;
alter table permission auto_increment = 1;
alter table product auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table user auto_increment = 1;
alter table purchase_order auto_increment = 1;
alter table item_order auto_increment = 1;


insert into kitchen (id, name) values (1, 'Tailandesa');
insert into kitchen (id, name) values (2, 'Indiana');
insert into kitchen (id, name) values (3, 'Argentina');
insert into kitchen (id, name) values (4, 'Brasileira');

insert into state (id, name) values (1, 'Minas Gerais');
insert into state (id, name) values (2, 'São Paulo');
insert into state (id, name) values (3, 'Ceará');

insert into city (id, name, state_id) values (1, 'Uberlândia', 1);
insert into city (id, name, state_id) values (2, 'Belo Horizonte', 1);
insert into city (id, name, state_id) values (3, 'São Paulo', 2);
insert into city (id, name, state_id) values (4, 'Campinas', 2);
insert into city (id, name, state_id) values (5, 'Fortaleza', 3);

insert into restaurant (id, name, freight_rate, kitchen_id, address_city_id, address_cep, address_street, address_number, address_district, registration_date, update_date, active, opened) values (1, 'Thai Gourmet', 10, 1, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro', utc_timestamp, utc_timestamp, true, false);
insert into restaurant (id, name, freight_rate, kitchen_id, registration_date, update_date, active, opened) values (2, 'Thai Delivery', 0, 1, utc_timestamp, utc_timestamp, true, false);
insert into restaurant (id, name, freight_rate, kitchen_id, registration_date, update_date, active, opened) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true, false);
insert into restaurant (id, name, freight_rate, kitchen_id, registration_date, update_date, active, opened) values (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true, false);
insert into restaurant (id, name, freight_rate, kitchen_id, registration_date, update_date, active, opened) values (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true, false);
insert into restaurant (id, name, freight_rate, kitchen_id, registration_date, update_date, active, opened) values (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true, false);

insert into payment_way (id, description, update_date) values (1, 'Cartão de crédito', utc_timestamp);
insert into payment_way (id, description, update_date) values (2, 'Cartão de débito', utc_timestamp);
insert into payment_way (id, description, update_date) values (3, 'Dinheiro', utc_timestamp);

insert into permission (id, name, description) values (1, 'EDIT_KITCHENS', 'Allow edit kitchens');
insert into permission (id, name, description) values (2, 'EDIT_PAYMENT_WAYS', 'allow create or edit payment ways');
insert into permission (id, name, description) values (3, 'EDIT_CITIES', 'Allow create or edit cities');
insert into permission (id, name, description) values (4, 'EDIT_STATES', 'Allow create or edit states');
insert into permission (id, name, description) values (5, 'FIND_USERS_TEAMS_PERMISSIONS', 'Allow find users, teams and permissions');
insert into permission (id, name, description) values (6, 'EDIT_USERS_TEAMS_PERMISSIONS', 'Allow create or edit users, teams and permissions');
insert into permission (id, name, description) values (7, 'EDIT_RESTAURANTS', 'Allow create, edit or manage restaurants');
insert into permission (id, name, description) values (8, 'FIND_PURCHASE_ORDERS', 'Allow find purchase orders');
insert into permission (id, name, description) values (9, 'MANAGE_PURCHASE_ORDERS', 'Allow manage purchase orders');
insert into permission (id, name, description) values (10, 'GENERATE_REPORTS', 'Allow generate reports');

insert into restaurant_payment_way (restaurant_id, payment_way_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into product (name, description, price, active, restaurant_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into product (name, description, price, active, restaurant_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);

insert into product (name, description, price, active, restaurant_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert into product (name, description, price, active, restaurant_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into product (name, description, price, active, restaurant_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert into product (name, description, price, active, restaurant_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into product (name, description, price, active, restaurant_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
insert into product (name, description, price, active, restaurant_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);
insert into product (name, description, price, active, restaurant_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert into user (name, email, password, registration_date) values ('filipe', 'filipearn@yahoo.com.br', '$2a$12$FxYCEcw3xG9acyxV89zIP.SZhCDrWkQSyAdsRpSgnC2iyWSgrfEna', utc_timestamp);
insert into user (name, email, password, registration_date) values ('jessica', 'jessicasereia@gmail.com.br', '$2a$12$FxYCEcw3xG9acyxV89zIP.SZhCDrWkQSyAdsRpSgnC2iyWSgrfEna', utc_timestamp);
insert into user (name, email, password, registration_date) values ('Marcus Vinícius', 'marcus@yahoo.com.br', '2a$12$FxYCEcw3xG9acyxV89zIP.SZhCDrWkQSyAdsRpSgnC2iyWSgrfEna', utc_timestamp);
insert into user (name, email, password, registration_date) values ('Leonardo Moyle', 'leo@yahoo.com.br', '$2a$12$FxYCEcw3xG9acyxV89zIP.SZhCDrWkQSyAdsRpSgnC2iyWSgrfEna', utc_timestamp);
insert into user (name, email, password, registration_date) values ('Joao', 'joao@yahoo.com.br', '$2a$12$FxYCEcw3xG9acyxV89zIP.SZhCDrWkQSyAdsRpSgnC2iyWSgrfEna', utc_timestamp);

insert into team (name) values ('Gerente'), ('Vendedor'), ('Secretária'), ('Cadastrador');

insert into team_permission (team_id, permission_id)
select 1, id from permission;

insert into team_permission (team_id, permission_id)
select 2, id from permission where name like 'FIND_%';

insert into team_permission (team_id, permission_id) values (2, 1);

insert into team_permission (team_id, permission_id)
select 3, id from permission where name like 'FIND_%';

insert into team_permission (team_id, permission_id)
select 4, id from permission where name like '%_RESTAURANTS' or name like '%_PRODUCTS';

insert into user_team (user_id, team_id) values (1, 1), (1, 2), (2, 2), (3, 3), (4, 4);

insert into restaurant_user (restaurant_id, user_id) values (1,2), (1,3), (5, 5);

insert into purchase_order (id, code, restaurant_id, user_client_id, payment_way_id, address_city_id, address_cep,
    address_street, address_number, address_complement, address_district,
    status, registration_date, sub_total, freight_rate, total_value)
values (1, '9197a815-51b9-4fd1-9f74-6e0c55a683d7', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
'CREATED', utc_timestamp, 298.90, 10, 308.90);

insert into item_order (id, purchase_order_id, product_id, quantity, unit_price, total_price, observation)
values (1, 1, 1, 1, 78.9, 78.9, null);

insert into item_order (id, purchase_order_id, product_id, quantity, unit_price, total_price, observation)
values (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');

insert into purchase_order (id, code, restaurant_id, user_client_id, payment_way_id, address_city_id, address_cep,
    address_street, address_number, address_complement, address_district,
    status, registration_date, sub_total, freight_rate, total_value)
values (2, '8c103b75-5eee-45ba-8d38-6babff83e644', 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
'CREATED', utc_timestamp, 79, 0, 79);

insert into item_order (id, purchase_order_id, product_id, quantity, unit_price, total_price, observation)
values (3, 2, 6, 1, 79, 79, 'Ao ponto');

insert into purchase_order (id, code, restaurant_id, user_client_id, payment_way_id, address_city_id, address_cep,
    address_street, address_number, address_complement, address_district,
    status, registration_date, confirmation_date, delivery_date, sub_total, freight_rate, total_value)
values (3, 'b5741512-8fbc-47fa-9ac1-b530354fc0ff', 1, 1, 1, 1, '38400-222', 'Rua Natal', '200', null, 'Brasil',
        'DELIVERED', '2019-10-30 21:10:00', '2019-10-30 21:10:45', '2019-10-30 21:55:44', 110, 10, 120);

insert into item_order (id, purchase_order_id, product_id, quantity, unit_price, total_price, observation)
values (4, 3, 2, 1, 110, 110, null);


insert into purchase_order (id, code, restaurant_id, user_client_id, payment_way_id, address_city_id, address_cep,
    address_street, address_number, address_complement, address_district,
    status, registration_date, confirmation_date, delivery_date, sub_total, freight_rate, total_value)
values (4, '5c621c9a-ba61-4454-8631-8aabefe58dc2', 1, 2, 1, 1, '38400-800', 'Rua Fortaleza', '900', 'Apto 504', 'Centro',
        'DELIVERED', '2019-11-02 20:34:04', '2019-11-02 20:35:10', '2019-11-02 21:10:32', 174.4, 5, 179.4);

insert into item_order (id, purchase_order_id, product_id, quantity, unit_price, total_price, observation)
values (5, 4, 3, 2, 87.2, 174.4, null);


insert into purchase_order (id, code, restaurant_id, user_client_id, payment_way_id, address_city_id, address_cep,
    address_street, address_number, address_complement, address_district,
    status, registration_date, confirmation_date, delivery_date, sub_total, freight_rate, total_value)
values (5, '8d774bcf-b238-42f3-aef1-5fb388754d63', 1, 3, 2, 1, '38400-200', 'Rua 10', '930', 'Casa 20', 'Martins',
        'DELIVERED', '2019-11-03 00:01:30', '2019-11-02 21:01:21', '2019-11-02 21:20:10', 87.2, 10, 97.2);

insert into item_order (id, purchase_order_id, product_id, quantity, unit_price, total_price, observation)
values (6, 5, 3, 1, 87.2, 87.2, null);

insert into oauth_client_details (
  client_id, resource_ids, client_secret,
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'fooddelivery-web', null, '$2y$12$w3igMjsfS5XoAYuowoH3C.54vRFWlcXSHLjX7MwF990Kc2KKKh72e',
  'READ,WRITE', 'password', null, null,
  60 * 60 * 6, 60 * 24 * 60 * 60, false
);

insert into oauth_client_details (
  client_id, resource_ids, client_secret,
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'foodanalytics', null, '$2b$10$lZcEA086JspCQOQdYeBD0elXf5eoFvpC8/q1PsxHWLw6x06xmB6iC',
  'READ,WRITE', 'authorization_code', 'http://www.foodanalytics.local:8082', null,
  null, null, false
);

insert into oauth_client_details (
  client_id, resource_ids, client_secret,
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'revenues', null, '$2y$12$fHixriC7yXX/i1/CmpnGH.RFyK/l5YapLCFOEbIktONjE8ZDykSnu',
  'READ,WRITE', 'client_credentials', null, 'FIND_PURCHASE_ORDERS,GENERATE_REPORTS',
  null, null, false
);

unlock tables;