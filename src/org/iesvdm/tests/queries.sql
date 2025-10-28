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


--