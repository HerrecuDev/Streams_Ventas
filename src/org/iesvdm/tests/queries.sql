-- 1. Devuelve un listado de todos los pedidos que se realizaron durante el año 2017,
    -- cuya cantidad total sea superior a 500€.

    SELECT * FROM pedido P WHERE YEAR(fecha) = 2017 AND total >= 500;

-- 2. Devuelve un listado con los identificadores de los clientes que NO han realizado algún pedido.
	SELECT * FROM cliente C left join pedido P on  C.id = P.id_Cliente WHERE P.id is null;

-- /**3. Devuelve el valor de la comisión de mayor valor que existe en la tabla comercial
    SELECT MAX(c.comisión) FROM comercial c

-- 4. Devuelve el identificador, nombre y primer apellido de aquellos clientes cuyo segundo apellido no es NULL.
-- El listado deberá estar ordenado alfabéticamente por apellidos y nombre.

    SELECT c.id as identificador , c.nombre as nombre_CLiente , c.apellido1 as primer_Apellido FROM cliente c WHERE c.apellido2 IS NOT NULL ORDER BY   c.apellido1 ASC , c.nombre ASC;
-- 5. Devuelve un listado con los nombres de los comerciales que terminan por "el" o "o".
    --- Tenga en cuenta que se deberán eliminar los nombres repetidos.

    select c.nombre from comercial c where c.nombre like '%el' or c.nombre like '%o';
/**
 * 6. Devuelve un listado de todos los clientes que realizaron un pedido durante el año 2017, cuya cantidad esté entre 300 € y 1000 €.
 */
select c.nombre from cliente c inner join ventas.pedido p on c.id = p.id_cliente where YEAR(p.fecha) = 2017 && p.total >= 300 && p.total <= 1000;
/**
 * 7. Calcula la media del campo total de todos los pedidos realizados por el comercial Daniel Sáez
 */
select AVG(p.total) from pedido p inner join ventas.comercial c on c.id = p.id_comercial  where c.nombre = 'Daniel' && c.apellido1 = 'Sáez';
/**
 * 8. Devuelve un listado con todos los pedidos que se han realizado.
 *  Los pedidos deben estar ordenados por la fecha de realización
 * , mostrando en primer lugar los pedidos más recientes
 */
select * from pedido p order by p.fecha desc;
/**
 * 9. Devuelve todos los datos de los dos pedidos de mayor valor.
 */
select * from pedido p  order by p.total desc limit 2;

/**
 * 10. Devuelve un listado con los identificadores de los clientes que han realizado algún pedido.
 * Tenga en cuenta que no debe mostrar identificadores que estén repetidos.
 */
select distinct (p.id_cliente) from pedido p;
/**
 * 11. Devuelve un listado con el nombre y los apellidos de los comerciales que tienen una comisión entre 0.05 y 0.11.
 *
 */
    select c.nombre , c.apellido1 from comercial c where c.comisión >= 0.05 && c.comisión <= 0.11;

    /**
 * 12. Devuelve el valor de la comisión de menor valor que existe para los comerciales.
 *
 */
    select MIN(c.comisión) from comercial c;


-- 13. Devuelve un listado de los nombres de los clientes que
-- empiezan por A y terminan por n y también los nombres que empiezan por P.
-- El listado deberá estar ordenado alfabéticamente.

select c.nombre from cliente c where c.nombre like 'A%n' or c.nombre like 'P%' order by c.nombre asc ;


/**
 * 15. Devuelve un listado de los clientes cuyo nombre no empieza por A.
 * El listado deberá estar ordenado alfabéticamente por nombre y apellidos.
 */

select c.nombre from cliente c where c.nombre not like 'A%' order by c.nombre , c.apellido1 , c.apellido2 asc;
/**
 * 16. Devuelve un listado con el identificador, nombre y los apellidos de todos
 * los clientes que han realizado algún pedido.
 * El listado debe estar ordenado alfabéticamente por apellidos y nombre y se deben eliminar los elementos repetidos.
 */
select  distinct c.id , c.nombre , c.apellido1 , c.apellido2 from cliente c inner join ventas.pedido p on c.id = p.id_cliente order by c.apellido1 , c.apellido2 , c.nombre asc;

/**
 * 17. Devuelve un listado que muestre todos los pedidos que ha realizado cada cliente.
 * El resultado debe mostrar todos los datos del cliente primero junto con un sublistado de sus pedidos.
 * El listado debe mostrar los datos de los clientes ordenados alfabéticamente por nombre y apellidos.
 * */

select c.id ,c.nombre ,c.apellido1 , c.apellido2 , p.id, p.total from cliente c join pedido p on c.id = p.id_cliente order by c.nombre , c.apellido1 , c.apellido2 asc;



/**
 * 18. Devuelve un listado que muestre todos los pedidos en los que ha participado un comercial.
 * El resultado debe mostrar todos los datos de los comerciales y el sublistado de pedidos.
 * El listado debe mostrar los datos de los comerciales ordenados alfabéticamente por apellidos.
 */

    select c.id , c.nombre , c.apellido1 , c.apellido2 , p.id , p.total from comercial c join pedido p on c.id = p.id_comercial order by c.apellido1 , c.apellido2 asc;

/**
 * 19. Devuelve el nombre y los apellidos de todos los comerciales que ha participado
 * en algún pedido realizado por María Santana Moreno.
 */
select distinct c.nombre , c.apellido1 , c.apellido2 from comercial c join pedido p on c.id = p.id_comercial where p.id_cliente = 6;

/**
 * 20. Devuelve un listado que solamente muestre los comerciales que no han realizado ningún pedido.
 */
select c.nombre from comercial c LEFT JOIN pedido p on c.id = p.id_comercial where p.id_comercial is null;
