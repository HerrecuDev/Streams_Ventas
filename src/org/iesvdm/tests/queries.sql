-- 1. Devuelve un listado de todos los pedidos que se realizaron durante el año 2017,
    -- cuya cantidad total sea superior a 500€.

-- 2. Devuelve un listado con los identificadores de los clientes que NO han realizado algún pedido.
	SELECT * FROM cliente C left join pedido P on  C.id = P.id_Cliente WHERE P.id is null;

-- /**3. Devuelve el valor de la comisión de mayor valor que existe en la tabla comercial